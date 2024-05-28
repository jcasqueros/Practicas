package com.viewnext.bsan.practica03.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RestApiErrorMessage {

    final int statusCode;
    final LocalDateTime timestamp;
    final String message;
    final String description;

}
