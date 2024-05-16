package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Actor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interface for performing CRUD operations on {@link Actor} using
 * {@link jakarta.persistence.criteria.CriteriaBuilder}.
 *
 * <p>This interface provides methods for creating, reading, updating and deleting actors using criteria.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // Create an actor
 * Actor createdActor = actorCriteriaRepository.createActor(new Actor("John Doe", 30));
 *
 * // Get all actors
 * List<Actor> allActors = actorCriteriaRepository.getAllActors();
 *
 * // Get an actor by ID
 * Optional<Actor> actorById = actorCriteriaRepository.getActorById(1L);
 *
 * // Update an actor
 * Actor updatedActor = actorCriteriaRepository.updateActor(new Actor("Jane Doe", 31));
 *
 * // Delete an actor
 * actorCriteriaRepository.deleteActor(1L);
 * }
 * </pre>
 *
 * @author Francisco Balonero Olivera
 */
public interface ActorCriteriaRepository {

    /**
     * Creates a new {@link Actor}.
     *
     * @param actor
     *         the {@link Actor} to be created
     * @return the created {@link Actor}
     */
    Actor createActor(Actor actor);

    /**
     * Retrieves all {@link Actor} paginated and sorted.
     *
     * @param pageable
     *         {@link Pageable} the pagination and sorting information
     * @return a {@link List} of all {@link Actor} matching the pagination and sorting criteria
     */
    List<Actor> getAllActors(Pageable pageable);

    /**
     * Retrieves an {@link Actor} by ID.
     *
     * @param id
     *         {@link Long} the ID of the {@link Actor} to be retrieved
     * @return an {@link Optional} containing the {@link Actor} if found, or an empty optional if not found
     */
    Optional<Actor> getActorById(Long id);

    /**
     * Updates an existing {@link Actor}.
     *
     * @param actor
     *         the {@link Actor} to be updated
     * @return the updated {@link Actor}
     */
    Actor updateActor(Actor actor);

    /**
     * Deletes an {@link Actor} by ID.
     *
     * @param id
     *         {@link Long} the ID of the {@link Actor} to be deleted
     */
    void deleteActor(Long id);

    /**
     * Filter actors by fields
     *
     * @param names
     *         list of names
     * @param ages
     *         list of ages
     * @param nationalities
     *         list of nationalities
     * @param pageable
     *         {@link Pageable} the pagination and sorting information
     * @return a {@link List} of all {@link Actor} filters.
     */
    List<Actor> filterActors(List<String> names, List<Integer> ages, List<String> nationalities, Pageable pageable);

    /**
     * Retrieves a list of actors by name and age.
     *
     * @param name
     *         the name of the actors to be retrieved
     * @param age
     *         the age of the actors to be retrieved
     * @return a list of actors that match the specified name and age, or an empty list if no actors are found
     */
    List<Actor> getActorsByNameAndAge(String name, int age);

}
