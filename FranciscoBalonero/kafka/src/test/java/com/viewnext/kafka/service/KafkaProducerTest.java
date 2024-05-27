package com.viewnext.kafka.service;

import com.viewnext.kafka.model.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@EmbeddedKafka(topics = "my_topic")
class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @Test
    void testSend() {

        // Arrange
        Message message= new Message("Jhon","hello",true, LocalDateTime.now());

        // Act
        kafkaProducer.sendMessage(message);

        // Assert
        verify(kafkaTemplate).send("my_topic", message.toString());
    }
}
