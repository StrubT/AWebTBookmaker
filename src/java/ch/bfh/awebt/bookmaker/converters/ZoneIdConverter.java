package ch.bfh.awebt.bookmaker.converters;

import java.time.DateTimeException;
import java.time.ZoneId;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.AttributeConverter;

/**
 * Represents a converter for {@link ZoneId}s.
 *
 * @author strut1 &amp; touwm1
 */
@javax.persistence.Converter
@FacesConverter("ch.bfh.awebt.bookmaker.ZONE_ID_CONVERTER")
public class ZoneIdConverter implements Converter, AttributeConverter<ZoneId, String> {

	/**
	 * Converts the {@link ZoneId} to a {@link String} value.
	 *
	 * @param context   JSF context (not used)
	 * @param component JSF UI component (not used)
	 * @param zoneId    {@link ZoneId} to convert
	 *
	 * @return {@link String} value for the specified {@link ZoneId}
	 *
	 * @throws ConverterException if the {@link ZoneId} could not be converted
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object zoneId) throws ConverterException {

		if (!(zoneId == null || zoneId instanceof ZoneId))
			throw new ConverterException("Object is not a ZoneId.");

		return zoneId != null ? ((ZoneId)zoneId).getId() : null;
	}

	/**
	 * Converts the {@link ZoneId} to a {@link String} value.
	 *
	 * @param zoneId {@link ZoneId} to convert
	 *
	 * @return {@link String} value for the specified {@link ZoneId}
	 */
	@Override
	public String convertToDatabaseColumn(ZoneId zoneId) {

		return zoneId != null ? zoneId.getId() : null;
	}

	/**
	 * Converts the {@link String} to a {@link ZoneId} value.
	 *
	 * @param context   JSF context (not used)
	 * @param component JSF UI component (not used)
	 * @param zoneId    {@link String} to convert
	 *
	 * @return {@link ZoneId} value for the specified {@link String}
	 *
	 * @throws ConverterException if the {@link String} could not be converted
	 */
	@Override
	public ZoneId getAsObject(FacesContext context, UIComponent component, String zoneId) throws ConverterException {

		try {
			return zoneId != null ? ZoneId.of(zoneId) : null;

		} catch (DateTimeException ex) {
			throw new ConverterException("Text is not a valid ZoneId.", ex);
		}
	}

	/**
	 * Converts the {@link String} to a {@link ZoneId} value.
	 *
	 * @param zoneId {@link ZoneId} to convert
	 *
	 * @return {@link ZoneId} value for the specified {@link String}
	 */
	@Override
	public ZoneId convertToEntityAttribute(String zoneId) {

		try {
			return zoneId != null ? ZoneId.of(zoneId) : null;

		} catch (DateTimeException ex) {
			throw new ConverterException("Value is not a valid ZoneId.", ex);
		}
	}
}
