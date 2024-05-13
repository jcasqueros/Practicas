package com.example.demo.repository.cb;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Actor;
import com.example.demo.model.Pelicula;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface PeliculaRepositoryCriteria {
	Page<Pelicula> getAll(Pageable pageable);

	Pelicula getById(long id) throws NotFoundException;

	Pelicula create(Pelicula pelicula) throws AlreadyExistsExeption;

	void deleteById(long id) throws NotFoundException;

}
