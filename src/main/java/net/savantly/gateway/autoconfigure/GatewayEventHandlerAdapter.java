package net.savantly.gateway.autoconfigure;

import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;

public class GatewayEventHandlerAdapter implements GatewayEventHandler<byte[]> {

	@Override
	public ResponseEntity onGet(String child, ProxyExchange<byte[]> proxy) throws Exception {
		return null;
	}

	@Override
	public ResponseEntity onPost(String child, ProxyExchange<byte[]> proxy) throws Exception {
		return null;
	}

	@Override
	public ResponseEntity onPut(String child, ProxyExchange<byte[]> proxy) throws Exception {
		return null;
	}

	@Override
	public ResponseEntity onOptions(String child, ProxyExchange<byte[]> proxy) throws Exception {
		return null;
	}

	@Override
	public ResponseEntity onPatch(String child, ProxyExchange<byte[]> proxy) throws Exception {
		return null;
	}

	@Override
	public ResponseEntity onDelete(String child, ProxyExchange<byte[]> proxy) throws Exception {
		return null;
	}

	@Override
	public ResponseEntity onHead(String child, ProxyExchange<byte[]> proxy) throws Exception {
		return null;
	}

	@Override
	public ResponseEntity afterGet(String child, ResponseEntity<byte[]> proxy) throws Exception {
		return proxy;
	}

	@Override
	public ResponseEntity afterPost(String child, ResponseEntity<byte[]> proxy) throws Exception {
		return proxy;
	}

	@Override
	public ResponseEntity afterPut(String child, ResponseEntity<byte[]> proxy) throws Exception {
		return proxy;
	}

	@Override
	public ResponseEntity afterOptions(String child, ResponseEntity<byte[]> proxy) throws Exception {
		return proxy;
	}

	@Override
	public ResponseEntity afterPatch(String child, ResponseEntity<byte[]> proxy) throws Exception {
		return proxy;
	}

	@Override
	public ResponseEntity afterDelete(String child, ResponseEntity<byte[]> proxy) throws Exception {
		return proxy;
	}

	@Override
	public ResponseEntity afterHead(String child, ResponseEntity<byte[]> proxy) throws Exception {
		return proxy;
	}

}
