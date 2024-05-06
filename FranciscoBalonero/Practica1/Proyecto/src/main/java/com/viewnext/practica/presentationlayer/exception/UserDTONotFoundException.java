package com.viewnext.practica.presentationlayer.exception;

import java.io.Serial;

/**
 * Exception indicating that the requested user was not found.
 *
 * <p>This exception is a specific type of {@link PresentationLayerException} that is thrown to indicate that
 * a requested user could not be found.</p>
 *
 * <p>The exception includes a serial version UID for serialization purposes.</p>
 *
 * @author Franciosco Balonero Olivera
 * @see PresentationLayerException
 */
public class UserDTONotFoundException extends PresentationLayerException {
    /**
     * Serial version UID for serialization.
     */
    @Serial
    private static final long serialVersionUID = -71988174444251L;

    /**
     * Constructs a new UserDTONotFoundException with default detail message and cause.
     *
     * @param cause
     *         {@link Exception} The cause (which is saved for later retrieval by the getCause() method)
     */
    public UserDTONotFoundException(Throwable cause) {
        super("User not found", cause);
    }

    /**
     * Constructs a new UserDTONotFoundException with the specified detail message and cause.
     *
     * @param message
     *         {@link String} The detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause
     *         {@link Exception} The cause (which is saved for later retrieval by the getCause() method)
     */
    public UserDTONotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
