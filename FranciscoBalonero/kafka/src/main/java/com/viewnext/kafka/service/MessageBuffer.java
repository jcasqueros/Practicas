package com.viewnext.kafka.service;

import com.viewnext.kafka.model.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;


/**
 * Service class for buffering Kafka messages.
 *
 * <p>This service provides a buffer for storing and retrieving Kafka messages.</p>
 *
 * @author PRV12 Balonero Olivera
 */
@Service
public class MessageBuffer {

    /**
     * The flux of messages, used for storing and retrieving messages.
     */
    private final Flux<Message> messageFlux;

    /**
     * The sink for adding messages to the flux.
     */
    private FluxSink<Message> messageSink;

    /**
     * Initializes the message buffer.
     */
    public MessageBuffer() {
        messageFlux = Flux.create(emitter -> messageSink = emitter);
    }

    /**
     * Retrieves the flux of messages from the buffer.
     *
     * @return The flux of messages
     */
    public Flux<Message> getMessages() {
        return messageFlux;
    }

    /**
     * Adds a message to the buffer.
     *
     * @param message The message to be added
     */
    public void addMessage(Message message) {
        messageSink.next(message);
    }
}