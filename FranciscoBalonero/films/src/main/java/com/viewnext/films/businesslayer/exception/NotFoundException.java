package com.viewnext.films.businesslayer.exception;

import java.io.Serial;

/**
 * Exception indicating that a resource was not found.
 *
 * <p>This exception is thrown to indicate that a business operation cannot be performed
 * because the specified resource was not found.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * try {
 *     // Business operation that may throw NotFoundException
 *     businessService.performOperation();
 * } catch (NotFoundException e) {
 *     // Handle the exception appropriately
 *     logger.error("Error performing the operation: " + e.getMessage(), e);
 * }
 * }
 * </pre>
 *
 * @author Francisco Balonero Olivera
 * @see ServiceException
 */
public class NotFoundException extends ServiceException {
    @Serial
    private static final long serialVersionUID = -2342342523566251L;

    /**
     * Constructs a new NotFoundException with default message.
     */
    public NotFoundException() {
        super("Resource not found");
    }

    /**
     * Constructs a new NotFoundException with the specified detail message.
     *
     * @param message
     *         The detail message (which is saved for later retrieval by the getMessage() method)
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new NotFoundException with the specified detail message and cause.
     *
     * @param message
     *         The detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause
     *         The cause (which is saved for later retrieval by the getCause() method)
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
