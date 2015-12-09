package ch.bfh.awebt.bookmaker.persistence;

import java.io.Serializable;
import java.util.List;
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

	public List<User> findAllOrderedByLogin() {

		return findByQuery(User.FIND_ALL_ORDERED_BY_LOGIN_QUERY);
	}

	public List<User> findManagersOrderedByLogin() {

		return findByQuery(User.FIND_MANAGERS_ORDERED_BY_LOGIN_QUERY);
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
