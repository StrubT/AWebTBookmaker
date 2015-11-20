package ch.bfh.awebt.bookmaker.converters;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.AttributeConverter;
import ch.bfh.awebt.bookmaker.TooManyElementsException;
import ch.bfh.awebt.bookmaker.persistence.data.BetType;
import ch.bfh.awebt.bookmaker.presentation.MessageFactory;

/**
 * Represents a converter for {@link LocalDate}s.
 *
 * @author strut1 &amp; touwm1
 */
@javax.persistence.Converter
@FacesConverter("ch.bfh.awebt.bookmaker.LOCAL_DATE_CONVERTER")
public class BetTypeConverter implements Converter, AttributeConverter<BetType, String> {

	/**
	 * Converts the {@link BetType} to a {@link String} value.
	 *
	 * @param context   JSF context (not used)
	 * @param component JSF UI component (not used)
	 * @param betType   {@link BetType} to convert
	 *
	 * @return {@link String} value for the specified {@link BetType}
	 *
	 * @throws ConverterException if the {@link BetType} could not be converted
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object betType) {

		if (!(betType == null || betType instanceof BetType))
			throw new ConverterException(MessageFactory.getError("ch.bfh.awebt.bookmaker.CONVERTER_ERROR"), new ClassCastException());

		return convertToDatabaseColumn((BetType)betType);
	}

	/**
	 * Converts the {@link BetType} to a {@link String} value.
	 *
	 * @param betType {@link BetType} to convert
	 *
	 * @return {@link String} value for the specified {@link BetType}
	 */
	@Override
	public String convertToDatabaseColumn(BetType betType) {

		return betType != null ? betType.getCode() : null;
	}

	/**
	 * Converts the {@link String} to a {@link BetType} value.
	 *
	 * @param context   JSF context (not used)
	 * @param component JSF UI component (not used)
	 * @param betType   {@link String} to convert
	 *
	 * @return {@link BetType} value for the specified {@link String}
	 *
	 * @throws ConverterException if the {@link String} could not be converted
	 */
	@Override
	public BetType getAsObject(FacesContext context, UIComponent component, String betType) {

		return convertToEntityAttribute(betType);
	}

	/**
	 * Converts the {@link String} to a {@link BetType} value.
	 *
	 * @param betType {@link BetType} to convert
	 *
	 * @return {@link BetType} value for the specified {@link String}
	 */
	@Override
	public BetType convertToEntityAttribute(String betType) {

		try {
			return betType != null ? BetType.getByCode(betType) : null;

		} catch (NoSuchElementException | TooManyElementsException ex) {
			throw new ConverterException(MessageFactory.getWarning("ch.bfh.awebt.bookmaker.CONVERTER_ERROR"), ex);
		}
	}
}
