package com.example.demo.repository.cb;

import java.util.List;

import com.example.demo.model.Pelicula;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface PeliculaRepositoryCriteria {
	List<Pelicula> getAll();

	Pelicula getById(long id) throws NotFoundException;

	Pelicula create(Pelicula pelicula) throws AlreadyExistsExeption;

	void deleteById(long id) throws NotFoundException;

}
