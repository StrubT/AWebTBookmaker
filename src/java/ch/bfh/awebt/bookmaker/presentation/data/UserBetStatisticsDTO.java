package ch.bfh.awebt.bookmaker.presentation.data;

import java.math.BigDecimal;
import java.util.List;
import ch.bfh.awebt.bookmaker.persistence.data.User;
import ch.bfh.awebt.bookmaker.persistence.data.UserBet;

/**
 * Represents a special presentation data object encapsulating {@link UserBet} statistics.
 *
 * @author strut1 &amp; touwm1
 */
public class UserBetStatisticsDTO {

	private int numberOfBets;
	private BigDecimal betsAmountWon, betsAmountLost, betsAmountSum;
	private double betsPercentageWon, betsPercentageLost;

	/**
	 * Constructs an empty statistics data transfer object.
	 */
	public UserBetStatisticsDTO() {

		numberOfBets = 0;
		betsAmountWon = betsAmountLost = betsAmountSum = BigDecimal.ZERO;
		betsPercentageWon = betsPercentageLost = 0.0;
	}

	/**
	 * Constructs a new statistics data transfer object for the specified {@link User}.
	 *
	 * @param user user to get statistics for
	 */
	public UserBetStatisticsDTO(User user) {
		this();

		List<UserBet> userBets = user.getBets();
		numberOfBets = userBets.size();

		for (UserBet userBet: userBets)
			if (Boolean.TRUE.equals(userBet.getBet().getOccurred())) {
				betsAmountWon = betsAmountWon.add(userBet.getGain());
				betsPercentageWon += 1.0;

			} else if (Boolean.FALSE.equals(userBet.getBet().getOccurred())) {
				betsAmountLost = betsAmountLost.add(userBet.getGain().negate());
				betsPercentageLost += 1.0;
			}

		betsAmountSum = betsAmountWon.subtract(betsAmountLost);
		betsPercentageWon /= numberOfBets;
		betsPercentageLost /= numberOfBets;
	}

	/**
	 * Gets the number of bets the user placed.
	 *
	 * @return number of bets the user placed
	 */
	public int getNumberOfBets() {
		return numberOfBets;
	}

	/**
	 * Gets the amount the user won.
	 *
	 * @return amount the user won
	 */
	public BigDecimal getBetsAmountWon() {
		return betsAmountWon;
	}

	/**
	 * Gets the amount the user lost.
	 *
	 * @return amount the user lost
	 */
	public BigDecimal getBetsAmountLost() {
		return betsAmountLost;
	}

	/**
	 * Gets the amount the user actually won.
	 *
	 * @return amount the user actually won
	 */
	public BigDecimal getBetsAmountSum() {
		return betsAmountSum;
	}

	/**
	 * Gets the percentage of bets the user won.
	 *
	 * @return percentage of bets the user won
	 */
	public double getBetsPercentageWon() {
		return betsPercentageWon;
	}

	/**
	 * Gets the percentage of bets the user lost.
	 *
	 * @return percentage of bets the user lost
	 */
	public double getBetsPercentageLost() {
		return betsPercentageLost;
	}
}
