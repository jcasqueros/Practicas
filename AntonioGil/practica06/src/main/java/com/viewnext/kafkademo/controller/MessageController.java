package com.viewnext.kafkademo.controller;

import com.viewnext.kafkademo.model.Message;
import com.viewnext.kafkademo.service.MessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A RESTful controller that allows interaction with the message producer(s).
 *
 * @author Antonio Gil
 */
@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    private final MessageProducer messageProducer;

    public MessageController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("")
    public ResponseEntity<Message> postRoot(@RequestBody Message message) {
        return sendMessage(message);
    }

    @PostMapping("/")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        messageProducer.putMessage(message);

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

}
