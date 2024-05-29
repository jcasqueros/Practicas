package com.viewnext.jorge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Productor {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, int partition, String key, String value) {
        kafkaTemplate.send(topic, partition, key, value);
    }
}
