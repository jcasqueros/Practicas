package com.viewnex.bsan.practica04.exception.service;

import java.io.Serial;

/**
 * The {@code ServiceLevelException} class is the root class for all service-level exceptions.
 *
 * @author Antonio Gil
 */
public class ServiceLevelException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ServiceLevelException(String message) {
        super(message);
    }

    public ServiceLevelException(String message, Throwable cause) {
        super(message, cause);
    }

}
