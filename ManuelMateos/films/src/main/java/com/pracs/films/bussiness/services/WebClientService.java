package com.pracs.films.bussiness.services;

import org.springframework.web.reactive.function.client.WebClient;

/**
 * Interface for make WebClient calls
 *
 * @see @{@link WebClient}
 */
public interface WebClientService {

    /**
     * Method for find out if an actor exist
     *
     * @param id
     */
    void existsActorJPA(long id);

    /**
     * Method for find out if a director exist
     *
     * @param id
     */
    void existsDirectorJPA(long id);

    /**
     * Method for find out if a producer exist
     *
     * @param id
     */
    void existsProducerJPA(long id);

    /**
     * Method for find out if an actor exist
     *
     * @param id
     */
    void existsActorCriteria(long id);

    /**
     * Method for find out if a director exist
     *
     * @param id
     */
    void existsDirectorCriteria(long id);

    /**
     * Method for find out if a producer exist
     *
     * @param id
     */
    void existsProducerCriteria(long id);
}
