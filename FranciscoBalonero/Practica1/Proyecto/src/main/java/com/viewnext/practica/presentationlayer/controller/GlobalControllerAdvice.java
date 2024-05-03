package com.viewnext.practica.presentationlayer.controller;

import com.viewnext.practica.presentationlayer.exception.PresentationLayerException;
import com.viewnext.practica.presentationlayer.exception.UserDTONotFoundException;
import com.viewnext.practica.util.Body;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global controller advice for handling exceptions across all controllers.
 *
 * <p>This class provides exception handling mechanisms for various exceptions that may occur during
 * the processing of HTTP requests. It is responsible for translating exceptions into appropriate HTTP responses with
 * meaningful error messages.</p>
 *
 * @author Franciosco Balonero Olivera
 * @see Body
 */
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(PresentationLayerException.class)
    public ResponseEntity<Body> handlePresentationLayerException(Exception ex) {
        Body error = new Body(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UserDTONotFoundException.class)
    public ResponseEntity<Body> handleUserDTONotFoundException(Exception ex) {
        Body error = new Body(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Body> handleValidationErrors(MethodArgumentNotValidException ex) {
        StringBuilder message = new StringBuilder();
        for (FieldError field : ex.getBindingResult().getFieldErrors()) {
            message.append(" ").append(field.getDefaultMessage());
        }
        Body error = new Body(message.toString());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

}
