package com.viewnext.bsan.practica04.business.exception;

import java.io.Serial;

/**
 * The {@code ResourceNotFoundException} class is a service-level exception type that represents a failed read, update
 * or delete operation due to a required resource not being found.
 *
 * @author Antonio Gil
 */
public class ResourceNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
