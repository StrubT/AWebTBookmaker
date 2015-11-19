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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Represents a bet placed by user with the bookmaker.
 *
 * @author strut1 &amp; touwm1
 */
@Entity
@IdClass(UserBet.PK.class)
@Table(name = "users_bets")
@NamedQuery(name = UserBet.FIND_BY_USER_BET, query = "select c from UserBet c where c.betId = :betId and c.userId = :userId")
public class UserBet extends PersistentObject<UserBet.PK> implements Serializable {

	private static final long serialVersionUID = 5153932768816522284L;

	public static final String FIND_BY_USER_BET = "UserBet.FIND_BY_USER_BET";

	@Id
	@Column(name = "user", nullable = false, insertable = false, updatable = false)
	private Integer userId;

	@ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "user", nullable = false)
	private User user;

	@Id
	@Column(name = "bet", nullable = false, insertable = false, updatable = false)
	private Integer betId;

	@ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "bet", nullable = false)
	private Bet bet;

	@Column(name = "stake", nullable = false, precision = 10, scale = 3)
	private BigDecimal stake;

	/**
	 * Constructs an empty user bet.
	 */
	protected UserBet() {
	}

	/**
	 * Constructs a user bet with a specified stake.
	 *
	 * @param user  user placing the bet
	 * @param bet   bet to stake on
	 * @param stake stake of the bet
	 */
	public UserBet(User user, Bet bet, BigDecimal stake) {
		this();

		this.userId = user.getId();
		this.user = user;
		this.userId = bet.getId();
		this.bet = bet;
		this.stake = stake;

		user.addBet(this);
		bet.addUserBet(this);
	}

	@Override
	protected PK getId() {
		return new PK(this.userId, this.betId);
	}

	/**
	 * Gets the user placing the bet.
	 *
	 * @return user placing the bet
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user placing the bet.
	 *
	 * @param user user placing the bet
	 */
	protected void setUser(User user) {

		this.userId = user.getId();
		this.user = user;
	}

	/**
	 * Gets the bet to stake on.
	 *
	 * @return bet to stake on
	 */
	public Bet getBet() {
		return bet;
	}

	/**
	 * Sets the bet to stake on
	 *
	 * @param bet bet to stake on
	 */
	protected void setBet(Bet bet) {

		this.betId = bet.getId();
		this.bet = bet;
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

	/**
	 * Represents the compound primary key of the {@link UserBet} {@link Entity}.
	 */
	public static class PK implements Serializable {

		private static final long serialVersionUID = -7560859734270886323L;

		private int userId;
		private int betId;

		/**
		 * Constructs an empty primary key.
		 */
		protected PK() {
		}

		/**
		 * Constructs a new compound primary key.
		 *
		 * @param userId {@link User} key part
		 * @param betId  {@link Bet} key part
		 */
		protected PK(int userId, int betId) {
			this();

			this.userId = userId;
			this.betId = betId;
		}

		/**
		 * Gets the {@link User} key part.
		 *
		 * @return {@link User} key part
		 */
		public int getUserId() {
			return userId;
		}

		/**
		 * Sets the {@link User} key part.
		 *
		 * @param userId {@link User} key part
		 */
		protected void setUserId(int userId) {
			this.userId = userId;
		}

		/**
		 * Gets the {@link Bet} key part.
		 *
		 * @return {@link Bet} key part
		 */
		public int getBetId() {
			return betId;
		}

		/**
		 * Sets the {@link Bet} key part.
		 *
		 * @param betId {@link Bet} key part
		 */
		protected void setBetId(int betId) {
			this.betId = betId;
		}

		/**
		 * Gets a hash code for the primary key.
		 *
		 * @return hash code for the primary key
		 */
		@Override
		public int hashCode() {
			return ((17 + userId) * 31 + betId) * 31;
		}

		/**
		 * Compares this primary key with another object.
		 *
		 * @param obj object to compare to the primary key
		 *
		 * @return whether or not the two objects are equal
		 */
		@Override
		public boolean equals(Object obj) {

			if (obj == null || getClass() != obj.getClass())
				return false;

			PK key = (PK)obj;
			return userId == key.userId && betId == key.betId;
		}

		/**
		 * Gets a string representation for the primary key.
		 *
		 * @return string representation for the primary key
		 */
		@Override
		public String toString() {
			return String.format("[user=%d, bet=%d]", userId, betId);
		}
	}
}
