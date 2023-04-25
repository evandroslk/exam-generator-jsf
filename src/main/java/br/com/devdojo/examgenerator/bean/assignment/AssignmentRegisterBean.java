package br.com.devdojo.examgenerator.bean.assignment;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import br.com.devdojo.examgenerator.annotation.ExceptionHandler;
import br.com.devdojo.examgenerator.persistence.dao.AssignmentDAO;
import br.com.devdojo.examgenerator.persistence.dao.CourseDAO;
import br.com.devdojo.examgenerator.persistence.model.Assignment;
import br.com.devdojo.examgenerator.persistence.model.Course;

@Named
@ViewScoped
public class AssignmentRegisterBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final CourseDAO courseDAO;
	private final AssignmentDAO assignmentDAO;
	private Course course;
	private Assignment assignment = new Assignment();
	private long courseID;

	@Inject
	public AssignmentRegisterBean(CourseDAO courseDAO, AssignmentDAO assignmentDAO) {
		this.courseDAO = courseDAO;
		this.assignmentDAO = assignmentDAO;
	}

	@ExceptionHandler
	public void save() {
		course = courseDAO.findOne(courseID);
		assignment.setCourse(course);
		assignmentDAO.create(assignment);
		Messages.addGlobalInfo("The assigment {0} was successfully added", assignment.getTitle());
		assignment = new Assignment();
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public long getCourseID() {
		return courseID;
	}

	public void setCourseID(long courseID) {
		this.courseID = courseID;
	}

}
