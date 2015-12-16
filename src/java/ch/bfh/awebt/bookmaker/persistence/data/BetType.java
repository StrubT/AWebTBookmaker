package ch.bfh.awebt.bookmaker.persistence.data;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Collector;
import ch.bfh.awebt.bookmaker.Streams;

/**
 * Represents the different types of bets with the bookmaker.
 *
 * @author strut1 &amp; touwm1
 */
public enum BetType {
	//
	//If you modify this enumerable, remember to modify the text bundle 'ch.bfh.awebt.bookmaker.bundles.bets' as well.
	//You might also want to add additional checks to 'ch.bfh.awebt.bookmaker.presentation.data.GameBetStatisticsDTO.COMPATIBLE_CHECKS' to improve the potential loss calculation.

	/**
	 * {0} kicks off
	 */
	TEAM_KICKS_OFF("KCK", true, false, false),

	/**
	 * {0} scores first goal
	 */
	TEAM_SCORES_FIRST_GOAL("FGL", true, false, false),

	/**
	 * {0} awarded first throw in
	 */
	TEAM_AWARDED_FIRST_THROW_IN("FTW", true, false, false),

	/**
	 * {0} leads after {1}
	 */
	TEAM_LEADS_AFTER_TIME("LDT", true, true, false),

	/**
	 * {0} leads after {1} with {2} or more goals
	 */
	TEAM_LEADS_AFTER_TIME_WITH_NOF_GOALS("LDG", true, true, true),

	/**
	 * tied after {1}
	 */
	TIED_AFTER_TIME("EVT", false, true, false),

	/**
	 * {2} or more goals after {1}
	 */
	NOF_GOALS_AFTER_TIME("GLT", false, true, true),

	/**
	 * {0} wins
	 */
	TEAM_WINS("WIN", true, false, false),

	/**
	 * {0} wins leading with {2} or more goals
	 */
	TEAM_WINS_LEADING_WITH_NOF_GOALS("WNG", true, false, true),

	/**
	 * ends in a draw
	 */
	ENDS_TIED("EVN", false, false, false),

	/**
	 * ends with {2} or more goals
	 */
	ENDS_WITH_NOF_GOALS("GLS", false, false, true),

	/**
	 * ends with {2} or more cards combined
	 */
	ENDS_WITH_NOF_CARDS_COMBINED("CRD", false, false, true),

	/**
	 * ends with {2} or more yellow cards
	 */
	ENDS_WITH_NOF_YELLOW_CARDS("YEL", false, false, true),

	/**
	 * ends with {2} or more red cards
	 */
	ENDS_WITH_NOF_RED_CARDS("RED", false, false, true),

	/**
	 * ends with {2} or more corners
	 */
	ENDS_WITH_NOF_CORNERS("CRN", false, false, true);

	private final String code;
	private final boolean teamRequired, timeRequired, numberRequired;

	private BetType(String code, boolean teamRequired, boolean timeRequired, boolean numberRequired) {

		this.code = code;
		this.teamRequired = teamRequired;
		this.timeRequired = timeRequired;
		this.numberRequired = numberRequired;
	}

	/**
	 * Gets the unique code of the bet type.
	 *
	 * @return unique code of the bet type
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Gets whether or not the {@link Bet#team} property is required / supported.
	 *
	 * @return whether or not the {@link Bet#team} property is required
	 */
	public boolean isTeamRequired() {
		return teamRequired;
	}

	/**
	 * Gets whether or not the {@link Bet#time} property is required / supported.
	 *
	 * @return whether or not the {@link Bet#time} property is required
	 */
	public boolean isTimeRequired() {
		return timeRequired;
	}

	/**
	 * Gets whether or not the {@link Bet#number} property is required / supported.
	 *
	 * @return whether or not the {@link Bet#number} property is required
	 */
	public boolean isNumberRequired() {
		return numberRequired;
	}

	/**
	 * Gets the bet type by its unique code.
	 *
	 * @param code unique code of the bet type to get
	 *
	 * @return bet type with the specified unique code
	 *
	 * @throws NoSuchElementException if the specified unique code does not exist
	 */
	public static BetType getByCode(String code) {

		return getByCode(code, false);
	}

	/**
	 * Gets the bet type by its unique code.
	 *
	 * @param code     unique code of the bet type to get
	 * @param nullable whether {@code null} is returned or a {@link NoSuchElementException} is thrown if the unique code does not exist
	 *
	 * @return bet type with the specified unique code
	 *
	 * @throws NoSuchElementException if the specified unique code does not exist and {@code nullable == false}
	 */
	public static BetType getByCode(String code, boolean nullable) {

		Collector<BetType, ?, BetType> collector = nullable ? Streams.nullableSingleCollector() : Streams.singleCollector();

		return Arrays.stream(BetType.values())
			.filter(t -> t.getCode().equals(code))
			.collect(collector);
	}
}
