package ch.bfh.awebt.bookmaker.presentation;

import java.io.Serializable;
import ch.bfh.awebt.bookmaker.persistence.data.Bet;
import ch.bfh.awebt.bookmaker.persistence.data.UserBet;

public class UserTypedBet implements Serializable {

	private Bet generic;
	private UserBet typed;

	public UserTypedBet(Bet generic, UserBet typed) {

		this.generic = generic;
		this.typed = typed;
	}

	public Bet getGeneric() {
		return generic;
	}

	public void setGeneric(Bet generic) {
		this.generic = generic;
	}

	public UserBet getTyped() {
		return typed;
	}

	public void setTyped(UserBet typed) {
		this.typed = typed;
	}
}
