package com.viewnext.bsan.practica04.repository.custom;

import com.viewnext.bsan.practica04.entity.Actor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * The {@code CustomActorRepository} interface is a custom repository class that uses the Criteria API to provide
 * data access logic for actors.
 *
 * @author Antonio Gil
 */
public interface CustomActorRepository {

    List<Actor> findAll(Pageable pageable);

    List<Actor> findAll(Specification<Actor> spec, Pageable pageable);

    boolean existsById(long id);

    Optional<Actor> findById(long id);

    Actor save(Actor actor);

    boolean deleteById(long id);

}
