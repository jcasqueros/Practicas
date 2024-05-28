package com.santander.peliculacrud.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santander.peliculacrud.model.dto.UserDTO;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * The type Endpoint service test.
 */
@ExtendWith(MockitoExtension.class)
class EndpointServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    private EndpointService endpointService;

    /**
     * The constant mockBackEnd.
     */
    public static MockWebServer mockBackEnd;

    /**
     * Sets up.
     *
     * @throws IOException
     *         the io exception
     */
    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    /**
     * Tear down.
     *
     * @throws IOException
     *         the io exception
     */
    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    /**
     * Initialize.
     */
    @BeforeEach
    void initialize() {
        WebClient webClient = WebClient.builder().baseUrl(mockBackEnd.url("/").toString()).build();
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        endpointService = new EndpointService(webClientBuilder);
    }

    /**
     * Test get user by name and age.
     *
     * @throws JsonProcessingException
     *         the json processing exception
     */
    @Test
    @DisplayName("Test get user by name and age with valid data")
    void testGetUserByNameAndAge() throws JsonProcessingException {

        // Configuración de mocks
        String name = "PRV13 Doe";
        int age = 30;
        List<UserDTO> users = Arrays.asList(UserDTO.builder().name(name).age(age).build(),
                UserDTO.builder().name(name).age(age).build());

        ObjectMapper objectMapper = new ObjectMapper();
        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(users))
                .addHeader("Content-Type", "application/json"));

        // Ejecucion del método
        List<UserDTO> result = endpointService.getUserByNameAndAge(name, age);

        // Verificaciones
        assertEquals(2, result.size());
        assertEquals(name, result.get(0).getName());
        assertEquals(age, result.get(0).getAge());
        assertEquals(name, result.get(1).getName());
        assertEquals(age, result.get(1).getAge());
    }

    /**
     * Test get user by name and age empty.
     */
    @Test
    @DisplayName("Test get user by name and age with empty data")
    void testGetUserByNameAndAge_empty() {
        // Configuración de mocks
        String name = "PRV17 Doe";
        int age = 30;

        MockResponse mockResponse = new MockResponse().setBody("[]").setHeader("Content-Type", "application/json");
        mockBackEnd.enqueue(mockResponse);

        // Ejecucion del método
        List<UserDTO> result = endpointService.getUserByNameAndAge(name, age);

        // Verificaciones
        assertEquals(0, result.size());
    }
}
