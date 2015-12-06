package ch.bfh.awebt.bookmaker.persistence.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ch.bfh.awebt.bookmaker.converters.BetTypeConverter;
import ch.bfh.awebt.bookmaker.converters.LocalTimeConverter;
import ch.bfh.awebt.bookmaker.converters.OddsConverterValidator;

/**
 * Represents a bet on a {@link Game}.
 *
 * @author strut1 &amp; touwm1
 */
@Entity
@Table(name = "bets")
public class Bet extends PersistentObject<Integer> implements Serializable {

	private static final long serialVersionUID = -7574282003042467392L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "game", nullable = false)
	private Game game;

	@Column(name = "type", nullable = false)
	@Convert(converter = BetTypeConverter.class)
	private BetType type;

	@Column(name = "odds", nullable = false, precision = OddsConverterValidator.PRECISION, scale = OddsConverterValidator.SCALE)
	private BigDecimal odds;

	@Column(name = "occurred")
	private Boolean occurred;

	@ManyToOne
	@JoinColumn(name = "team")
	private Team team;

	@Column(name = "time")
	@Convert(converter = LocalTimeConverter.class)
	private LocalTime time;

	@Column(name = "goals")
	private Integer number;

	@OneToMany(mappedBy = "bet", fetch = FetchType.LAZY)
	private List<UserBet> userBets;

	/**
	 * Constructs an empty bet.
	 */
	protected Bet() {

		userBets = new ArrayList<>();
	}

	/**
	 * Constructs a bet on a game of a specific type and known odds.
	 *
	 * @param game game this bet refers to
	 * @param type type of the bet
	 * @param odds odds for this bet
	 */
	public Bet(Game game, BetType type, BigDecimal odds) {
		this(game, type, odds, null, null, null, null);
	}

	/**
	 * Constructs a bet on a game of a specific type and known odds and outcome.
	 *
	 * @param game     game this bet refers to
	 * @param type     type of the bet
	 * @param odds     odds for this bet
	 * @param occurred whether or not this the situation this bet refers to actually occurred
	 */
	public Bet(Game game, BetType type, BigDecimal odds, Boolean occurred) {
		this(game, type, odds, occurred, null, null, null);
	}

	/**
	 * Constructs a bet on a game of a specific type and known odds, outcome and parameters.
	 *
	 * @param game     game this bet refers to
	 * @param type     type of the bet
	 * @param odds     odds for this bet
	 * @param occurred whether or not this the situation this bet refers to actually occurred
	 * @param team     team this bet refers to
	 */
	public Bet(Game game, BetType type, BigDecimal odds, Boolean occurred, Team team) {
		this(game, type, odds, occurred, team, null, null);
	}

	/**
	 * Constructs a bet on a game of a specific type and known odds, outcome and parameters.
	 *
	 * @param game     game this bet refers to
	 * @param type     type of the bet
	 * @param odds     odds for this bet
	 * @param occurred whether or not this the situation this bet refers to actually occurred
	 * @param time     playing time this bet refers to
	 */
	public Bet(Game game, BetType type, BigDecimal odds, Boolean occurred, LocalTime time) {
		this(game, type, odds, occurred, null, time, null);
	}

	/**
	 * Constructs a bet on a game of a specific type and known odds, outcome and parameters.
	 *
	 * @param game     game this bet refers to
	 * @param type     type of the bet
	 * @param odds     odds for this bet
	 * @param occurred whether or not this the situation this bet refers to actually occurred
	 * @param team     team this bet refers to
	 * @param time     playing time this bet refers to
	 */
	public Bet(Game game, BetType type, BigDecimal odds, Boolean occurred, Team team, LocalTime time) {
		this(game, type, odds, occurred, team, time, null);
	}

	/**
	 * Constructs a bet on a game of a specific type and known odds, outcome and parameters.
	 *
	 * @param game     game this bet refers to
	 * @param type     type of the bet
	 * @param odds     odds for this bet
	 * @param occurred whether or not this the situation this bet refers to actually occurred
	 * @param team     team this bet refers to
	 * @param time     playing time this bet refers to
	 * @param number   number of goals / cards / etc. this bet refers to
	 */
	public Bet(Game game, BetType type, BigDecimal odds, Boolean occurred, Team team, LocalTime time, Integer number) {
		this();

		this.game = game;
		this.type = type;
		this.odds = odds;
		this.occurred = occurred;
		this.team = team;
		this.time = time;
		this.number = number;
	}

	@Override
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the unique identifier of the bet.
	 *
	 * @param id unique identifier of the bet
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the game this bet refers to.
	 *
	 * @return game this bet refers to
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Sets the game this bet refers to.
	 *
	 * @param game game this bet refers to
	 */
	public void setGame(Game game) {
		this.game = game;
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
	 * Gets the team this bet refers to, if any.
	 *
	 * @return team this bet refers to, or {@code null} if none
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Sets the team this bet refers to, if any.
	 *
	 * @param team team this bet refers to, or {@code null} if none
	 */
	public void setTeam(Team team) {
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
	 * Gets the number of goals / cards / etc. this bet refers to, if any.
	 *
	 * @return number of goals / cards / etc. this bet refers to, or {@code null} if none
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * Sets the number of goals / cards / etc. this bet refers to, if any.
	 *
	 * @param number number of goals / cards / etc. this bet refers to, or {@code null} if none
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * Gets the actual bets user placed with the bookmaker.
	 *
	 * @return an unmodifiable {@link List} actual bets placed by user
	 */
	public List<UserBet> getUserBets() {
		return Collections.unmodifiableList(userBets);
	}
}
