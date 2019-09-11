package net.savantly.gateway.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnWebApplication
@ConditionalOnProperty(name = "gateway.enabled", matchIfMissing = true)
@Configuration
@EnableConfigurationProperties(GatewayProperties.class)
public class GatewayAutoConfiguration {
	
	@Bean
	@ConditionalOnMissingBean
	public Gateway gateway(GatewayProperties config, GatewayEventHandler eventHandler) {
		return new SimpleGateway(config, eventHandler);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public GatewayEventHandler eventHandler() {
		return new GatewayEventHandlerAdapter();
	}
}
