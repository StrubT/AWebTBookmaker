package ch.bfh.awebt.bookmaker.presentation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import ch.bfh.awebt.bookmaker.Streams;
import ch.bfh.awebt.bookmaker.presentation.data.AccessCondition;
import ch.bfh.awebt.bookmaker.presentation.data.NavigationPage;

/**
 * Represents a {@link ApplicationScoped} {@link ManagedBean} providing navigation helpers.
 *
 * @author strut1 &amp; touwm1
 */
@ManagedBean
@ApplicationScoped
public class NavigationBean implements Serializable {

	private static final long serialVersionUID = -1616488627854886893L;

	private final NavigationPage PAGE_HOME, PAGE_LOGIN, PAGE_REGISTER;
	private final List<NavigationPage> PAGES;

	public NavigationBean() {

		PAGES = Arrays.asList(PAGE_HOME = new NavigationPage("/home.xhtml", "Home"),
													PAGE_LOGIN = new NavigationPage("/login.xhtml", "Login", AccessCondition.ALWAYS, AccessCondition.NEVER), //show in footer
													PAGE_REGISTER = new NavigationPage("/register.xhtml", "LoginRegister", AccessCondition.ALWAYS, AccessCondition.NEVER), //show in footer
													new NavigationPage("/players/account.xhtml", "GameAccount", AccessCondition.REGISTERED),
													new NavigationPage("/players/upcoming-games.xhtml", "GameUpcoming", AccessCondition.ALWAYS, AccessCondition.NON_MANAGER),
													new NavigationPage("/players/game.xhtml", "GameDetails", AccessCondition.ALWAYS, AccessCondition.NEVER), //access via in-page links
													new NavigationPage("/managers/game.xhtml", "GameCreate", AccessCondition.MANAGER),
													new NavigationPage("/managers/upcoming-games.xhtml", "GameUpcoming", AccessCondition.MANAGER),
													new NavigationPage("/managers/past-games.xhtml", "GamePast", AccessCondition.MANAGER));
	}

	/**
	 * Gets the identifier of the current view.
	 *
	 * @return identifier of the current view
	 */
	public String getCurrentView() {

		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}

	/**
	 * Gets the home page.
	 *
	 * @return home page
	 */
	public NavigationPage getHomePage() {
		return PAGE_HOME;
	}

	/**
	 * Gets the login/logout page.
	 *
	 * @return login/logout page
	 */
	public NavigationPage getLoginPage() {
		return PAGE_LOGIN;
	}

	/**
	 * Gets the registration page.
	 *
	 * @return registration page
	 */
	public NavigationPage getRegisterPage() {
		return PAGE_REGISTER;
	}

	/**
	 * Gets whether or not to show a login/register option in the template.
	 *
	 * @return whether or not to show a login/register option in the template
	 */
	public boolean showLoginRegister() {

		NavigationPage page = getCurrentPage();
		return page != null && !page.getView().equals(PAGE_LOGIN.getView()) && !page.getView().equals(PAGE_REGISTER.getView());
	}

	/**
	 * Gets a {@link List} of all the pages in this web site.
	 *
	 * @return {@link List} of all the pages in this web site
	 */
	public List<NavigationPage> getPages() {

		return Collections.unmodifiableList(PAGES);
	}

	/**
	 * Gets the current page.
	 *
	 * @return current page
	 */
	public NavigationPage getCurrentPage() {

		String view = getCurrentView();
		return PAGES.stream()
			.filter(p -> view.equals(p.getView()))
			.collect(Streams.nullableSingleCollector());
	}

	public boolean hasMessage(Iterator<FacesMessage> messages, FacesMessage.Severity severity) {

		return Streams.iteratorStream(messages)
			.anyMatch(m -> m.getSeverity().compareTo(severity) >= 0);
	}

	public boolean hasMessage(FacesMessage.Severity severity) {

		return hasMessage(FacesContext.getCurrentInstance().getMessages(), severity);
	}

	public boolean hasMessage(UIComponent component, FacesMessage.Severity severity) {

		if (component == null)
			return hasMessage(severity);

		FacesContext context = FacesContext.getCurrentInstance();
		return hasMessage(context.getMessages(component.getClientId(context)), severity);
	}

	public boolean hasInformation() {
		return hasMessage(FacesMessage.SEVERITY_INFO);
	}

	public boolean hasInformation(UIComponent component) {
		return hasMessage(component, FacesMessage.SEVERITY_INFO);
	}

	public boolean hasWarning() {
		return hasMessage(FacesMessage.SEVERITY_WARN);
	}

	public boolean hasWarning(UIComponent component) {
		return hasMessage(component, FacesMessage.SEVERITY_WARN);
	}

	public boolean hasError() {
		return hasMessage(FacesMessage.SEVERITY_ERROR);
	}

	public boolean hasError(UIComponent component) {
		return hasMessage(component, FacesMessage.SEVERITY_ERROR);
	}
}
