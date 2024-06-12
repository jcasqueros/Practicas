package com.santander.kafkaprovider.model.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Time;

/**
 * The type Message.
 */
@Data
@Builder
public class Message {

    private String user;
    private String text;
    private Boolean primerUser;
    private Time timestamp;

}
