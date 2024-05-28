package com.viewnext.kafka.service;

import com.viewnext.kafka.model.Message;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service class for producing Kafka messages.
 *
 * <p>This service sends messages to a Kafka topic using the Kafka template.</p>
 *
 * @author Francisco Balonero Olivera
 * @see org.springframework.kafka.core.KafkaTemplate
 */
@Service
@AllArgsConstructor
public class KafkaProducer {

    /**
     * The Kafka template, used for sending messages to Kafka topics.
     */
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Sends a message to the "my_topic" topic.
     *
     * @param message The message to be sent
     */
    public void sendMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        kafkaTemplate.send("my_topic", message);
    }
}
