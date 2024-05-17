package com.viewnext.films.businesslayer.service.impl;

import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.service.WebClientService;
import com.viewnext.films.presentationlayer.dto.ActorOutDTO;
import com.viewnext.films.presentationlayer.dto.DirectorOutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

/**
 * Implementation of the {@link WebClientService} interface.
 *
 * @author Francisco Balonero Olivera
 */
@Service
@RequiredArgsConstructor
public class WebClientServiceImpl implements WebClientService {

    /**
     * The WebClient instance used to make HTTP requests.
     */
    private final WebClient webClient;

    public void existsActor(long id) throws NotFoundException {
        try {
            webClient.get().uri("http://localhost:8080/api/v1/Actor/getActor?select=true&id=" + id).retrieve()
                    .bodyToMono(ActorOutDTO.class).map(Objects::nonNull).block();
        } catch (Exception e) {
            throw new NotFoundException("The actors in that production don't exist.");
        }
    }

    public void existsDirector(long id) throws NotFoundException {
        try {
            webClient.get().uri("http://localhost:8080/api/v1/Director/getDirector?select=true&id=" + id).retrieve()
                    .bodyToMono(DirectorOutDTO.class).map(Objects::nonNull).block();
        } catch (Exception e) {
            throw new NotFoundException("The director in that production don't exist.");
        }
    }

    public void existsProducer(long id) throws NotFoundException {
        try {
            webClient.get().uri("http://localhost:8080/api/v1/Producer/getProducer?select=true&id=" + id).retrieve()
                    .bodyToMono(DirectorOutDTO.class).map(Objects::nonNull).block();
        } catch (Exception e) {
            throw new NotFoundException("The producer in that production don't exist.");
        }
    }
}
