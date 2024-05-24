package com.viewnext.kafkademo.service;

import com.viewnext.kafkademo.model.Message;

/**
 * Service that writes messages to a Kafka topic.
 *
 * @author Antonio Gil
 */
public interface MessageProducer {

    void putMessage(Message message);

}
