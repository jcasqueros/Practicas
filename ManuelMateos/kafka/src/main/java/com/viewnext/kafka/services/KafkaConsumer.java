package com.viewnext.kafka.services;

import com.viewnext.kafka.objects.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * A service class that consumes messages from a Kafka topic.
 *
 * @author Manuel Mateos de Torres
 */
@RequiredArgsConstructor
@Service
public class KafkaConsumer {

    /**
     * The Kafka template instance.
     */
    private final KafkaTemplate<String, Message> kafkaTemplateConsumer;

    /**
     * A Kafka listener method that consumes messages from the "customer" topic.
     *
     * @param message the message received from Kafka
     */
    @KafkaListener(topics = "customer", groupId = "manuel")
    public void getMessage(Message message) {
        System.out.println("Received message: " + message);
    }
}