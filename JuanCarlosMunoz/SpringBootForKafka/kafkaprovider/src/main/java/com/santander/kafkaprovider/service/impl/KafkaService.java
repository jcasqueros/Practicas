package com.santander.kafkaprovider.service.impl;

import com.santander.kafkaprovider.model.bo.MessageBO;
import com.santander.kafkaprovider.model.entity.Message;
import com.santander.kafkaprovider.service.KafkaServiceImpl;
import com.santander.kafkaprovider.utils.mapper.bo.MessageBOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;

/**
 * The type Kafka service.
 */
@Service
public class KafkaService implements KafkaServiceImpl {

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final MessageBOMapper messageBOMapper;

    private final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    /**
     * Instantiates a new Kafka service.
     *
     * @param kafkaTemplate
     *         the kafka template
     * @param messageBOMapper
     *         the message bo mapper
     */
    @Autowired
    public KafkaService(KafkaTemplate<String, Message> kafkaTemplate, MessageBOMapper messageBOMapper) {
        this.kafkaTemplate = kafkaTemplate;

        this.messageBOMapper = messageBOMapper;
    }

    @Override
    public boolean sendMessage(MessageBO messageBO) {

        Message message = messageBOMapper.boToEntity(messageBO);
        message.setTimestamp(Time.valueOf(LocalDateTime.now().toLocalTime()));

        try {
            kafkaTemplate.send("santander", message);
            return true;
        } catch (Exception e) {
            logger.error("Error sending message to Kafka: {}", e.getMessage());
            return false;
        }
    }
}
