package com.example.demo.repository.cb;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Actor;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface ActorRepositoryCriteria {

	List<Actor> getAll();

	Optional<Actor> getById(long id) throws NotFoundException;

	Actor create(Actor actor) throws AlreadyExistsExeption, NotFoundException;

	void deleteById(long id) throws NotFoundException;

}
