package com.practise.practise.exceptions;

import java.io.Serial;

public class DuplicatedIdException extends DataAccessException {

    @Serial
    private static final long serialVersionUID = -5725989998985999956L;

    public DuplicatedIdException(String message) {
        super(message);
    }

    public DuplicatedIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
