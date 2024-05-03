package com.viewnext.bsan.practica03.exception.dao;

import java.io.Serial;

public class DaoLevelException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public DaoLevelException(String message) {
        super(message);
    }

    public DaoLevelException(String message, Throwable cause) {
        super(message, cause);
    }

}
