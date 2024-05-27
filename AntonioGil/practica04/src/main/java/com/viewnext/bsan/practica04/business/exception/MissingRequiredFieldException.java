package com.viewnext.bsan.practica04.business.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * The {@code MissingRequiredFieldException} class is a service-level exception type that represents a failed operation
 * due to required data not being present (e.g.: set to null or a blank string).
 *
 * @author Antonio Gil
 */
@Getter
public class MissingRequiredFieldException extends BadInputDataException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String missingFieldName;

    public MissingRequiredFieldException(String message) {
        super(message);
        this.missingFieldName = "";
    }

    public MissingRequiredFieldException(String message, String missingFieldName) {
        super(message);
        this.missingFieldName = missingFieldName;
    }

}
