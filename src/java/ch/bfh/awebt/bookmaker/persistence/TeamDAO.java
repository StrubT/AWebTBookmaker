package ch.bfh.awebt.bookmaker.persistence;

import java.io.Serializable;
import java.util.List;
import ch.bfh.awebt.bookmaker.persistence.data.Team;

/**
 * Represents a data access object for {@link Team} entities.
 *
 * @author strut1 &amp; touwm1
 */
public class TeamDAO extends GenericDAO<Team, String> implements Serializable {

	private static final long serialVersionUID = -5893067542641772425L;

	@Override
	protected Class<Team> getEntityClass() {
		return Team.class;
	}

	/**
	 * Gets all teams ordered by their unique identifier.
	 *
	 * @return {@link List} of all teams
	 */
	public List<Team> findAllOrderedById() {

		return findByQuery(Team.FIND_ALL_ORDERED_BY_ID_QUERY);
	}
}
