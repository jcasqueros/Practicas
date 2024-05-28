//package com.santander.peliculacrud.service.impl;
//
//
//import com.santander.peliculacrud.model.dto.UserDTO;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(MockitoExtension.class)
//class EndpointServiceTest {
//
//    private MockWebServer mockWebServer;
//    private WebClient webClient;
//    private EndpointService endpointService;
//
//    @BeforeEach
//    public void setup() {
//        mockWebServer = new MockWebServer();
//        webClient = WebClient.builder()
//                .baseUrl(mockWebServer.url("/").toString())
//                .build();
//        endpointService = new EndpointService(webClient);
//    }
//
//    @AfterEach
//    public void tearDown() throws Exception {
//        mockWebServer.shutdown();
//    }
//
//    @Test
//    void testGetUserByNameAndAge() {
//        // Configuración de mocks
//        String name = "PRV13 Doe";
//        int age = 30;
//        List<UserDTO> users = Arrays.asList(
//                UserDTO.builder().name(name).age(age).build(),
//                UserDTO.builder().name(name).age(age).build()
//        );
//
//        // Configuración del servidor mockeado
//        MockResponse response = new MockResponse()
//                .setBody("{\"users\": " + users + "}")
//                .setHeader("Content-Type", "application/json");
//        mockWebServer.enqueue(response);
//
//        // Ejecución del método
//        List<UserDTO> result = endpointService.getUserByNameAndAge(name, age);
//
//        // Verificaciones
//        assertEquals(2, result.size());
//        assertEquals(name, result.get(0).getName());
//        assertEquals(age, result.get(0).getAge());
//        assertEquals(name, result.get(1).getName());
//        assertEquals(age, result.get(1).getAge());
//    }
//
//    @Test
//    void testGetUserByNameAndAge_empty() {
//        // Configuración de mocks
//        String name = "PRV17 Doe";
//        int age = 30;
//
//        // Configuración del servidor mockeado
//        MockResponse response = new MockResponse()
//                .setBody("[]")
//                .setHeader("Content-Type", "application/json");
//        mockWebServer.enqueue(response);
//
//        // Ejecución del método
//        List<UserDTO> result = endpointService.getUserByNameAndAge(name, age);
//
//        // Verificaciones
//        assertEquals(0, result.size());
//    }
//}
