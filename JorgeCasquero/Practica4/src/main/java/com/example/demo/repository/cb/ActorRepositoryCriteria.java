package com.example.demo.repository.cb;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.exception.AlreadyExistsExeption;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Actor;

public interface ActorRepositoryCriteria {

	Page<Actor> getAll(Pageable pageable);

	Optional<Actor> getById(long id) throws NotFoundException;

	Actor create(Actor actor) throws AlreadyExistsExeption, NotFoundException;

	void deleteById(long id) throws NotFoundException;

	Page<Actor> findAllFilter(Pageable pageable, List<String> nombres, List<Integer> edades,
			List<String> nacionalidades);
}
