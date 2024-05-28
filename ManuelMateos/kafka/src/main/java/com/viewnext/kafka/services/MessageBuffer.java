package com.viewnext.kafka.services;

import com.viewnext.kafka.objects.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

/**
 * A message buffer that stores and provides messages as a reactive flux.
 *
 * This class is responsible for storing messages and providing them as a reactive flux.
 *
 * @author Manuel Mateos de Torres
 */
@Service
public class MessageBuffer {

    /**
     * The reactive flux of messages.
     */
    private final Flux<Message> messageFlux;

    /**
     * The flux sink used to add messages to the flux.
     */
    private FluxSink<Message> fluxSink;

    /**
     * Creates a new message buffer.
     */
    public MessageBuffer() {
        messageFlux = Flux.create(emitter -> fluxSink = emitter);
    }

    /**
     * Returns the reactive flux of messages.
     *
     * @return the flux of messages
     */
    public Flux<Message> getMessages() {
        return messageFlux;
    }

    /**
     * Adds a message to the buffer.
     *
     * @param message
     *         the message to add
     */
    public void addMessage(Message message) {
        fluxSink.next(message);
    }
}
