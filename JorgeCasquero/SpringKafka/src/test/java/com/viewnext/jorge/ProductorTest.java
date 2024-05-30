package com.viewnext.jorge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

public class ProductorTest {

    private KafkaTemplate<String, Message> kafkaTemplate;
    private Productor productor;

    @BeforeEach
    public void setUp() {
        kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        productor = new Productor();
        productor.setKafkaTemplate(kafkaTemplate); // Inyección manual para pruebas
    }

    @Test
    public void testSend() {
        // Arrange
        String topic = "Prueba";
        int partition = 0;
        String key = "key1";
        Message message = new Message("user1", "HolaMundo", true, LocalDateTime.now());

        // Act
        productor.send(topic, partition, key, message);

        // Assert
        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> partitionCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);

        verify(kafkaTemplate).send(topicCaptor.capture(), partitionCaptor.capture(), keyCaptor.capture(), messageCaptor.capture());

        assertEquals(topic, topicCaptor.getValue());
        assertEquals(partition, partitionCaptor.getValue());
        assertEquals(key, keyCaptor.getValue());
        assertEquals(message, messageCaptor.getValue());
    }
}
