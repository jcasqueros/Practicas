package com.viewnex.bsan.practica04.repository.custom;

import com.viewnex.bsan.practica04.entity.Actor;

import java.util.List;
import java.util.Optional;

/**
 * The {@code CustomActorRepository} interface is a custom repository class that uses the Criteria API to provide
 * data access logic for actors.
 *
 * @author Antonio Gil
 */
public interface CustomActorRepository {

    /**
     * Finds all actors registered in the system.
     *
     * @return A list containing all the actors registered in the system
     */
    List<Actor> getAll();

    /**
     * Checks whether an actor with the given ID exists in the system.
     *
     * @param id The ID for the actor
     * @return True if an actor with the given ID exists, false otherwise
     */
    boolean existsById(long id);

    /**
     * Finds the actor with the given ID, if it exists.
     *
     * @param id The ID for the actor
     * @return An {@code Optional} containing the found actor, or an empty {@code Optional} if it wasn't found
     */
    Optional<Actor> getById(long id);

    /**
     * Saves the given actor to the system.
     *
     * @param actor The actor that shall be saved
     * @return The actor as it was saved
     */
    Actor save(Actor actor);

    /**
     * Deletes the actor with the given ID.
     *
     * @param id The ID for the actor that shall be deleted
     * @return True if an actor was deleted, false otherwise
     */
    boolean deleteById(long id);

}
