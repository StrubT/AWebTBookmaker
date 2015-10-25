package ch.bfh.awebt.bookmaker.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class GenericDAO<T> {

	private static final String PERSISTENCE_UNIT = "bookmaker";

	protected final EntityManager entityManager;

	public GenericDAO() {

		entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT).createEntityManager();
	}

	public T create(T entity) {

		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();

		return entity;
	}

	public T find(Class<T> type, Long id) {

		return entityManager.find(type, id);
	}

	public T update(T entity) {

		entityManager.getTransaction().begin();
		entity = entityManager.merge(entity);
		entityManager.getTransaction().commit();

		return entity;
	}

	public void delete(T entity) {

		entityManager.getTransaction().begin();
		entity = entityManager.merge(entity);
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
	}
}
