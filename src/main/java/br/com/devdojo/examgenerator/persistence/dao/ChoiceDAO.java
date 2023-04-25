package br.com.devdojo.examgenerator.persistence.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import br.com.devdojo.examgenerator.custom.CustomRestTemplate;
import br.com.devdojo.examgenerator.persistence.model.Choice;
import br.com.devdojo.examgenerator.persistence.model.Question;
import br.com.devdojo.examgenerator.util.APIUtil;
import br.com.devdojo.examgenerator.util.JsonUtil;

public class ChoiceDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String LIST_URL = APIUtil.BASE_URL + "/professor/course/question/choice/list/{questionId}/";
	private final String DELETE_OR_FIND_ONE_URL = APIUtil.BASE_URL + "/professor/course/question/choice/{id}";
	private final String CREATE_UPDATE_URL = APIUtil.BASE_URL + "/professor/course/question/choice";

	private final CustomRestTemplate restTemplate;
	private final JsonUtil jsonUtil;
	private final ParameterizedTypeReference<List<Choice>> choiceListTypeReference = 
			new ParameterizedTypeReference<List<Choice>>() {};

	@Inject
	public ChoiceDAO(CustomRestTemplate restTemplate, JsonUtil jsonUtil) {
		this.restTemplate = restTemplate;
		this.jsonUtil = jsonUtil;
	}
	
	public List<Choice> list(long questionId) {
		return restTemplate.exchange(LIST_URL, HttpMethod.GET, 
				jsonUtil.tokenizedEntityHeader(), 
				choiceListTypeReference, questionId).getBody();
	}
	
	public Choice create(Choice choice) {
		return createOrUpdate(HttpMethod.POST, choice);
	}
	
	private Choice createOrUpdate(HttpMethod httpMethod, Choice choice) {
		return restTemplate.exchange(CREATE_UPDATE_URL, httpMethod,
				jsonUtil.tokenizedEntityHeader(choice), Choice.class).getBody();
	}
	
	public Choice update(Choice choice) {
		return createOrUpdate(HttpMethod.PUT, choice);
	}
	
	public void delete(Choice choice) {
		restTemplate.exchange(DELETE_OR_FIND_ONE_URL, HttpMethod.DELETE,
				jsonUtil.tokenizedEntityHeader(choice), Question.class, choice.getId());
	}


}
