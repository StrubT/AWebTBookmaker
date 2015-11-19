package ch.bfh.awebt.bookmaker.persistence;

import java.io.Serializable;
import ch.bfh.awebt.bookmaker.MapBuilder;
import ch.bfh.awebt.bookmaker.Streams;
import ch.bfh.awebt.bookmaker.persistence.data.Bet;
import ch.bfh.awebt.bookmaker.persistence.data.User;
import ch.bfh.awebt.bookmaker.persistence.data.UserBet;

public class UserBetDAO extends GenericDAO<UserBet> implements Serializable {

	private static final long serialVersionUID = -3307307888664577411L;

	@Override
	protected Class<UserBet> getEntityClass() {
		return UserBet.class;
	}

	public UserBet findByUserAndBet(User user, Bet bet) {

		return findByUserAndBet(user.getId(), bet.getId());
	}

	public UserBet findByUserAndBet(Integer userId, Integer betId) {

		return findByQuery(UserBet.FIND_BY_USER_BET, MapBuilder.first("userId", userId).last("betId", betId))
			.stream().collect(Streams.nullableSingleCollector());
	}
}
