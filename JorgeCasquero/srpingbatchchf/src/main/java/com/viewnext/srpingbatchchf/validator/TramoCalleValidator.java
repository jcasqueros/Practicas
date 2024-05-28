package com.viewnext.srpingbatchchf.validator;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

import com.viewnext.srpingbatchchf.model.TramoCalle;

public class TramoCalleValidator implements Validator<TramoCalle> {

	@Override
	public void validate(TramoCalle tramoCalle) throws ValidationException {
		if (tramoCalle.getTipoVia() == null || tramoCalle.getTipoVia().isEmpty()) {
			throw new ValidationException("el campo tipoVia es obligatorio");
		}

	}

}
