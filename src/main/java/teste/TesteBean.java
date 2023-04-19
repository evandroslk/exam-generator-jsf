package teste;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.devdojo.examgenerator.persistence.dao.LoginDAO;
import br.com.devdojo.examgenerator.persistence.dao.ProfessorDAO;
import br.com.devdojo.examgenerator.persistence.model.Professor;
import br.com.devdojo.examgenerator.persistence.model.support.Token;

@Named
@ViewScoped
public class TesteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final LoginDAO loginDAO;

	private final ProfessorDAO professorDAO;

	private String message = "Working";

	@Inject
	public TesteBean(LoginDAO loginDAO, ProfessorDAO professorDAO) {
		this.loginDAO = loginDAO;
		this.professorDAO = professorDAO;
	}

	public void login() {
		Token token = loginDAO.loginReturningToken("evandro", "devdojo");
		System.out.println(token.getToken());
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void checkProfessor() {
		Professor professor = professorDAO.getProfessorById(1L);
		System.out.println(professor.getName());
	}

}
