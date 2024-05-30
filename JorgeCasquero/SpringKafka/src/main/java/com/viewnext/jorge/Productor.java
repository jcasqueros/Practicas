package com.viewnext.jorge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Productor {

	@Autowired
	private KafkaTemplate<String, Message> kafkaTemplate;

	public void setKafkaTemplate(KafkaTemplate<String, Message> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void send(String topic, int partition, String key, Message message) {
		kafkaTemplate.send(topic, partition, key, message);
	}
}
