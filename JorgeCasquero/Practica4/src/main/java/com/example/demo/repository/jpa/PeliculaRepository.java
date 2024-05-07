package com.example.demo.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Actor;

public interface PeliculaRepository extends JpaRepository<Actor, Long>{

}
