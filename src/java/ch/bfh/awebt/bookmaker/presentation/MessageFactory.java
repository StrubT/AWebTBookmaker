package ch.bfh.awebt.bookmaker.presentation;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
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
	 * Creates a {@link FacesMessage}.
	 *
	 * @param severity  severity of the message to add
	 * @param key       message bundle key of the message to add
	 * @param arguments arguments to pass to the {@link MessageFormat}
	 *
	 * @return a {@link FacesMessage} with the specified severity and formatted message
	 */
	public static FacesMessage getMessage(FacesMessage.Severity severity, String key, Object... arguments) {

		String summary = String.format("???%s???", key);
		String detail = null;

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			ResourceBundle bundle = ResourceBundle.getBundle(context.getApplication().getMessageBundle(), context.getViewRoot().getLocale());

			summary = MessageFormat.format(bundle.getString(key), arguments);
			detail = MessageFormat.format(bundle.getString(key + "_DETAILS"), arguments);

		} catch (MissingResourceException ex) {
			//do nothing, simply show default values
		}

		return new FacesMessage(severity, summary, detail);
	}

	/**
	 * Adds a message to the {@link FacesContext}.
	 *
	 * @param component JSF component to add the message to (may be null)
	 * @param message   message to add
	 */
	public static void addMessage(UIComponent component, FacesMessage message) {

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(component.getClientId(context), message);
	}

	/**
	 * Creates a {@link FacesMessage}.
	 *
	 * @param key       message bundle key of the message to add
	 * @param arguments arguments to pass to the {@link MessageFormat}
	 *
	 * @return a {@link FacesMessage} with the severity {@link FacesMessage#SEVERITY_INFO} and formatted message
	 */
	public static FacesMessage getInformation(String key, Object... arguments) {

		return getMessage(FacesMessage.SEVERITY_INFO, key, arguments);
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
	 * @param component        JSF component to add the message to (may be null)
	 * @param messageKey       message bundle key of the message to add
	 * @param messageArguments arguments to pass to the {@link MessageFormat}
	 */
	public static void addInformation(UIComponent component, String messageKey, Object... messageArguments) {

		addMessage(component, getInformation(messageKey, messageArguments));
	}

	/**
	 * Creates a {@link FacesMessage}.
	 *
	 * @param key       message bundle key of the message to add
	 * @param arguments arguments to pass to the {@link MessageFormat}
	 *
	 * @return a {@link FacesMessage} with the severity {@link FacesMessage#SEVERITY_WARN} and formatted message
	 */
	public static FacesMessage getWarning(String key, Object... arguments) {

		return getMessage(FacesMessage.SEVERITY_WARN, key, arguments);
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
	 * @param component        JSF component to add the message to (may be null)
	 * @param messageKey       message bundle key of the message to add
	 * @param messageArguments arguments to pass to the {@link MessageFormat}
	 */
	public static void addWarning(UIComponent component, String messageKey, Object... messageArguments) {

		addMessage(component, getWarning(messageKey, messageArguments));
	}

	/**
	 * Creates a {@link FacesMessage}.
	 *
	 * @param key       message bundle key of the message to add
	 * @param arguments arguments to pass to the {@link MessageFormat}
	 *
	 * @return a {@link FacesMessage} with the severity {@link FacesMessage#SEVERITY_ERROR} and formatted message
	 */
	public static FacesMessage getError(String key, Object... arguments) {

		return getMessage(FacesMessage.SEVERITY_ERROR, key, arguments);
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
	 * @param component        JSF component to add the message to (may be null)
	 * @param messageKey       message bundle key of the message to add
	 * @param messageArguments arguments to pass to the {@link MessageFormat}
	 */
	public static void addError(UIComponent component, String messageKey, Object... messageArguments) {

		addMessage(component, getError(messageKey, messageArguments));
	}
}
