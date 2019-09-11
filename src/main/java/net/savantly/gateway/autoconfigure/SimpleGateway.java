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
@RequestMapping(SimpleGateway.PATH)
@SuppressWarnings({"rawtypes", "unchecked"})
public class SimpleGateway implements Gateway<byte[]> {
	
	protected static final String PATH = "/gateway";
	private static final Logger log = LoggerFactory.getLogger(SimpleGateway.class);

	private GatewayProperties config;
	private GatewayEventHandler<byte[]> eventHandler;
	
	public SimpleGateway(GatewayProperties config, GatewayEventHandler eventHandler) {
		this.config = config;
		this.eventHandler = eventHandler;
	}
	
	@GetMapping("/")
	public String imok() {
		return "imok";
	}
	
	@Override
	@GetMapping("/{child}/**")
	public ResponseEntity<byte[]> get(@PathVariable String child, ProxyExchange<byte[]> proxy) throws Exception {
		log.debug("doing GET: {}", proxy.path());
		ResponseEntity intercepted = this.eventHandler.onGet(child, proxy);
		if(null != intercepted) {
			return intercepted;
		}

		final HttpHeaders headers = new HttpHeaders();

		ResponseEntity<byte[]> response = proxy.uri(getDestinationPath(child, proxy))
				.get(r -> ResponseEntity.status(r.getStatusCode())
				.body(r.getBody()));
		response.getHeaders().forEach((h, l) -> {
			if (h.toUpperCase() != "TRANSFER-ENCODING") {
				headers.addAll(h, l);
			}
		});
		return this.eventHandler.afterGet(child, response);
	}
	
	@Override
	@PostMapping("/{child}/**")
	public ResponseEntity<byte[]> post(@PathVariable String child, ProxyExchange<byte[]> proxy) throws Exception {
		ResponseEntity intercepted = this.eventHandler.onPost(child, proxy);
		if(null != intercepted) {
			return intercepted;
		}
		ResponseEntity<byte[]> response = proxy.uri(getDestinationPath(child, proxy)).post();
		return this.eventHandler.afterPost(child, response);
	}
	
	@Override
	@PutMapping("/{child}/**")
	public ResponseEntity<byte[]> put(@PathVariable String child, ProxyExchange<byte[]> proxy) throws Exception {
		ResponseEntity intercepted = this.eventHandler.onPut(child, proxy);
		if(null != intercepted) {
			return intercepted;
		}
		ResponseEntity<byte[]> response = proxy.uri(getDestinationPath(child, proxy)).put();
		return this.eventHandler.afterPut(child, response);
	}
	
	@Override
	@RequestMapping(path = "/{child}/**", method = RequestMethod.OPTIONS)
	public ResponseEntity<byte[]> options(@PathVariable String child, ProxyExchange<byte[]> proxy) throws Exception {
		ResponseEntity intercepted = this.eventHandler.onOptions(child, proxy);
		if(null != intercepted) {
			return intercepted;
		}
		ResponseEntity<byte[]> response = proxy.uri(getDestinationPath(child, proxy)).options();
		return this.eventHandler.afterOptions(child, response);
	}
	
	@Override
	@RequestMapping(path = "/{child}/**", method = RequestMethod.PATCH)
	public ResponseEntity<byte[]> patch(@PathVariable String child, ProxyExchange<byte[]> proxy) throws Exception {
		ResponseEntity intercepted = this.eventHandler.onPatch(child, proxy);
		if(null != intercepted) {
			return intercepted;
		}
		ResponseEntity<byte[]> response = proxy.uri(getDestinationPath(child, proxy)).patch();
		return this.eventHandler.afterPatch(child, response);
	}
	
	@Override
	@RequestMapping(path = "/{child}/**", method = RequestMethod.DELETE)
	public ResponseEntity<byte[]> delete(@PathVariable String child, ProxyExchange<byte[]> proxy) throws Exception {
		ResponseEntity intercepted = this.eventHandler.onDelete(child, proxy);
		if(null != intercepted) {
			return intercepted;
		}
		ResponseEntity<byte[]> response = proxy.uri(getDestinationPath(child, proxy)).delete();
		return this.eventHandler.afterDelete(child, response);
	}
	
	@Override
	@RequestMapping(path = "/{child}/**", method = RequestMethod.HEAD)
	public ResponseEntity<byte[]> head(@PathVariable String child, ProxyExchange<byte[]> proxy) throws Exception {
		ResponseEntity intercepted = this.eventHandler.onHead(child, proxy);
		if(null != intercepted) {
			return intercepted;
		}
		ResponseEntity<byte[]> response = proxy.uri(getDestinationPath(child, proxy)).head();
		return this.eventHandler.afterHead(child, response);
	}
	
	private String getDestinationPath(String child, ProxyExchange<byte[]> proxy) {
		log.debug("getting route config for {}", child);
		String destination = this.config.getRoutes().get(child);
		log.debug("found destination: {}", destination);
		String path = proxy.path(String.format("%s/%s", PATH, child));
		log.debug("with prefix removed: {}", path);
		String uriString = String.format("%s%s", destination, path);
		log.debug("full redirect uri: {}", uriString);
		return uriString;
	}
}
