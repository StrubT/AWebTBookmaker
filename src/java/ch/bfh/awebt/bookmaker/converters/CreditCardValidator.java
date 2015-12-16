package ch.bfh.awebt.bookmaker.converters;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import ch.bfh.awebt.bookmaker.presentation.MessageFactory;

/**
 * Represents a validator for credit card numbers.
 *
 * @author strut1 &amp; touwm1
 */
@FacesValidator("ch.bfh.awebt.bookmaker.CREDIT_CARD_VALIDATOR")
public class CreditCardValidator extends org.apache.commons.validator.CreditCardValidator implements Validator {

	/**
	 * The regex {@link Pattern} to use when cleaning superfluous characters from the credit card number.
	 */
	public static final Pattern CLEAN_CREDIT_CARD_PATTERN = Pattern.compile("\\s+");

	private static final Set<String> CREDIT_CARD_EXCEPTIONS = new HashSet<>(Arrays.asList("1234123412341234"));

	/**
	 * Gets the credit card numbers to recognise as valid even though they do not match the official constraints.
	 *
	 * @return credit card numbers to recognise as valid
	 */
	public static Set<String> getCreditCardsExceptions() {

		return Collections.unmodifiableSet(CREDIT_CARD_EXCEPTIONS);
	}

	/**
	 * Check whether or not the credit card number matches the constraints.
	 *
	 * @param card credit card number to convert
	 *
	 * @return whether or not the credit card number matches the constraints
	 */
	@Override
	public boolean isValid(String card) {

		String clean = CLEAN_CREDIT_CARD_PATTERN.matcher(card).replaceAll("");
		return CREDIT_CARD_EXCEPTIONS.contains(clean) || super.isValid(card);
	}

	/**
	 * Validate the credit card number using constraints.
	 *
	 * @param context   JSF context (not used)
	 * @param component JSF UI component (not used)
	 * @param value     credit card number to convert
	 *
	 * @throws ValidatorException if the credit card number is not valid
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if (!(value == null || value instanceof String))
			throw new ValidatorException(MessageFactory.getError("ch.bfh.awebt.bookmaker.CONVERTER_ERROR"));

		if (!isValid((String)value))
			throw new ValidatorException(MessageFactory.getWarning("org.apache.myfaces.Creditcard.INVALID"));
	}
}
