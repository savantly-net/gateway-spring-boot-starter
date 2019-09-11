package net.savantly.gateway.autoconfigure;

import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;

public interface Gateway<T> {

	ResponseEntity<T> get(String child, ProxyExchange<T> proxy) throws Exception;

	ResponseEntity<T> post(String child, ProxyExchange<T> proxy) throws Exception;

	ResponseEntity<T> put(String child, ProxyExchange<T> proxy) throws Exception;

	ResponseEntity<T> options(String child, ProxyExchange<T> proxy) throws Exception;

	ResponseEntity<T> patch(String child, ProxyExchange<T> proxy) throws Exception;

	ResponseEntity<T> delete(String child, ProxyExchange<T> proxy) throws Exception;

	ResponseEntity<T> head(String child, ProxyExchange<T> proxy) throws Exception;

}