package com.example.demo.repository.cb;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Actor;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;


public interface ActorRepositoryCriteria {

	List<Actor> getAll();

	Actor getById(long id) throws NotFoundException;

	Actor create(Actor actor) throws AlreadyExistsExeption;

	void deleteById(long id) throws NotFoundException;

}
