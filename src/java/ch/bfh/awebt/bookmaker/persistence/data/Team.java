package ch.bfh.awebt.bookmaker.persistence.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Represents a FIFA-registered football team.
 *
 * @author strut1 &amp; touwm1
 */
@Entity
@Table(name = "teams")
@NamedQuery(name = Team.FIND_ALL_ORDERED_BY_ID_QUERY, query = "select t from Team t order by t.id")
public class Team extends PersistentObject<String> implements Serializable {

	private static final long serialVersionUID = -7712017410251177486L;

	/**
	 * Name of the {@link NamedQuery} to get all teams ordered by their unique identifier.
	 */
	public static final String FIND_ALL_ORDERED_BY_ID_QUERY = "Team.FIND_ALL_ORDERED_BY_ID_QUERY";

	@Id
	@Column(name = "code", nullable = false, length = 10, unique = true)
	private String id;

	@OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
	private List<Bet> bets;

	/**
	 * Constructs an empty football team.
	 */
	protected Team() {

		bets = new ArrayList<>();
	}

	/**
	 * Constructs a new football team with a specified FIFA code.
	 *
	 * @param code unique FIFA country code of the football team
	 */
	public Team(String code) {
		this();

		this.id = code;
	}

	/**
	 * Gets the unique FIFA country code of the football team.
	 *
	 * @return unique FIFA country code of the football team
	 */
	@Override
	protected String getId() {
		return id;
	}

	/**
	 * Sets the unique FIFA country code of the football team.
	 *
	 * @param id unique FIFA country code of the football team
	 */
	protected void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the unique FIFA country code of the football team.
	 *
	 * @return unique FIFA country code of the football team
	 */
	public String getCode() {
		return id;
	}

	/**
	 * Gets the bets available for the football team.
	 *
	 * @return unmodifiable {@link List} of the bets available for the football team
	 */
	public List<Bet> getBets() {
		return Collections.unmodifiableList(bets);
	}
}
