package com.viewnext.kafka.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * A service class that produces messages to a Kafka topic.
 *
 * @author Manuel Mateos de Torres
 */
@RequiredArgsConstructor
@Service
public class KafkaProducer {

    /**
     * The Kafka template instance.
     */
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Sends a message to the "customer" topic.
     *
     * @param message the message to be sent
     */
    public void send(String message) {
        kafkaTemplate.send("customer", message);
    }
}