package com.viewnex.bsan.practica04.util.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RestApiErrorMessage {

    int statusCode;
    LocalDateTime timestamp;
    String message;
    String description;

}
