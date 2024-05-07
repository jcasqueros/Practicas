package com.viewnext.films.persistencelayer.repository.jpa;

import com.viewnext.films.persistencelayer.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

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
//@Repository
public interface ActorJPARepository extends JpaRepository<Actor, Long> {
}
