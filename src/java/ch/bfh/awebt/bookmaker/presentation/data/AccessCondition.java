package ch.bfh.awebt.bookmaker.presentation.data;

import ch.bfh.awebt.bookmaker.persistence.data.User;

/**
 * Represents the well-known conditions under which a page can be accessed.
 */
public enum AccessCondition {

	/**
	 * The page is inaccessible. <br>
	 * This constant should only be used for navigation visibility, not access control.
	 */
	NEVER {

			@Override
			public boolean hasAccess(User user) {
				return false;
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
	 * This page is only accessible to logged-in non-managers (players). <br>
	 * This constant should only be used for navigation visibility, not access control.
	 */
	REGISTERED_NOT_MANAGER {

			@Override
			public boolean hasAccess(User user) {
				return user != null && !user.isManager();
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
	 * This page is only accessible to non-managers (anonymous users or logged-in players). <br>
	 * This constant should only be used for navigation visibility, not access control.
	 */
	NOT_MANAGER {

			@Override
			public boolean hasAccess(User user) {
				return user == null || !user.isManager();
			}
		},

	/**
	 * This page is accessible to everyone (even anonymous visitors).
	 */
	ALWAYS {

			@Override
			public boolean hasAccess(User user) {
				return true;
			}
		};

	/**
	 * Evaluates whether or not a user has access.
	 *
	 * @param user user to run evaluation for
	 *
	 * @return whether or not the user has access
	 */
	public abstract boolean hasAccess(User user);
}
