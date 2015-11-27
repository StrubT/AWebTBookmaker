package ch.bfh.awebt.bookmaker.converters;

import java.sql.Date;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.AttributeConverter;
import ch.bfh.awebt.bookmaker.presentation.MessageFactory;

/**
 * Represents a converter for {@link LocalDate}s.
 *
 * @author strut1 &amp; touwm1
 */
@javax.persistence.Converter
@FacesConverter("ch.bfh.awebt.bookmaker.LOCAL_DATE_CONVERTER")
public class LocalDateConverter implements Converter, AttributeConverter<LocalDate, Date> {

	/**
	 * Gets the format used to represent the date.
	 */
	public static final DateTimeFormatter FORMAT = DateTimeFormatter.ISO_LOCAL_DATE.withResolverStyle(ResolverStyle.SMART);

	/**
	 * Converts the {@link LocalDate} to a {@link String} value.
	 *
	 * @param context   JSF context (not used)
	 * @param component JSF UI component (not used)
	 * @param date      {@link LocalDate} to convert
	 *
	 * @return {@link String} value for the specified {@link LocalDate}
	 *
	 * @throws ConverterException if the {@link LocalDate} could not be converted
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object date) {

		if (!(date == null || date instanceof LocalDate))
			throw new ConverterException(MessageFactory.getError("ch.bfh.awebt.bookmaker.CONVERTER_ERROR"), new ClassCastException());

		return date != null ? ((ChronoLocalDate)date).format(FORMAT) : null;
	}

	/**
	 * Converts the {@link LocalDate} to a {@link Date} value.
	 *
	 * @param date {@link LocalDate} to convert
	 *
	 * @return {@link Date} value for the specified {@link LocalDate}
	 */
	@Override
	public Date convertToDatabaseColumn(LocalDate date) {

		return date != null ? Date.valueOf(date) : null;
	}

	/**
	 * Converts the {@link String} to a {@link LocalDate} value.
	 *
	 * @param context   JSF context (not used)
	 * @param component JSF UI component (not used)
	 * @param date      {@link String} to convert
	 *
	 * @return {@link LocalDate} value for the specified {@link String}
	 *
	 * @throws ConverterException if the {@link String} could not be converted
	 */
	@Override
	public LocalDate getAsObject(FacesContext context, UIComponent component, String date) {

		try {
			return date != null ? LocalDate.parse(date, FORMAT) : null;

		} catch (DateTimeParseException ex) {
			throw new ConverterException(MessageFactory.getWarning("ch.bfh.awebt.bookmaker.DATETIME_FORMAT_ERROR"), ex);
		}
	}

	/**
	 * Converts the {@link Date} to a {@link LocalDate} value.
	 *
	 * @param date {@link LocalDate} to convert
	 *
	 * @return {@link LocalDate} value for the specified {@link Date}
	 */
	@Override
	public LocalDate convertToEntityAttribute(Date date) {

		return date != null ? date.toLocalDate() : null;
	}
}
