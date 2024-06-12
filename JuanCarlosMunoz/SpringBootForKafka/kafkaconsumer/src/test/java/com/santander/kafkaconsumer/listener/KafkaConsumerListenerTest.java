package com.santander.kafkaconsumer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.santander.kafkaconsumer.listener.KafkaConsumerListener;
import com.santander.kafkaconsumer.model.entity.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.sql.Time;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerListenerTest {

    @Mock
    private Logger log;

    @InjectMocks
    private KafkaConsumerListener kafkaConsumerListener;

    @Test
    void testListener_withValidMessageJson() throws JsonProcessingException {

        Time timeNow = Time.valueOf("12:29:32");
        String messageJson = "{\"user\": \"PRV21\", \"text\": \"Hello, world!\", \"primerUser\": true, \"timestamp\": \"12:29:32\"}";
        Message message = Message.builder()
                .primerUser(true)
                .text("Hello, world!")
                .user("PRV21")
                .timestamp(timeNow)
                .build();

        // Act
       String returnMessage =  kafkaConsumerListener.listener(messageJson);
        String expectedMessage = "[" + message.getTimestamp() + "] User: " + message.getUser() + " send this message: " + message.getText();

       assertEquals(expectedMessage,returnMessage);
    }



    @Test
    void testListener_withInvalidMessageJson() {
        // Arrange
        String messageJson = "{\"invalid\": \"json\"}";

        // Act and Assert
        assertThrows(JsonProcessingException.class, () -> kafkaConsumerListener.listener(messageJson));
    }

    @Test
    void testListener_withNullMessageJson() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> kafkaConsumerListener.listener(null));
    }
}

