package com.viewnext.kafkamessagebrowser.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.viewnext.kafkamessagebrowser.model.ChatMessage;

import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Service
public class KafkaProducerServices {
    private static final String TOPIC = "FirstTopic";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerServices(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
    }

    public void sendMessage(ChatMessage message) {
        kafkaTemplate.send(TOPIC, message.toString());
    }
}
