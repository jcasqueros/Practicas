package com.viewnext.kafka.services;

import com.viewnext.kafka.objects.Message;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * A service class that produces messages to a Kafka topic.
 *
 * @author Manuel Mateos de Torres
 */
@AllArgsConstructor
@Service
public class KafkaProducer {

    /**
     * The Kafka template instance.
     */
    private final KafkaTemplate<String, Message> kafkaTemplate;

    /**
     * Sends a message to the "customer" topic.
     *
     * @param message
     *         the message to be sent
     */
    public void send(Message message) {
        message.setTimestamp(LocalDateTime.now());
        kafkaTemplate.send("customer", message);
    }
}