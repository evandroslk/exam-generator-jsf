package br.com.devdojo.examgenerator.persistence.dao;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import br.com.devdojo.examgenerator.annotation.ExceptionHandler;
import br.com.devdojo.examgenerator.custom.CustomRestTemplate;
import br.com.devdojo.examgenerator.persistence.model.support.Token;
import br.com.devdojo.examgenerator.util.JsonUtil;

public class LoginDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private final CustomRestTemplate restTemplate;

	private JsonUtil jsonUtil;

	private final String BASE_URL = "http://localhost:8085/login";

	@Inject
	public LoginDAO(CustomRestTemplate restTemplate, JsonUtil jsonUtil) {
		this.restTemplate = restTemplate;
		this.jsonUtil = jsonUtil;
	}

	@ExceptionHandler
	public Token loginReturningToken(String username, String password) {
		String loginJson = "{\"username\":" + addQuotes(username) + ",\"password\":" + addQuotes(password) + "}";
		ResponseEntity<Token> tokenExchange = restTemplate.exchange(BASE_URL, HttpMethod.POST,
				new HttpEntity<>(loginJson, jsonUtil.createJsonHeader()), Token.class);
		return tokenExchange.getBody();
	}

	private String addQuotes(String value) {
		return new StringBuilder(300).append("\"").append(value).append("\"").toString();
	}

}
