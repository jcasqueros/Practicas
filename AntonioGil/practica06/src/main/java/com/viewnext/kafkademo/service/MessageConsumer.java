package com.viewnext.kafkademo.service;

import com.viewnext.kafkademo.model.Message;

/**
 * Service that consumes messages received from a Kafka topic.
 *
 * @author Antonio Gil
 */
public interface MessageConsumer {

    void consume(Message message);

}
