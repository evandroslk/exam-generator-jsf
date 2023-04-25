package br.com.devdojo.examgenerator.persistence.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.devdojo.examgenerator.annotation.ExceptionHandler;
import br.com.devdojo.examgenerator.custom.CustomRestTemplate;
import br.com.devdojo.examgenerator.persistence.model.Assignment;
import br.com.devdojo.examgenerator.util.APIUtil;
import br.com.devdojo.examgenerator.util.JsonUtil;

public class AssignmentDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String LIST_URL = APIUtil.BASE_URL + "/professor/course/assignment/list/{courseId}/";
	private final String DELETE_OR_FIND_ONE_URL = APIUtil.BASE_URL + "/professor/course/assignment/{id}";
	private final String CREATE_UPDATE_URL = APIUtil.BASE_URL + "/professor/course/assignment";
	
	private final CustomRestTemplate restTemplate;
	private final JsonUtil jsonUtil;
	private final ParameterizedTypeReference<List<Assignment>> assignmentListTypeReference = 
			new ParameterizedTypeReference<List<Assignment>>() {};
	
	@Inject
	public AssignmentDAO(CustomRestTemplate restTemplate, JsonUtil jsonUtil) {
		this.restTemplate = restTemplate;
		this.jsonUtil = jsonUtil;
	}
	
	@ExceptionHandler
	public List<Assignment> list(long courseId, String title) {
		UriComponents url = UriComponentsBuilder.fromUriString(LIST_URL).queryParam("title", title).build();
		return restTemplate.exchange(url.toUriString(), HttpMethod.GET, 
				jsonUtil.tokenizedEntityHeader(), 
				assignmentListTypeReference, courseId).getBody();
	}

	@ExceptionHandler
	public Assignment findOne(long id) {
		return restTemplate.exchange(DELETE_OR_FIND_ONE_URL, HttpMethod.GET,
				jsonUtil.tokenizedEntityHeader(), Assignment.class, id).getBody();
	}
	
	public Assignment update(Assignment assignment) {
		return createOrUpdate(HttpMethod.PUT, assignment);
	}

	public Assignment create(Assignment assignment) {
		return createOrUpdate(HttpMethod.POST, assignment);
	}
	
	private Assignment createOrUpdate(HttpMethod httpMethod, Assignment assignment) {
		return restTemplate.exchange(CREATE_UPDATE_URL, httpMethod,
				jsonUtil.tokenizedEntityHeader(assignment), Assignment.class).getBody();
	}

	public void delete(Assignment assignment) {
		restTemplate.exchange(DELETE_OR_FIND_ONE_URL, HttpMethod.DELETE,
				jsonUtil.tokenizedEntityHeader(assignment), Assignment.class, assignment.getId());
	}

}
