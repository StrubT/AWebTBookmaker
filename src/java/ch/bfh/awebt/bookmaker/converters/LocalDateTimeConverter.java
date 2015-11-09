package ch.bfh.awebt.bookmaker.converters;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.AttributeConverter;

/**
 * Represents a converter for {@link LocalDateTime}s.
 *
 * @author strut1 &amp; touwm1
 */
@javax.persistence.Converter
@FacesConverter("ch.bfh.awebt.bookmaker.LOCAL_DATE_TIME_CONVERTER")
public class LocalDateTimeConverter implements Converter, AttributeConverter<LocalDateTime, Timestamp> {

	/**
	 * Gets the format used to represent the date/time.
	 */
	public static final DateTimeFormatter FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

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
	public String getAsString(FacesContext context, UIComponent component, Object dateTime) throws ConverterException {

		if (!(dateTime == null || dateTime instanceof LocalDateTime))
			throw new ConverterException("Object is not a LocalDateTime.");

		return dateTime != null ? ((ChronoLocalDateTime)dateTime).format(FORMAT) : null;
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
	public LocalDateTime getAsObject(FacesContext context, UIComponent component, String dateTime) throws ConverterException {

		try {
			return dateTime != null ? LocalDateTime.parse(dateTime, FORMAT) : null;

		} catch (DateTimeParseException ex) {
			throw new ConverterException("Format is not valid for LocalDate.");
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
