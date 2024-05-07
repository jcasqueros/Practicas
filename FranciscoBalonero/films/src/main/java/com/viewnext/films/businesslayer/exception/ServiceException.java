package com.viewnext.films.businesslayer.exception;

import java.io.Serial;

/**
 * Base exception for service layer exceptions.
 *
 * <p>This exception is thrown to indicate that a business operation cannot be performed
 * due to a service layer error.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * try {
 *     // Business operation that may throw ServiceException
 *     businessService.performOperation();
 * } catch (ServiceException e) {
 *     // Handle the exception appropriately
 *     logger.error("Error performing the operation: " + e.getMessage(), e);
 * }
 * }
 * </pre>
 *
 * @author Francisco Balonero Olivera
 */
public class ServiceException extends Exception {
    @Serial
    private static final long serialVersionUID = -12365674651L;

    /**
     * Constructs a new ServiceException with default message.
     */
    public ServiceException() {
        super("Service error");
    }

    /**
     * Constructs a new ServiceException with the specified detail message.
     *
     * @param message
     *         The detail message (which is saved for later retrieval by the getMessage() method)
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new ServiceException with the specified detail message and cause.
     *
     * @param message
     *         The detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause
     *         The cause (which is saved for later retrieval by the getCause() method)
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
