package ch.bfh.awebt.bookmaker.converters;

import java.math.BigDecimal;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import ch.bfh.awebt.bookmaker.presentation.MessageFactory;

@FacesConverter("ch.bfh.awebt.bookmaker.CURRENCY_CONVERTER")
@FacesValidator("ch.bfh.awebt.bookmaker.CURRENCY_VALIDATOR")
public class CurrencyConverterValidator implements Converter, Validator {

	public static final int PRECISION = 10;
	public static final int SCALE = 3;

	/**
	 *
	 * @param context
	 * @param component
	 * @param value
	 *
	 * @return
	 *
	 * @throws ConverterException
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (!(value == null || value instanceof BigDecimal))
			throw new ConverterException(MessageFactory.getError("ch.bfh.awebt.bookmaker.CONVERTER_ERROR"), new ClassCastException());

		return value != null ? value.toString() : null;
	}

	/**
	 *
	 * @param context
	 * @param component
	 * @param value
	 *
	 * @return
	 *
	 * @throws ConverterException
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		try {
			return value != null ? new BigDecimal(value) : null;

		} catch (NumberFormatException ex) {
			throw new ConverterException(MessageFactory.getWarning("ch.bfh.awebt.bookmaker.CONVERTER_ERROR"), ex);
		}
	}

	/**
	 *
	 * @param context
	 * @param component
	 * @param value
	 *
	 * @throws ValidatorException
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) {

		if (!(value == null || value instanceof BigDecimal))
			throw new ValidatorException(MessageFactory.getError("ch.bfh.awebt.bookmaker.CONVERTER_ERROR"));

		BigDecimal bd = (BigDecimal)value;
		if (bd == null || bd.compareTo(BigDecimal.ZERO) < 0)
			throw new ValidatorException(MessageFactory.getWarning("ch.bfh.awebt.bookmaker.CURRENCY_NEGATIVE"));

		if (bd.precision() > PRECISION || bd.scale() > SCALE)
			throw new ValidatorException(MessageFactory.getWarning("ch.bfh.awebt.bookmaker.CURRENCY_PRECISION_ERROR"));
	}
}
