package com.viewnext.films.businesslayer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.service.impl.WebClientServiceImpl;
import com.viewnext.films.presentationlayer.dto.ActorOutDTO;
import com.viewnext.films.presentationlayer.dto.DirectorOutDTO;
import com.viewnext.films.presentationlayer.dto.ProducerOutDTO;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WebClientServiceImplTest {

    public static MockWebServer mockBackEnd;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    private WebClientService webClientService;

    @BeforeEach
    void initialize() {
        WebClient webClient = WebClient.builder().build();
        webClientService = new WebClientServiceImpl(webClient);
    }

    @Test
    void existsActor_success() throws Exception {
        ActorOutDTO actorOutDTO = new ActorOutDTO(1L, "Actor", 22, "spain");
        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(actorOutDTO))
                .addHeader("Content-Type", "application/json"));

        webClientService.existsActor(1L);
    }

    @Test
    void existsActor_notFound() {
        mockBackEnd.enqueue(new MockResponse().setResponseCode(404));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> webClientService.existsActor(1L));
        assertEquals("The actors in that production don't exist.", exception.getMessage());
    }

    @Test
    void existsDirector_success() throws Exception {
        DirectorOutDTO directorOutDTO = new DirectorOutDTO(1L, "Director", 22, "spain");
        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(directorOutDTO))
                .addHeader("Content-Type", "application/json"));

        webClientService.existsDirector(1L);
    }

    @Test
    void existsDirector_notFound() {
        mockBackEnd.enqueue(new MockResponse().setResponseCode(404));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> webClientService.existsDirector(1L));
        assertEquals("The director in that production don't exist.", exception.getMessage());
    }

    @Test
    void existsProducer_success() throws Exception {
        ProducerOutDTO producerOutDTO = new ProducerOutDTO(1L, "Producer", 2222);
        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(producerOutDTO))
                .addHeader("Content-Type", "application/json"));

        webClientService.existsProducer(1L);
    }

    @Test
    void existsProducer_notFound() {
        mockBackEnd.enqueue(new MockResponse().setResponseCode(404));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> webClientService.existsProducer(1L));
        assertEquals("The producer in that production don't exist.", exception.getMessage());
    }
}
