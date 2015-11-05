package ch.bfh.awebt.bookmaker.persistence.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import ch.bfh.awebt.bookmaker.converters.LocalTimeConverter;

@Entity
@Table(name = "bets")
public class Bet extends PersistentObject<Integer> implements Serializable {

	private static final long serialVersionUID = -7574282003042467392L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "game", nullable = false)
	private Game game;

	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private BetType type;

	@Column(name = "odds", nullable = false, precision = 10, scale = 3)
	private BigDecimal odds;

	@Column(name = "occurred")
	private Boolean occurred;

	@ManyToOne(optional = false)
	@JoinColumn(name = "team")
	private Team team;

	@Column(name = "time")
	@Convert(converter = LocalTimeConverter.class)
	private LocalTime time;

	@Column(name = "goals")
	private Integer goals;

	protected Bet() {
	}

	public Bet(int id, Game game, BetType type, BigDecimal odds) {
		this(id, game, type, odds, null, null, null, null);
	}

	public Bet(int id, Game game, BetType type, BigDecimal odds, Boolean occurred) {
		this(id, game, type, odds, occurred, null, null, null);
	}

	public Bet(int id, Game game, BetType type, BigDecimal odds, Boolean occurred, Team team) {
		this(id, game, type, odds, occurred, team, null, null);
	}

	public Bet(int id, Game game, BetType type, BigDecimal odds, Boolean occurred, LocalTime time) {
		this(id, game, type, odds, occurred, null, time, null);
	}

	public Bet(int id, Game game, BetType type, BigDecimal odds, Boolean occurred, Team team, LocalTime time) {
		this(id, game, type, odds, occurred, team, time, null);
	}

	public Bet(int id, Game game, BetType type, BigDecimal odds, Boolean occurred, Team team, LocalTime time, Integer goals) {
		this();

		this.id = id;
		this.game = game;
		this.type = type;
		this.odds = odds;
		this.occurred = occurred;
		this.team = team;
		this.time = time;
		this.goals = goals;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public BetType getType() {
		return type;
	}

	public void setType(BetType type) {
		this.type = type;
	}

	public BigDecimal getOdds() {
		return odds;
	}

	public void setOdds(BigDecimal odds) {
		this.odds = odds;
	}

	public Boolean getOccurred() {
		return occurred;
	}

	public void setOccurred(Boolean occurred) {
		this.occurred = occurred;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public Integer getGoals() {
		return goals;
	}

	public void setGoals(Integer goals) {
		this.goals = goals;
	}
}
