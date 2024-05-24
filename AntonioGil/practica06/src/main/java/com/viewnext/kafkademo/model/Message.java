package com.viewnext.kafkademo.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Domain model class that represents a message.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    String messageContent;

    String user;

    boolean writtenByPrimeUser;

    LocalDateTime timestamp;

}
