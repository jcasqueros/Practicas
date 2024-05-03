package com.viewnext.practica.persistencelayer.exception;

import java.io.Serial;

/**
 * Custom exception class indicating that the requested entity was not found.
 *
 * <p>This exception is a subtype of {@link PersistenceLayerException} and is intended to be thrown when an entity is
 * not found during persistence layer operations.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * try {
 *     // Data access operation to find an entity
 *     // ...
 *     if (entity == null) {
 *         throw new EntityNotFoundException("Entity not found");
 *     }
 * } catch (EntityNotFoundException e) {
 *     // Handle the exception
 *     logger.error("Entity not found: " + e.getMessage(), e);
 * }
 * }
 * </pre>
 * <p>The exception includes a serial version UID for serialization purposes.</p>
 *
 * @author Franciosco Balonero Olivera
 * @see PersistenceLayerException
 */
public class EntityNotFoundException extends PersistenceLayerException {
    @Serial
    private static final long serialVersionUID = -7198824234234234L;

    /**
     * Constructs a new EntityNotFoundException with the specified detail message.
     *
     * @param message
     *         {@link String} The detail message (which is saved for later retrieval by the getMessage() method)
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new EntityNotFoundException with the specified detail message and cause.
     *
     * @param message
     *         {@link String} The detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause
     *         {@link Exception}   The cause (which is saved for later retrieval by the getCause() method)
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
