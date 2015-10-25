package ch.bfh.awebt.bookmaker.persistence;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import ch.bfh.awebt.bookmaker.persistence.data.User;

public class UserDAO extends GenericDAO<User> {

	public User find(Long id) {
		return super.find(User.class, id);
	}

	public User findByLogin(String login) {

		Query query = entityManager.createNamedQuery(User.FIND_BY_LOGIN_QUERY);
		query.setParameter("login", login);

		try {
			return (User)query.getSingleResult();

		} catch (NoResultException ex) {
			return null;
		}
	}
}
