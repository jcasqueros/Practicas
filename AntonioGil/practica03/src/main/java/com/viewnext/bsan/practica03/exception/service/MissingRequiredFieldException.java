package com.viewnext.bsan.practica03.exception.service;

import lombok.Getter;

import java.io.Serial;
/**
 * The {@code MissingRequiredFieldException} class is a service level exception that represents a failed operation
 * because there's required data that is missing (i.e. has been set to a null or blank value).
 *
 * @author Antonio Gil
 */

@Getter
public class MissingRequiredFieldException extends BadInputDataException {

    private final String missingFieldName;

    @Serial
    private static final long serialVersionUID = 1L;

    public MissingRequiredFieldException(String message) {
        super(message);
        this.missingFieldName = "<unknown>";
    }

    public MissingRequiredFieldException(String message, String missingFieldName) {
        super(message);
        this.missingFieldName = missingFieldName;
    }

}
