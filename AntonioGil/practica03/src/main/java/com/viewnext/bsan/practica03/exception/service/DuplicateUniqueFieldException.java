package com.viewnext.bsan.practica03.exception.service;

import lombok.Getter;

import java.io.Serial;

/**
 * The {@code DuplicateUniqueFieldException} class is a service level exception that represents a failed create/update
 * operation due to a uniqueness constraint violation.
 *
 * @author Antonio Gil
 */
@Getter
public class DuplicateUniqueFieldException extends BadInputDataException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String duplicateFieldName;

    public DuplicateUniqueFieldException(String message) {
        super(message);
        this.duplicateFieldName = "<unknown>";
    }

    public DuplicateUniqueFieldException(String message, String duplicateFieldName) {
        super(message);
        this.duplicateFieldName = duplicateFieldName;
    }

}
