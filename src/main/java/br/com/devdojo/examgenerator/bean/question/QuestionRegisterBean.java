package br.com.devdojo.examgenerator.bean.question;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import br.com.devdojo.examgenerator.annotation.ExceptionHandler;
import br.com.devdojo.examgenerator.persistence.dao.CourseDAO;
import br.com.devdojo.examgenerator.persistence.dao.QuestionDAO;
import br.com.devdojo.examgenerator.persistence.model.Course;
import br.com.devdojo.examgenerator.persistence.model.Question;

@Named
@ViewScoped
public class QuestionRegisterBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final CourseDAO courseDAO;
	private final QuestionDAO questionDAO;
	private Course course;
	private Question question = new Question();
	private long courseID;

	@Inject
	public QuestionRegisterBean(CourseDAO courseDAO, QuestionDAO questionDAO) {
		this.courseDAO = courseDAO;
		this.questionDAO = questionDAO;
	}
	
	@ExceptionHandler
	public void save() {
		course = courseDAO.findOne(courseID);
		question.setCourse(course);
		questionDAO.create(question);
		Messages.addGlobalInfo("The question {0} was successfully added", question.getTitle());
		question = new Question();
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public long getCourseID() {
		return courseID;
	}

	public void setCourseID(long courseID) {
		this.courseID = courseID;
	}

}
