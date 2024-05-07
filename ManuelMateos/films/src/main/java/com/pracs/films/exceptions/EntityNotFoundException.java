package com.pracs.films.exceptions;

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -8413009886792448483L;

    public EntityNotFoundException(String message) {
        super(message);
    }

}
