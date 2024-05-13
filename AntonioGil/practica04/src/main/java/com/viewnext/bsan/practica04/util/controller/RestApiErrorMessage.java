package com.viewnext.bsan.practica04.util.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * POJO class that contains all the relevant data that shall be returned on REST API errors.
 *
 * @author Antonio Gil
 */
@Getter
@AllArgsConstructor
public class RestApiErrorMessage {

    int statusCode;
    LocalDateTime timestamp;
    String message;
    String description;

}
