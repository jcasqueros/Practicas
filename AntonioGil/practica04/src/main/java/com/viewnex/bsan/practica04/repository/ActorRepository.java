package com.viewnex.bsan.practica04.repository;

import com.viewnex.bsan.practica04.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code ActorRepository} class is a JPA repository class that provides data access logic for actors.
 *
 * @author Antonio Gil
 */
@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

}
