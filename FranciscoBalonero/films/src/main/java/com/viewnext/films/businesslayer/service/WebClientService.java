package com.viewnext.films.businesslayer.service;

import com.viewnext.films.businesslayer.exception.NotFoundException;

/**
 * Interface for a service that checks if an actor, director, or producer exists.
 *
 * This service provides methods to verify the existence of an actor, director, or producer by their ID. If the entity
 * does not exist, a {@link NotFoundException} is thrown.
 *
 * @author Francisco Balonero Olivera
 */
public interface WebClientService {

    /**
     * Checks if an actor with the given ID exists.
     *
     * @param id
     *         the ID of the actor to check
     * @throws NotFoundException
     *         if the actor does not exist
     */
    void existsActor(long id) throws NotFoundException;

    /**
     * Checks if a director with the given ID exists.
     *
     * @param id
     *         the ID of the director to check
     * @throws NotFoundException
     *         if the director does not exist
     */
    void existsDirector(long id) throws NotFoundException;

    /**
     * Checks if a producer with the given ID exists.
     *
     * @param id
     *         the ID of the producer to check
     * @throws NotFoundException
     *         if the producer does not exist
     */
    void existsProducer(long id) throws NotFoundException;
}
