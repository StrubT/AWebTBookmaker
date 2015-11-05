package ch.bfh.awebt.bookmaker.persistence.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "teams")
public class Team extends PersistentObject<String> implements Serializable {

	private static final long serialVersionUID = -7712017410251177486L;

	@Id
	@Column(name = "code", nullable = false, length = 10, unique = true)
	private String code;

	protected Team() {
	}

	public Team(String code) {
		this();

		this.code = code;
	}

	@Override
	protected String getId() {
		return code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
