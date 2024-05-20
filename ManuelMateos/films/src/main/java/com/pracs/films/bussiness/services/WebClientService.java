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
     * @param port
     */
    void existsActorJPA(long id, String port);

    /**
     * Method for find out if a director exist
     *
     * @param id
     * @param port
     */
    void existsDirectorJPA(long id, String port);

    /**
     * Method for find out if a producer exist
     *
     * @param id
     * @param port
     */
    void existsProducerJPA(long id, String port);

    /**
     * Method for find out if an actor exist
     *
     * @param id
     * @param port
     */
    void existsActorCriteria(long id, String port);

    /**
     * Method for find out if a director exist
     *
     * @param id
     * @param port
     */
    void existsDirectorCriteria(long id, String port);

    /**
     * Method for find out if a producer exist
     *
     * @param id
     * @param port
     */
    void existsProducerCriteria(long id, String port);
}
