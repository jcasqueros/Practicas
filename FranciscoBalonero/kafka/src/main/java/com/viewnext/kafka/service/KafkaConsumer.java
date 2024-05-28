package com.viewnext.kafka.service;

import com.viewnext.kafka.model.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Service class for consuming Kafka messages.
 *
 * <p>This service listens to a Kafka topic and processes incoming messages by adding them to the message buffer.</p>
 *
 * @author Francisco Balonero Olivera
 * @see org.springframework.kafka.annotation.KafkaListener
 */
@Service
@AllArgsConstructor
@Slf4j
public class KafkaConsumer {

    /**
     * The message buffer, responsible for storing incoming messages.
     */
    private final MessageBuffer messageRepository;

    /**
     * Listens to the "my_topic" topic and processes incoming messages by adding them to the message buffer.
     *
     * @param message The incoming message
     */
    @KafkaListener(topics = "my_topic", groupId = "PRV11 Balonero")
    public void receiveMessage(Message message) {
        log.info("Received message : {} ", message);
        messageRepository.addMessage(message);
    }
}