package com.viewnext.practica.businesslayer.exception;

import java.io.Serial;

/**
 * Generic business layer exception.
 *
 * <p>This exception serves as a base class for custom exceptions in the business layer.
 * It provides a way to handle and communicate business-specific errors within the application.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * try {
 *     // Business operation that may throw BusinessException
 *     businessService.performOperation();
 * } catch (BusinessException e) {
 *     // Handle the exception appropriately
 *     logger.error("Error performing the operation: " + e.getMessage(), e);
 * }
 * }
 * </pre>
 *
 * <p>The exception includes a serial version UID for serialization purposes.</p>
 *
 * @author Franciso Balonero Olivera
 */
public class BusinessLayerException extends Exception {
    @Serial
    private static final long serialVersionUID = -7198817472286130251L;

    /**
     * Constructs a new BusinessException with default message and the specified detail message.
     *
     * @param cause
     *         The cause (which is saved for later retrieval by the getCause() method)
     */
    public BusinessLayerException(Throwable cause) {
        super("Error with business layer", cause);
    }

    /**
     * Constructs a new BusinessException with the specified detail message.
     *
     * @param message
     *         {@link String} The detail message (which is saved for later retrieval by the getMessage() method)
     */
    public BusinessLayerException(String message) {
        super(message);
    }

    /**
     * Constructs a new BusinessException with the specified detail message and cause.
     *
     * @param message
     *         {@link String} The detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause
     *         {@link Exception}    The cause (which is saved for later retrieval by the getCause() method)
     */
    public BusinessLayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
