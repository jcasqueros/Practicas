package com.viewnext.kafka.controllers;

import com.viewnext.kafka.services.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that handles Kafka request.
 *
 * @author Manuel Mateos de Torres
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    /**
     *  Send a message to a Kafka topic.
     *
     * @param message
     * @return ResponseEntity<String>
     */
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody String message) {
        kafkaProducer.send(message);
        return ResponseEntity.ok("Menssage OK");
    }
}
