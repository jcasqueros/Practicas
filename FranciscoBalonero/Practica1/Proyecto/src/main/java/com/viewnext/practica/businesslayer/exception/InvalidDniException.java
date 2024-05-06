package com.viewnext.practica.businesslayer.exception;

import java.io.Serial;

/**
 * Exception indicating that the DNI is invalid.
 *
 * <p>This exception is thrown to indicate that a business operation cannot be performed
 * due to an invalid DNI.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * try {
 *     // Business operation that may throw InvalidDNIException
 *     businessService.save(User)
 * } catch (InvalidDateException e) {
 *     // Handle the exception appropriately
 *     logger.error("Error validating user DNI: " + e.getMessage(), e);
 * }
 * }
 * </pre>
 *
 * <p>The exception includes a serial version UID for serialization purposes.</p>
 *
 * @author Franciso Balonero Olivera
 * @see BusinessLayerException
 */
public class InvalidDniException extends BusinessLayerException {
    @Serial
    private static final long serialVersionUID = -719881222322351L;

    /**
     * Constructs a new InvalidDniException with the specified detail message.
     *
     * @param message
     *         {@link String} The detail message (which is saved for later retrieval by the getMessage() method)
     */
    public InvalidDniException(String message) {
        super(message);
    }
}
