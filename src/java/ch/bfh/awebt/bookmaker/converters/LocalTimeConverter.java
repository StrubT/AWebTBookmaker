package ch.bfh.awebt.bookmaker.converters;

import java.sql.Time;
import java.time.LocalTime;
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
 * Represents a converter for {@link LocalTime}s.
 *
 * @author strut1 &amp; touwm1
 */
@javax.persistence.Converter
@FacesConverter("ch.bfh.awebt.bookmaker.LOCAL_TIME_CONVERTER")
public class LocalTimeConverter implements Converter, AttributeConverter<LocalTime, Time> {

	/**
	 * Gets the format used to represent the time.
	 */
	public static final DateTimeFormatter FORMAT = DateTimeFormatter.ISO_LOCAL_TIME.withResolverStyle(ResolverStyle.SMART);

	/**
	 * Converts the {@link LocalTime} to a {@link String} value.
	 *
	 * @param context   JSF context (not used)
	 * @param component JSF UI component (not used)
	 * @param time      {@link LocalTime} to convert
	 *
	 * @return {@link String} value for the specified {@link LocalTime}
	 *
	 * @throws ConverterException if the {@link LocalTime} could not be converted
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object time) {

		if (!(time == null || time instanceof LocalTime))
			throw new ConverterException(MessageFactory.getError("ch.bfh.awebt.bookmaker.CONVERTER_ERROR"), new ClassCastException());

		return time != null ? ((LocalTime)time).format(FORMAT) : null;
	}

	/**
	 * Converts the {@link LocalTime} to a {@link Time} value.
	 *
	 * @param time {@link LocalTime} to convert
	 *
	 * @return {@link Time} value for the specified {@link LocalTime}
	 */
	@Override
	public Time convertToDatabaseColumn(LocalTime time) {

		return time != null ? Time.valueOf(time) : null;
	}

	/**
	 * Converts the {@link String} to a {@link LocalTime} value.
	 *
	 * @param context   JSF context (not used)
	 * @param component JSF UI component (not used)
	 * @param time      {@link String} to convert
	 *
	 * @return {@link LocalTime} value for the specified {@link String}
	 *
	 * @throws ConverterException if the {@link String} could not be converted
	 */
	@Override
	public LocalTime getAsObject(FacesContext context, UIComponent component, String time) {

		try {
			return time != null && time.length() > 0 ? LocalTime.parse(time, FORMAT) : null;

		} catch (DateTimeParseException ex) {
			throw new ConverterException(MessageFactory.getWarning("ch.bfh.awebt.bookmaker.DATETIME_FORMAT_ERROR"), ex);
		}
	}

	/**
	 * Converts the {@link Time} to a {@link LocalTime} value.
	 *
	 * @param time {@link LocalTime} to convert
	 *
	 * @return {@link LocalTime} value for the specified {@link Time}
	 */
	@Override
	public LocalTime convertToEntityAttribute(Time time) {

		return time != null ? time.toLocalTime() : null;
	}
}
