package com.pracs.films.bussiness.services.impl;

import com.pracs.films.bussiness.services.WebClientService;
import com.pracs.films.configuration.ConstantMessages;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.persistence.models.Actor;
import com.pracs.films.presentation.dto.ActorDtoOut;
import com.pracs.films.presentation.dto.DirectorDtoOut;
import com.pracs.films.presentation.dto.ProducerDtoOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

/**
 * Implementation of {@link WebClientService}
 *
 * @author Manuel Mateos de Torres
 */
@RequiredArgsConstructor
@Service
public class WebClientServiceImpl implements WebClientService {

    private final WebClient webClient;

    @Override
    public void existsActorJPA(long id, String port) {
        try {
            webClient.get().uri("http://localhost:" + port + "/actors/findById/" + id + "?method=false").retrieve()
                    .bodyToMono(ActorDtoOut.class).map(Objects::nonNull).block();
        } catch (Exception e) {
            throw new EntityNotFoundException(ConstantMessages.NOACTORS);
        }
        // retrieve recibe el cuerpo de la respuesta HTPP
        //block bloquea el hilo hasta que se complete la solicitud HTTP
    }

    @Override
    public void existsDirectorJPA(long id, String port) {
        try {
            webClient.get().uri("http://localhost:" + port + "/directors/findById/" + id + "?method=false").retrieve()
                    .bodyToMono(DirectorDtoOut.class).map(Objects::nonNull).block();
        } catch (Exception e) {
            throw new EntityNotFoundException(ConstantMessages.NODIRECTORS);
        }
    }

    @Override
    public void existsProducerJPA(long id, String port) {
        try {
            webClient.get().uri("http://localhost:" + port + "/producers/findById/" + id + "?method=false").retrieve()
                    .bodyToMono(ProducerDtoOut.class).map(Objects::nonNull).block();
        } catch (Exception e) {
            throw new EntityNotFoundException(ConstantMessages.NOPRODUCERS);
        }
    }

    @Override
    public void existsActorCriteria(long id, String port) {
        try {
            webClient.get().uri("http://localhost:" + port + "/actors/findById/" + id + "?method=true").retrieve()
                    .bodyToMono(Actor.class).map(Objects::nonNull).block();
        } catch (Exception e) {
            throw new EntityNotFoundException(ConstantMessages.NOACTORS);
        }
    }

    @Override
    public void existsDirectorCriteria(long id, String port) {
        try {
            webClient.get().uri("http://localhost:" + port + "/directors/findById/" + id + "?method=true").retrieve()
                    .bodyToMono(DirectorDtoOut.class).map(Objects::nonNull).block();
        } catch (Exception e) {
            throw new EntityNotFoundException(ConstantMessages.NODIRECTORS);
        }
    }

    @Override
    public void existsProducerCriteria(long id, String port) {
        try {
            webClient.get().uri("http://localhost:" + port + "/producers/findById/" + id + "?method=true").retrieve()
                    .bodyToMono(ProducerDtoOut.class).map(Objects::nonNull).block();
        } catch (Exception e) {
            throw new EntityNotFoundException(ConstantMessages.NOPRODUCERS);
        }
    }
}
