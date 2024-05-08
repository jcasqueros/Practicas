package com.viewnex.bsan.practica04.exception.service;

import java.io.Serial;

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
