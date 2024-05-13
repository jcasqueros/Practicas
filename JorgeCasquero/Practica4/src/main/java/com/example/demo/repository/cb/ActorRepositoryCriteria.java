package com.example.demo.repository.cb;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Actor;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface ActorRepositoryCriteria {

	Page<Actor> getAll(Pageable pageable);

	Optional<Actor> getById(long id) throws NotFoundException;

	Actor create(Actor actor) throws AlreadyExistsExeption, NotFoundException;

	void deleteById(long id) throws NotFoundException;

}
