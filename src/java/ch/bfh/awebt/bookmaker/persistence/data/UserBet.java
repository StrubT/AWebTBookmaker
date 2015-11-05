package ch.bfh.awebt.bookmaker.persistence.data;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(UserBet.PK.class)
@Table(name = "users_bets")
public class UserBet extends PersistentObject<UserBet.PK> implements Serializable {

	private static final long serialVersionUID = 5153932768816522284L;

	@Id
	@Column(name = "user", nullable = false, insertable = false, updatable = false)
	private int userId;

	@ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "user", nullable = false)
	private User user;

	@Id
	@Column(name = "bet", nullable = false, insertable = false, updatable = false)
	private int betId;

	@ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "bet", nullable = false)
	private Bet bet;

	@Column(name = "stake", nullable = false, precision = 10, scale = 3)
	private BigDecimal stake;

	protected UserBet() {
	}

	public UserBet(User user, Bet bet, BigDecimal stake) {
		this();

		this.userId = user.getId();
		this.user = user;
		this.userId = bet.getId();
		this.bet = bet;
		this.stake = stake;
	}

	@Override
	protected PK getId() {
		return new PK(this.userId, this.betId);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {

		this.userId = user.getId();
		this.user = user;
	}

	public Bet getBet() {
		return bet;
	}

	public void setBet(Bet bet) {

		this.betId = bet.getId();
		this.bet = bet;
	}

	public BigDecimal getStake() {
		return stake;
	}

	public void setStake(BigDecimal stake) {
		this.stake = stake;
	}

	public static class PK implements Serializable {

		private static final long serialVersionUID = -7560859734270886323L;

		private int userId;
		private int betId;

		protected PK() {
		}

		private PK(int userId, int betId) {
			this();

			this.userId = userId;
			this.betId = betId;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public int getBetId() {
			return betId;
		}

		public void setBetId(int betId) {
			this.betId = betId;
		}

		@Override
		public int hashCode() {
			return ((17 + userId) * 31 + betId) * 31;
		}

		@Override
		public boolean equals(Object obj) {

			if (obj == null || getClass() != obj.getClass())
				return false;

			PK key = (PK)obj;
			return userId == key.userId && betId == key.betId;
		}

		@Override
		public String toString() {
			return String.format("[user=%d, bet=%d]", userId, betId);
		}
	}
}
