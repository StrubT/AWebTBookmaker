package ch.bfh.awebt.bookmaker.converters;

import java.util.Locale;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.AttributeConverter;

/**
 * Represents a converter for {@link Locale}s.
 *
 * @author strut1 &amp; touwm1
 */
@javax.persistence.Converter
@FacesConverter("ch.bfh.awebt.bookmaker.LOCAL_DATE_CONVERTER")
public class LocaleConverter implements Converter, AttributeConverter<Locale, String> {

	/**
	 * Converts the {@link Locale} to a {@link String} value.
	 *
	 * @param context   JSF context (not used)
	 * @param component JSF UI component (not used)
	 * @param locale    {@link Locale} to convert
	 *
	 * @return {@link String} value for the specified {@link Locale}
	 *
	 * @throws ConverterException if the {@link Locale} could not be converted
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object locale) throws ConverterException {

		if (!(locale == null || locale instanceof Locale))
			throw new ConverterException("Object is not a Locale.");

		return convertToDatabaseColumn((Locale)locale);
	}

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
	 * @param context   JSF context (not used)
	 * @param component JSF UI component (not used)
	 * @param locale    {@link String} to convert
	 *
	 * @return {@link Locale} value for the specified {@link String}
	 *
	 * @throws ConverterException if the {@link String} could not be converted
	 */
	@Override
	public Locale getAsObject(FacesContext context, UIComponent component, String locale) throws ConverterException {

		return convertToEntityAttribute(locale);
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
