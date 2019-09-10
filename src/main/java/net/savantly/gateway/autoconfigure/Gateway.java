package net.savantly.gateway.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(Gateway.PATH)
public class Gateway {
	
	protected static final String PATH = "/gateway";
	private static final Logger log = LoggerFactory.getLogger(Gateway.class);

	private GatewayProperties config;

	public Gateway(GatewayProperties config) {
		this.config = config;
	}
	
	@GetMapping("/{child}/**")
	public ResponseEntity<?> get(@PathVariable String child, ProxyExchange<byte[]> proxy) throws Exception {
		log.debug("doing GET: {}", proxy.path());
		final HttpHeaders headers = new HttpHeaders();

		ResponseEntity<Object> response = proxy.uri(getDestinationPath(child, proxy))
				.get(r -> ResponseEntity.status(r.getStatusCode())
				.body(r.getBody()));
		response.getHeaders().forEach((h, l) -> {
			if (h.toUpperCase() != "TRANSFER-ENCODING") {
				headers.addAll(h, l);
			}
		});
		return response;
	}
	
	@PostMapping("/{child}/**")
	public ResponseEntity<?> post(@PathVariable String child, ProxyExchange<byte[]> proxy) throws Exception {
		log.debug("doing GET: {}", proxy.path());
		return proxy.uri(getDestinationPath(child, proxy)).post();
	}
	
	@PutMapping("/{child}/**")
	public ResponseEntity<?> put(@PathVariable String child, ProxyExchange<byte[]> proxy) throws Exception {
		log.debug("doing GET: {}", proxy.path());
		return proxy.uri(getDestinationPath(child, proxy)).put();
	}
	
	@RequestMapping(path = "/{child}/**", method = RequestMethod.OPTIONS)
	public ResponseEntity<?> options(@PathVariable String child, ProxyExchange<byte[]> proxy) throws Exception {
		log.debug("doing GET: {}", proxy.path());
		return proxy.uri(getDestinationPath(child, proxy)).options();
	}
	
	@RequestMapping(path = "/{child}/**", method = RequestMethod.PATCH)
	public ResponseEntity<?> patch(@PathVariable String child, ProxyExchange<byte[]> proxy) throws Exception {
		log.debug("doing GET: {}", proxy.path());
		return proxy.uri(getDestinationPath(child, proxy)).patch();
	}
	
	@RequestMapping(path = "/{child}/**", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable String child, ProxyExchange<byte[]> proxy) throws Exception {
		log.debug("doing GET: {}", proxy.path());
		return proxy.uri(getDestinationPath(child, proxy)).delete();
	}
	
	@RequestMapping(path = "/{child}/**", method = RequestMethod.HEAD)
	public ResponseEntity<?> head(@PathVariable String child, ProxyExchange<byte[]> proxy) throws Exception {
		log.debug("doing GET: {}", proxy.path());
		return proxy.uri(getDestinationPath(child, proxy)).head();
	}
	
	private String getDestinationPath(String child, ProxyExchange<byte[]> proxy) {
		String destination = this.config.getRoutes().get(child);
		String path = proxy.path(String.format("%s/%s", PATH, child));
		log.debug("with prefix removed: {}", path);
		return String.format("%s%s", destination, path);
	}
}
