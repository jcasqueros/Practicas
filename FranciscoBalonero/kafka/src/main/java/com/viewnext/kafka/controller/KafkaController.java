package com.viewnext.kafka.controller;

import com.viewnext.kafka.model.Message;
import com.viewnext.kafka.service.MessageBuffer;
import com.viewnext.kafka.service.KafkaProducer;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * REST controller for Kafka-related operations.
 *
 * <p>This controller provides endpoints for sending messages to a Kafka topic and retrieving messages from the message buffer.</p>
 *
 * @author Francisco Balonero Olivera
 * @see com.viewnext.kafka.service.KafkaProducer
 * @see com.viewnext.kafka.service.MessageBuffer
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
     * The message buffer, responsible for storing and retrieving messages.
     */
    private final MessageBuffer messageBuffer;

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

    /**
     * Retrieves messages from the message buffer.
     *
     * @return A flux of messages from the message buffer
     */
    @GetMapping(value = "/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Message> getMessages() {
        return messageBuffer.getMessages();
    }
}
