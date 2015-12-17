package ch.bfh.awebt.bookmaker.presentation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import ch.bfh.awebt.bookmaker.Streams;
import ch.bfh.awebt.bookmaker.persistence.UserDAO;
import ch.bfh.awebt.bookmaker.persistence.data.User;
import ch.bfh.awebt.bookmaker.presentation.data.AccessCondition;
import ch.bfh.awebt.bookmaker.presentation.data.NavigationPage;

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

	private transient UIComponent loginField, passwordField;

	private transient UserDAO userDAO;

	private Locale locale;
	private ZoneId timeZone;

	private Integer userId;
	private String userLogin;
	private char[] userPassword;

	/**
	 * Initialises the managed bean.
	 */
	@PostConstruct
	public void init() {

		List<Locale> supportedLocales = getSupportedLocales();
		locale = getRequestLocales().stream() //find the first requested locale which is supported by the application
			.filter(r -> supportedLocales.stream().anyMatch(s -> r.getLanguage().equals(s.getLanguage())))
			.sorted(this::compareLocalesPreferRegionalised) //prefer regionalised locales
			.findFirst().orElse(supportedLocales.get(0)); //or use the default locale instad

		timeZone = ZoneId.systemDefault();
	}

	private int compareLocalesPreferRegionalised(Locale locale1, Locale locale2) {

		boolean regionalised1 = locale1.getCountry() != null && locale1.getCountry().length() > 0;
		boolean regionalised2 = locale2.getCountry() != null && locale2.getCountry().length() > 0;

		return (regionalised2 ? 1 : 0) - (regionalised1 ? 1 : 0);
	}

	/**
	 * Gets the {@link NavigationBean}. <br>
	 * This method is not to be called by client code, the framework automatically sets the bean instance.
	 *
	 * @return the {@link NavigationBean} to use
	 */
	public NavigationBean getNavigationBean() {
		return navigationBean;
	}

	/**
	 * Sets the {@link NavigationBean}. <br>
	 * This method is not to be called by client code, the framework automatically sets the bean instance.
	 *
	 * @param navigationBean the {@link NavigationBean} to use
	 */
	public void setNavigationBean(NavigationBean navigationBean) {
		this.navigationBean = navigationBean;
	}

	/**
	 * Gets the login (user name) field of the current view.
	 *
	 * @return login (user name) field of the current view
	 */
	public UIComponent getLoginField() {
		return loginField;
	}

	/**
	 * Sets the login (user name) field of the current view.
	 *
	 * @param loginField login (user name) field of the current view
	 */
	public void setLoginField(UIComponent loginField) {
		this.loginField = loginField;
	}

	/**
	 * Gets the password field of the current view.
	 *
	 * @return password field of the current view
	 */
	public UIComponent getPasswordField() {
		return passwordField;
	}

	/**
	 * Sets the password field of the current view.
	 *
	 * @param passwordField password field of the current view
	 */
	public void setPasswordField(UIComponent passwordField) {
		this.passwordField = passwordField;
	}

	/**
	 * Gets a {@link List} containing the locales supported by the application.
	 *
	 * @return {@link List} containing the locales supported by the application
	 */
	public List<Locale> getSupportedLocales() {

		Application application = FacesContext.getCurrentInstance().getApplication();

		return Stream.concat(Stream.of(application.getDefaultLocale()), //get the default locale first
												 Streams.iteratorStream(application.getSupportedLocales())) //get other supported locales
			.distinct() //remove any duplicates (the default locale is actually included in the supported locales, but in the last position)
			.collect(Collectors.toList());
	}

	/**
	 * Gets a {@link List} containing the locales requested by the user.
	 *
	 * @return {@link List} containing the locales requested by the user
	 */
	public List<Locale> getRequestLocales() {

		return Streams.iteratorStream(FacesContext.getCurrentInstance().getExternalContext().getRequestLocales()) //wrap the requested locales in a list
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
	 * Sets the locale to show the application in.
	 *
	 * @param locale locale to show the application in
	 */
	public void setLocale(Locale locale) {

		this.locale = locale;
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale); //change the locale of the web page

		User user = getUser();
		if (user != null) {
			user.setLocale(locale); //remember the language for logged-in users
			getUserDAO().merge(user);
		}
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
	 */
	public void setLanguage(String language) {

		setLocale(getRequestLocales().stream() //find the first requested locale with the selected language
			.filter(r -> r.getLanguage().equalsIgnoreCase(language))
			.sorted(this::compareLocalesPreferRegionalised) //prefer regionalised locales
			.findFirst().orElse(new Locale(language))); //or use a non-regionalised locale instad
	}

	/**
	 * Gets the available time zones.
	 *
	 * @return a {@code List} containing the available time zones
	 */
	public List<ZoneId> getTimeZones() {

		return ZoneId.getAvailableZoneIds().stream()
			.map(ZoneId::of)
			.sorted(this::compareTimeZones)
			.collect(Collectors.toList());
	}

	/**
	 * Gets the time zone to show times in.
	 *
	 * @return time zone to show times in
	 */
	public ZoneId getTimeZone() {
		return timeZone;
	}

	/**
	 * Sets the time zone to show times in.
	 *
	 * @param timeZone time zone to show times in
	 */
	public void setTimeZone(ZoneId timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * Format a number according to the user settings.
	 *
	 * @param number number to format
	 *
	 * @return number formatted according to the user settings
	 */
	public String formatNumberUser(BigDecimal number) {

		return number != null ? NumberFormat.getNumberInstance(locale).format(number) : null;
	}

	/**
	 * Format a number according to the user settings. <br>
	 * <em>This method merely redirects to {@link #formatNumberUser(long)} but is needed for JSF.</em>
	 *
	 * @param number number to format
	 *
	 * @return number formatted according to the user settings
	 */
	public String formatNumberUser(int number) {

		return formatNumberUser((long)number);
	}

	/**
	 * Format a number according to the user settings.
	 *
	 * @param number number to format
	 *
	 * @return number formatted according to the user settings
	 */
	public String formatNumberUser(long number) {

		return NumberFormat.getNumberInstance(locale).format(number);
	}

	/**
	 * Format a number according to the user settings.
	 *
	 * @param number number to format
	 *
	 * @return number formatted according to the user settings
	 */
	public String formatNumberUser(double number) {

		return NumberFormat.getNumberInstance(locale).format(number);
	}

	/**
	 * Format a percentage according to the user settings.
	 *
	 * @param percentage percentage to format
	 *
	 * @return percentage formatted according to the user settings
	 */
	public String formatPercentUser(BigDecimal percentage) {

		return percentage != null ? NumberFormat.getPercentInstance(locale).format(percentage) : null;
	}

	/**
	 * Format a percentage according to the user settings.
	 *
	 * @param percentage percentage to format
	 *
	 * @return percentage formatted according to the user settings
	 */
	public String formatPercentUser(double percentage) {

		return NumberFormat.getPercentInstance(locale).format(percentage);
	}

	/**
	 * Formats a date/time according to the ISO standard.
	 *
	 * @param dateTime date time to format
	 *
	 * @return date/time formatted according to the ISO standard
	 */
	public String formatDateTimeISO(LocalDateTime dateTime) {

		return dateTime != null ? dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null;
	}

	/**
	 * Formats a date/time according to the ISO standard.
	 *
	 * @param dateTime date time to format
	 *
	 * @return date/time formatted according to the ISO standard
	 */
	public String formatDateTimeISO(ZonedDateTime dateTime) {

		return dateTime != null ? dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) : null;
	}

	/**
	 * Formats a date/time according to the user settings.
	 *
	 * @param dateTime date time to format
	 *
	 * @return date/time formatted according to the user settings
	 */
	public String formatDateTimeUser(LocalDateTime dateTime) {

		if (dateTime == null)
			return null;

		return dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT).withLocale(locale));
	}

	/**
	 * Formats a date/time according to the user settings.
	 *
	 * @param dateTime        date time to format
	 * @param includeTimeZone whether or not to include the time zone
	 *
	 * @return date/time formatted according to the user settings
	 */
	public String formatDateTimeUser(ZonedDateTime dateTime, boolean includeTimeZone) {

		if (dateTime == null)
			return null;

		return String.format(includeTimeZone ? "%s (%s)" : "%s",
												 dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT).withZone(timeZone).withLocale(locale)),
												 timeZone.getDisplayName(TextStyle.FULL, locale));
	}

	/**
	 * Formats a time zone according to the user settings.
	 *
	 * @param timeZone       time zone to format
	 * @param includeDetails whether or not to include details such as offset and detailed location
	 *
	 * @return time zone formatted according to the user settings
	 */
	public String formatTimeZoneUser(ZoneId timeZone, boolean includeDetails) {

		if (timeZone == null)
			return null;

		return String.format(includeDetails ? "(%s) %s - %s" : "%3$s",
												 timeZone.getRules().getOffset(LocalDateTime.now()),
												 timeZone,
												 timeZone.getDisplayName(TextStyle.FULL, locale));
	}

	private int compareTimeZones(ZoneId timeZone1, ZoneId timeZone2) {

		LocalDateTime now = LocalDateTime.now();
		int c = timeZone2.getRules().getOffset(now).compareTo(timeZone1.getRules().getOffset(now)); //compare by current offset first

		if (c == 0) {
			c = timeZone1.toString().compareTo(timeZone2.toString()); //compare by id in second priority

			if (c == 0)
				c = timeZone1.getDisplayName(TextStyle.FULL, locale).compareTo(timeZone2.getDisplayName(TextStyle.FULL, locale)); //compare by name last
		}

		return c;
	}

	private UserDAO getUserDAO() {

		if (userDAO == null)
			userDAO = new UserDAO();

		return userDAO;
	}

	/**
	 * Gets all users ordered by their login.
	 *
	 * @return {@link List} of all users
	 */
	public List<User> getUsers() {

		return getUserDAO().findAllOrderedByLogin();
	}

	/**
	 * Gets the user currently logged in.
	 *
	 * @return user currently logged in
	 */
	public User getUser() {

		return userId != null ? getUserDAO().find(userId) : null;
	}

	private void setUser(User user) {

		userId = user != null ? user.getId() : null;
		userPassword = null;

		if (user != null) {
			userLogin = user.getLogin();
			locale = user.getLocale();
			timeZone = user.getTimeZone();
		}
	}

	/**
	 * Gets the current user's unique identifier.
	 *
	 * @return user's unique identifier, if any
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * Gets the login of the user to log in OR already logged in.
	 *
	 * @return login of the user
	 */
	public String getLogin() {
		return userLogin;
	}

	/**
	 * Sets the login of the user to log in.
	 *
	 * @param login login of the user to log in
	 */
	public void setLogin(String login) {
		userLogin = login;
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
		return userPassword != null;
	}

	/**
	 * Sets the password of the user to log in.
	 *
	 * @param password password of the user to log in
	 */
	public void setPassword(String password) {

		userPassword = password.length() > 0 ? password.toCharArray() : null;
	}

	/**
	 * Gets whether or not a page should be show in the navigation.
	 *
	 * @param page page to determine whether or not to show in the navigation
	 *
	 * @return whether or not the specified page should be show in the navigation
	 */
	public boolean showInNavigation(NavigationPage page) {

		return (page != null ? page.getNavigationCondition() : AccessCondition.NEVER).hasAccess(getUser());
	}

	/**
	 * Gets whether or not the user has access to a page.
	 *
	 * @param page page to determine whether or not the user has access to
	 *
	 * @return whether or not the user has access to the page
	 */
	public boolean userHasAccessTo(NavigationPage page) {

		return (page != null ? page.getAccessCondition() : AccessCondition.NEVER).hasAccess(getUser());
	}

	/**
	 * Gets whether or not the user has access to a resource.
	 *
	 * @param accessCondition the condition to use to determine whether or not the user has access
	 *
	 * @return whether or not the user matches the condition
	 */
	public boolean userHasAccess(AccessCondition accessCondition) {

		return accessCondition.hasAccess(getUser());
	}

	/**
	 * Gets whether or no there are multiple managers.
	 *
	 * @return whether or no there are multiple managers
	 */
	public boolean hasMultipleManagers() {

		return getUserDAO().findManagersOrderedByLogin().size() > 1;
	}

	/**
	 * Toggle whether or not a user is manager.
	 *
	 * @param user user to perform action on
	 */
	public void toggleIsManager(User user) {

		user.setIsManager(!user.isManager());
		getUserDAO().merge(user);
	}

	/**
	 * Tries to register a user with the entered login and password.
	 *
	 * @return outcome: identifier of the view to navigate to
	 */
	public String register() {

		if (userLogin != null && userLogin.length() > 0 && userPassword != null)

			if (getUserDAO().findByLogin(userLogin) != null)
				MessageFactory.addError(loginField, "ch.bfh.awebt.bookmaker.LOGIN_REGISTER_LOGIN_TAKEN");

			else
				try {
					User user = new User(userLogin, locale, timeZone, userPassword);
					getUserDAO().persist(user);

					setUser(user); //log in
					return "/players/account.xhtml?faces-redirect=true";

				} catch (NoSuchAlgorithmException ex) {
					MessageFactory.addError("ch.bfh.awebt.bookmaker.SECURITY_ERROR");

				} catch (PersistenceException ex) {
					MessageFactory.addError("ch.bfh.awebt.bookmaker.PERSISTENCE_ERROR");
				}

		else
			MessageFactory.addError("ch.bfh.awebt.bookmaker.LOGIN_ERROR_MISSING_INFORMATION");

		if (navigationBean.showLoginRegister())
			return navigationBean.getRegisterPage().getView();

		return null;
	}

	/**
	 * Tries to log in the user with the entered login and password.
	 *
	 * @return outcome: identifier of the view to navigate to
	 */
	public String login() {

		try {
			User user = getUserDAO().findByLogin(userLogin);
			if (user != null && user.validatePassword(userPassword)) {
				setUser(user);
				if (!navigationBean.showLoginRegister())
					return String.format("%s?faces-redirect=true", navigationBean.getHomePage().getView());
				else
					return null;

			} else
				MessageFactory.addWarning("ch.bfh.awebt.bookmaker.LOGIN_ERROR_INCORRECT_INFORMATION");

		} catch (NoSuchAlgorithmException ex) {
			MessageFactory.addError("ch.bfh.awebt.bookmaker.SECURITY_ERROR");

		} catch (PersistenceException ex) {
			MessageFactory.addError("ch.bfh.awebt.bookmaker.PERSISTENCE_ERROR");
		}

		if (navigationBean.showLoginRegister())
			return navigationBean.getLoginPage().getView();

		return null;
	}

	/**
	 * Logs out the currently logged in user.
	 *
	 * @return outcome: identifier of the view to navigate to
	 */
	public String logout() {

		setUser(null);
		if (!userHasAccessTo(navigationBean.getCurrentPage()))
			return String.format("%s?faces-redirect=true", navigationBean.getHomePage().getView());

		return null;
	}
}
