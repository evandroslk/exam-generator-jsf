package br.com.devdojo.examgenerator.persistence.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.devdojo.examgenerator.annotation.ExceptionHandler;
import br.com.devdojo.examgenerator.custom.CustomRestTemplate;
import br.com.devdojo.examgenerator.custom.CustomTypeReference;
import br.com.devdojo.examgenerator.persistence.model.Question;
import br.com.devdojo.examgenerator.util.APIUtil;
import br.com.devdojo.examgenerator.util.JsonUtil;

public class QuestionDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String LIST_URL = APIUtil.BASE_URL + "/professor/course/question/list/{courseId}/";
	private final String DELETE_OR_FIND_ONE_URL = APIUtil.BASE_URL + "/professor/course/question/{id}";
	private final String CREATE_UPDATE_URL = APIUtil.BASE_URL + "/professor/course/question";
	
	private final CustomRestTemplate restTemplate;
	private final JsonUtil jsonUtil;
	private final CustomTypeReference<List<Question>> listQuestionTypeReference;
	
	@Inject
	public QuestionDAO(CustomRestTemplate restTemplate, JsonUtil jsonUtil, 
			CustomTypeReference<List<Question>> listQuestionTypeReference) {
		this.restTemplate = restTemplate;
		this.jsonUtil = jsonUtil;
		this.listQuestionTypeReference = listQuestionTypeReference;
	}
	
	@ExceptionHandler
	public List<Question> list(long courseId, String title) {
		UriComponents url = UriComponentsBuilder.fromUriString(LIST_URL).queryParam("title", title).build();
		return restTemplate.exchange(url.toUriString(), HttpMethod.GET, 
				jsonUtil.tokenizedEntityHeader(), 
				listQuestionTypeReference.typeReference(), courseId).getBody();
	}

	@ExceptionHandler
	public Question findOne(long id) {
		return restTemplate.exchange(DELETE_OR_FIND_ONE_URL, HttpMethod.GET,
				jsonUtil.tokenizedEntityHeader(), Question.class, id).getBody();
	}
	
	public Question update(Question question) {
		return createOrUpdate(HttpMethod.PUT, question);
	}

	public Question create(Question question) {
		return createOrUpdate(HttpMethod.POST, question);
	}
	
	private Question createOrUpdate(HttpMethod httpMethod, Question question) {
		return restTemplate.exchange(CREATE_UPDATE_URL, httpMethod,
				jsonUtil.tokenizedEntityHeader(question), Question.class).getBody();
	}

	public void delete(Question question) {
		restTemplate.exchange(DELETE_OR_FIND_ONE_URL, HttpMethod.DELETE,
				jsonUtil.tokenizedEntityHeader(question), Question.class, question.getId());
	}

}
