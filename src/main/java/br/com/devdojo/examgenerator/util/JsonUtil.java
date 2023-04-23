package br.com.devdojo.examgenerator.util;

import java.io.Serializable;
import java.util.Map;

import javax.faces.annotation.RequestCookieMap;
import javax.inject.Inject;
import javax.servlet.http.Cookie;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import br.com.devdojo.examgenerator.custom.CustomURLEncoder;

public class JsonUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@RequestCookieMap
	private Map<String, Object> cookieMap;

	public HttpHeaders createJsonHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return headers;
	}

	public HttpHeaders createTokenizedHeader() {
		HttpHeaders headers = createJsonHeader();
		Cookie tokenCookie = (Cookie) cookieMap.get("token");
		headers.add("Authorization", CustomURLEncoder.decodeUTF8(tokenCookie.getValue()));
		return headers;
	}

	public HttpEntity<?> tokenizedEntityHeader() {
		return new HttpEntity<>(createTokenizedHeader());
	}

	public <E> HttpEntity<E> tokenizedEntityHeader(E e) {
		return new HttpEntity<>(e, createTokenizedHeader());
	}
}
