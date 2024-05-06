package com.pracs.films.persistence.repositories;

import com.pracs.films.persistence.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}
