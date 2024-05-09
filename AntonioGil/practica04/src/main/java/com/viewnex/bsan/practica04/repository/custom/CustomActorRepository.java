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

    List<Actor> getAll();

    boolean existsById(long id);

    Optional<Actor> getById(long id);

    Actor save(Actor actor);

    boolean deleteById(long id);

}
