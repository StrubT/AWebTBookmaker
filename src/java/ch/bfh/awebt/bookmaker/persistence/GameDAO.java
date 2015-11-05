package ch.bfh.awebt.bookmaker.persistence;

import java.io.Serializable;
import ch.bfh.awebt.bookmaker.persistence.data.Game;

public class GameDAO extends GenericDAO<Game> implements Serializable {

	private static final long serialVersionUID = -5138682905529615638L;

	@Override
	protected Class<Game> getEntityClass() {
		return Game.class;
	}
}
