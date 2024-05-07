package com.pracs.films.exceptions;

import java.io.Serial;

public class DuplicatedIdException extends ServiceException {

    @Serial
    private static final long serialVersionUID = -5725989998985999956L;

    public DuplicatedIdException(String message) {
        super(message);
    }
    
}
