package com.viewnext.kafka.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a message sent by a user.
 *
 * @author Manuel Mateos de Torres
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    /**
     * The username of the user who sent the message.
     */
    private String user;

    /**
     * The text of the message.
     */
    private String message;

    /**
     * A flag indicating whether the user is a prime user.
     */
    private boolean primeUser;

    /**
     * The timestamp when the message was sent.
     */
    private LocalDateTime timestamp;
}

