package com.viewnex.bsan.practica04.repository;

import com.viewnex.bsan.practica04.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

}
