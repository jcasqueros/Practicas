package com.viewnext.Practica4.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viewnext.Practica4.backend.business.model.Pelicula;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long>{

	Optional<Pelicula> findById(Long id);
}
