package ch.bfh.awebt.bookmaker.presentation;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

/**
 * Represents a {@link SessionScoped} {@link ManagedBean} providing login and localisation helpers.
 *
 * @author strut1 &amp; touwm1
 */
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
	private byte[] _userPasswordHash;

	/**
	 * Initialises the managed bean.
	 */
	@PostConstruct
	public void init() {

		List<Locale> supportedLocales = getLocales();
		locale = Streams.iteratorStream(FacesContext.getCurrentInstance().getExternalContext().getRequestLocales()) //find the first requested locale which is supported by the application
			.flatMap(r -> supportedLocales.stream().filter(s -> r.getLanguage().equalsIgnoreCase(s.getLanguage())))
			.findFirst().orElse(supportedLocales.get(0)); //or use the default locale instad
	}

	/**
	 * Sets the {@link NavigationBean}. <br>
	 * This method is not to be called by client code, the framework automatically sets the bean instance.
	 *
	 * @param navigationBean the bean to use
	 */
	public void setNavigationBean(NavigationBean navigationBean) {
		this.navigationBean = navigationBean;
	}

	/**
	 * Gets a {@link List} containing the locales supported by the application.
	 *
	 * @return {@link List} containing the locales supported by the application
	 */
	public List<Locale> getLocales() {

		Application application = FacesContext.getCurrentInstance().getApplication();

		return Stream.concat(Stream.of(application.getDefaultLocale()), //get the default locale first
												 Streams.iteratorStream(application.getSupportedLocales())) //get other supported locales
			.distinct() //remove any duplicates (the default locale is actually included in the supported locales, but in the last position)
			.collect(Collectors.toList());
	}

	/**
	 * Gets the locale to show the application in.
	 *
	 * @return locale to show the application in
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Gets the language to show the application in.
	 *
	 * @return language to show the application in
	 */
	public String getLanguage() {
		return getLocale().getLanguage();
	}

	/**
	 * Sets the language to show the application in.
	 *
	 * @param language language to show the application in
	 *
	 * @return outcome: identifier of the view to navigate to
	 */
	public String setLanguage(String language) {

		locale = new Locale(language);

		if (user != null) {
			user.setLanguage(language); //remember the language for logged-in users
			userDAO.merge(user);
		}

		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale); //change the locale of the web page

		return navigationBean.getCurrentView(); //forces the application to reload the texts
	}

	/**
	 * Gets whether or not a page should be show in the navigation.
	 *
	 * @param page page to determine whether or not to show in the navigation
	 *
	 * @return whether or not the specified page should be show in the navigation
	 */
	public boolean showInNavigation(NavigationPage page) {

		return userHasAccess(page != null ? page.getNavigationCondition() : AccessCondition.NEVER);
	}

	/**
	 * Gets whether or not the user has access to a page.
	 *
	 * @param page page to determine whether or not the user has access to
	 *
	 * @return whether or not the user has access to the page
	 */
	public boolean userHasAccessTo(NavigationPage page) {

		return userHasAccess(page != null ? page.getAccessCondition() : AccessCondition.NEVER);
	}

	/**
	 * Gets whether or not the user has access to a resource.
	 *
	 * @param accessCondition the condition to use to determine whether or not the user has access
	 *
	 * @return whether or not the user matches the condition
	 */
	public boolean userHasAccess(AccessCondition accessCondition) {

		switch (accessCondition) {
			case ALWAYS:
				return true;

			case PLAYER:
				return user != null;

			case MANAGER:
				return user != null && user.isManager();

			default:
				return false;
		}
	}

	/**
	 * Gets the user currently logged in.
	 *
	 * @return user currently logged in
	 */
	public User getUser() {
		return user;
	}

	private void setUser(User user) {

		this.user = user;
		_userLogin = user.getLogin();
		_userPasswordHash = null;

		locale = new Locale(user.getLanguage());
	}

	/**
	 * Gets the login of the user to log in OR already logged in.
	 *
	 * @return login of the user
	 */
	public String getLogin() {
		return _userLogin;
	}

	/**
	 * Sets the login of the user to log in.
	 *
	 * @param login login of the user to log in
	 */
	public void setLogin(String login) {
		_userLogin = login;
	}

	/**
	 * Gets the password of the user to log in. <br>
	 * This method will always return {@code null}, the password is not being exposed.
	 * It is only needed for {@link #setPassword(String)} to work properly.
	 *
	 * @return {@code null}
	 */
	public String getPassword() {
		return null;
	}

	/**
	 * Gets whether or not the password of the user to log in has already been entered.
	 *
	 * @return whether or not the password of the user to log in has already been entered
	 */
	public boolean hasPassword() {
		return _userPasswordHash != null;
	}

	/**
	 * Sets the password of the user to log in.
	 *
	 * @param password password of the user to log in
	 */
	public void setPassword(String password) {

		try {
			_userPasswordHash = User.hashPassword(password.toCharArray());

		} catch (NoSuchAlgorithmException ex) {
			MessageFactory.addWarning("ch.bfh.awebt.bookmaker.SECURITY_ERROR");
		}
	}

	/**
	 * Tries to register a user with the entered login and password.
	 *
	 * @return outcome: identifier of the view to navigate to
	 */
	public String register() {

		if (_userLogin != null && _userLogin.length() > 0 && _userPasswordHash != null)

			if (userDAO.findByLogin(_userLogin) != null)
				MessageFactory.addWarning("ch.bfh.awebt.bookmaker.LOGIN_REGISTER_LOGIN_TAKEN");

			else
				try {
					User user = new User(_userLogin, locale.getLanguage(), _userPasswordHash);
					userDAO.persist(user);

					setUser(user); //log in
					return "secret?faces-redirect=true";

				} catch (PersistenceException ex) {
					MessageFactory.addWarning("ch.bfh.awebt.bookmaker.PERSISTENCE_ERROR");
				}

		else
			MessageFactory.addWarning("ch.bfh.awebt.bookmaker.LOGIN_ERROR_MISSING_INFORMATION");

		return "register?faces-redirect=true";
	}

	/**
	 * Tries to log in the user with the entered login and password.
	 *
	 * @return outcome: identifier of the view to navigate to
	 */
	public String login() {

		try {
			User user = userDAO.findByLogin(_userLogin);
			if (user != null && user.validatePassword(_userPasswordHash)) {
				setUser(user);
				return "secret?faces-redirect=true";

			} else
				MessageFactory.addWarning("ch.bfh.awebt.bookmaker.LOGIN_ERROR_INCORRECT_INFORMATION");

		} catch (PersistenceException ex) {
			MessageFactory.addWarning("ch.bfh.awebt.bookmaker.PERSISTENCE_ERROR");
		}

		return "login?faces-redirect=true";
	}

	/**
	 * Logs out the currently logged in user.
	 *
	 * @return outcome: identifier of the view to navigate to
	 */
	public String logout() {

		user = null;
		_userLogin = null;
		_userPasswordHash = null;

		if (userHasAccessTo(navigationBean.getCurrentPage()))
			return navigationBean.getCurrentView(); //forces the application to reload

		else
			return "home?faces-redirect=true";
	}
}
