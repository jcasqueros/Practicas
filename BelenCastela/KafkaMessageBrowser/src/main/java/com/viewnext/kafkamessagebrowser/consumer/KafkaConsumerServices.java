package com.viewnext.kafkamessagebrowser.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumerServices {
    private static final String TOPIC = "FirstTopic";
    private final SimpMessagingTemplate messagingTemplate;

    public KafkaConsumerServices(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    
    @KafkaListener(topics = TOPIC, groupId = "my-group")
    public void handleMessage(String message) {
        log.info("Received raw message from Kafka: {}", message);
        messagingTemplate.convertAndSend("/topic/public", message);
    }
}

