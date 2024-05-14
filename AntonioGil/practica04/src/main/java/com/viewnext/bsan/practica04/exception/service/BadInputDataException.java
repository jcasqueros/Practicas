package com.viewnext.bsan.practica04.exception.service;

import java.io.Serial;

/**
 * The {@code BadInputDataException} class is a service-level exception type that represents a failed operation because
 * of malformed or semantically invalid input data.
 *
 * @author Antonio Gil
 */
public class BadInputDataException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BadInputDataException(String message) {
        super(message);
    }

}
