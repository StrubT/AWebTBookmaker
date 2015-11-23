package ch.bfh.awebt.bookmaker.persistence;

import java.io.Serializable;
import ch.bfh.awebt.bookmaker.MapBuilder;
import ch.bfh.awebt.bookmaker.Streams;
import ch.bfh.awebt.bookmaker.persistence.data.Bet;
import ch.bfh.awebt.bookmaker.persistence.data.User;
import ch.bfh.awebt.bookmaker.persistence.data.UserBet;

/**
 * Represents a data access object for {@link UserBet} entities.
 *
 * @author strut1 &amp; touwm1
 */
public class UserBetDAO extends GenericDAO<UserBet> implements Serializable {

	private static final long serialVersionUID = -3307307888664577411L;

	@Override
	protected Class<UserBet> getEntityClass() {
		return UserBet.class;
	}

	/**
	 * Finds a specific bet placed by a specific user.
	 *
	 * @param user user the bet was placed by
	 * @param bet  bet the user put stakes on
	 *
	 * @return the specific bet placed by a specific user, or {@code null} if the user did not bet
	 */
	public UserBet findByUserAndBet(User user, Bet bet) {

		return findByUserAndBet(user.getId(), bet.getId());
	}

	/**
	 * Finds a specific bet placed by a specific user.
	 *
	 * @param userId unique identifier of the user the bet was placed by
	 * @param betId  unique identifier of the bet the user put stakes on
	 *
	 * @return the specific bet placed by a specific user, or {@code null} if the user did not bet
	 */
	public UserBet findByUserAndBet(Integer userId, Integer betId) {

		return findByQuery(UserBet.FIND_BY_USER_BET, MapBuilder.first("userId", userId).last("betId", betId))
			.stream().collect(Streams.nullableSingleCollector());
	}
}
