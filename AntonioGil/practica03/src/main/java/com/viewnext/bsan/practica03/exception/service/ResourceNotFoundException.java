package com.viewnext.bsan.practica03.exception.service;

import java.io.Serial;

/**
 * The {@code ResourceNotFoundException} class is a service level exception that represents a failed read/update/delete
 * operation due to a required resource not being found.
 *
 * @author Antonio Gil
 */
public class ResourceNotFoundException extends ServiceLevelException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
