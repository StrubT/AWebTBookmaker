package ch.bfh.awebt.bookmaker.presentation;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public final class MessageFactory {

	private MessageFactory() {
		//utility class
	}

	public static void addMessage(String componentClientId, FacesMessage.Severity messageSeverity, String messageKey, Object... messageArguments) {

		String summary;
		String detail;

		FacesContext context = FacesContext.getCurrentInstance();
		String name = context.getApplication().getMessageBundle();
		if (name == null)
			name = FacesMessage.FACES_MESSAGES;

		Locale locale = context.getViewRoot().getLocale();

		try {
			ResourceBundle bundle = ResourceBundle.getBundle(name, locale);
			summary = MessageFormat.format(bundle.getString(messageKey), messageArguments);
			detail = MessageFormat.format(bundle.getString(messageKey + "_detail"), messageArguments);

		} catch (MissingResourceException ex) {
			summary = "???" + messageKey + "???";
			detail = null;
		}

		context.addMessage(componentClientId, new FacesMessage(messageSeverity, summary, detail));
	}

	public static void addInformation(String messageKey, Object... messageArguments) {

		addInformation(null, messageKey, messageArguments);
	}

	public static void addInformation(String componentClientId, String messageKey, Object... messageArguments) {

		addMessage(componentClientId, FacesMessage.SEVERITY_INFO, messageKey, messageArguments);
	}

	public static void addWarning(String messageKey, Object... messageArguments) {

		addWarning(null, messageKey, messageArguments);
	}

	public static void addWarning(String componentClientId, String messageKey, Object... messageArguments) {

		addMessage(componentClientId, FacesMessage.SEVERITY_WARN, messageKey, messageArguments);
	}

	public static void addError(String messageKey, Object... messageArguments) {

		addError(null, messageKey, messageArguments);
	}

	public static void addError(String componentClientId, String messageKey, Object... messageArguments) {

		addMessage(componentClientId, FacesMessage.SEVERITY_ERROR, messageKey, messageArguments);
	}
}
