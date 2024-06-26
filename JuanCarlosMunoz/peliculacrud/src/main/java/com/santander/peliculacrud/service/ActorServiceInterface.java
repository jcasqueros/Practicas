package com.santander.peliculacrud.service;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.util.exception.GenericException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The interface Actor service interface.
 */
@Service
public interface ActorServiceInterface {

    /**
     * Create actor.
     *
     * @param actorBO
     *         the actor out
     * @return the actor
     * @throws GenericException
     *         the generic exception
     */
    ActorBO createActor(ActorBO actorBO) throws GenericException;

    /**
     * Gets all actors.
     *
     * @param page
     *         the page
     * @return the all actors
     */
    List<ActorBO> getAllActors(int page);

    /**
     * Gets actor by id.
     *
     * @param id
     *         the id
     * @return the actor by id
     * @throws GenericException
     *         the generic exception
     */
    ActorBO getActorById(long id) throws GenericException;

    /**
     * Update actor boolean.
     *
     * @param id
     *         the id
     * @param actorBO
     *         the actor outº
     * @return the boolean
     * @throws GenericException
     *         the generic exception
     */
    boolean updateActor(long id, ActorBO actorBO) throws GenericException;

    /**
     * Delete actor boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     * @throws GenericException
     *         the generic exception
     */
    boolean deleteActor(long id) throws GenericException;

    /**
     * Gets actor by age.
     *
     * @param age
     *         the age
     * @param page
     *         the page
     * @return the actor by age
     */
    List<ActorBO> getActorByAge(int age, int page);

    /**
     * Gets actor by name.
     *
     * @param name
     *         the name
     * @param page
     *         the page
     * @return the actor by name
     */
    List<ActorBO> getActorByName(String name, int page);

    /**
     * Gets actor by nation.
     *
     * @param nation
     *         the nation
     * @param page
     *         the page
     * @return the actor by nation
     */
    List<ActorBO> getActorByNation(String nation, int page);

    List<ActorBO> getActorByAllFilter(List<String> name, List<Integer> age, List<String> nation,  int page);

}
