package com.viewnext.kafka.controller;

import com.viewnext.kafka.model.Message;
import com.viewnext.kafka.service.KafkaProducer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for Kafka-related operations.
 *
 * <p>This controller provides a single endpoint for sending messages to a Kafka topic.</p>
 *
 * @author Francisco Balonero Olivera
 * @see com.viewnext.kafka.service.KafkaProducer
 */
@RestController
@RequestMapping("/api/kafka")
@AllArgsConstructor
public class KafkaController {

    /**
     * The Kafka producer service, responsible for sending messages to Kafka topics.
     */
    private final KafkaProducer kafkaProducer;

    /**
     * Sends a message to a Kafka topic.
     *
     * @param message The message to be sent
     * @return A response indicating that the message was sent successfully
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody Message message) {
        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }
}
