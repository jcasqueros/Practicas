package com.viewnext.films.persistencelayer.repository.jpa;

import com.viewnext.films.persistencelayer.entity.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface for performing CRUD operations on {@link com.viewnext.films.persistencelayer.entity.Actor}.
 *
 * <p>This interface extends the {@link JpaRepository} interface, providing methods for performing CRUD (Create, Read,
 * Update, Delete) operations on {@link Actor} entities.</p>
 *
 * @author Francisco Balonero Olivera
 * @see JpaRepository
 * @see Actor
 **/

@Repository
public interface ActorJPARepository extends JpaRepository<Actor, Long> {

    /**
     * Retrieves a page of actors matching the given pagination and sorting criteria.
     *
     * @param pageable
     *         the pagination and sorting information
     * @return a page of actors matching the pagination and sorting criteria
     */
    Page<Actor> findAll(Pageable pageable);

    /**
     * Retrieves a list of actors by name.
     *
     * @param name
     *         the name of the actors to be retrieved
     * @return a list of actors that match the specified name, or an empty list if no actors are found
     */
    List<Actor> findByName(String name);

    /**
     * Retrieves a list of actors by name and age.
     *
     * @param name
     *         the name of the actors to be retrieved
     * @param age
     *         the age of the actors to be retrieved
     * @return a list of actors that match the specified name and age, or an empty list if no actors are found
     */
    List<Actor> findByNameAndAge(String name, int age);

}
