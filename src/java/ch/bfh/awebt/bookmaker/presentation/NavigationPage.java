package ch.bfh.awebt.bookmaker.presentation;

public class NavigationPage {

	public static enum Condition {

		NEVER,
		MANAGER,
		PLAYER,
		ALWAYS
	}

	private final String view;
	private final String name;
	private final Condition condition;

	public NavigationPage(String target, String name) {
		this(target, name, Condition.ALWAYS);
	}

	public NavigationPage(String view, String name, Condition condition) {

		this.view = view;
		this.name = name;
		this.condition = condition;
	}

	public String getView() {
		return view;
	}

	public String getName() {
		return name;
	}

	public Condition getCondition() {
		return condition;
	}

	public String getTitle() {

		return String.format("%sTitle", name);
	}
}
