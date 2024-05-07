package com.example.demo.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Actor;

public interface SerieRepository extends JpaRepository<Actor, Long>{

}
