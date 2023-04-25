package br.com.devdojo.examgenerator.bean.assignment;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.devdojo.examgenerator.persistence.dao.AssignmentDAO;
import br.com.devdojo.examgenerator.persistence.dao.CourseDAO;
import br.com.devdojo.examgenerator.persistence.model.Assignment;
import br.com.devdojo.examgenerator.persistence.model.Course;

@Named
@ViewScoped
public class AssignmentListBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final AssignmentDAO assignmentDAO;
	private final CourseDAO courseDAO;
	private List<Assignment> assignmentList;
	private String title = "";
	private long courseId;
	private Course course;

	@Inject
	public AssignmentListBean(AssignmentDAO assignmentDAO, CourseDAO courseDAO) {
		this.assignmentDAO = assignmentDAO;
		this.courseDAO = courseDAO;
	}

	public void init() {
		course = courseDAO.findOne(courseId);
		search();
	}

	public void search() {
		assignmentList = assignmentDAO.list(courseId, title);
	}

	public List<Assignment> getAssignmentList() {
		return assignmentList;
	}

	public void setAssignmentList(List<Assignment> assignmentList) {
		this.assignmentList = assignmentList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
