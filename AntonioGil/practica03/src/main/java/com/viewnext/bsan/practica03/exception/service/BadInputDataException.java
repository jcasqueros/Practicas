package com.viewnext.bsan.practica03.exception.service;

import java.io.Serial;

/**
 * The {@code BadInputDataException} class is a service level exception that represents a failed operation because of
 * malformed or semantically incorrect input data.
 *
 * @author Antonio Gil
 */
public class BadInputDataException extends ServiceLevelException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BadInputDataException(String message) {
        super(message);
    }

}
