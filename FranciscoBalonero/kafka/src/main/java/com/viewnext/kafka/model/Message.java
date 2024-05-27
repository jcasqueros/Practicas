package com.viewnext.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a message sent to a Kafka topic.
 *
 * <p>This class contains the properties of a message, including the user who sent it, the text of the message,
 * a flag indicating whether the message is prime, and a timestamp indicating when the message was sent.</p>
 *
 * @author Francisco Balonero Olivera
 */
@Data
@AllArgsConstructor
public class Message implements Serializable {
    /**
     * The serial version UID of the class.
     */
    @Serial
    private static final long serialVersionUID = 3245274350L;

    /**
     * The user who sent the message.
     */
    private String user;

    /**
     * The text of the message.
     */
    private String text;

    /**
     * A flag indicating whether the message is prime.
     */
    private boolean prime;

    /**
     * The timestamp when the message was sent.
     */
    private LocalDateTime timestamp;
}

