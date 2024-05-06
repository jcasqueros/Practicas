package com.viewnext.bsan.practica03.exception.dao;

import java.io.Serial;

/**
 * The {@code BadUpsertException} class is a DAO level exception class that represents a failed insert or update
 * operation, for reasons like integrity constraint violations or semantically incorrect data.
 *
 * @author Antonio Gil
 */
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
