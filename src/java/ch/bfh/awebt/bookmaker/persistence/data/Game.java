package ch.bfh.awebt.bookmaker.persistence.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ch.bfh.awebt.bookmaker.converters.LocalDateTimeConverter;

/**
 * Represents a competitive football game between two football {@link Team}s.
 *
 * @author strut1 &amp; touwm1
 */
@Entity
@Table(name = "games")
@NamedQueries({
	@NamedQuery(name = Game.FIND_START_AFTER, query = "select g from Game g where g.startTimeUTC > :startTimeUTC order by g.startTimeUTC"),
	@NamedQuery(name = Game.FIND_START_BEFORE, query = "select g from Game g where g.startTimeUTC <= :startTimeUTC order by g.startTimeUTC desc")})
public class Game extends PersistentObject<Integer> implements Serializable {

	private static final long serialVersionUID = 5535286169721878761L;

	/**
	 * Name of the {@link NamedQuery} to find games starting after a specified start date/time in UTC.
	 */
	public static final String FIND_START_AFTER = "Game.FIND_START_AFTER";

	/**
	 * Name of the {@link NamedQuery} to find games starting before a specified start date/time in UTC.
	 */
	public static final String FIND_START_BEFORE = "Game.FIND_START_BEFORE";

	/**
	 * Gets the UTC (Coordinated Universal Time) time zone.
	 */
	public static final ZoneId ZONE_UTC = ZoneId.of("Z");

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "team1", nullable = false)
	private Team team1;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "team2", nullable = false)
	private Team team2;

	@Column(name = "starttime", nullable = false)
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime startTimeUTC;

	@OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
	private List<Bet> bets;

	/**
	 * Constructs an empty football game.
	 */
	protected Game() {

		bets = new ArrayList<>();
	}

	/**
	 * Constructs an football game with defined teams and start time.
	 *
	 * @param team1        first (home) team to compete in the game
	 * @param team2        second (away) team to compete in the game
	 * @param startTimeUTC date &amp; time the game is scheduled to start in UTC (Coordinated Universal Time)
	 */
	public Game(Team team1, Team team2, LocalDateTime startTimeUTC) {
		this();

		this.team1 = team1;
		this.team2 = team2;
		this.startTimeUTC = startTimeUTC;
	}

	public Game(Team team1, Team team2, ZonedDateTime startTimeZoned) {
		this(team1, team2, startTimeZoned.withZoneSameInstant(ZONE_UTC).toLocalDateTime());
	}

	@Override
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the unique identifier of the game.
	 *
	 * @param id unique identifier of the game
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the first (home) team to compete in the game.
	 *
	 * @return first (home) team to compete in the game
	 */
	public Team getTeam1() {
		return team1;
	}

	/**
	 * Sets the first (home) team to compete in the game.
	 *
	 * @param team first (home) team to compete in the game
	 */
	public void setTeam1(Team team) {
		this.team1 = team;
	}

	/**
	 * Gets the second (away) team to compete in the game.
	 *
	 * @return second (away) team to compete in the game
	 */
	public Team getTeam2() {
		return team2;
	}

	/**
	 * Sets the second (away) team to compete in the game.
	 *
	 * @param team second (away) team to compete in the game
	 */
	public void setTeam2(Team team) {
		this.team2 = team;
	}

	/**
	 * Gets the date &amp; time the game is scheduled to start in UTC (Coordinated Universal Time).
	 *
	 * @return date &amp; time the game is scheduled to start in UTC (Coordinated Universal Time)
	 */
	public LocalDateTime getStartTimeUTC() {
		return startTimeUTC;
	}

	/**
	 * Sets the date &amp; time the game is scheduled to start in UTC (Coordinated Universal Time).
	 *
	 * @param startTimeUTC date &amp; time the game is scheduled to start in UTC (Coordinated Universal Time)
	 */
	public void setStartTimeUTC(LocalDateTime startTimeUTC) {
		this.startTimeUTC = startTimeUTC;
	}

	/**
	 * Gets the date &amp; time the game is scheduled to start.
	 *
	 * @return date &amp; time the game is scheduled to start
	 */
	public ZonedDateTime getStartTimeZoned() {

		return ZonedDateTime.of(startTimeUTC, ZONE_UTC);
	}

	/**
	 * Sets the date &amp; time the game is scheduled to start.
	 *
	 * @param startTimeZoned date &amp; time the game is scheduled to start
	 */
	public void setStartTimeZoned(ZonedDateTime startTimeZoned) {

		this.startTimeUTC = startTimeZoned.withZoneSameInstant(ZONE_UTC).toLocalDateTime();
	}

	/**
	 * Gets the bets available for the game.
	 *
	 * @return unmodifiable {@link List} of the bets available for the game
	 */
	public List<Bet> getBets() {
		return Collections.unmodifiableList(bets);
	}

	/**
	 * Adds a new available bet for the game.
	 *
	 * @param bet bet to add to the game
	 *
	 * @return whether or not the bet was added
	 */
	boolean addBet(Bet bet) {
		return bets.add(bet);
	}
}
