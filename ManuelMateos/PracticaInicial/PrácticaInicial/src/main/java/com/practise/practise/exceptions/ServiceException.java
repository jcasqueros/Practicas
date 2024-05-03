package com.practise.practise.exceptions;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 1356738783056747376L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
