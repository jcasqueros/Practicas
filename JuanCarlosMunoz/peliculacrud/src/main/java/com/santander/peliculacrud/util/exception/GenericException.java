package com.santander.peliculacrud.util.exception;

/**
 * The type Generic exception.
 */
public class GenericException extends Exception {

    /**
     * Instantiates a new Generic exception.
     *
     * @param message
     *         the message
     * @param cause
     *         the cause
     */
    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Generic exception.
     *
     * @param message
     *         the message
     */
    public GenericException(String message) {
        super(message);
    }

}
