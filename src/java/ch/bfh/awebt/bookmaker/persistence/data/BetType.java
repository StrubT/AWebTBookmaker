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

	/**
	 * Represents a bet on the winning team.
	 */
	TEAM_WINS("WIN", true, false, false),

	/**
	 * Represents a bet on a tied outcome.
	 */
	ENDS_TIED("EVN", false, false, false),

	/**
	 * Represents a bet on the leading team after a specified playing time.
	 */
	TEAM_LEADS_AFTER_TIME("LDT", true, true, false),

	/**
	 * Represents a bet on a tie after a specified playing time.
	 */
	TIED_AFTER_TIME("EVT", false, true, false),

	/**
	 * Represents a bet on the leading team after a specified playing time with a number of goals.
	 */
	TEAM_LEADS_AFTER_TIME_WITH_NOF_GOALS("LDG", true, true, true);

	private final String code;
	private final boolean teamRequired, timeRequired, goalsRequired;

	BetType(String code, boolean teamRequired, boolean timeRequired, boolean goalsRequired) {

		this.code = code;
		this.teamRequired = teamRequired;
		this.timeRequired = timeRequired;
		this.goalsRequired = goalsRequired;
	}

	/**
	 * Gets the unique code of the bet type.
	 *
	 * @return unique code of the bet type
	 */
	public String getCode() {
		return code;
	}

	public boolean isTeamRequired() {
		return teamRequired;
	}

	public boolean isTimeRequired() {
		return timeRequired;
	}

	public boolean isGoalsRequired() {
		return goalsRequired;
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
