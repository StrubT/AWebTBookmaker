package ch.bfh.awebt.bookmaker.persistence.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PersistentObject implements Serializable {

	private static final long serialVersionUID = -8288309083957125216L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected int id;

	protected PersistentObject() {

		id = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		id = id;
	}
}
