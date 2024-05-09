package com.viewnext.Practica4.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viewnext.Practica4.backend.business.model.Director;

public interface DirectorRepository extends JpaRepository<Director, Long>{
	Optional<Director> findById(Long id);
}
