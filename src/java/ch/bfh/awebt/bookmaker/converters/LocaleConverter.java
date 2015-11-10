package ch.bfh.awebt.bookmaker.converters;

import java.util.Locale;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Represents a converter for {@link Locale}s.
 *
 * @author strut1 &amp; touwm1
 */
@Converter
public class LocaleConverter implements AttributeConverter<Locale, String> {

	/**
	 * Converts the {@link Locale} to a {@link String} value.
	 *
	 * @param locale {@link Locale} to convert
	 *
	 * @return {@link String} value for the specified {@link Locale}
	 */
	@Override
	public String convertToDatabaseColumn(Locale locale) {

		if (locale == null)
			return null;

		StringBuilder sb = new StringBuilder(locale.getLanguage());
		if (locale.getCountry() != null && locale.getCountry().length() > 0)
			sb.append('_').append(locale.getCountry());
		if (locale.getVariant() != null && locale.getVariant().length() > 0)
			sb.append('_').append(locale.getVariant());
		return sb.toString();
	}

	/**
	 * Converts the {@link String} to a {@link Locale} value.
	 *
	 * @param locale {@link Locale} to convert
	 *
	 * @return {@link Locale} value for the specified {@link String}
	 */
	@Override
	public Locale convertToEntityAttribute(String locale) {

		if (locale == null)
			return null;

		String[] lcv = locale.split("_");
		return new Locale(lcv.length > 0 ? lcv[0] : "", lcv.length > 1 ? lcv[1] : "", lcv.length > 2 ? lcv[2] : "");
	}
}
