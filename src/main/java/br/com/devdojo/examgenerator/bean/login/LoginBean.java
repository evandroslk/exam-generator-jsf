package br.com.devdojo.examgenerator.bean.login;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.devdojo.examgenerator.custom.CustomURLEncoder;
import br.com.devdojo.examgenerator.persistence.dao.LoginDAO;
import br.com.devdojo.examgenerator.persistence.model.support.Token;

@Named
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private final LoginDAO loginDAO;
	private final ExternalContext externalContext;

	@Inject
	public LoginBean(LoginDAO loginDAO, ExternalContext externalContext) {
		this.loginDAO = loginDAO;
		this.externalContext = externalContext;
	}

	public String login() throws UnsupportedEncodingException {
		Token token = loginDAO.loginReturningToken(username, password);
		if (token == null) {
			return null;
		}
		addTokenAndExpirationTimeToCookies(token.getToken(), token.getExpirationTime().toString());
		return "index.xhtml?faces-redirect=true";
	}
	
	public String logout() {
		removeTokenAndExpirationTimeFromCookies();
		return "login.xhtml?faces-redirect=true";
	}

	private void addTokenAndExpirationTimeToCookies(String token, String expirationTime) {
		externalContext.addResponseCookie("token", 
				CustomURLEncoder.encodeUTF8(token), null);
		externalContext.addResponseCookie("expirationTime", 
				CustomURLEncoder.encodeUTF8(expirationTime), null);
	}

	private void removeTokenAndExpirationTimeFromCookies() {
		addTokenAndExpirationTimeToCookies(null, null);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
