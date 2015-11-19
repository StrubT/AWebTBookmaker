package ch.bfh.awebt.bookmaker.presentation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import ch.bfh.awebt.bookmaker.persistence.GameDAO;
import ch.bfh.awebt.bookmaker.persistence.TeamDAO;
import ch.bfh.awebt.bookmaker.persistence.UserBetDAO;
import ch.bfh.awebt.bookmaker.persistence.data.Game;
import ch.bfh.awebt.bookmaker.persistence.data.Team;
import ch.bfh.awebt.bookmaker.persistence.data.User;
import ch.bfh.awebt.bookmaker.persistence.data.UserBet;

/**
 * Represents a {@link ViewScoped} {@link ManagedBean} providing bookmaker game helpers.
 *
 * @author strut1 &amp; touwm1
 */
@ManagedBean
@ViewScoped
public class GameBean implements Serializable {

	private static final long serialVersionUID = -6462762135849827257L;

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	private transient GameDAO gameDAO;
	private transient TeamDAO teamDAO;
	private transient UserBetDAO userBetDAO;

	private Integer gameId;
	private String gameTeam1;
	private String gameTeam2;
	private LocalDateTime gameStartTime;
	private ZoneId gameTimeZone;
	private List<UserTypedBet> gameBets;

	@PostConstruct
	public void init() {

		gameTimeZone = loginBean.getTimeZone();
	}

	/**
	 * Sets the {@link LoginBean}. <br>
	 * This method is not to be called by client code, the framework automatically sets the bean instance.
	 *
	 * @param loginBean the bean to use
	 */
	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	private GameDAO getGameDAO() {

		if (gameDAO == null)
			gameDAO = new GameDAO();

		return gameDAO;
	}

	private TeamDAO getTeamDAO() {

		if (teamDAO == null)
			teamDAO = new TeamDAO();

		return teamDAO;
	}

	private UserBetDAO getUserBetDAO() {

		if (userBetDAO == null)
			userBetDAO = new UserBetDAO();

		return userBetDAO;
	}

	/**
	 * Gets the unique identifier of the game.
	 *
	 * @return unique identifier of the game
	 */
	public Integer getGameId() {
		return gameId;
	}

	/**
	 * Sets the unique identifier of the game.
	 * Invoking this method will load the game with the corresponding identifier and set its properties too.
	 *
	 * @param gameId unique identifier of the game
	 */
	public void setGameId(Integer gameId) {

		this.gameId = gameId;

		Game game = gameId != null ? getGame() : null;

		gameTeam1 = game != null ? game.getTeam1().getCode() : null;
		gameTeam2 = game != null ? game.getTeam2().getCode() : null;
		gameStartTime = game != null ? game.getStartTimeZoned().withZoneSameInstant(gameTimeZone).toLocalDateTime() : null;

		User u = loginBean.getUser();
		gameBets = game != null ? game.getBets().stream().map(b -> {
			UserBet c = u != null ? getUserBetDAO().findByUserAndBet(u, b) : null;
			if (c == null)
				c = new UserBet(u != null ? u : User.ANONYMOUS, b, BigDecimal.ZERO);
			return new UserTypedBet(b, c);
		}).collect(Collectors.toList()) : Arrays.asList();
	}

	/**
	 * Gets the first (home) team to compete in the game.
	 *
	 * @return first (home) team to compete in the game
	 */
	public String getGameTeam1() {
		return gameTeam1;
	}

	/**
	 * Sets the first (home) team to compete in the game.
	 *
	 * @param gameTeam1 first (home) team to compete in the game
	 */
	public void setGameTeam1(String gameTeam1) {
		this.gameTeam1 = gameTeam1;
	}

	/**
	 * Gets the second (away) team to compete in the game.
	 *
	 * @return second (away) team to compete in the game
	 */
	public String getGameTeam2() {
		return gameTeam2;
	}

	/**
	 * Sets the second (away) team to compete in the game.
	 *
	 * @param gameTeam2 second (away) team to compete in the game
	 */
	public void setGameTeam2(String gameTeam2) {
		this.gameTeam2 = gameTeam2;
	}

	public LocalDateTime getGameStartTime() {
		return gameStartTime;
	}

	public void setGameStartTime(LocalDateTime gameStartTime) {
		this.gameStartTime = gameStartTime;
	}

	public ZoneId getGameTimeZone() {
		return gameTimeZone;
	}

	public void setGameTimeZone(ZoneId gameTimeZone) {
		this.gameTimeZone = gameTimeZone;
	}

	public List<UserTypedBet> getGameBets() {
		return gameBets;
	}

	public void setGameBets(List<UserTypedBet> gameBets) {
		this.gameBets = gameBets;
	}

	/**
	 * Gets a {@link List} of all games.
	 *
	 * @return {@link List} of all games
	 */
	public List<Game> getGames() {

		return getGameDAO().findAll();
	}

	/**
	 * Gets the upcoming games.
	 *
	 * @return {@link List} of upcoming games
	 */
	public List<Game> getUpcomingGames() {

		return getGameDAO().findUpcoming();
	}

	/**
	 * Gets the past games.
	 *
	 * @return {@link List} of past games
	 */
	public List<Game> getPastGames() {

		return getGameDAO().findPast();
	}

	/**
	 * Gets the game.
	 *
	 * @return game with the specified unique identifier
	 */
	public Game getGame() {

		return getGameDAO().find(gameId);
	}

	/**
	 * Gets a {@link List} of all teams.
	 *
	 * @return {@link List} of all teams
	 */
	public List<Team> getTeams() {

		return getTeamDAO().findAll();
	}
}
