package ch.bfh.awebt.bookmaker.persistence;

import java.io.Serializable;
import ch.bfh.awebt.bookmaker.MapBuilder;
import ch.bfh.awebt.bookmaker.Streams;
import ch.bfh.awebt.bookmaker.persistence.data.User;

/**
 * Represents a data access object for {@link User} entities.
 *
 * @author strut1 &amp; touwm1
 */
public class UserDAO extends GenericDAO<User, Integer> implements Serializable {

	private static final long serialVersionUID = 7855160381047946895L;

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

	/**
	 * Finds a user by its unique login.
	 *
	 * @param login unique login of the user
	 *
	 * @return user with the specified login, or {@code null} if there is none
	 */
	public User findByLogin(String login) {

		return findByQuery(User.FIND_BY_LOGIN_QUERY, MapBuilder.single("login", login))
			.stream().collect(Streams.nullableSingleCollector());
	}
}
