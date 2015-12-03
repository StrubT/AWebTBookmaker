package ch.bfh.awebt.bookmaker.converters;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.AttributeConverter;
import ch.bfh.awebt.bookmaker.presentation.MessageFactory;

/**
 * Represents a converter for {@link LocalDateTime}s.
 *
 * @author strut1 &amp; touwm1
 */
@javax.persistence.Converter
@FacesConverter("ch.bfh.awebt.bookmaker.LOCAL_DATE_TIME_CONVERTER")
public class LocalDateTimeConverter implements Converter, AttributeConverter<LocalDateTime, Timestamp> {

	/**
	 * Gets the technical format used to represent the date/time.
	 */
	public static final DateTimeFormatter FORMAT_ISO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	/**
	 * Gets the human format used to represent the date/time.
	 */
	public static final DateTimeFormatter FORMAT_HUMAN = new DateTimeFormatterBuilder()
		.parseCaseInsensitive().append(LocalDateConverter.FORMAT).appendLiteral(' ').append(LocalTimeConverter.FORMAT).toFormatter();

	/**
	 * Converts the {@link LocalDateTime} to a {@link String} value.
	 *
	 * @param context   JSF context (not used)
	 * @param component JSF UI component (not used)
	 * @param dateTime  {@link LocalDateTime} to convert
	 *
	 * @return {@link String} value for the specified {@link LocalDateTime}
	 *
	 * @throws ConverterException if the {@link LocalDateTime} could not be converted
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object dateTime) {

		if (!(dateTime == null || dateTime instanceof LocalDateTime))
			throw new ConverterException(MessageFactory.getError("ch.bfh.awebt.bookmaker.CONVERTER_ERROR"), new ClassCastException());

		return dateTime != null ? ((ChronoLocalDateTime)dateTime).format(FORMAT_ISO) : null;
	}

	/**
	 * Converts the {@link LocalDateTime} to a {@link Timestamp} value.
	 *
	 * @param dateTime {@link LocalDateTime} to convert
	 *
	 * @return {@link Timestamp} value for the specified {@link LocalDateTime}
	 */
	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime dateTime) {

		return dateTime != null ? Timestamp.valueOf(dateTime) : null;
	}

	/**
	 * Converts the {@link String} to a {@link LocalDateTime} value.
	 *
	 * @param context   JSF context (not used)
	 * @param component JSF UI component (not used)
	 * @param dateTime  {@link String} to convert
	 *
	 * @return {@link LocalDateTime} value for the specified {@link String}
	 *
	 * @throws ConverterException if the {@link String} could not be converted
	 */
	@Override
	public LocalDateTime getAsObject(FacesContext context, UIComponent component, String dateTime) {

		try {
			return dateTime != null && dateTime.length() > 0 ? LocalDateTime.parse(dateTime, FORMAT_ISO) : null;

		} catch (DateTimeParseException ex1) {
			try {
				return LocalDateTime.parse(dateTime, FORMAT_HUMAN);

			} catch (DateTimeParseException ex2) {
				throw new ConverterException(MessageFactory.getWarning("ch.bfh.awebt.bookmaker.DATETIME_FORMAT_ERROR"), ex2);
			}
		}
	}

	/**
	 * Converts the {@link Timestamp} to a {@link LocalDateTime} value.
	 *
	 * @param dateTime {@link LocalDateTime} to convert
	 *
	 * @return {@link LocalDateTime} value for the specified {@link Timestamp}
	 */
	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp dateTime) {

		return dateTime != null ? dateTime.toLocalDateTime() : null;
	}
}
