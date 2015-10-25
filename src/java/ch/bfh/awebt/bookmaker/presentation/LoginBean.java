package ch.bfh.awebt.bookmaker.presentation;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import ch.bfh.awebt.bookmaker.persistence.UserDAO;
import ch.bfh.awebt.bookmaker.persistence.data.User;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private Locale locale;
	private UserDAO userDAO;

	private User user;
	private String _userLogin;
	private char[] _userPassword;

	@PostConstruct
	public void init() {

		locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
		userDAO = new UserDAO();
	}

	public Locale getLocale() {
		return locale;
	}

	public String getLanguage() {
		return locale.getLanguage();
	}

	public String setLanguage(String language, String action) {

		locale = new Locale(language);
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);

		return String.format("%s?faces-redirect=true", action);
	}

	public boolean isLoggedIn() {
		return user != null;
	}

	public User getUser() {
		return user;
	}

	public String getLogin() {
		return user != null ? user.getLogin() : _userLogin;
	}

	public void setLogin(String login) {
		_userLogin = login;
	}

	@Deprecated
	public String getPassword() {
		return null;
	}

	public void setPassword(String password) {
		_userPassword = password.toCharArray();
	}

	public String register() {

		if (_userLogin != null && _userLogin.length() > 0
				&& _userPassword != null && _userPassword.length > 0)

			try {
				User user = new User(_userLogin, _userPassword);
				userDAO.create(user);
				_userPassword = null;

				this.user = user; //log in
				return "secret?faces-redirect=true";

			} catch (NoSuchAlgorithmException ex) {
				MessageFactory.addWarning("ch.bfh.awebt.bookmaker.LOGIN_ERROR_HASHING_FAILED");
			}

		else
			MessageFactory.addWarning("ch.bfh.awebt.bookmaker.LOGIN_ERROR_MISSING_INFORMATION");

		return "register?faces-redirect=true";
	}

	public String login() {

		try {
			User user = userDAO.findByLogin(_userLogin);
			if (user != null && user.validatePassword(_userPassword)) {
				this.user = user;
				return "secret?faces-redirect=true";

			} else
				MessageFactory.addWarning("ch.bfh.awebt.bookmaker.LOGIN_ERROR_INCORRECT_INFORMATION");

		} catch (NoSuchAlgorithmException ex) {
			MessageFactory.addWarning("ch.bfh.awebt.bookmaker.LOGIN_ERROR_HASHING_FAILED");
		}

		return "login?faces-redirect=true";
	}

	public String logout() {

		user = null;
		_userLogin = null;
		_userPassword = null;

		return "home?faces-redirect=true";
	}
}
