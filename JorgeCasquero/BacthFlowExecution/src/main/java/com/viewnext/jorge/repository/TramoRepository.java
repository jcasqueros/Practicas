package com.viewnext.jorge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viewnext.jorge.model.Calle;

public interface TramoRepository extends JpaRepository<Calle, String> {
	

}
