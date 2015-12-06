package ch.bfh.awebt.bookmaker.converters;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import javax.faces.convert.ConverterException;
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
public class BetTypeConverter implements AttributeConverter<BetType, String> {

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
