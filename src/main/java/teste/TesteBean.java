package teste;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.devdojo.examgenerator.persistence.dao.LoginDAO;
import br.com.devdojo.examgenerator.persistence.model.Token;

@Named
@ViewScoped
public class TesteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final LoginDAO loginDAO;

	private String message = "Working";

	@Inject
	public TesteBean(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
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

}
