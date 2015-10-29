package ch.bfh.awebt.bookmaker.persistence.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Represents the base for all persistent objects.
 *
 * @author strut1 &amp; touwm1
 */
@MappedSuperclass
public abstract class PersistentObject implements Serializable {

	private static final long serialVersionUID = -3830488409885088579L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private int id;

	/**
	 * Constructs a new persistent record with a unique identifier of {@code -1}.
	 */
	protected PersistentObject() {
		this(-1);
	}

	/**
	 * Constructs a new persistent record with a given unique identifier.
	 */
	protected PersistentObject(int id) {

		this.id = id;
	}

	/**
	 * Gets the unique identifier of the persistent record.
	 *
	 * @return unique identifier of the persistent record
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the unique identifier of the persistent record.
	 *
	 * @param id unique identifier of the persistent record
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Generates a hash code for the persistent record. <br>
	 * The hash code is only based on the unique identifier.
	 *
	 * @return hash code for the persistent record
	 */
	@Override
	public int hashCode() {
		return (31 + id) * 37;
	}

	/**
	 * Compares two persistent records for equality.
	 *
	 * @param obj object to compare to the persistent record
	 *
	 * @return whether or not the two objects represent the same persistent record
	 */
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == getClass() && ((PersistentObject)obj).id == id;
	}

	/**
	 * Generates a string representation of the persistent record.
	 *
	 * @return string representation of the persistent record
	 */
	@Override
	public String toString() {
		return String.format("%s[id=%d]", this.getClass().getSimpleName(), this.id);
	}
}
