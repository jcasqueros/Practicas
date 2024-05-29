package com.viewnext.jorge;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProductorTest {
	private KafkaProducer<String, String> mockProducer;
	private Productor productor;

	@BeforeEach
	void setUp() throws Exception {
		mockProducer = Mockito.mock(KafkaProducer.class);
		productor = new Productor();
	}

	@Test
	void testSend() {
		// Arrange
		Properties props = new Properties();
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("acks", "all");
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

		String topic = "Prueba";
		int partition = 0;
		String key = "";
		String value = "HolaMundo";

		// Act
		productor.send(mockProducer, topic, partition, key, value);

		// Assert
		verify(mockProducer).send(any(ProducerRecord.class));
	}
}
