package com.viewnex.bsan.practica04.util.controller;

import com.viewnex.bsan.practica04.exception.service.*;
import com.viewnex.bsan.practica04.util.MessageBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public RestApiErrorMessage methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
                                                                   WebRequest request) {
        return new RestApiErrorMessage(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                MessageBuilder.parameterTypeMismatchMessage(ex.getParameter().getParameterName(),
                        ex.getParameter().getParameterType().getSimpleName()),
                request.getDescription(false));
    }

    @ExceptionHandler(value = {ServiceLevelException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public RestApiErrorMessage serviceLevelException(BadInputDataException ex, WebRequest request) {
        return new RestApiErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
    }

}