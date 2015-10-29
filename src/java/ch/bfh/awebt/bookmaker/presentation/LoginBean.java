package ch.bfh.awebt.bookmaker.presentation;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import ch.bfh.awebt.bookmaker.Streams;
import ch.bfh.awebt.bookmaker.persistence.UserDAO;
import ch.bfh.awebt.bookmaker.persistence.data.User;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 2026572716229390497L;

	@ManagedProperty("#{navigationBean}")
	private NavigationBean navigationBean;

	private final UserDAO userDAO = new UserDAO();

	private Locale locale;
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
	}

	public void setNavigationBean(NavigationBean navigationBean) {
		this.navigationBean = navigationBean;
	}

	public List<Locale> getLocales() {

		return Streams.iteratorList(FacesContext.getCurrentInstance().getApplication().getSupportedLocales());
	}

	public Locale getLocale() {
		return locale;
	}

	public String getLanguage() {
		return getLocale().getLanguage();
	}

	public String setLanguage(String language) {

		locale = new Locale(language);

		if (user != null) {
			user.setLanguage(language);
			userDAO.update(user);
		}

		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);

		return String.format("%s?faces-redirect=true", navigationBean.getCurrentView());
	}

	public boolean isLoggedIn() {
		return user != null;
	}

	public boolean isVisible(NavigationPage page) {

		switch (page.getCondition()) {
			case ALWAYS:
				return true;
			case NEVER:
				return false;

			case PLAYER:
				return isLoggedIn();

			case MANAGER:
				return isLoggedIn() && user.isManager();

			default:
				throw new IllegalStateException("Unhandled page condition.");
		}
	}

	public User getUser() {
		return user;
	}

	private void setUser(User user) {

		this.user = user;
		_userLogin = user.getLogin();
		locale = new Locale(user.getLanguage());
	}

	public String getLogin() {
		return _userLogin;
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
				User user = new User(_userLogin, locale.getLanguage(), _userPassword);
				userDAO.create(user);
				_userPassword = null;

				setUser(user); //log in
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
				setUser(user);
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

		return String.format("%s?faces-redirect=true", navigationBean.getCurrentView());
	}
}
