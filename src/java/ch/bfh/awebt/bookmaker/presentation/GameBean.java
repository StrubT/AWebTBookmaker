package ch.bfh.awebt.bookmaker.presentation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.persistence.PersistenceException;
import ch.bfh.awebt.bookmaker.persistence.BetDAO;
import ch.bfh.awebt.bookmaker.persistence.GameDAO;
import ch.bfh.awebt.bookmaker.persistence.TeamDAO;
import ch.bfh.awebt.bookmaker.persistence.UserBetDAO;
import ch.bfh.awebt.bookmaker.persistence.UserDAO;
import ch.bfh.awebt.bookmaker.persistence.data.Bet;
import ch.bfh.awebt.bookmaker.persistence.data.Game;
import ch.bfh.awebt.bookmaker.persistence.data.Team;
import ch.bfh.awebt.bookmaker.persistence.data.User;
import ch.bfh.awebt.bookmaker.persistence.data.UserBet;
import ch.bfh.awebt.bookmaker.presentation.data.BetDTO;

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

	private transient UIComponent creditCardNumberField, creditCardCodeField;

	private transient TeamDAO teamDAO;
	private transient GameDAO gameDAO;
	private transient UserDAO userDAO;
	private transient BetDAO betDAO;
	private transient UserBetDAO userBetDAO;

	private boolean statusConfirmation;

	private Integer gameId, gameBetsUserId;
	private String gameTeam1, gameTeam2;
	private LocalDateTime gameStartTime;
	private ZoneId gameTimeZone;
	private List<BetDTO> gameBets;
	private BigDecimal gameBetAmount, gamePaymentAmount;
	private String gamePaymentCreditCardNumber, gamePaymentCreditCardCode;
	private Integer gamePaymentCreditCardExpirationMonth, gamePaymentCreditCardExpirationYear, gamePaymentCreditCardExpirationYearMinimum, gamePaymentCreditCardExpirationYearMaximum;

	/**
	 * Initialises the managed bean.
	 */
	@PostConstruct
	public void init() {

		gameTimeZone = loginBean.getTimeZone();
	}

	/**
	 * Gets the {@link LoginBean}. <br>
	 * This method is not to be called by client code, the framework automatically sets the bean instance.
	 *
	 * @return the {@link LoginBean} to use
	 */
	public LoginBean getLoginBean() {
		return loginBean;
	}

	/**
	 * Sets the {@link LoginBean}. <br>
	 * This method is not to be called by client code, the framework automatically sets the bean instance.
	 *
	 * @param loginBean the {@link LoginBean} to use
	 */
	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public UIComponent getCreditCardNumberField() {
		return creditCardNumberField;
	}

	public void setCreditCardNumberField(UIComponent creditCardNumberField) {
		this.creditCardNumberField = creditCardNumberField;
	}

	public UIComponent getCreditCardCodeField() {
		return creditCardCodeField;
	}

	public void setCreditCardCodeField(UIComponent creditCardCodeField) {
		this.creditCardCodeField = creditCardCodeField;
	}

	private TeamDAO getTeamDAO() {

		if (teamDAO == null)
			teamDAO = new TeamDAO();

		return teamDAO;
	}

	private GameDAO getGameDAO() {

		if (gameDAO == null)
			gameDAO = new GameDAO();

		return gameDAO;
	}

	private UserDAO getUserDAO() {

		if (userDAO == null)
			userDAO = new UserDAO();

		return userDAO;
	}

	private BetDAO getBetDAO() {

		if (betDAO == null)
			betDAO = new BetDAO();

		return betDAO;
	}

	private UserBetDAO getUserBetDAO() {

		if (userBetDAO == null)
			userBetDAO = new UserBetDAO();

		return userBetDAO;
	}

	public boolean isConfirmation() {

		return statusConfirmation;
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
		setGameId(gameId, false);
	}

	private void setGameId(Integer gameId, boolean force) {

		if (force || this.gameId == null || !this.gameId.equals(gameId)) {
			this.gameId = gameId;

			Game game = gameId != null ? getGame() : null;
			if (game != null) {
				gameTeam1 = game.getTeam1().getCode();
				gameTeam2 = game.getTeam2().getCode();
				gameStartTime = game.getStartTimeZoned().withZoneSameInstant(gameTimeZone).toLocalDateTime();

				User u = loginBean.getUser();
				gameBetsUserId = u != null ? u.getId() : null;
				gameBets = new ArrayList<>(game.getBets()).stream().map(b -> { //BUGFIX: new ArrayList<>(...) needed in eclipselink < 2.7
					UserBet c = u != null ? getUserBetDAO().findByUserAndBet(u, b) : null;
					return new BetDTO(b, c != null ? c : new UserBet(u != null ? u : User.ANONYMOUS, b, BigDecimal.ZERO));
				}).collect(Collectors.toList());

			} else {
				if (gameId != null)
					MessageFactory.addWarning("ch.bfh.awebt.bookmaker.ITEM_NOT_FOUND_ERROR");

				gameTeam1 = gameTeam2 = null;
				gameStartTime = null;
				gameBets = Arrays.asList();
			}
		}
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

	/**
	 * Gets the date &amp; time the game is scheduled to start.
	 *
	 * @return date &amp; time the game is scheduled to start
	 */
	public LocalDateTime getGameStartTime() {
		return gameStartTime;
	}

	/**
	 * Sets the date &amp; time the game is scheduled to start.
	 *
	 * @param gameStartTime date &amp; time the game is scheduled to start
	 */
	public void setGameStartTime(LocalDateTime gameStartTime) {
		this.gameStartTime = gameStartTime;
	}

	/**
	 * Gets the time zone the game's scheduled start date &amp; time.
	 *
	 * @return time zone the game's scheduled start date &amp; time
	 */
	public ZoneId getGameTimeZone() {
		return gameTimeZone;
	}

	/**
	 * Sets the time zone the game's scheduled start date &amp; time.
	 *
	 * @param gameTimeZone time zone the game's scheduled start date &amp; time
	 */
	public void setGameTimeZone(ZoneId gameTimeZone) {

		if (gameStartTime != null)
			gameStartTime = gameStartTime.atZone(this.gameTimeZone).withZoneSameInstant(gameTimeZone).toLocalDateTime();

		this.gameTimeZone = gameTimeZone;
	}

	public ZonedDateTime getGameStartTimeZoned() {

		return gameStartTime != null ? ZonedDateTime.of(gameStartTime, gameTimeZone) : null;
	}

	/**
	 * Gets the bets associated with the game.
	 *
	 * @return bets associated with the game
	 */
	public List<BetDTO> getGameBets() {

		if (loginBean.getUserId() != null && !loginBean.getUserId().equals(gameBetsUserId))
			setGameId(gameId, true);

		return gameBets;
	}

	/**
	 * Sets the bets associated with the game.
	 *
	 * @param gameBets bets associated with the game
	 */
	public void setGameBets(List<BetDTO> gameBets) {
		this.gameBets = gameBets;
	}

	/**
	 * Gets the amount to bet.
	 *
	 * @return the amount to bet
	 */
	public BigDecimal getGameBetAmount() {
		return gameBetAmount;
	}

	/**
	 * Sets the amount to bet.
	 *
	 * @param gameBetAmount the amount to bet
	 */
	public void setGameBetAmount(BigDecimal gameBetAmount) {
		this.gameBetAmount = gameBetAmount;
	}

	/**
	 * Gets the amount to pay.
	 *
	 * @return amount to pay
	 */
	public BigDecimal getGamePaymentAmount() {
		return gamePaymentAmount;
	}

	/**
	 * Sets the amount to pay.
	 *
	 * @param gamePaymentAmount amount to pay
	 */
	public void setGamePaymentAmount(BigDecimal gamePaymentAmount) {
		this.gamePaymentAmount = gamePaymentAmount;
	}

	/**
	 * Gets the number of the credit card used to pay the bets.
	 *
	 * @return number of the credit card
	 */
	public String getGamePaymentCreditCardNumber() {
		return gamePaymentCreditCardNumber;
	}

	/**
	 * Sets the number of the credit card used to pay the bets.
	 *
	 * @param gamePaymentCreditCardNumber number of the credit card
	 */
	public void setGamePaymentCreditCardNumber(String gamePaymentCreditCardNumber) {
		this.gamePaymentCreditCardNumber = gamePaymentCreditCardNumber;
	}

	/**
	 * Gets the code of the credit card used to pay the bets.
	 *
	 * @return code of the credit card
	 */
	public String getGamePaymentCreditCardCode() {
		return gamePaymentCreditCardCode;
	}

	/**
	 * Sets the code of the credit card used to pay the bets.
	 *
	 * @param gamePaymentCreditCardCode code of the credit card
	 */
	public void setGamePaymentCreditCardCode(String gamePaymentCreditCardCode) {
		this.gamePaymentCreditCardCode = gamePaymentCreditCardCode;
	}

	/**
	 * Gets the expiration month the credit card used to pay the bets.
	 *
	 * @return expiration month the credit card
	 */
	public Integer getGamePaymentCreditCardExpirationMonth() {
		return gamePaymentCreditCardExpirationMonth;
	}

	/**
	 * Sets the expiration month the credit card used to pay the bets.
	 *
	 * @param gamePaymentCreditCardExpirationMonth expiration month the credit card
	 */
	public void setGamePaymentCreditCardExpirationMonth(Integer gamePaymentCreditCardExpirationMonth) {
		this.gamePaymentCreditCardExpirationMonth = gamePaymentCreditCardExpirationMonth;
	}

	/**
	 * Gets the expiration year the credit card used to pay the bets.
	 *
	 * @return expiration year the credit card
	 */
	public Integer getGamePaymentCreditCardExpirationYear() {
		return gamePaymentCreditCardExpirationYear;
	}

	/**
	 * Sets the expiration year the credit card used to pay the bets.
	 *
	 * @param gamePaymentCreditCardExpirationYear expiration year the credit card
	 */
	public void setGamePaymentCreditCardExpirationYear(Integer gamePaymentCreditCardExpirationYear) {
		this.gamePaymentCreditCardExpirationYear = gamePaymentCreditCardExpirationYear;
	}

	/**
	 * Gets the minimum expiration year for a valid credit card.
	 *
	 * @return minimum expiration year for a valid credit card
	 */
	public Integer getGamePaymentCreditCardExpirationYearMinimum() {
		return gamePaymentCreditCardExpirationYearMinimum;
	}

	/**
	 * Gets the maximum expiration year for a valid credit card.
	 *
	 * @return maximum expiration year for a valid credit card
	 */
	public Integer getGamePaymentCreditCardExpirationYearMaximum() {
		return gamePaymentCreditCardExpirationYearMaximum;
	}

	/**
	 * Gets a {@link List} of all teams.
	 *
	 * @return {@link List} of all teams
	 */
	public List<Team> getTeams() {

		return getTeamDAO().findAll();
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
	 * Saves (inserts or updates) the game.
	 *
	 * @return outcome: identifier of the view to navigate to
	 */
	public String saveGame() {

		try {
			Team team1 = getTeamDAO().find(gameTeam1);
			Team team2 = getTeamDAO().find(gameTeam2);
			ZonedDateTime startTimeZoned = ZonedDateTime.of(gameStartTime, gameTimeZone);

			Game game = gameId != null ? getGameDAO().find(gameId) : null;
			if (game != null) {
				game.setTeam1(team1);
				game.setTeam2(team2);
				game.setStartTimeZoned(startTimeZoned);
				getGameDAO().merge(game);

			} else {
				game = new Game(team1, team2, startTimeZoned);
				getGameDAO().persist(game);
			}

			return String.format("/players/game.xhtml?id=%d&faces-redirect=true", game.getId());

		} catch (PersistenceException ex) {
			MessageFactory.addError("ch.bfh.awebt.bookmaker.PERSISTENCE_ERROR");
		}

		return null;
	}

	/**
	 * Deletes the game.
	 *
	 * @return outcome: identifier of the view to navigate to
	 */
	public String deleteGame() {

		try {
			Game game = gameId != null ? getGameDAO().find(gameId) : null;
			if (game != null) {
				getGameDAO().remove(game);
				return "/managers/upcoming-games.xhtml";

			} else
				MessageFactory.addWarning("ch.bfh.awebt.bookmaker.ITEM_NOT_FOUND_ERROR");

		} catch (PersistenceException ex) {
			MessageFactory.addError("ch.bfh.awebt.bookmaker.PERSISTENCE_ERROR");
		}

		return null;
	}

	/**
	 * Shows the confirmation page for changing user bets.
	 */
	public void confirmUserBets() {

		statusConfirmation = true;

		UserBetDAO userBetDAO = getUserBetDAO();
		gameBetAmount = BigDecimal.ZERO;
		for (BetDTO bet: gameBets) {
			UserBet userBet = userBetDAO.findByUserAndBet(loginBean.getUserId(), bet.getId());
			gameBetAmount = gameBetAmount.add(bet.getStake().subtract(userBet != null ? userBet.getStake() : BigDecimal.ZERO));
		}

		User user = loginBean.getUser();
		gamePaymentAmount = gameBetAmount.compareTo(user.getBalance()) > 0 ? gameBetAmount.subtract(user.getBalance()) : BigDecimal.ZERO;

		gamePaymentCreditCardNumber = gamePaymentCreditCardCode = null;

		LocalDate date = LocalDate.now();
		gamePaymentCreditCardExpirationYearMinimum = gamePaymentCreditCardExpirationYear = date.getYear();
		gamePaymentCreditCardExpirationYearMaximum = date.getYear() + 25;
		gamePaymentCreditCardExpirationMonth = date.getMonthValue();
	}

	/**
	 * Discards all changed user bets.
	 */
	public void cancelUserBets() {

		statusConfirmation = false;

		Integer gameId = this.gameId;
		setGameId(gameId, true); //refresh all properties
	}

	/**
	 * Saves the stakes the user put on the bets.
	 */
	public void saveUserBets() {

		try {
			User user = loginBean.getUser();
			BetDAO betDAO = getBetDAO();
			UserBetDAO userBetDAO = getUserBetDAO();

			gamePaymentAmount = gameBetAmount.compareTo(user.getBalance()) > 0 ? gameBetAmount.subtract(user.getBalance()) : BigDecimal.ZERO;
			if (gamePaymentAmount.compareTo(BigDecimal.ZERO) > 0 && !confirmPayment()) {
				MessageFactory.addWarning("ch.bfh.awebt.bookmaker.PAYMENT_ERROR");
				return;
			}

			user.deposit(gamePaymentAmount);
			user.withdraw(gameBetAmount);
			getUserDAO().merge(user);

			for (BetDTO betDTO: gameBets) {
				Bet bet = betDAO.find(betDTO.getId());
				UserBet userBet = userBetDAO.findByUserAndBet(user, bet);
				boolean persist = betDTO.getStake().compareTo(BigDecimal.ZERO) > 0;

				if (userBet != null)
					if (persist) {
						userBet.setStake(betDTO.getStake());
						userBetDAO.merge(userBet);
					} else
						userBetDAO.remove(userBet);

				else if (persist)
					userBetDAO.persist(new UserBet(user, bet, betDTO.getStake()));
			}

			statusConfirmation = false;

		} catch (PersistenceException ex) {
			MessageFactory.addError("ch.bfh.awebt.bookmaker.PERSISTENCE_ERROR");
		}
	}

	private boolean confirmPayment() {

		LocalDate now = LocalDate.now();
		LocalDate exp = LocalDate.of(gamePaymentCreditCardExpirationYear, gamePaymentCreditCardExpirationMonth, 1).plusMonths(1).minusDays(1);

		return gamePaymentCreditCardCode != null && gamePaymentCreditCardCode != null && now.compareTo(exp) < 0; //implement actual payment process
	}
}
