package net.savantly.gateway.autoconfigure;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@RunWith(SpringRunner.class)
@WebMvcTest(SimpleGateway.class)
public class SimpleGatewayTests  {
	private static final Logger log = LoggerFactory.getLogger(SimpleGatewayTests.class);

	@Autowired
	private MockMvc mvc;
	// coutdown the number of interceptions by the handler
	private static CountDownLatch latch = new CountDownLatch(2);

	@Test
	public void testGet() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/gateway/").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		latch.await(5, TimeUnit.SECONDS);
	}

	@SpringBootApplication
	@Import(GatewayAutoConfiguration.class)
	public static class TestConfig {
		
		@Bean
		public GatewayEventHandler gatewayEventHandler() {
			return new GatewayEventHandlerAdapter() {
				
				@Override
				public ResponseEntity onGet(String child, ProxyExchange<byte[]> proxy) throws Exception {
					log.info("intercepted in onGet()");
					latch.countDown();
					return super.onGet(child, proxy);
				}
				
				@Override
				public ResponseEntity afterGet(String child, ResponseEntity<byte[]> proxy) throws Exception {
					log.info("intercepted in afterGet()");
					latch.countDown();
					return super.afterGet(child, proxy);
				}
				
			};
		}

	}

}