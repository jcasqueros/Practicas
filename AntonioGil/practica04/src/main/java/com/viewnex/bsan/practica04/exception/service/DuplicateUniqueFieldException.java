package com.viewnex.bsan.practica04.exception.service;

import lombok.Getter;

import java.io.Serial;

/**
 * The {@code DuplicateUniqueFieldException} class is a service-level exception type that represents a failed create or
 * update operation due to a uniqueness constraint violation.
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
        this.duplicateFieldName = "";
    }

    public DuplicateUniqueFieldException(String message, String duplicateFieldName) {
        super(message);
        this.duplicateFieldName = duplicateFieldName;
    }

}
