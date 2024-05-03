package com.practise.practise.exceptions;

public class UpsertException extends DataAccessException {
    private static final long serialVersionUID = 3288754026165622986L;

    public UpsertException(String message) {
        super(message);
    }

    public UpsertException(String message, Throwable cause) {
        super(message, cause);
    }
}
