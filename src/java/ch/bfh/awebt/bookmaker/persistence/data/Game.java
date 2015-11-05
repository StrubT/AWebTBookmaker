package ch.bfh.awebt.bookmaker.persistence.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import ch.bfh.awebt.bookmaker.converters.LocalDateTimeConverter;

@Entity
@Table(name = "game")
public class Game implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private int id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "team1", nullable = false)
	private Team team1;

	@ManyToOne(optional = false)
	@JoinColumn(name = "team2", nullable = false)
	private Team team2;

	@Column(name = "starttime", nullable = false)
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime starttime;
}
