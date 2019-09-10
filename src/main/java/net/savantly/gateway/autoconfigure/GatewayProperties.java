package net.savantly.gateway.autoconfigure;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("gateway")
public class GatewayProperties {
	
	private Map<String, String> routes = new HashMap<String, String>();

	public Map<String, String> getRoutes() {
		return routes;
	}

	public void setRoutes(Map<String, String> routes) {
		this.routes = routes;
	}
	
	public void addRoute(String key, String value) {
		this.routes.put(key, value);
	}
}
