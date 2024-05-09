package com.viewnext.Practica4.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viewnext.Practica4.backend.business.model.Productora;

public interface ProductoraRepository extends JpaRepository<Productora, Long>{
	Optional<Productora> findById(Long id);
}
