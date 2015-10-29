package ch.bfh.awebt.bookmaker.persistence;

import ch.bfh.awebt.bookmaker.MapBuilder;
import ch.bfh.awebt.bookmaker.Streams;
import ch.bfh.awebt.bookmaker.persistence.data.User;

public class UserDAO extends GenericDAO<User> {

	private static final long serialVersionUID = 7855160381047946895L;

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

	public User findByLogin(String login) {

		return findByQuery(User.FIND_BY_LOGIN_QUERY, MapBuilder.single("login", (Object)login))
			.stream().collect(Streams.nullableSingleCollector());
	}
}
