package com.viewnext.kafka.service;

import com.viewnext.kafka.model.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service class for consuming Kafka messages.
 *
 * <p>This service listens to a Kafka topic and processes incoming messages.</p>
 *
 * @author Francisco Balonero Olivera
 * @see org.springframework.kafka.annotation.KafkaListener
 * @see org.springframework.kafka.core.KafkaTemplate
 */
@Service
@AllArgsConstructor
@Slf4j
public class KafkaConsumer {

    /**
     * The Kafka template, used for sending messages to Kafka topics.
     */
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Listens to the "my_topic" topic and processes incoming messages.
     *
     * @param message The incoming message
     */
    @KafkaListener(topics = "my_topic", groupId = "Francisco Balonero")
    public void receiveMessage(String message) {
        log.info("Received message : {} ",message);
    }
}
