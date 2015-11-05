package ch.bfh.awebt.bookmaker.persistence.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import ch.bfh.awebt.bookmaker.converters.LocalTimeConverter;

/**
 * Represents a bet.
 *
 * @author strut1 &amp; touwm1
 */
@Entity
@Table(name = "bets")
public class Bet implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private int id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "game", nullable = false)
	private Game game;
	//
	//@ManyToOne(optional = false)
	//@JoinColumn(name = "type", nullable = false)
	//private BetType type;

	@Column(name = "odds", nullable = false, precision = 10, scale = 3)
	private BigDecimal odds;

	@Column(name = "occurred", nullable = true)
	private Boolean occurred;

	@ManyToOne(optional = false)
	@JoinColumn(name = "team", nullable = true)
	private Team team;

	@Column(name = "time", nullable = true)
	@Convert(converter = LocalTimeConverter.class)
	private LocalTime time;

	@Column(name = "goals", nullable = true)
	private Integer goals;
}
