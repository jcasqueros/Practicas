package com.example.demo.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Pelicula;


@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long>{
	public Page<Pelicula> findAll(Pageable pageable);
}
