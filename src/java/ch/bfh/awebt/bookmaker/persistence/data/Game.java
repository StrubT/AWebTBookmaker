package ch.bfh.awebt.bookmaker.persistence.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
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
import ch.bfh.awebt.bookmaker.converters.LocalDateTimeConverter;

@Entity
@Table(name = "games")
public class Game extends PersistentObject<Integer> implements Serializable {

	private static final long serialVersionUID = 5535286169721878761L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "team1", nullable = false)
	private Team team1;

	@ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "team2", nullable = false)
	private Team team2;

	@Column(name = "starttime", nullable = false)
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime startTime;

	@OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Bet> bets;

	protected Game() {
	}

	public Game(int id, Team team1, Team team2, LocalDateTime startTime) {
		this();

		this.id = id;
		this.team1 = team1;
		this.team2 = team2;
		this.startTime = startTime;

		bets = new ArrayList<>();
	}

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Team getTeam1() {
		return team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public List<Bet> getBets() {
		return Collections.unmodifiableList(bets);
	}

	public boolean addBet(Bet bet) {
		return bets.add(bet);
	}

	public boolean removeBet(Bet bet) {
		return bets.remove(bet);
	}
}
