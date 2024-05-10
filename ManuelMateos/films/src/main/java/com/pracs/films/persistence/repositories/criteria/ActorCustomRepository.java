package com.pracs.films.persistence.repositories.criteria;

import com.pracs.films.persistence.models.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Criteria Repository of the class {@link Actor}
 *
 * @author Manuel Mateos de Torres
 */
public interface ActorCustomRepository {

    /**
     * Method for create an actor
     *
     * @param actor
     * @return Optional Actor
     */
    Actor saveActor(Actor actor);

    /**
     * Method for update an actor
     *
     * @param actor
     * @return Optional Actor
     */
    Actor updateActor(Actor actor);

    /**
     * Method for find an actor by his id
     *
     * @param id
     * @return Optional Actor
     */
    Optional<Actor> findActorById(long id);

    /**
     * Method for get all actors
     *
     * @return List of Actor
     */
    Page<Actor> findAllActors(Pageable pageable);

    /**
     * Method for delete an actor by his id
     *
     * @param actor
     */
    void deleteActorById(Actor actor);
}
