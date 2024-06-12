package com.santander.kafkaprovider.service.impl;

import com.santander.kafkaprovider.model.bo.MessageBO;
import com.santander.kafkaprovider.model.entity.Message;
import com.santander.kafkaprovider.utils.mapper.bo.MessageBOMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaServiceTest {

    @Mock
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Mock
    private MessageBOMapper messageBOMapper;

    @InjectMocks
    private KafkaService kafkaService;

    private MessageBO messageBO;
    private Message message;

    @BeforeEach
    void init() {
        messageBO = MessageBO.builder().user("user").text("message").primerUser(true).build();
        message = Message.builder().user("user").text("message").primerUser(true)
                .timestamp(Time.valueOf(LocalDateTime.now().toLocalTime())).build();

    }

    @Test
    void testSendMessage() {

        when(messageBOMapper.boToEntity(messageBO)).thenReturn(message);

        CompletableFuture<SendResult<String, Message>> future = CompletableFuture.completedFuture(new SendResult<>(new ProducerRecord<>("OGR23", message), null));
        when(kafkaTemplate.send("santander", message)).thenReturn(future);

        boolean result = kafkaService.sendMessage(messageBO);

        verify(kafkaTemplate).send("santander", message);

        Assertions.assertTrue(result);
    }

    @Test
    void testSendMessageWithError() {

        when(messageBOMapper.boToEntity(messageBO)).thenReturn(message);

        when(kafkaTemplate.send("santander", message)).thenThrow(
                new RuntimeException("Error sending message to Kafka"));

        boolean result = kafkaService.sendMessage(messageBO);

        verify(kafkaTemplate).send("santander", message);

        assertFalse(result);
    }
}

