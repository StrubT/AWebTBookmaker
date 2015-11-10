package ch.bfh.awebt.bookmaker.converters;

import java.time.ZoneId;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Represents a converter for {@link ZoneId}s.
 *
 * @author strut1 &amp; touwm1
 */
@Converter
public class ZoneIdConverter implements AttributeConverter<ZoneId, String> {

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
	 * @param zoneId {@link ZoneId} to convert
	 *
	 * @return {@link ZoneId} value for the specified {@link String}
	 */
	@Override
	public ZoneId convertToEntityAttribute(String zoneId) {

		return zoneId != null ? ZoneId.of(zoneId) : null;
	}
}
