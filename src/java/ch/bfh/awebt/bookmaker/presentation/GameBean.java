package ch.bfh.awebt.bookmaker.presentation;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ch.bfh.awebt.bookmaker.persistence.GameDAO;
import ch.bfh.awebt.bookmaker.persistence.data.Game;

@ManagedBean
@SessionScoped
public class GameBean implements Serializable {

	private static final long serialVersionUID = -6462762135849827257L;

	public List<Game> getGames() {
		return new GameDAO().findAll();
	}
}
