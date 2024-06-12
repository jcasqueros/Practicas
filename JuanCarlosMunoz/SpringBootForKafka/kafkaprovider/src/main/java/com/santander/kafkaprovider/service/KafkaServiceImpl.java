package com.santander.kafkaprovider.service;

import com.santander.kafkaprovider.model.bo.MessageBO;

/**
 * The interface Kafka service.
 */
public interface KafkaServiceImpl {

    /**
     * Send message boolean.
     *
     * @param messageBO
     *         the message bo
     * @return the boolean
     */
    boolean sendMessage(MessageBO messageBO);

}
