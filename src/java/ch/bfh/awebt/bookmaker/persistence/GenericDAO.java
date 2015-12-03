package ch.bfh.awebt.bookmaker.persistence;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import ch.bfh.awebt.bookmaker.persistence.data.PersistentObject;

/**
 * Represents a data access object for a generic entity type. <br>
 * Provides CRUD operations for generic {@link PersistentObject}s.
 *
 * @param <T> type of the entities
 *
 * @author strut1 &amp; touwm1
 */
public abstract class GenericDAO<T extends PersistentObject<?>> {

	/**
	 * Gets the name of the persistence unit to use.
	 */
	public static final String PERSISTENCE_UNIT = "bookmaker";

	private transient EntityManager _entityManager;

	/**
	 * Gets an entity manager for the configured {@link #PERSISTENCE_UNIT}.
	 *
	 * @return entity manager for the configured persistence unit
	 */
	protected EntityManager getEntityManager() {

		if (_entityManager == null)
			_entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT).createEntityManager();

		return _entityManager;
	}

	/**
	 * Gets the {@link Class} of the entities.
	 *
	 * @return {@link Class} of the entities
	 */
	protected abstract Class<T> getEntityClass();

	/**
	 * <strong>C</strong>reate: persists an entity in the data source.
	 *
	 * @param entity entity to persist
	 *
	 * @return persisted entity
	 */
	public T persist(T entity) {

		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		try {
			entityManager.persist(entity);
			transaction.commit();

			return entity;

		} catch (Exception ex) {
			if (transaction.isActive())
				transaction.rollback();

			throw new PersistenceException("Could not persist the entity.", ex);
		}
	}

	/**
	 * <strong>R</strong>ead: finds an entity in the data source by its unique identifier.
	 *
	 * @param id unique identifier of the entity
	 *
	 * @return entity with the specified unique identifier
	 */
	public T find(Object id) {

		return getEntityManager().find(getEntityClass(), id);
	}

	/**
	 * <strong>R</strong>ead: finds all entities in the data source.
	 *
	 * @return {@link List} of all entities
	 */
	public List<T> findAll() {

		CriteriaQuery<T> criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery(getEntityClass());
		TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery.select(criteriaQuery.from(getEntityClass())));

		return query.getResultList();
	}

	/**
	 * Read: finds entities in the the data source using a {@link NamedQuery}.
	 *
	 * @param name       name of the query to use
	 * @param parameters parameters to pass to the query
	 * @param <P>        type of the parameters
	 *
	 * @return a {@link List} with the found entities
	 */
	protected <P> List<T> findByQuery(String name, Map<String, P> parameters) {

		TypedQuery<T> query = getEntityManager().createNamedQuery(name, getEntityClass());
		parameters.forEach(query::setParameter);

		return query.getResultList();
	}

	/**
	 * <strong>R</strong>ead: refreshes an entity with the data source.
	 *
	 * @param entity entity to refresh
	 *
	 * @return refreshed entity
	 */
	public T refresh(T entity) {

		getEntityManager().refresh(entity);
		return entity;
	}

	/**
	 * <strong>U</strong>pdate: merges an entity into the data source.
	 *
	 * @param entity entity to update
	 *
	 * @return merged entity
	 */
	public T merge(T entity) {

		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		try {
			entity = entityManager.merge(entity);
			transaction.commit();

			return entity;

		} catch (Exception ex) {
			if (transaction.isActive())
				transaction.rollback();

			throw new PersistenceException("Could not merge the entity.", ex);
		}
	}

	/**
	 * <strong>D</strong>elete: removes an entity from the data source.
	 *
	 * @param entity entity to remove
	 */
	public void remove(T entity) {

		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		try {
			entityManager.remove(entityManager.merge(entity));
			transaction.commit();

		} catch (Exception ex) {
			if (transaction.isActive())
				transaction.rollback();

			throw new PersistenceException("Could not remove the entity.", ex);
		}
	}
}
