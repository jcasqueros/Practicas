package com.practise.practise.exceptions;

public class EntityNotFoundException extends PersistenceException {

    private static final long serialVersionUID = -8413009886792448483L;

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
