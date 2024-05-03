package com.practise.practise.exceptions;

import java.io.Serial;

public class DataAccessException extends Exception {

    @Serial
    private static final long serialVersionUID = -8413009886792448483L;

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
