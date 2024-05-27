package com.viewnext.kafka.services;

import com.viewnext.kafka.objects.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final KafkaTemplate<String, Message> kafkaTemplate;
    /**
     * Sends a message to the "customer" topic.
     *
     * @param message the message to be sent
     */
    public void send(Message message) {
        kafkaTemplate.send("customer", message);
    }
}