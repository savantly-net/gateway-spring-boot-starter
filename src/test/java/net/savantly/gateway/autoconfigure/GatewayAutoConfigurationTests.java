package net.savantly.gateway.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class GatewayAutoConfigurationTests {


	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(GatewayAutoConfiguration.class));

	@Test
	public void gatewayCanBeDisabled() {
		this.contextRunner.withPropertyValues("gateway.enabled=false").run((context) -> {
			assertThat(context).doesNotHaveBean(GatewayAutoConfiguration.class);
			assertThat(context).doesNotHaveBean(SimpleGateway.class);
			assertThat(context).doesNotHaveBean(GatewayProperties.class);
		});
	}

	@Test
	public void gatewayIsEnabled() {
		this.contextRunner.withInitializer(new ConfigFileApplicationContextInitializer()).run((context) -> {
			assertThat(context).hasSingleBean(GatewayAutoConfiguration.class);
			assertThat(context).hasSingleBean(SimpleGateway.class);
			assertThat(context).hasSingleBean(GatewayProperties.class);
			GatewayProperties gatewayProperties = context.getBean(GatewayProperties.class);
			assertThat(gatewayProperties.getRoutes().size()).isGreaterThan(0);

		});
	}

}
