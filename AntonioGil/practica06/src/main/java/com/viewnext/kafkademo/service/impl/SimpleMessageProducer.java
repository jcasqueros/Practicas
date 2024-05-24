package com.viewnext.kafkademo.service.impl;

import com.viewnext.kafkademo.model.Message;
import com.viewnext.kafkademo.service.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * A simple implementation for the {@code MessageProducer} interface. It simply sends a message to the Kafka topic when
 * instructed to.
 *
 * @author Antonio Gil
 */
@Slf4j
@Service
public class SimpleMessageProducer implements MessageProducer {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Value("${kafka-demo.kafka.topic-name}")
    private String topicName;

    public SimpleMessageProducer(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void putMessage(Message message) {
        kafkaTemplate.send(topicName, message);
    }

}
