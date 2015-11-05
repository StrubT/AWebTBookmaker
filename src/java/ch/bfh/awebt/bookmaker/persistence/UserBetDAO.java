package ch.bfh.awebt.bookmaker.persistence;

import java.io.Serializable;
import ch.bfh.awebt.bookmaker.persistence.data.UserBet;

public class UserBetDAO extends GenericDAO<UserBet> implements Serializable {

	private static final long serialVersionUID = -3307307888664577411L;

	@Override
	protected Class<UserBet> getEntityClass() {
		return UserBet.class;
	}
}
