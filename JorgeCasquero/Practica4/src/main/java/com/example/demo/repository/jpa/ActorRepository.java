package com.example.demo.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

	public Page<Actor> findAll(Pageable pageable);

	public List<Actor> findByName(String nombre);
}
