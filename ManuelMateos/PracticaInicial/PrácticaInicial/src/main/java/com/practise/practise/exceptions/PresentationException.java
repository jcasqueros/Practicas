package com.practise.practise.exceptions;

public class PresentationException extends RuntimeException {

    private static final long serialVersionUID = -5763766004373459635L;

    public PresentationException(String message) {
        super(message);
    }

    public PresentationException(String message, Throwable cause) {
        super(message, cause);
    }
}
