package com.example.demo.servcice;

import java.util.List;

import com.example.demo.servcice.bo.ActorBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface ActorService {
	
	List<ActorBo>getAll();
	ActorBo getById(long id) throws NotFoundException;
	ActorBo create(ActorBo actorBo) throws AlreadyExistsExeption;
	void deleteById(long id) throws NotFoundException;

}
