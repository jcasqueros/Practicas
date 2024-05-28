package com.viewnext.kafka;

import com.viewnext.kafka.services.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@EmbeddedKafka(topics = "customer")
public class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;

//    @Test
//    public void testSend() {
//        // Arrange
//        String message = "prueba";
//
//        // Act
//        kafkaProducer.send(message);
//
//        // Assert
//        verify(kafkaTemplate).send("customer", message);
//    }
}
