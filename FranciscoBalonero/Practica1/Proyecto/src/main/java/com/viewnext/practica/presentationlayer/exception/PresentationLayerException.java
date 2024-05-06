package com.viewnext.practica.presentationlayer.exception;

import java.io.Serial;

/**
 * Generic exception in the presentation layer.
 *
 * <p>This exception is a runtime exception indicating an issue in the presentation layer of the application.</p>
 *
 * <p>The exception includes a serial version UID for serialization purposes.</p>
 *
 * @author Franciosco Balonero Olivera
 */
public class PresentationLayerException extends Exception {
    /**
     * Serial version UID for serialization.
     */
    @Serial
    private static final long serialVersionUID = -719232386130251L;

    /**
     * Constructs a new PresentationLayerException with default detail message and cause.
     *
     * @param cause
     *         {@link Exception} The cause (which is saved for later retrieval by the getCause() method)
     */
    public PresentationLayerException(Throwable cause) {
        super("Error with presentation layer", cause);
    }

    /**
     * Constructs a new PresentationLayerException with the specified detail message and cause.
     *
     * @param message
     *         {@link String} The detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause
     *         {@link Exception} The cause (which is saved for later retrieval by the getCause() method)
     */
    public PresentationLayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
