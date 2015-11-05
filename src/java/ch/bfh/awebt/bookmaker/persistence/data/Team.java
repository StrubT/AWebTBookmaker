package ch.bfh.awebt.bookmaker.persistence.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

public class Team implements Serializable {

	@Id
	@Column(name = "code", nullable = false, unique = true)
	private String code;
}
