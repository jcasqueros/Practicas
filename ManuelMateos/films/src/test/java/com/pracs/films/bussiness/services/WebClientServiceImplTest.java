package com.pracs.films.bussiness.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pracs.films.bussiness.services.impl.WebClientServiceImpl;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.persistence.repositories.jpa.ActorRepository;
import com.pracs.films.persistence.repositories.jpa.DirectorRepository;
import com.pracs.films.persistence.repositories.jpa.ProducerRepository;
import com.pracs.films.presentation.dto.ActorDtoOut;
import com.pracs.films.presentation.dto.DirectorDtoOut;
import com.pracs.films.presentation.dto.ProducerDtoOut;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test for {@link WebClientServiceImplTest}
 *
 * @author Manuel Mateos de Torres
 */

@ExtendWith(MockitoExtension.class)
class WebClientServiceImplTest {

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private DirectorRepository directorRepository;

    @Mock
    private ProducerRepository producerRepository;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private WebClientServiceImpl webClientService;

    private ObjectMapper objectMapper;

    private ActorDtoOut actorDtoOut;

    private DirectorDtoOut directorDtoOut;

    private ProducerDtoOut producerDtoOut;

    private static MockWebServer mockBackEnd;

    @BeforeEach
    void setup() throws IOException {

        actorDtoOut = new ActorDtoOut();
        actorDtoOut.setId(1L);
        actorDtoOut.setName("prueba");
        actorDtoOut.setAge(25);
        actorDtoOut.setNationality("Spain");

        directorDtoOut = new DirectorDtoOut();
        directorDtoOut.setId(2L);
        directorDtoOut.setName("prueba");
        directorDtoOut.setAge(25);
        directorDtoOut.setNationality("Spain");

        producerDtoOut = new ProducerDtoOut();
        producerDtoOut.setId(1L);
        producerDtoOut.setName("prueba");
        producerDtoOut.setDebut(2000);

        objectMapper = new ObjectMapper();
    }

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @DisplayName("JUnit test for find out an actor - positive")
    @Test
    void givenActorIdAndPort_whenExistsActor_thenReturnNothing() throws JsonProcessingException, InterruptedException {

        mockBackEnd.enqueue(new MockResponse().addHeader("Content-Type", "application/json")
                .setBody(objectMapper.writeValueAsString(actorDtoOut)));

        webClientService.existsActorJPA(actorDtoOut.getId(), String.valueOf(mockBackEnd.getPort()));

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/actors/findById/1?method=false", recordedRequest.getPath());
    }

    @DisplayName("JUnit test for find out an actor - negative")
    @Test
    void givenActorIdAndPort_whenExistsActor_thenThrowException() throws JsonProcessingException, InterruptedException {

        mockBackEnd.enqueue(new MockResponse().setResponseCode(401));

        assertThrows(EntityNotFoundException.class,
                () -> webClientService.existsActorJPA(actorDtoOut.getId(), String.valueOf(mockBackEnd.getPort())));
    }

