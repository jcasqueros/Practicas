package com.viewnext.kafka.objects;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Message {

    private String user;
    private String message;
    private boolean primeUser;
    //private Timestamp timestamp;
}
