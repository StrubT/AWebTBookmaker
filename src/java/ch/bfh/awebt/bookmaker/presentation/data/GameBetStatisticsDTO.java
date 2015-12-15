package ch.bfh.awebt.bookmaker.presentation.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ch.bfh.awebt.bookmaker.persistence.data.Bet;
import ch.bfh.awebt.bookmaker.persistence.data.Game;
import ch.bfh.awebt.bookmaker.persistence.data.User;
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
	 * Constructs an empty statistics data transfer object.
	 */
	public GameBetStatisticsDTO() {

		numberOfBets = numberOfUserBets = 0;
		evaluated = true;
		betsStake = betsPotentialGain = betsAmountWon = betsAmountLost = betsAmountSum = BigDecimal.ZERO;
	}

	/**
	 * Constructs a new statistics data transfer object for the specified {@link Game}.
	 *
	 * @param game game to get statistics for
	 * @param user user to get statistics for
	 */
	public GameBetStatisticsDTO(Game game, User user) {
		this();

		List<Bet> bets = game.getBets();
		numberOfBets = bets.size();

		Map<Integer, UserBet> userBetsMap = user != null ? new ArrayList<>(user.getBets()).stream().collect(Collectors.toMap(b -> b.getBet().getId(), b -> b)) : null; //BUGFIX: new ArrayList<>(...) needed in eclipselink < 2.7

		for (Bet bet: bets) {
			evaluated &= bet.getOccurred() != null;

			List<UserBet> userBets = user == null ? bet.getUserBets() : userBetsMap.containsKey(bet.getId()) ? Arrays.asList(userBetsMap.get(bet.getId())) : Arrays.asList();
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
	 * Constructs a new statistics data transfer object for the specified {@link Game}.
	 *
	 * @param game game to get statistics for
	 */
	public GameBetStatisticsDTO(Game game) {
		this(game, null);
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
	 * Gets the total potential loss for the bookmaker.
	 *
	 * @return total potential loss for the bookmaker
	 */
	public BigDecimal getBetsPotentialLoss() {

		return betsPotentialGain; //detect incompatible bets
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

	/**
	 * Combines the current statistics with a second statistics data transfer object.
	 *
	 * @param other second statistics data transfer object to use
	 *
	 * @return a statistics data transfer object containing the combined values
	 */
	public GameBetStatisticsDTO combine(GameBetStatisticsDTO other) {

		GameBetStatisticsDTO combined = new GameBetStatisticsDTO();

		combined.numberOfBets = numberOfBets + other.numberOfBets;
		combined.numberOfUserBets = numberOfUserBets + other.numberOfUserBets;

		combined.evaluated = evaluated && other.evaluated;

		combined.betsStake = betsStake.add(other.betsStake);
		combined.betsPotentialGain = betsPotentialGain.add(other.betsPotentialGain);
		combined.betsAmountWon = betsAmountWon.add(other.betsAmountWon);
		combined.betsAmountLost = betsAmountLost.add(other.betsAmountLost);
		combined.betsAmountSum = betsAmountSum.add(other.betsAmountSum);

		return combined;
	}
}
