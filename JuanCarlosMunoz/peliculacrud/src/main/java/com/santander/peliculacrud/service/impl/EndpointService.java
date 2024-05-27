package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.dto.UserDTO;

import com.santander.peliculacrud.service.EndpointServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * The type Endpoint service.
 */
@Service
public class EndpointService implements EndpointServiceInterface {

    private final WebClient webClient;

    /**
     * Instantiates a new Endpoint service.
     *
     * @param webClientBuilder
     *         the web client builder
     */
    public EndpointService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081/").build();
    }

    /**
     * Gets user byname.
     *
     * @param name
     *         the name
     * @return the user byname
     */
    public List<UserDTO> getUserByNameAndAge(String name, int age) {
        return this.webClient.get().uri("users/by-name-age/?name={name}&age={age}", name, age)
                .retrieve()
                .bodyToFlux(UserDTO.class)
                .collectList()
                .block();
    }


}

