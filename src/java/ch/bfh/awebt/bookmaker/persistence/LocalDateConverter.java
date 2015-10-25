package ch.bfh.awebt.bookmaker.persistence;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDate date) {
		return date != null ? Date.valueOf(date) : null;
	}

	@Override
	public LocalDate convertToEntityAttribute(Date date) {
		return date != null ? date.toLocalDate() : null;
	}
}
