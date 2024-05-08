package com.example.demo.servcice;

import java.util.List;

import com.example.demo.servcice.bo.ActorBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface ActorService {

	List<ActorBo> getAll();

	List<ActorBo> getAllCriteria();

	ActorBo getById(long id) throws NotFoundException;

	ActorBo getByIdCriteria(long id) throws NotFoundException;

	ActorBo create(ActorBo actorBo) throws AlreadyExistsExeption;

	ActorBo createCriteria(ActorBo actorBo) throws AlreadyExistsExeption, NotFoundException;

	void deleteById(long id) throws NotFoundException;

	void deleteByIdCriteria(long id) throws NotFoundException;

}
