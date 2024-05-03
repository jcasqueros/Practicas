package com.practise.practise.exceptions;

public class EmptyException extends DataAccessException {

    private static final long serialVersionUID = 7996670377012553826L;

    public EmptyException(String message) {
        super(message);
    }

    public EmptyException(String message, Throwable cause) {
        super(message, cause);
    }
}
