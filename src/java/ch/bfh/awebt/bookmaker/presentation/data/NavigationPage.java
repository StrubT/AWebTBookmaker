package ch.bfh.awebt.bookmaker.presentation.data;

import java.io.Serializable;

/**
 * Represents a page in the site navigation.
 *
 * @author strut1 &amp; touwm1
 */
public class NavigationPage implements Serializable {

	private static final long serialVersionUID = -4638236743177666379L;

	private final String view, name;
	private final AccessCondition accessCondition, navigationCondition;

	/**
	 * Constructs a new page object accessible to everyone.
	 *
	 * @param view identifier of the view to show
	 * @param name name of the page (used for the internationalised title)
	 */
	public NavigationPage(String view, String name) {
		this(view, name, AccessConditions.ALWAYS);
	}

	/**
	 * Construcst a new page object that will only be accessible under a certain condition.
	 *
	 * @param view            identifier of the view to show
	 * @param name            name of the page (used for the internationalised title)
	 * @param accessCondition condition under which the page is accessible
	 */
	public NavigationPage(String view, String name, AccessCondition accessCondition) {
		this(view, name, accessCondition, accessCondition);
	}

	/**
	 * Constructs a new page object that will only be shown under a certain condition.
	 *
	 * @param view                identifier of the view to show
	 * @param name                name of the page (used for the internationalised title)
	 * @param accessCondition     condition under which the page is accessible
	 * @param navigationCondition condition under which the page is shown in the navigation
	 */
	public NavigationPage(String view, String name, AccessCondition accessCondition, AccessCondition navigationCondition) {

		this.view = view;
		this.name = name;
		this.accessCondition = accessCondition;
		this.navigationCondition = navigationCondition;
	}

	/**
	 * Gets the identifier of the view to show.
	 *
	 * @return identifier of the view to show
	 */
	public String getView() {
		return view;
	}

	/**
	 * Gets the name of the page (used for the internationalised title).
	 *
	 * @return name of the page
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the condition under which the page is accessible.
	 *
	 * @return condition under which the page is accessible
	 */
	public AccessCondition getAccessCondition() {
		return accessCondition;
	}

	/**
	 * Gets the condition under which the page is shown in the navigation.
	 *
	 * @return condition under which the page is shown in the navigation
	 */
	public AccessCondition getNavigationCondition() {
		return navigationCondition;
	}

	/**
	 * Gets the text name of the page title.
	 *
	 * @return text name of the page title
	 */
	public String getTitle() {

		return String.format("%sTitle", name);
	}

	/**
	 * Gets a hash code for the navigation page.
	 *
	 * @return hash code for the navigation page
	 */
	@Override
	public int hashCode() {
		return (17 + view.hashCode()) * 31;
	}

	/**
	 * Compares a second object with the current one and determines whether or not they are equal.
	 *
	 * @param obj object to compare to
	 *
	 * @return whether or not the two objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == getClass() && ((NavigationPage)obj).view.equals(view);
	}
}
