package com.pracs.films.exceptions;

public class EmptyException extends ServiceException {

    private static final long serialVersionUID = 7996670377012553826L;

    public EmptyException(String message) {
        super(message);
    }

}
