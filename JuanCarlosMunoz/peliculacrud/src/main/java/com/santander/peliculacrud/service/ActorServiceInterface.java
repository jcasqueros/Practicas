package com.santander.peliculacrud.service;

import com.santander.peliculacrud.model.input.Actor;
import com.santander.peliculacrud.model.output.ActorModelController;
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
     * @param actorModelController
     *         the actor out
     * @return the actor
     */
    Actor createActor(ActorModelController actorModelController);

    /**
     * Gets all actors.
     *
     * @return the all actors
     */
    List<ActorModelController> getAllActors();

    /**
     * Gets actor by id.
     *
     * @param id
     *         the id
     * @return the actor by id
     */
    ActorModelController getActorById(Long id);

    /**
     * Update actor boolean.
     *
     * @param id
     *         the id
     * @param actorModelController
     *         the actor out
     * @return the boolean
     */
    boolean updateActor(Long id, ActorModelController actorModelController);

    /**
     * Delete actor boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     */
    boolean deleteActor(Long id);

    /**
     * Gets last user.
     *
     * @return the last user
     */
    Actor getLastActor();

    /**
     * Gets list size.
     *
     * @return the list size
     */
    int getListSize();
}
