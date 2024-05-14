package com.example.demo.repository.cb;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Actor;
import com.example.demo.model.Director;
import com.example.demo.model.Pelicula;
import com.example.demo.model.Productora;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface PeliculaRepositoryCriteria {
	Page<Pelicula> getAll(Pageable pageable);

	Optional<Pelicula> getById(long id) throws NotFoundException;

	Pelicula create(Pelicula pelicula) throws AlreadyExistsExeption;

	void deleteById(long id) throws NotFoundException;

	Page<Pelicula> findAllFilter(Pageable pageable, List<String> titulos, List<Integer> anioages, List<Director> directores,
            List<Productora> productora, List<Actor> actores);
}
