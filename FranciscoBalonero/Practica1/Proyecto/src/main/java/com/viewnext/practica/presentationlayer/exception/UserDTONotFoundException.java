package com.viewnext.practica.presentationlayer.exception;

import java.io.Serial;

public class UserDTONotFoundException extends PresentationLayerException {
    @Serial
    private static final long serialVersionUID = -71988174444251L;

    public UserDTONotFoundException(Throwable cause) {
        super("User not found", cause);
    }

    public UserDTONotFoundException(String message) {
        super(message);
    }

    public UserDTONotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
