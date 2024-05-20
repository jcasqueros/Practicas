package com.viewnext.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viewnext.springbatch.model.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
	
}
