package ch.bfh.awebt.bookmaker.persistence;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime dateTime) {
		return dateTime != null ? Timestamp.valueOf(dateTime) : null;
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp dateTime) {
		return dateTime != null ? dateTime.toLocalDateTime() : null;
	}
}
