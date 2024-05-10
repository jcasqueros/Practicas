package com.pracs.films.bussiness.services;

import com.pracs.films.bussiness.bo.ActorBO;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Method for get all actors paginated
     *
     * @param pageable
     * @return List of ActorBO
     */
    Page<ActorBO> findAll(Pageable pageable) throws ServiceException;

    /**
     * Method for delete an actor by his id
     *
     * @param id
     */
    void deleteById(long id) throws ServiceException;

    /**
     * Method for create an actor
     *
     * @param actorBO
     * @return ActorBO
     */
    ActorBO saveCriteria(ActorBO actorBO) throws ServiceException;

    /**
     * Method for update an actor
     *
     * @param actorBO
     * @return ActorBO
     */
    ActorBO updateCriteria(ActorBO actorBO) throws ServiceException;

    /**
     * Method for find an actor by his id
     *
     * @param id
     * @return ActorBO
     */
    ActorBO findByIdCriteria(long id) throws ServiceException;

    /**
     * Method for get all actors
     *
     * @return List of ActorBO
     */
    List<ActorBO> findAllCriteria() throws ServiceException;

    /**
     * Method for delete an actor by his id
     *
     * @param id
     */
    void deleteByIdCriteria(long id) throws ServiceException;
}
