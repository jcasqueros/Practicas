package com.viewnext.bsan.practica03.exception.service;

import java.io.Serial;

/**
 * The {@code ServiceLevelException} class is the root class for all exceptions that happen at service level.
 *
 * @author Antonio Gil
 */
public class ServiceLevelException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public ServiceLevelException(String message) {
        super(message);
    }

    public ServiceLevelException(String message, Throwable cause) {
        super(message, cause);
    }

}
