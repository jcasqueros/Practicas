package com.pracs.films.bussiness.service;

import com.pracs.films.bussiness.bo.ActorBO;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Actor;

import java.util.List;

/**
 * Service of the model {@link Actor}
 *
 * @author Manuel Mateos de Torres
 */
public interface ActorService {

    /**
     * Method for create an actor
     *
     * @param actorBO
     * @return ActorBO
     */
    ActorBO save(ActorBO actorBO) throws ServiceException;

    /**
     * Method for update an actor
     *
     * @param actorBO
     * @return ActorBO
     */
    ActorBO update(ActorBO actorBO) throws ServiceException;

    /**
     * Method for find an actor by his id
     *
     * @param id
     * @return ActorBO
     */
    ActorBO findById(long id) throws ServiceException;

    /**
     * Method for get all actors
     *
     * @return List of ActorBO
     */
    List<ActorBO> findAll() throws ServiceException;

    /**
     * Method for delete an actor by his id
     *
     * @param id
     */
    void deleteById(long id) throws ServiceException;
}
