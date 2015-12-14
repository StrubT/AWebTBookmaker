package ch.bfh.awebt.bookmaker.presentation.data;

import java.math.BigDecimal;
import java.util.List;
import ch.bfh.awebt.bookmaker.persistence.data.Bet;
import ch.bfh.awebt.bookmaker.persistence.data.Game;
import ch.bfh.awebt.bookmaker.persistence.data.UserBet;

/**
 * Represents a special presentation data object encapsulating statistics about a {@link Game}'s {@link Bet}s.
 *
 * @author strut1 &amp; touwm1
 */
public class GameBetStatisticsDTO {

	private int numberOfBets, numberOfUserBets;
	private boolean evaluated;
	private BigDecimal betsStake, betsPotentialGain, betsAmountWon, betsAmountLost, betsAmountSum;

	/**
	 * Constructs a new statistics data transfer object for the specified {@link Game}.
	 *
	 * @param game game to get statistics for
	 */
	public GameBetStatisticsDTO(Game game) {

		List<Bet> bets = game.getBets();
		numberOfBets = bets.size();

		numberOfUserBets = 0;
		evaluated = true;
		betsStake = betsPotentialGain = betsAmountWon = betsAmountLost = BigDecimal.ZERO;
		for (Bet bet: bets) {
			evaluated &= bet.getOccurred() != null;

			List<UserBet> userBets = bet.getUserBets();
			numberOfUserBets += userBets.size();

			for (UserBet userBet: userBets) {
				betsStake = betsStake.add(userBet.getStake());
				betsPotentialGain = betsPotentialGain.add(userBet.getPotentialGain());

				if (Boolean.TRUE.equals(userBet.getBet().getOccurred()))
					betsAmountWon = betsAmountWon.add(userBet.getGain());
				else if (Boolean.FALSE.equals(userBet.getBet().getOccurred()))
					betsAmountLost = betsAmountLost.add(userBet.getGain().negate());
			}
		}

		betsAmountSum = betsAmountWon.subtract(betsAmountLost);
	}

	/**
	 * Gets the number of bets defined for the game.
	 *
	 * @return number of bets defined for the game
	 */
	public int getNumberOfBets() {
		return numberOfBets;
	}

	/**
	 * Gets the number of bets users placed on the game.
	 *
	 * @return number of bets users placed on the game
	 */
	public int getNumberOfUserBets() {
		return numberOfUserBets;
	}

	/**
	 * Gets whether or not the game's bets are completely evaluated.
	 *
	 * @return whether or not the game's bets are completely evaluated
	 */
	public boolean isEvaluated() {
		return evaluated;
	}

	/**
	 * Gets the total stake on the bets.
	 *
	 * @return total stake on the bets
	 */
	public BigDecimal getBetsStake() {
		return betsStake;
	}

	/**
	 * Gets the total potential gain for the users.
	 *
	 * @return total potential gain for the users
	 */
	public BigDecimal getBetsPotentialGain() {
		return betsPotentialGain;
	}

	/**
	 * Gets the total amount the users won.
	 *
	 * @return total amount the users won
	 */
	public BigDecimal getBetsAmountWon() {
		return betsAmountWon;
	}

	/**
	 * Gets the total amount the users lost.
	 *
	 * @return total amount the users lost
	 */
	public BigDecimal getBetsAmountLost() {
		return betsAmountLost;
	}

	/**
	 * Gets the total amount the users actually won.
	 *
	 * @return total amount the users actually won
	 */
	public BigDecimal getBetsAmountSum() {
		return betsAmountSum;
	}
}
