package com.viewnext.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaProducerController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("/post")
    public String sendMessageToKafka(@RequestBody String message) {
        kafkaProducerService.sendMessage(message);
        return "SUCCESS: Message sent to Kafka topic!";
    }
}
