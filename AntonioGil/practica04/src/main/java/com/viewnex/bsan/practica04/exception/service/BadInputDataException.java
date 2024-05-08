package com.viewnex.bsan.practica04.exception.service;

import java.io.Serial;

public class BadInputDataException extends ServiceLevelException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BadInputDataException(String message) {
        super(message);
    }

}
