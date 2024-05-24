package com.viewnext.kafka;

import com.viewnext.kafka.services.KafkaConsumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.TopicPartitionOffset;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@EmbeddedKafka(topics = "customer")
public class KafkaConsumerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @Test
    public void testGetMessage() {
        // Arrange
        String message = "prueba";
        TopicPartitionOffset topicPartitionOffset = new TopicPartitionOffset("customer", 0, 0L);
        Collection<TopicPartitionOffset> topicPartitionOffsets = Collections.singletonList(topicPartitionOffset);
        Duration pollTimeout = Duration.ofMillis(100);

        // Act
        kafkaConsumer.getMessage(message);

        // Assert
        verify(kafkaTemplate).receive(topicPartitionOffsets, pollTimeout);
    }
}
