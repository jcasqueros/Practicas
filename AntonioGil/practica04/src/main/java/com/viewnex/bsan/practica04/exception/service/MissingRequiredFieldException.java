package com.viewnex.bsan.practica04.exception.service;

import lombok.Getter;

import java.io.Serial;

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
