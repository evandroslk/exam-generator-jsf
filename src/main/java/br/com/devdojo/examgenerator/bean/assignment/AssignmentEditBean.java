package br.com.devdojo.examgenerator.bean.assignment;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import br.com.devdojo.examgenerator.annotation.ExceptionHandler;
import br.com.devdojo.examgenerator.persistence.dao.AssignmentDAO;
import br.com.devdojo.examgenerator.persistence.model.Assignment;

@Named
@ViewScoped
public class AssignmentEditBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final AssignmentDAO assignmentDAO;
	private Assignment assignment;
	private long assignmentID;

	@Inject
	public AssignmentEditBean(AssignmentDAO assignmentDAO) {
		this.assignmentDAO = assignmentDAO;
	}

	@ExceptionHandler
	public void init() {
		assignment = assignmentDAO.findOne(assignmentID);
	}

	@ExceptionHandler
	public String update() {
		assignmentDAO.update(assignment);
		Messages.create("The assigment {0} was successfully updated", assignment.getTitle()).flash().add();
		return "list.xhtml?faces-redirect=true&courseId=" + assignment.getCourse().getId();
	}

	@ExceptionHandler
	public String delete() {
		assignmentDAO.delete(assignment);
		Messages.create("The assignment {0} was successfully deleted", assignment.getTitle()).flash().add();
		return "list.xhtml?faces-redirect=true&courseId=" + assignment.getCourse().getId();
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public long getAssignmentID() {
		return assignmentID;
	}

	public void setAssignmentID(long assignmentID) {
		this.assignmentID = assignmentID;
	}

}
