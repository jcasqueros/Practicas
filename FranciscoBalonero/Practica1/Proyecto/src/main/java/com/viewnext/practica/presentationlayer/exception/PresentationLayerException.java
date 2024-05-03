package com.viewnext.practica.presentationlayer.exception;

import java.io.Serial;

public class PresentationLayerException extends Exception {
    @Serial
    private static final long serialVersionUID = -719232386130251L;

    public PresentationLayerException(Throwable cause) {
        super("Error with presentation layer", cause);
    }

    public PresentationLayerException(String message) {
        super(message);
    }

    public PresentationLayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
