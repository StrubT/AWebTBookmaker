package ch.bfh.awebt.bookmaker.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import ch.bfh.awebt.bookmaker.persistence.data.PersistentObject;

/**
 * Represents a data access object for a generic entity type.
 *
 * @param <T> type of the entities
 *
 * @author strut1 &amp; touwm1
 */
public abstract class GenericDAO<T extends PersistentObject> implements Serializable {

	private static final long serialVersionUID = -6512800996496357465L;

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
	 * <strong>C</strong>reates / persists an entity in the data source.
	 *
	 * @param entity entity to persist
	 *
	 * @return persisted entity
	 */
	public T create(T entity) {

		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		try {
			entityManager.persist(entity);
			transaction.commit();

		} catch (Exception ex) {
			transaction.rollback();
			throw ex;
		}

		return entity;
	}

	/**
	 * <strong>R</strong>ead: finds an entity in the data source by its unique identifier.
	 *
	 * @param id unique identifier of the entity
	 *
	 * @return entity with the specified unique identifier
	 */
	public T find(int id) {

		return getEntityManager().find(getEntityClass(), id);
	}

	/**
	 * Read: finds entities in the the data source using a {@link NamedQuery}.
	 *
	 * @param name       name of the query to use
	 * @param parameters parameters to pass to the query
	 *
	 * @return a {@link List} with the found entities
	 */
	public List<T> findByQuery(String name, Map<String, Object> parameters) {

		TypedQuery<T> query = getEntityManager().createNamedQuery(name, getEntityClass());
		parameters.forEach(query::setParameter);

		return query.getResultList();
	}

	/**
	 * <strong>U</strong>pdates / merges an entity into the data source.
	 *
	 * @param entity entity to update
	 *
	 * @return merged entity
	 */
	public T update(T entity) {

		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		try {
			entity = entityManager.merge(entity);
			transaction.commit();

		} catch (Exception ex) {
			transaction.rollback();
			throw ex;
		}

		return entity;
	}

	/**
	 * <strong>D</strong>eletes / removes an entity from the data source.
	 *
	 * @param entity entity to remove
	 */
	public void delete(T entity) {

		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		try {
			entity = entityManager.merge(entity);
			entityManager.remove(entity);
			transaction.commit();

		} catch (Exception ex) {
			transaction.rollback();
			throw ex;
		}
	}
}
