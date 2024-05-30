package com.viewnext.jorge;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumidor {

    @KafkaListener(topics = "Prueba", groupId = "JorgeCasquero")
    public void listen(Message message) {
        System.out.println("Received Message: " + message.toString());
    }
}
