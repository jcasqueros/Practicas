package com.viewnex.bsan.practica04.exception.service;

import java.io.Serial;

public class ResourceNotFoundException extends ServiceLevelException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
