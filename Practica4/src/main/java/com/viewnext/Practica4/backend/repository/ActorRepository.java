package com.viewnext.Practica4.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viewnext.Practica4.backend.business.model.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long>{
	Optional<Actor> findById(Long id);
}
