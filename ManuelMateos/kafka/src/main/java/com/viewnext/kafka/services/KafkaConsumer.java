package com.viewnext.kafka.services;

import com.viewnext.kafka.objects.Message;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * A service class that consumes messages from a Kafka topic.
 *
 * @author Manuel Mateos de Torres
 */
@AllArgsConstructor
@Service
public class KafkaConsumer {

    /**
     * The message buffer instance.
     */
    private final MessageBuffer messageBuffer;

    /**
     * A Kafka listener method that consumes messages from the "customer" topic.
     *
     * @param message
     *         the message received from Kafka
     */
    @KafkaListener(topics = "customer", groupId = "manuel")
    public void getMessage(Message message) {
        messageBuffer.addMessage(message);
    }
}