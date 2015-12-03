package ch.bfh.awebt.bookmaker.presentation.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import ch.bfh.awebt.bookmaker.persistence.data.Bet;
import ch.bfh.awebt.bookmaker.persistence.data.BetType;
import ch.bfh.awebt.bookmaker.persistence.data.UserBet;

/**
 * Represents a special presentation data object encapsulating both {@link Bet} and {@link UserBet} data.
 *
 * @author strut1 &amp; touwm1
 */
public class BetDTO implements Serializable {

	private Integer id;
	private BetType type;
	private BigDecimal odds;
	private Boolean occurred;
	private String team;
	private LocalTime time;
	private Integer goals;

	private Integer user;
	private BigDecimal stake;

	private boolean used;

	public BetDTO() {

	}

	/**
	 * Constructs a new data object from a specified {@link Bet} entity.
	 *
	 * @param bet {@link Bet} entity to take data from
	 */
	public BetDTO(Bet bet) {
		this();

		id = bet.getId();
		type = bet.getType();
		odds = bet.getOdds();
		occurred = bet.getOccurred();
		team = bet.getTeam() != null ? bet.getTeam().getCode() : null;
		time = bet.getTime();
		goals = bet.getGoals();

		used = !bet.getUserBets().isEmpty();
	}

	/**
	 * Constructs a new data object from specified {@link Bet} and {@link UserBet} entities.
	 *
	 * @param bet     {@link Bet} entity to take data from
	 * @param userBet {@link UserBet} entity to take data from
	 */
	public BetDTO(Bet bet, UserBet userBet) {
		this(bet);

		user = userBet.getUser().getId();
		stake = userBet.getStake();
	}

	/**
	 * Gets the unique identifier of the bet.
	 *
	 * @return unique identifier of the bet
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the unique identifier of the bet.
	 *
	 * @param id unique identifier of the bet
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the type of the bet.
	 *
	 * @return type of the bet
	 */
	public BetType getType() {
		return type;
	}

	/**
	 * Sets the type of the bet.
	 *
	 * @param type type of the bet
	 */
	public void setType(BetType type) {
		this.type = type;
	}

	/**
	 * Gets the odds for this bet.
	 *
	 * @return odds for this bet
	 */
	public BigDecimal getOdds() {
		return odds;
	}

	/**
	 * Sets the odds for this bet.
	 *
	 * @param odds odds for this bet
	 */
	public void setOdds(BigDecimal odds) {
		this.odds = odds;
	}

	/**
	 * Gets whether or not this the situation this bet refers to actually occurred.
	 *
	 * @return whether or not this the situation this bet refers to actually occurred
	 */
	public Boolean getOccurred() {
		return occurred;
	}

	/**
	 * Sets whether or not this the situation this bet refers to actually occurred.
	 *
	 * @param occurred whether or not this the situation this bet refers to actually occurred
	 */
	public void setOccurred(Boolean occurred) {
		this.occurred = occurred;
	}

	/**
	 * Gets the unique code of the team this bet refers to, if any.
	 *
	 * @return unique code of the team this bet refers to, or {@code null} if none
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * Sets the unique code of the team this bet refers to, if any.
	 *
	 * @param team unique code of the team this bet refers to, or {@code null} if none
	 */
	public void setTeam(String team) {
		this.team = team;
	}

	/**
	 * Gets the playing time this bet refers to, if any.
	 *
	 * @return playing time this bet refers to, or {@code null} if none
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * Sets the playing time this bet refers to, if any.
	 *
	 * @param time playing time this bet refers to, or {@code null} if none
	 */
	public void setTime(LocalTime time) {
		this.time = time;
	}

	/**
	 * Gets the number of goals this bet refers to, if any.
	 *
	 * @return number of goals this bet refers to, or {@code null} if none
	 */
	public Integer getGoals() {
		return goals;
	}

	/**
	 * Sets the number of goals this bet refers to, if any.
	 *
	 * @param goals number of goals this bet refers to, or {@code null} if none
	 */
	public void setGoals(Integer goals) {
		this.goals = goals;
	}

	/**
	 * Gets the unique identifier of the user placing the bet.
	 *
	 * @return unique identifier of the user placing the bet
	 */
	public Integer getUserId() {
		return user;
	}

	/**
	 * Sets the unique identifier of the user placing the bet.
	 *
	 * @param user unique identifier of the user placing the bet
	 */
	public void setUserId(Integer user) {
		this.user = user;
	}

	/**
	 * Gets the stake of the bet.
	 *
	 * @return stake of the bet
	 */
	public BigDecimal getStake() {
		return stake;
	}

	/**
	 * Sets the stake of the bet.
	 *
	 * @param stake stake of the bet
	 */
	public void setStake(BigDecimal stake) {
		this.stake = stake;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
}