    @DisplayName("JUnit test for find out an actor - positive")
    @Test
    void givenActorIdAndPort_whenExistsActorCriteria_thenReturnNothing()
            throws JsonProcessingException, InterruptedException {

        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(actorDtoOut))
                .addHeader("Content-Type", "application/json"));

        webClientService.existsActorCriteria(actorDtoOut.getId(), String.valueOf(mockBackEnd.getPort()));

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/actors/findById/1?method=true", recordedRequest.getPath());
    }

    @DisplayName("JUnit test for find out an actor - negative")
    @Test
    void givenActorIdAndPort_whenExistsActorCriteria_thenThrowException()
            throws JsonProcessingException, InterruptedException {

        mockBackEnd.enqueue(new MockResponse().setResponseCode(401));

        assertThrows(EntityNotFoundException.class,
                () -> webClientService.existsActorCriteria(actorDtoOut.getId(), String.valueOf(mockBackEnd.getPort())));
    }

    @DisplayName("JUnit test for find out a director - positive")
    @Test
    void givenDirectorIdAndPort_whenExistsDirector_thenReturnNothing()
            throws JsonProcessingException, InterruptedException {

        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(directorDtoOut))
                .addHeader("Content-Type", "application/json"));

        webClientService.existsDirectorJPA(directorDtoOut.getId(), String.valueOf(mockBackEnd.getPort()));

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/directors/findById/1?method=false", recordedRequest.getPath());
    }

    @DisplayName("JUnit test for find out a director - negative")
    @Test
    void givenDirectorIdAndPort_whenExistsDirector_thenThrowException()
            throws JsonProcessingException, InterruptedException {

        mockBackEnd.enqueue(new MockResponse().setResponseCode(401));

        assertThrows(EntityNotFoundException.class, () -> webClientService.existsDirectorJPA(directorDtoOut.getId(),
                String.valueOf(mockBackEnd.getPort())));
    }

    @DisplayName("JUnit test for find out a director - positive")
    @Test
    void givenDirectorIdAndPort_whenExistsDirectorCriteria_thenReturnNothing()
            throws JsonProcessingException, InterruptedException {

        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(directorDtoOut))
                .addHeader("Content-Type", "application/json"));

        webClientService.existsDirectorCriteria(directorDtoOut.getId(), String.valueOf(mockBackEnd.getPort()));

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/directors/findById/1?method=true", recordedRequest.getPath());
    }

    @DisplayName("JUnit test for find out a director - negative")
    @Test
    void givenDirectorIdAndPort_whenExistsDirectorCriteria_thenThrowException()
            throws JsonProcessingException, InterruptedException {

        mockBackEnd.enqueue(new MockResponse().setResponseCode(401));

        assertThrows(EntityNotFoundException.class,
                () -> webClientService.existsDirectorCriteria(directorDtoOut.getId(),
                        String.valueOf(mockBackEnd.getPort())));
    }

    @DisplayName("JUnit test for find out a producer - positive")
    @Test
    void givenProducerIdAndPort_whenExistsProducer_thenReturnNothing()
            throws JsonProcessingException, InterruptedException {

        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(producerDtoOut))
                .addHeader("Content-Type", "application/json"));

        webClientService.existsProducerJPA(producerDtoOut.getId(), String.valueOf(mockBackEnd.getPort()));

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/producers/findById/1?method=false", recordedRequest.getPath());
    }

    @DisplayName("JUnit test for find out a producer - negative")
    @Test
    void givenProducerIdAndPort_whenExistsProducer_thenThrowException()
            throws JsonProcessingException, InterruptedException {

        mockBackEnd.enqueue(new MockResponse().setResponseCode(401));

        assertThrows(EntityNotFoundException.class, () -> webClientService.existsProducerJPA(producerDtoOut.getId(),
                String.valueOf(mockBackEnd.getPort())));
    }

    @DisplayName("JUnit test for find out a producer - positive")
    @Test
    void givenProducerIdAndPort_whenExistsProducerCriteria_thenReturnNothing()
            throws JsonProcessingException, InterruptedException {

        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(producerDtoOut))
                .addHeader("Content-Type", "application/json"));

        webClientService.existsProducerCriteria(producerDtoOut.getId(), String.valueOf(mockBackEnd.getPort()));

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/producers/findById/1=method=true", recordedRequest.getPath());
    }

    @DisplayName("JUnit test for find out a producer - negative")
    @Test
    void givenProducerIdAndPort_whenExistsProducerCriteria_thenThrowException()
            throws JsonProcessingException, InterruptedException {

        mockBackEnd.enqueue(new MockResponse().setResponseCode(401));

        assertThrows(EntityNotFoundException.class,
                () -> webClientService.existsProducerCriteria(producerDtoOut.getId(),
                        String.valueOf(mockBackEnd.getPort())));
    }
}
