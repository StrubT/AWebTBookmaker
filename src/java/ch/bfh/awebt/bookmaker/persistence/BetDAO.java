package ch.bfh.awebt.bookmaker.persistence;

import java.io.Serializable;
import ch.bfh.awebt.bookmaker.persistence.data.Bet;

public class BetDAO extends GenericDAO<Bet> implements Serializable {

	private static final long serialVersionUID = 7653413437250369536L;

	@Override
	protected Class<Bet> getEntityClass() {
		return Bet.class;
	}
}