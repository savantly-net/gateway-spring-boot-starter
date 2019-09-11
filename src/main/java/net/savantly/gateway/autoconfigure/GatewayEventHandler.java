package net.savantly.gateway.autoconfigure;

import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;

public interface GatewayEventHandler<T> {
	
	ResponseEntity<T> onGet(String child, ProxyExchange<T> proxy) throws Exception;

	ResponseEntity<T> onPost(String child, ProxyExchange<T> proxy) throws Exception;

	ResponseEntity<T> onPut(String child, ProxyExchange<T> proxy) throws Exception;

	ResponseEntity<T> onOptions(String child, ProxyExchange<T> proxy) throws Exception;

	ResponseEntity<T> onPatch(String child, ProxyExchange<T> proxy) throws Exception;

	ResponseEntity<T> onDelete(String child, ProxyExchange<T> proxy) throws Exception;

	ResponseEntity<T> onHead(String child, ProxyExchange<T> proxy) throws Exception;
	
	ResponseEntity<T> afterGet(String child, ResponseEntity<T> response) throws Exception;

	ResponseEntity<T> afterPost(String child, ResponseEntity<T> response) throws Exception;

	ResponseEntity<T> afterPut(String child, ResponseEntity<T> response) throws Exception;

	ResponseEntity<T> afterOptions(String child, ResponseEntity<T> response) throws Exception;

	ResponseEntity<T> afterPatch(String child, ResponseEntity<T> response) throws Exception;

	ResponseEntity<T> afterDelete(String child, ResponseEntity<T> response) throws Exception;

	ResponseEntity<T> afterHead(String child, ResponseEntity<T> response) throws Exception;

}
