package com.practise.practise.exceptions;

public class PersistenceException extends RuntimeException {

    private static final long serialVersionUID = 5799483758275066028L;

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

}
