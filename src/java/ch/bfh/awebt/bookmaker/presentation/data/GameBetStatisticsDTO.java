package ch.bfh.awebt.bookmaker.presentation.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import ch.bfh.awebt.bookmaker.MapBuilder;
import ch.bfh.awebt.bookmaker.persistence.data.Bet;
import ch.bfh.awebt.bookmaker.persistence.data.BetType;
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
	private BigDecimal betsStake, betsPotentialGain, betsPotentialLoss, betsAmountWon, betsAmountLost, betsAmountSum;

	/**
	 * Constructs an empty statistics data transfer object.
	 */
	public GameBetStatisticsDTO() {

		numberOfBets = numberOfUserBets = 0;
		evaluated = true;
		betsStake = betsPotentialGain = betsPotentialLoss = betsAmountWon = betsAmountLost = betsAmountSum = BigDecimal.ZERO;
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
		List<Map<Bet, BigDecimal>> checkGroups = new ArrayList<>();

		for (Bet bet: bets) {
			evaluated &= bet.getOccurred() != null;

			BigDecimal betPotentialGain = BigDecimal.ZERO;
			List<UserBet> userBets = user == null ? bet.getUserBets() : userBetsMap.containsKey(bet.getId()) ? Arrays.asList(userBetsMap.get(bet.getId())) : Arrays.asList();
			numberOfUserBets += userBets.size();

			for (UserBet userBet: userBets) {
				betsStake = betsStake.add(userBet.getStake());
				betPotentialGain = betPotentialGain.add(userBet.getPotentialGain());

				if (Boolean.TRUE.equals(userBet.getBet().getOccurred()))
					betsAmountWon = betsAmountWon.add(userBet.getGain());
				else if (Boolean.FALSE.equals(userBet.getBet().getOccurred()))
					betsAmountLost = betsAmountLost.add(userBet.getGain().negate());
			}

			betsPotentialGain = betsPotentialGain.add(betPotentialGain);

			boolean addedToCheck = false;
			for (Map<Bet, BigDecimal> checkGroup: checkGroups)
				if (addedToCheck = checkGroup.keySet().stream().noneMatch(b -> compatible(bet, b))) {
					checkGroup.put(bet, betPotentialGain);
					break;
				}
			if (!addedToCheck)
				checkGroups.add(MapBuilder.single(bet, betPotentialGain));
		}

		betsAmountSum = betsAmountWon.subtract(betsAmountLost);

		betsPotentialLoss = checkGroups.stream()
			.map(g -> g.values().stream().max((a, b) -> a.compareTo(b)).get())
			.reduce((a, b) -> a.add(b)).orElse(betsPotentialLoss);
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

		return betsPotentialLoss;
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
		combined.betsPotentialLoss = betsPotentialLoss.add(other.betsPotentialLoss);
		combined.betsAmountWon = betsAmountWon.add(other.betsAmountWon);
		combined.betsAmountLost = betsAmountLost.add(other.betsAmountLost);
		combined.betsAmountSum = betsAmountSum.add(other.betsAmountSum);

		return combined;
	}

	private static Map<BetType, Map<BetType, BiPredicate<Bet, Bet>>> COMPATIBLE_CHECKS;

	static {

		//helper functions
		Function<Bet, String> teamCodeOrDefault = t -> t.getTeam() != null ? t.getTeam().getCode() : "???";

		//bi-predicates to use for the checks
		BiPredicate<Bet, Bet> compatibleSameTeam = (a, b) -> teamCodeOrDefault.apply(a).equals(teamCodeOrDefault.apply(b));
		BiPredicate<Bet, Bet> compatibleSameTeamAndTime = compatibleSameTeam.and((a, b) -> a.getTime().equals(b.getTime()));

		//basic checks for bets of same type only depending on the team
		MapBuilder<BetType, Map<BetType, BiPredicate<Bet, Bet>>> checks = MapBuilder
			.first(BetType.TEAM_KICKS_OFF, MapBuilder
						 .single(BetType.TEAM_KICKS_OFF, compatibleSameTeam))
			.add(BetType.TEAM_SCORES_FIRST_GOAL, MapBuilder
					 .single(BetType.TEAM_KICKS_OFF, compatibleSameTeam))
			.add(BetType.TEAM_AWARDED_FIRST_THROW_IN, MapBuilder
					 .single(BetType.TEAM_AWARDED_FIRST_THROW_IN, compatibleSameTeam));

		checks //checks for bets of different types representing end states only depending on the team
			.add(BetType.TEAM_WINS, MapBuilder
					 .first(BetType.TEAM_WINS, compatibleSameTeam)
					 .add(BetType.TEAM_WINS_LEADING_WITH_NOF_GOALS, compatibleSameTeam)
					 .last(BetType.ENDS_TIED, compatibleSameTeam))
			.add(BetType.TEAM_WINS_LEADING_WITH_NOF_GOALS, MapBuilder
					 .first(BetType.TEAM_WINS_LEADING_WITH_NOF_GOALS, compatibleSameTeam)
					 .last(BetType.ENDS_TIED, compatibleSameTeam))
			.add(BetType.ENDS_TIED, MapBuilder
					 .single(BetType.ENDS_TIED, compatibleSameTeam));

		COMPATIBLE_CHECKS = checks //advanced checks for bets of different types representing intermediary states depending on the team and time
			.add(BetType.TEAM_LEADS_AFTER_TIME, MapBuilder
					 .first(BetType.TEAM_LEADS_AFTER_TIME, compatibleSameTeamAndTime)
					 .add(BetType.TEAM_LEADS_AFTER_TIME_WITH_NOF_GOALS, compatibleSameTeamAndTime)
					 .last(BetType.TIED_AFTER_TIME, compatibleSameTeamAndTime))
			.add(BetType.TEAM_LEADS_AFTER_TIME_WITH_NOF_GOALS, MapBuilder
					 .first(BetType.TEAM_LEADS_AFTER_TIME_WITH_NOF_GOALS, compatibleSameTeamAndTime)
					 .last(BetType.TIED_AFTER_TIME, compatibleSameTeamAndTime))
			.last(BetType.TIED_AFTER_TIME, MapBuilder
						.single(BetType.TIED_AFTER_TIME, compatibleSameTeamAndTime));
	}

	private boolean compatible(Bet bet1, Bet bet2) {

		Map<BetType, BiPredicate<Bet, Bet>> subMap;
		BiPredicate<Bet, Bet> check;

		if ((subMap = COMPATIBLE_CHECKS.getOrDefault(bet1.getType(), null)) != null && (check = subMap.getOrDefault(bet2.getType(), null)) != null)
			return check.test(bet1, bet2);

		else if ((subMap = COMPATIBLE_CHECKS.getOrDefault(bet2.getType(), null)) != null && (check = subMap.getOrDefault(bet1.getType(), null)) != null)
			return check.test(bet2, bet1);

		else
			return true;
	}
}
