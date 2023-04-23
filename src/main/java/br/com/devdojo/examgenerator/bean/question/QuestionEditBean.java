package br.com.devdojo.examgenerator.bean.question;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import br.com.devdojo.examgenerator.annotation.ExceptionHandler;
import br.com.devdojo.examgenerator.persistence.dao.QuestionDAO;
import br.com.devdojo.examgenerator.persistence.model.Question;

@Named
@ViewScoped
public class QuestionEditBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final QuestionDAO questionDAO;
	private Question question;
	private long questionID;

	@Inject
	public QuestionEditBean(QuestionDAO questionDAO) {
		this.questionDAO = questionDAO;
	}

	@ExceptionHandler
	public void init() {
		question = questionDAO.findOne(questionID);
	}

	@ExceptionHandler
	public String update() {
		questionDAO.update(question);
		Messages.create("The question {0} was successfully updated", question.getTitle()).flash().add();
		return "list.xhtml?faces-redirect=true&courseId=" + question.getCourse().getId();
	}
	
	@ExceptionHandler
	public String delete() {
		questionDAO.delete(question);
		Messages.create("The question {0} was successfully deleted", question.getTitle()).flash().add();
		return "list.xhtml?faces-redirect=true&courseId=" + question.getCourse().getId();
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public long getQuestionID() {
		return questionID;
	}

	public void setQuestionID(long questionID) {
		this.questionID = questionID;
	}

}
