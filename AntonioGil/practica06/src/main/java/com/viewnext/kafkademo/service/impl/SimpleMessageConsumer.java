package com.viewnext.kafkademo.service.impl;

import com.viewnext.kafkademo.model.Message;
import com.viewnext.kafkademo.service.MessageConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * A simple implementation for the {@code MessageConsumer} interface. It simply listens to a Kafka topic and logs any
 * received messages, using SLF4J.
 */
@Slf4j
@Service
public class SimpleMessageConsumer implements MessageConsumer {

    @KafkaListener(id = "MessageConsumerImpl", topics = {"${kafka-demo.kafka.topic-name}"})
    public void listen(Message receivedMessage) {
        consume(receivedMessage);
    }

    @Override
    public void consume(Message message) {
        log.info("Message received: {}", message);
    }

}
