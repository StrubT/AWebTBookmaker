package ch.bfh.awebt.bookmaker.presentation;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ch.bfh.awebt.bookmaker.persistence.GameDAO;
import ch.bfh.awebt.bookmaker.persistence.data.Game;

/**
 * Represents a {@link SessionScoped} {@link ManagedBean} providing bookmaker game helpers.
 *
 * @author strut1 &amp; touwm1
 */
@ManagedBean
@SessionScoped
public class GameBean implements Serializable {

	private static final long serialVersionUID = -6462762135849827257L;

	/**
	 * Gets a {@link List} of all games.
	 *
	 * @return {@link List} of all games
	 */
	public List<Game> getGames() {
		return new GameDAO().findAll();
	}
}
