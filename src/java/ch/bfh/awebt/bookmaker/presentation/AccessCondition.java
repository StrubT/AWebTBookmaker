package ch.bfh.awebt.bookmaker.presentation;

/**
 * Represents the conditions under which a page can be accessed.
 */
public enum AccessCondition {

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