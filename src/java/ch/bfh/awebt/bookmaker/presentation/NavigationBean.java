package ch.bfh.awebt.bookmaker.presentation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import ch.bfh.awebt.bookmaker.Streams;

@ManagedBean
@SessionScoped
public class NavigationBean implements Serializable {

	private static final long serialVersionUID = -1616488627854886893L;

	private static final List<NavigationPage> PAGES = Arrays.asList(new NavigationPage("/home.xhtml", "Home"),
																																	new NavigationPage("/secret.xhtml", "Secret", NavigationPage.Condition.PLAYER),
																																	new NavigationPage("/login.xhtml", "Login", NavigationPage.Condition.NEVER),
																																	new NavigationPage("/register.xhtml", "LoginRegister", NavigationPage.Condition.NEVER));

	public List<NavigationPage> getPages() {

		return Collections.unmodifiableList(PAGES);
	}

	public String getCurrentView() {

		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}

	public NavigationPage getCurrentPage() {

		String view = getCurrentView();
		return PAGES.stream()
			.filter(p -> view.equals(p.getView()))
			.collect(Streams.singleCollector());
	}
}
