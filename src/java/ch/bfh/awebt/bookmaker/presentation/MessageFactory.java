package ch.bfh.awebt.bookmaker.presentation;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Utility class to create internationalised JSF messages.
 *
 * @author strut1 &amp; touwm1
 */
public final class MessageFactory {

	private MessageFactory() {
		//utility class
	}

	/**
	 * Adds a message to the {@link FacesContext}.
	 *
	 * @param componentClientId client identifier of the JSF component to add the message to (may be null)
	 * @param messageSeverity   severity of the message to add
	 * @param messageKey        message bundle key of the message to add
	 * @param messageArguments  arguments to pass to the {@link MessageFormat}
	 */
	public static void addMessage(String componentClientId, FacesMessage.Severity messageSeverity, String messageKey, Object... messageArguments) {

		String summary = String.format("???%s???", messageKey);
		String detail = null;

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			ResourceBundle bundle = ResourceBundle.getBundle(context.getApplication().getMessageBundle(),
																											 context.getViewRoot().getLocale());

			summary = MessageFormat.format(bundle.getString(messageKey), messageArguments);
			detail = MessageFormat.format(bundle.getString(messageKey + "_DETAILS"), messageArguments);

		} catch (MissingResourceException ex) {
			//do nothing, simply show default values
		}

		context.addMessage(componentClientId, new FacesMessage(messageSeverity, summary, detail));
	}

	/**
	 * Adds an information message to the {@link FacesContext}.
	 *
	 * @param messageKey       message bundle key of the message to add
	 * @param messageArguments arguments to pass to the {@link MessageFormat}
	 */
	public static void addInformation(String messageKey, Object... messageArguments) {

		addInformation(null, messageKey, messageArguments);
	}

	/**
	 * Adds an information message to the {@link FacesContext}.
	 *
	 * @param componentClientId client identifier of the JSF component to add the message to (may be null)
	 * @param messageKey        message bundle key of the message to add
	 * @param messageArguments  arguments to pass to the {@link MessageFormat}
	 */
	public static void addInformation(String componentClientId, String messageKey, Object... messageArguments) {

		addMessage(componentClientId, FacesMessage.SEVERITY_INFO, messageKey, messageArguments);
	}

	/**
	 * Adds a warning message to the {@link FacesContext}.
	 *
	 * @param messageKey       message bundle key of the message to add
	 * @param messageArguments arguments to pass to the {@link MessageFormat}
	 */
	public static void addWarning(String messageKey, Object... messageArguments) {

		addWarning(null, messageKey, messageArguments);
	}

	/**
	 * Adds a warning message to the {@link FacesContext}.
	 *
	 * @param componentClientId client identifier of the JSF component to add the message to (may be null)
	 * @param messageKey        message bundle key of the message to add
	 * @param messageArguments  arguments to pass to the {@link MessageFormat}
	 */
	public static void addWarning(String componentClientId, String messageKey, Object... messageArguments) {

		addMessage(componentClientId, FacesMessage.SEVERITY_WARN, messageKey, messageArguments);
	}

	/**
	 * Adds an error message to the {@link FacesContext}.
	 *
	 * @param messageKey       message bundle key of the message to add
	 * @param messageArguments arguments to pass to the {@link MessageFormat}
	 */
	public static void addError(String messageKey, Object... messageArguments) {

		addError(null, messageKey, messageArguments);
	}

	/**
	 * Adds an error message to the {@link FacesContext}.
	 *
	 * @param componentClientId client identifier of the JSF component to add the message to (may be null)
	 * @param messageKey        message bundle key of the message to add
	 * @param messageArguments  arguments to pass to the {@link MessageFormat}
	 */
	public static void addError(String componentClientId, String messageKey, Object... messageArguments) {

		addMessage(componentClientId, FacesMessage.SEVERITY_ERROR, messageKey, messageArguments);
	}
}
