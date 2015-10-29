package ch.bfh.awebt.bookmaker.presentation;

/**
 * Represents a page in the site navigation.
 *
 * @author strut1 &amp; touwm1
 */
public class NavigationPage {

	/**
	 * Represents the conditions under which a page is shown in the navigation.
	 */
	public static enum Condition {

		/**
		 * Never show the page in the navigation. <br>
		 * The page may be accessed via in-page links.
		 */
		NEVER,

		/**
		 * Show this page only in the navigation if the logged-in user is a manager.
		 */
		MANAGER,

		/**
		 * Show this page only in the navigation if a user is logged in.
		 */
		PLAYER,

		/**
		 * Show this page always (whether or not a user is logged in).
		 */
		ALWAYS
	}

	private final String view;
	private final String name;
	private final Condition condition;

	/**
	 * Construct a new page object that will always be shown.
	 *
	 * @param view identifier of the view to show
	 * @param name name of the page (used for the internationalised title)
	 */
	public NavigationPage(String view, String name) {
		this(view, name, Condition.ALWAYS);
	}

	/**
	 * Construct a new page object that will only be shown under a certain condition.
	 *
	 * @param view      identifier of the view to show
	 * @param name      name of the page (used for the internationalised title)
	 * @param condition condition the page is shown under
	 */
	public NavigationPage(String view, String name, Condition condition) {

		this.view = view;
		this.name = name;
		this.condition = condition;
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
	 * Gets the condition the page is shown under.
	 *
	 * @return condition the page is shown under
	 */
	public Condition getCondition() {
		return condition;
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
