package ch.bfh.awebt.bookmaker.persistence;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import ch.bfh.awebt.bookmaker.MapBuilder;
import ch.bfh.awebt.bookmaker.persistence.data.Game;

/**
 * Represents a data access object for {@link Game} entities.
 *
 * @author strut1 &amp; touwm1
 */
public class GameDAO extends GenericDAO<Game> implements Serializable {

	private static final long serialVersionUID = -5138682905529615638L;

	@Override
	protected Class<Game> getEntityClass() {
		return Game.class;
	}

	/**
	 * Gets the upcoming games.
	 *
	 * @return {@link List} of upcoming games
	 */
	public List<Game> findUpcoming() {

		return findByQuery(Game.FIND_START_AFTER, MapBuilder.single("startTimeUTC", ZonedDateTime.now(Game.ZONE_UTC).toLocalDateTime()));
	}

	/**
	 * Gets the past games.
	 *
	 * @return {@link List} of past games
	 */
	public List<Game> findPast() {

		return findByQuery(Game.FIND_START_BEFORE, MapBuilder.single("startTimeUTC", ZonedDateTime.now(Game.ZONE_UTC).toLocalDateTime()));
	}
}
