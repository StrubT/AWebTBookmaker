package ch.bfh.awebt.bookmaker.presentation.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import ch.bfh.awebt.bookmaker.persistence.data.Bet;
import ch.bfh.awebt.bookmaker.persistence.data.BetType;
import ch.bfh.awebt.bookmaker.persistence.data.UserBet;

public class BetDTO implements Serializable {

	private Integer id;
	private BetType type;
	private BigDecimal odds;
	private Boolean occurred;
	private String team;
	private LocalTime time;
	private Integer goals;

	private Integer userId;
	private BigDecimal stake;

	public BetDTO(Bet bet) {

		id = bet.getId();
		type = bet.getType();
		odds = bet.getOdds();
		occurred = bet.getOccurred();
		team = bet.getTeam() != null ? bet.getTeam().getCode() : null;
		time = bet.getTime();
		goals = bet.getGoals();
	}

	public BetDTO(Bet bet, UserBet userBet) {
		this(bet);

		userId = userBet.getUser().getId();
		stake = userBet.getStake();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public BigDecimal getStake() {
		return stake;
	}

	public void setStake(BigDecimal stake) {
		this.stake = stake;
	}
}
