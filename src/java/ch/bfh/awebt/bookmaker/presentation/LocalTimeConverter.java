package ch.bfh.awebt.bookmaker.presentation;

import java.time.LocalTime;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("ch.bfh.awebt.bookmaker.presentation.LocalTimeConverter")
public class LocalTimeConverter implements Converter {

	@Override
	public LocalTime getAsObject(FacesContext fc, UIComponent uic, String string) throws ConverterException {
		return string != null ? LocalTime.parse(string) : null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) throws ConverterException {
		return o != null && o instanceof LocalTime ? o.toString() : null;
	}
}
