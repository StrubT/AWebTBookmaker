package ch.bfh.awebt.bookmaker.presentation;

/**
 * Represents a page in the site navigation.
 *
 * @author strut1 &amp; touwm1
 */
public class NavigationPage {

	/**
	 * Represents the conditions under which a page can be accessed.
	 */
	public static enum AccessCondition {

		/**
		 * The page is inaccessible. <br>
		 * This constant should only be used for navigation visibility, not access control.
		 */
		NEVER,

		/**
		 * This page is only accessible to logged-in managers.
		 */
		MANAGER,

		/**
		 * This page is only accessible to logged-in user.
		 */
		PLAYER,

		/**
		 * This page is accessible to everyone (even anonymous visitors).
		 */
		ALWAYS
	}

	private final String view, name;
	private final AccessCondition accessCondition, navigationCondition;

	/**
	 * Construct a new page object accessible to everyone.
	 *
	 * @param view identifier of the view to show
	 * @param name name of the page (used for the internationalised title)
	 */
	public NavigationPage(String view, String name) {
		this(view, name, AccessCondition.ALWAYS);
	}

	/**
	 * Construct a new page object that will only be accessible under a certain condition.
	 *
	 * @param view            identifier of the view to show
	 * @param name            name of the page (used for the internationalised title)
	 * @param accessCondition condition under which the page is accessible
	 */
	public NavigationPage(String view, String name, AccessCondition accessCondition) {
		this(view, name, accessCondition, accessCondition);
	}

	/**
	 * Construct a new page object that will only be shown under a certain condition.
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
	 * Get the identifier of the view to show.
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
}
