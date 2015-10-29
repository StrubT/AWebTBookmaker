package ch.bfh.awebt.bookmaker.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public abstract class GenericDAO<T> implements Serializable {

	private static final long serialVersionUID = -6512800996496357465L;

	private static final String PERSISTENCE_UNIT = "bookmaker";

	private transient EntityManager _entityManager;

	protected EntityManager getEntityManager() {

		if (_entityManager == null)
			_entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT).createEntityManager();

		return _entityManager;
	}

	protected abstract Class<T> getEntityClass();

	public T create(T entity) {

		EntityManager entityManager = getEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();

		return entity;
	}

	public T find(int id) {

		return getEntityManager().find(getEntityClass(), id);
	}

	public List<T> findByQuery(String name, Map<String, Object> parameters) {

		TypedQuery<T> query = getEntityManager().createNamedQuery(name, getEntityClass());
		parameters.forEach(query::setParameter);

		return query.getResultList();
	}

	public T update(T entity) {

		EntityManager entityManager = getEntityManager();

		entityManager.getTransaction().begin();
		entity = entityManager.merge(entity);
		entityManager.getTransaction().commit();

		return entity;
	}

	public void delete(T entity) {

		EntityManager entityManager = getEntityManager();

		entityManager.getTransaction().begin();
		entity = entityManager.merge(entity);
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
	}
}
