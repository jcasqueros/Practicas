package com.viewnext.Practica4.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viewnext.Practica4.backend.business.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long>{
	Optional<Serie> findById(Long id);

}
