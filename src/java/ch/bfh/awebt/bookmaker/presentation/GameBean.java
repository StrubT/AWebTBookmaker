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
import ch.bfh.awebt.bookmaker.Streams;
import ch.bfh.awebt.bookmaker.persistence.BetDAO;
import ch.bfh.awebt.bookmaker.persistence.GameDAO;
import ch.bfh.awebt.bookmaker.persistence.TeamDAO;
import ch.bfh.awebt.bookmaker.persistence.UserBetDAO;
import ch.bfh.awebt.bookmaker.persistence.UserDAO;
import ch.bfh.awebt.bookmaker.persistence.data.Bet;
import ch.bfh.awebt.bookmaker.persistence.data.BetType;
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

	private transient UIComponent creditCardNumberField, creditCardCodeField, startTimeField;

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
	private boolean gameUsed;

	private BigDecimal gameBetAmount, gamePaymentAmount;
	private String gamePaymentCreditCardNumber, gamePaymentCreditCardCode;
	private Integer gamePaymentCreditCardExpirationMonth, gamePaymentCreditCardExpirationYear;

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

	/**
	 * Gets the credit card number field of the current view.
	 *
	 * @return credit card number field of the current view
	 */
	public UIComponent getCreditCardNumberField() {
		return creditCardNumberField;
	}

	/**
	 * Sets the credit card number field of the current view.
	 *
	 * @param creditCardNumberField credit card number field of the current view
	 */
	public void setCreditCardNumberField(UIComponent creditCardNumberField) {
		this.creditCardNumberField = creditCardNumberField;
	}

	/**
	 * Gets the credit card validation code field of the current view.
	 *
	 * @return credit card validation code field of the current view
	 */
	public UIComponent getCreditCardCodeField() {
		return creditCardCodeField;
	}

	/**
	 * Sets the credit card validation code field of the current view.
	 *
	 * @param creditCardCodeField credit card validation code field of the current view
	 */
	public void setCreditCardCodeField(UIComponent creditCardCodeField) {
		this.creditCardCodeField = creditCardCodeField;
	}

	/**
	 * Gets the game start time field of the current view.
	 *
	 * @return game start time field of the current view
	 */
	public UIComponent getStartTimeField() {
		return startTimeField;
	}

	/**
	 * Sets the game start time field of the current view.
	 *
	 * @param startTimeField game start time field of the current view
	 */
	public void setStartTimeField(UIComponent startTimeField) {
		this.startTimeField = startTimeField;
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

	/**
	 * Gets whether or not to show the confirmation view.
	 *
	 * @return whether or not to show the confirmation view
	 */
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

			User u = loginBean.getUser();
			gameBetsUserId = u != null ? u.getId() : null;

			Game game = gameId != null ? getGame() : null;
			if (game != null) {
				gameTeam1 = game.getTeam1().getCode();
				gameTeam2 = game.getTeam2().getCode();
				gameStartTime = game.getStartTimeZoned().withZoneSameInstant(gameTimeZone).toLocalDateTime();

				gameBets = new ArrayList<>(game.getBets()).stream().map(b -> { //BUGFIX: new ArrayList<>(...) needed in eclipselink < 2.7
					UserBet c = u != null ? getUserBetDAO().findByUserAndBet(u, b) : null;
					return new BetDTO(b, c != null ? c : new UserBet(u != null ? u : User.ANONYMOUS, b, BigDecimal.ZERO));
				}).collect(Collectors.toList());
				gameUsed = gameBets.stream().anyMatch(BetDTO::isUsed);

			} else {
				if (gameId != null)
					MessageFactory.addWarning("ch.bfh.awebt.bookmaker.ITEM_NOT_FOUND_ERROR");

				gameTeam1 = gameTeam2 = null;
				gameStartTime = null;

				gameBets = loginBean.getNavigationBean().getCurrentView().equals("/managers/game.xhtml") ? new ArrayList<>(Arrays.asList(new BetDTO())) : new ArrayList<>();
				gameUsed = false;
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

	/**
	 * Gets the game's scheduled start date &amp; time.
	 *
	 * @return game's scheduled start date &amp; time
	 */
	public ZonedDateTime getGameStartTimeZoned() {

		return gameStartTime != null ? ZonedDateTime.of(gameStartTime, gameTimeZone) : null;
	}

	/**
	 * Gets whether or not the game has started / passed.
	 *
	 * @return whether or not the game has started / passed
	 */
	public boolean isGamePassed() {

		return gameStartTime != null ? gameStartTime.isBefore(LocalDateTime.now(gameTimeZone)) : false;
	}

	/**
	 * Gets the bets associated with the game.
	 *
	 * @return bets associated with the game
	 */
	public List<BetDTO> getGameBets() {

		if (loginBean.getUserId() != null && !loginBean.getUserId().equals(gameBetsUserId))
			setGameId(gameId, true); //reload stakes

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
	 * Gets whether or not the game is in use.
	 * (Whether any user put stakes on its bets.)
	 *
	 * @return whether or not the game is in use
	 */
	public boolean isGameUsed() {
		return gameUsed;
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

		return LocalDate.now().getYear();
	}

	/**
	 * Gets the maximum expiration year for a valid credit card.
	 *
	 * @return maximum expiration year for a valid credit card
	 */
	public Integer getGamePaymentCreditCardExpirationYearMaximum() {

		return LocalDate.now().getYear() + 25;
	}

	public BigDecimal getGameBetsAmountWon() {

		return gameBets.stream().filter(b -> Boolean.TRUE.equals(b.getOccurred()))
			.reduce(BigDecimal.ZERO, (a, b) -> a.add(b.getStake().multiply(b.getOdds())), (a, b) -> a.add(b));
	}

	public BigDecimal getGameBetsAmountLost() {

		return gameBets.stream().filter(b -> Boolean.FALSE.equals(b.getOccurred()))
			.reduce(BigDecimal.ZERO, (a, b) -> a.add(b.getStake()), (a, b) -> a.add(b));
	}

	public BigDecimal getGameBetsAmountSum() {

		return getGameBetsAmountWon().subtract(getGameBetsAmountLost());
	}

	/**
	 * Gets the number of bets the user put stakes on.
	 *
	 * @return number of bets the user put stakes on
	 */
	public int getUserNumberOfBets() {

		return loginBean.getUser().getBets().size();
	}

	/**
	 * Gets the percentage of bets the user won.
	 *
	 * @return percentage of bets the user won
	 */
	public double getUserBetsPercentageWon() {

		List<UserBet> bets = loginBean.getUser().getBets();
		if (bets.size() == 0)
			return 0.0;

		return new ArrayList<>(bets).stream().filter(b -> Boolean.TRUE.equals(b.getBet().getOccurred())).count() / (double)bets.size(); //BUGFIX: new ArrayList<>(...) needed in eclipselink < 2.7
	}

	/**
	 * Gets the percentage of bets the user lost.
	 *
	 * @return percentage of bets the user lost
	 */
	public double getUserBetsPercentageLost() {

		List<UserBet> bets = loginBean.getUser().getBets();
		if (bets.size() == 0)
			return 0.0;

		return new ArrayList<>(bets).stream().filter(b -> Boolean.FALSE.equals(b.getBet().getOccurred())).count() / (double)bets.size(); //BUGFIX: new ArrayList<>(...) needed in eclipselink < 2.7
	}

	/**
	 * Gets the amount the user won.
	 *
	 * @return amount the user won
	 */
	public BigDecimal getUserBetsAmountWon() {

		return new ArrayList<>(loginBean.getUser().getBets()).stream().filter(b -> Boolean.TRUE.equals(b.getBet().getOccurred())) //BUGFIX: new ArrayList<>(...) needed in eclipselink < 2.7
			.reduce(BigDecimal.ZERO, (a, b) -> a.add(b.getStake().multiply(b.getBet().getOdds())), (a, b) -> a.add(b));
	}

	/**
	 * Gets the amount the user lost.
	 *
	 * @return amount the user lost
	 */
	public BigDecimal getUserBetsAmountLost() {

		return new ArrayList<>(loginBean.getUser().getBets()).stream().filter(b -> Boolean.FALSE.equals(b.getBet().getOccurred())) //BUGFIX: new ArrayList<>(...) needed in eclipselink < 2.7
			.reduce(BigDecimal.ZERO, (a, b) -> a.add(b.getStake()), (a, b) -> a.add(b));
	}

	/**
	 * Gets the amount the user actually won/lost. <br>
	 * This returns the the won amount minus the lost amount.
	 *
	 * @return amount the user actually won/lost
	 *
	 * @see #getUserBetsAmountWon()
	 * @see #getUserBetsAmountLost()
	 */
	public BigDecimal getUserBetsAmountSum() {

		return getUserBetsAmountWon().subtract(getUserBetsAmountLost());
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
	 * Gets the game.
	 *
	 * @return game with the specified unique identifier
	 */
	public Game getGame() {

		return getGameDAO().find(gameId);
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
	 * Gets the supported bet types.
	 *
	 * @return all supported bet types
	 */
	public List<BetType> getBetTypes() {

		return Arrays.asList(BetType.values());
	}

	/**
	 * Adds a new (empty) bet to the list.
	 */
	public void addNewBet() {

		gameBets.add(new BetDTO());
	}

	/**
	 * Removes a specific bet from the list.
	 *
	 * @param betDTO bet to remove
	 */
	public void removeBet(BetDTO betDTO) {

		gameBets.remove(betDTO);
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
			boolean passed = game != null && game.getStartTimeUTC() != null ? game.getStartTimeUTC().isBefore(LocalDateTime.now(Game.ZONE_UTC)) : false;

			if (game != null) {
				game.setTeam1(team1);
				game.setTeam2(team2);
				game.setStartTimeZoned(startTimeZoned);
				getGameDAO().merge(game);

			} else {
				getGameDAO().persist(game = new Game(team1, team2, startTimeZoned));
				gameId = game.getId();
			}

			List<Bet> gameBetsOld = game.getBets();
			for (BetDTO betDTO: gameBets) {
				Bet bet = betDTO.getId() != null ? new ArrayList<>(gameBetsOld).stream().filter(b -> b.getId().equals(betDTO.getId())).collect(Streams.nullableSingleCollector()) : null; //BUGFIX: new ArrayList<>(...) needed in eclipselink < 2.7
				if (bet != null) {
					if (bet.getType().isTeamRequired() && betDTO.getTeam() == null
							&& bet.getType().isTimeRequired() && betDTO.getTime() == null
							&& bet.getType().isNumberRequired() && betDTO.getNumber() == null) {
						MessageFactory.addWarning("ch.bfh.awebt.bookmaker.GAME_BET_PROPERTY_MISSING");
						return null;
					}

					if (!passed)
						if (bet.getUserBets().isEmpty()) {
							bet.setType(betDTO.getType());
							bet.setTeam(betDTO.getTeam() != null ? getTeamDAO().find(betDTO.getTeam()) : null);
							bet.setTime(betDTO.getTime());
							bet.setNumber(betDTO.getNumber());
							bet.setOdds(betDTO.getOdds());
							getBetDAO().merge(bet);

						} else
							continue; //don't save changes

					else { //already passed
						Boolean old = bet.getOccurred();
						bet.setOccurred(betDTO.getOccurred());
						getBetDAO().merge(bet);

						for (UserBet userBet: bet.getUserBets()) {
							User user = getUserDAO().find(userBet.getUser().getId());
							if (old != null)
								user.bookAmount(bet.getOdds().multiply(userBet.getStake()), !old); //reverse old setting
							if (bet.getOccurred() != null)
								user.bookAmount(bet.getOdds().multiply(userBet.getStake()), bet.getOccurred()); //apply new outcome

							getUserDAO().merge(user);
						}
					}

				} else if (!passed)
					getBetDAO().persist(bet = new Bet(game, betDTO.getType(), betDTO.getOdds(), null, getTeamDAO().find(betDTO.getTeam()), betDTO.getTime(), betDTO.getNumber()));

				else
					MessageFactory.addError("ch.bfh.awebt.bookmaker.GAME_CANNOT_CREATE_PAST");
			}

			if (!passed)
				for (Bet bet: gameBetsOld) {
					BetDTO betDTO = gameBets.stream().filter(b -> bet.getId().equals(b.getId())).collect(Streams.nullableSingleCollector());
					if (betDTO == null)
						if (bet.getUserBets().isEmpty())
							getBetDAO().remove(bet);

						else {
							MessageFactory.addError("ch.bfh.awebt.bookmaker.GAME_CANNOT_DELETE_IN_USE");
							return null;
						}
				}

			//return String.format("/players/game.xhtml?id=%d&faces-redirect=true", game.getId());
			return String.format("/managers/%s-games.xhtml?faces-redirect=true", isGamePassed() ? "past" : "upcoming");

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
			if (game != null)
				if (!new ArrayList<>(game.getBets()).stream().anyMatch(b -> !b.getUserBets().isEmpty())) { //BUGFIX: new ArrayList<>(...) needed in eclipselink < 2.7
					getGameDAO().remove(game);
					return "/managers/upcoming-games.xhtml?faces-redirect=true";

				} else
					MessageFactory.addError("ch.bfh.awebt.bookmaker.GAME_CANNOT_DELETE_IN_USE");

			else
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

		if (!isGamePassed()) {
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
			gamePaymentCreditCardExpirationYear = date.getYear();
			gamePaymentCreditCardExpirationMonth = date.getMonthValue();

		} else
			MessageFactory.addError("ch.bfh.awebt.bookmaker.GAME_CANNOT_BET_PAST");
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

		if (!isGamePassed())
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

		else
			MessageFactory.addError("ch.bfh.awebt.bookmaker.GAME_CANNOT_BET_PAST");
	}

	/**
	 * Confirms the payment succeeded.
	 *
	 * @return whether or not the payment succeeded
	 */
	private boolean confirmPayment() {

		LocalDate now = LocalDate.now();
		LocalDate exp = LocalDate.of(gamePaymentCreditCardExpirationYear, gamePaymentCreditCardExpirationMonth, 1).plusMonths(1).minusDays(1);

		//implement actual payment process
		//
		return gamePaymentCreditCardCode != null && gamePaymentCreditCardCode != null && now.compareTo(exp) < 0;
	}
}
