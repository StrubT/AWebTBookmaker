package ch.bfh.awebt.bookmaker.persistence;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public abstract class GenericDAO<T> {

	private static final String PERSISTENCE_UNIT = "bookmaker";

	protected final EntityManager entityManager;

	public GenericDAO() {

		entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT).createEntityManager();
	}

	protected abstract Class<T> getEntityClass();

	public T create(T entity) {

		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();

		return entity;
	}

	public T find(int id) {

		return entityManager.find(getEntityClass(), id);
	}

	public List<T> findByQuery(String name, Map<String, Object> parameters) {

		TypedQuery<T> query = entityManager.createNamedQuery(name, getEntityClass());
		parameters.forEach(query::setParameter);

		return query.getResultList();
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
