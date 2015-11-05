package ch.bfh.awebt.bookmaker.persistence;

import java.io.Serializable;
import ch.bfh.awebt.bookmaker.persistence.data.Team;

public class TeamDAO extends GenericDAO<Team> implements Serializable {

	private static final long serialVersionUID = -5893067542641772425L;

	@Override
	protected Class<Team> getEntityClass() {
		return Team.class;
	}
}
