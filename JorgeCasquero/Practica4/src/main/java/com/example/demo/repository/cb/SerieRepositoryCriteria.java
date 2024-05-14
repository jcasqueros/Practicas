package com.example.demo.repository.cb;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Actor;
import com.example.demo.model.Director;
import com.example.demo.model.Productora;
import com.example.demo.model.Serie;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface SerieRepositoryCriteria {
	Page<Serie> getAll(Pageable pageable);

	Optional<Serie> getById(long id) throws NotFoundException;

	Serie create(Serie serie) throws AlreadyExistsExeption;

	void deleteById(long id) throws NotFoundException;

	Page<Serie> findAllFilter(Pageable pageable, List<String> titulos, List<Integer> anio, List<Director> directores,
            List<Productora> productora, List<Actor> actores);
}
