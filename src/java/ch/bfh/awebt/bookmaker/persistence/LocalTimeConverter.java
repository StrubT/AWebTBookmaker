package ch.bfh.awebt.bookmaker.persistence;

import java.sql.Time;
import java.time.LocalTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocalTimeConverter implements AttributeConverter<LocalTime, Time> {

	@Override
	public Time convertToDatabaseColumn(LocalTime time) {
		return time != null ? Time.valueOf(time) : null;
	}

	@Override
	public LocalTime convertToEntityAttribute(Time time) {
		return time != null ? time.toLocalTime() : null;
	}
}
