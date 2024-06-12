package com.santander.kafkaconsumer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santander.kafkaconsumer.model.entity.Message;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * The type Kafka consumer listener.
 */
@AllArgsConstructor
@Service
public class KafkaConsumerListener {

    private final Logger log = LoggerFactory.getLogger(KafkaConsumerListener.class);

    /**
     * Listener.
     *
     * @param messageJson
     *         the message json
     * @throws JsonProcessingException
     *         the json processing exception
     */
    //GroupId para agrupar listener
    @KafkaListener(topics = { "santander" }, groupId = "santander")
    public void listener(String messageJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(messageJson, Message.class);

        log.info("[{}] User: {} send this message: {}", message.getTimestamp(), message.getUser(), message.getText());
    }

}
