package ch.bfh.awebt.bookmaker.presentation.data;

import ch.bfh.awebt.bookmaker.persistence.data.User;

/**
 * Represents the conditions under which a page can be accessed.
 */
@FunctionalInterface
public interface AccessCondition {

	/**
	 * Evaluates whether or not a user has access.
	 *
	 * @param user user to run evaluation for
	 *
	 * @return whether or not the user has access
	 */
	boolean hasAccess(User user);

	/**
	 * Gets the inverse condition.
	 *
	 * @return an {@link AccessCondition} representing the inverse
	 */
	default AccessCondition not() {

		return u -> !hasAccess(u);
	}

	/**
	 * Gets a condition that is only satisfied if both this and the second condition are satisfied.
	 *
	 * @param second second condition to use
	 *
	 * @return an {@link AccessCondition} satisfied if both this and the second condition are satisfied
	 */
	default AccessCondition and(AccessCondition second) {

		return u -> hasAccess(u) && second.hasAccess(u);
	}

	/**
	 * Gets a condition that is satisfied if either this or the second condition is satisfied.
	 *
	 * @param second second condition to use
	 *
	 * @return an {@link AccessCondition} satisfied if either this or the second condition is satisfied
	 */
	default AccessCondition or(AccessCondition second) {

		return u -> hasAccess(u) || second.hasAccess(u);
	}
}
