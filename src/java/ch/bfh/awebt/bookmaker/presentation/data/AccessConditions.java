package ch.bfh.awebt.bookmaker.presentation.data;

import ch.bfh.awebt.bookmaker.persistence.data.User;

/**
 * Represents the well-known conditions under which a page can be accessed.
 */
public enum AccessConditions implements AccessCondition {

	/**
	 * This page is accessible to everyone (even anonymous visitors).
	 */
	ALWAYS {

			@Override
			public boolean hasAccess(User user) {
				return true;
			}
		},

	/**
	 * This page is only accessible to logged-in users.
	 */
	REGISTERED {

			@Override
			public boolean hasAccess(User user) {
				return user != null;
			}
		},

	/**
	 * This page is only accessible to logged-in managers.
	 */
	MANAGER {

			@Override
			public boolean hasAccess(User user) {
				return user != null && user.isManager();
			}
		},

	/**
	 * The page is inaccessible. <br>
	 * This constant should only be used for navigation visibility, not access control.
	 */
	NEVER {

			@Override
			public boolean hasAccess(User user) {
				return false;
			}
		}
}
