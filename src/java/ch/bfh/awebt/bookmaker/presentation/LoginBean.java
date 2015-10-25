package ch.bfh.awebt.bookmaker.presentation;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import ch.bfh.awebt.bookmaker.Streams;
import ch.bfh.awebt.bookmaker.persistence.UserDAO;
import ch.bfh.awebt.bookmaker.persistence.data.User;

@ManagedBean
@SessionScoped
public class LoginBean {

	private Locale locale;
	private UserDAO userDAO;

	private User user;
	private String _userLogin;
	private char[] _userPassword;

	@PostConstruct
	public void init() {

		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		List<Locale> supportedLocales = Streams.iteratorList(application.getSupportedLocales());
		locale = Streams.iteratorStream(context.getExternalContext().getRequestLocales())
			.flatMap(r -> supportedLocales.stream().filter(s -> r.getLanguage().equalsIgnoreCase(s.getLanguage())))
			.findFirst().orElse(application.getDefaultLocale());

		userDAO = new UserDAO();
	}

	public List<Locale> getLocales() {

		return Streams.iteratorList(FacesContext.getCurrentInstance().getApplication().getSupportedLocales());
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

	public boolean hasPassword() {
		return _userPassword != null;
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

			} catch (PersistenceException | NoSuchAlgorithmException ex) {
				MessageFactory.addWarning("ch.bfh.awebt.bookmaker.LOGIN_REGISTER_ERROR_UNEXPECTED");
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

		} catch (PersistenceException | NoSuchAlgorithmException ex) {
			MessageFactory.addWarning("ch.bfh.awebt.bookmaker.LOGIN_ERROR_UNEXPECTED");
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
