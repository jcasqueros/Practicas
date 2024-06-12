package com.viewnext.kafkamessagebrowser.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.kafkamessagebrowser.consumer.KafkaConsumerServices;
import com.viewnext.kafkamessagebrowser.model.ChatMessage;
import com.viewnext.kafkamessagebrowser.producer.KafkaProducerServices;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WebSocketController {
	
	private static final Logger log = LoggerFactory.getLogger(WebSocketController.class);
	
    private final KafkaProducerServices kafkaProducerServices;
    private final KafkaConsumerServices kafkaConsumerServices;
    public WebSocketController(KafkaProducerServices kafkaProducerServices, KafkaConsumerServices kafkaConsumerServices) {
        this.kafkaProducerServices = kafkaProducerServices;
        this.kafkaConsumerServices = kafkaConsumerServices;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public void handleChatMessage(@Payload ChatMessage message) {
        // Send the message to Kafka
        kafkaProducerServices.sendMessage(message);
        log.info("SENT MESSAGE");
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public void addUser(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor){
        //add user to join room chat
        log.info("User added: {}", message.getSender());
        if (headerAccessor != null && headerAccessor.getSessionAttributes() != null) {
            headerAccessor.getSessionAttributes().put("username", message.getSender());
        } else {
            log.error("headerAccessor or session attributes is null.");
        }
        kafkaProducerServices.sendMessage(message);
    }
    
    @MessageMapping("/chat.removeUser")
    @SendTo("/topic/public")
    public void removeUser(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        //disconnect
        log.info("User disconnected: {}", message.getSender());
        //"/topic/public"
        kafkaProducerServices.sendMessage(message);
        if (headerAccessor != null && headerAccessor.getSessionAttributes() != null) {
            headerAccessor.getSessionAttributes().remove("username");
        } else {
            log.error("headerAccessor or session attributes is null.");
        }
    }
    
    /*@GetMapping("/api/chat")
    public List<ChatMessage> getChatMessages() {
        return kafkaConsumerServices.getChatMessages();
    }*/
    
}