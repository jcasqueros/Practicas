package com.viewnext.practica.persistencelayer.exception;

import java.io.Serial;

/**
 * Custom exception class for generic persistence layer exceptions.
 *
 * <p>This exception is intended to be thrown when an error occurs during persistence operations.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * try {
 *     //Persistence layer operation
 *     // ...
 * } catch (PersistenceLayerException e) {
 *     // Handle the exception
 *     logger.error("Persistence layer error: " + e.getMessage(), e);
 * }
 * }
 * </pre>
 *
 * <p>The exception includes a serial version UID for serialization purposes.</p>
 *
 * @author Franciosco Balonero Olivera
 */
public class PersistenceLayerException extends Exception {
    @Serial
    private static final long serialVersionUID = -7198817472286130251L;

    /**
     * Constructs a new PersistenceLayerException with a default message and specified cause.
     *
     * @param cause
     *         {@link Throwable} The cause (which is saved for later retrieval by the getCause() method)
     */
    public PersistenceLayerException(Throwable cause) {
        super("Error with persistence layer", cause);
    }

    /**
     * Constructs a new PersistenceLayerException with the specified detail message.
     *
     * @param message
     *         {@link String} The detail message (which is saved for later retrieval by the getMessage() method)
     */
    public PersistenceLayerException(String message) {
        super(message);
    }

    /**
     * Constructs a new PersistenceLayerException with the specified detail message and cause.
     *
     * @param message
     *         {@link String} The detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause
     *         {@link Exception}   The cause (which is saved for later retrieval by the getCause() method)
     */
    public PersistenceLayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
