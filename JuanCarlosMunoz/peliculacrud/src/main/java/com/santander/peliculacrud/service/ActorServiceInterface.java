package com.santander.peliculacrud.service;

import com.santander.peliculacrud.model.bo.ActorBO;
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
     */
    ActorBO createActor(ActorBO actorBO);

    /**
     * Gets all actors.
     *
     * @return the all actors
     */
    List<ActorBO> getAllActors();

    /**
     * Gets actor by id.
     *
     * @param id
     *         the id
     * @return the actor by id
     */
    ActorBO getActorById(long id);

    /**
     * Update actor boolean.
     *
     * @param id
     *         the id
     * @param actorBO
     *         the actor outº
     * @return the boolean
     */
    boolean updateActor(long id, ActorBO actorBO);

    /**
     * Delete actor boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     */
    boolean deleteActor(long id);

}
