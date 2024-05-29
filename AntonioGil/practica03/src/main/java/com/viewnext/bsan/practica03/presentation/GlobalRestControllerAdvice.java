package com.viewnext.bsan.practica03.presentation;

import com.viewnext.bsan.practica03.business.exception.BadInputDataException;
import com.viewnext.bsan.practica03.business.exception.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica03.business.exception.MissingRequiredFieldException;
import com.viewnext.bsan.practica03.business.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public RestApiErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new RestApiErrorMessage(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(value = {DuplicateUniqueFieldException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public RestApiErrorMessage duplicateUniqueFieldException(DuplicateUniqueFieldException ex, WebRequest request) {
        return new RestApiErrorMessage(HttpStatus.CONFLICT.value(), LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(value = {MissingRequiredFieldException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public RestApiErrorMessage missingRequiredFieldException(MissingRequiredFieldException ex, WebRequest request) {
        return new RestApiErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(value = {BadInputDataException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public RestApiErrorMessage badInputDataException(BadInputDataException ex, WebRequest request) {
        return new RestApiErrorMessage(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
    }

}