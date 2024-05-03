package com.viewnext.bsan.practica03.exception.dao;

import java.io.Serial;

public class BadUpsertException extends DaoLevelException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BadUpsertException(String message) {
        super(message);
    }

    public BadUpsertException(String message, Throwable cause) {
        super(message, cause);
    }

}
