package com.santander.kafkaconsumer.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

/**
 * The type Message.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String user;
    private String text;
    private Boolean primerUser;
    private Time timestamp;

}
