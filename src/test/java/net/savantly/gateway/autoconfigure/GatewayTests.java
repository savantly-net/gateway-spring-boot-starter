package net.savantly.gateway.autoconfigure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(Gateway.class)
public class GatewayTests {

	@Autowired
	MockMvc mvc;

	@Test
	public void testGet() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/gateway/google/").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@SpringBootApplication(scanBasePackageClasses = GatewayAutoConfiguration.class)
	public static class TestConfig {
		
		@Bean
		@ConditionalOnMissingBean
		@ConfigurationProperties("gateway")
		public GatewayProperties gatewayProperties() {
			return new GatewayProperties();
		}


	}

}
