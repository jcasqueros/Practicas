package com.viewnext.jorge;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.function.BooleanSupplier;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ConsumidorTest {

    private KafkaConsumer<String, String> mockConsumer;
    private Consumidor consumidor;

    @BeforeEach
    public void setUp() {
        mockConsumer = Mockito.mock(KafkaConsumer.class);
        consumidor = new Consumidor();
    }

    @Test
    public void testConsume() {
        // Arrange
        ConsumerRecord<String, String> record = new ConsumerRecord<>("Prueba", 0, 0L, "key", "value");
        TopicPartition topicPartition = new TopicPartition("Prueba", 0);
        ConsumerRecords<String, String> records = new ConsumerRecords<>(Map.of(topicPartition, Collections.singletonList(record)));

        when(mockConsumer.poll(Duration.ofSeconds(10))).thenReturn(records);

        // Act
		consumidor.consume(mockConsumer, new BooleanSupplier() {
            private int count = 0;

            @Override
            public boolean getAsBoolean() {
                return count++ < 1; // Ejecuta solo una iteración del bucle
            }
        });

        // Assert
        verify(mockConsumer).poll(Duration.ofSeconds(10));
        verify(mockConsumer).subscribe(Collections.singletonList("Prueba"));
    }
}
