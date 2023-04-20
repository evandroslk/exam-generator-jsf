package br.com.devdojo.examgenerator.persistence.dao;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.devdojo.examgenerator.annotation.ExceptionHandler;
import br.com.devdojo.examgenerator.persistence.model.Professor;
import br.com.devdojo.examgenerator.util.JsonUtil;

public class ProfessorDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String BASE_URL = "http://localhost:8085/v1/professor";

	private final JsonUtil jsonUtil;

	@Inject
	public ProfessorDAO(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}

	@ExceptionHandler
	public Professor getProfessorById(long id) {
		ResponseEntity<Professor> professorEntity = new RestTemplate()
				.exchange(BASE_URL+"/1", HttpMethod.GET,
						new HttpEntity<>(jsonUtil.createTokenizedHeader()), Professor.class);
		Professor professor = professorEntity.getBody();
		return professor;
	}

}
