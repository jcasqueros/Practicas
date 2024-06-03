package com.viewnext.kafka.controllers;

import com.viewnext.kafka.objects.Message;
import com.viewnext.kafka.services.KafkaProducer;
import com.viewnext.kafka.services.MessageBuffer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * A REST controller that handles Kafka requests.
 *
 * This controller provides endpoints for sending messages to a Kafka topic and retrieving messages from a message
 * buffer.
 *
 * @author Manuel Mateos de Torres
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    /**
     * The Kafka producer instance.
     */
    private final KafkaProducer kafkaProducer;
    

    /**
     * The message buffer instance.
     */
    private final MessageBuffer messageBuffer;

    /**
     * Sends a message to a Kafka topic.
     *
     * @param message
     *         the message to send
     * @return a response entity with a success message
     */
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody Message message) {
        kafkaProducer.send(message);
        return ResponseEntity.ok("Message OK");
    }

    /**
     * Retrieves messages from the message buffer as a reactive flux.
     *
     * @return a flux of messages
     */
    @GetMapping(value = "/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Message> getMessages() {
        return messageBuffer.getMessages();
    }
}
