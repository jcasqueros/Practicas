package com.example.demo.servcice;

import java.util.List;

import com.example.demo.servcice.bo.DirectorBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface DirectorService {
	List<DirectorBo> getAll();
	List<DirectorBo> getAllCriteria();

	DirectorBo getById(long id) throws NotFoundException;
	DirectorBo getByIdCriteria(long id) throws NotFoundException;

	DirectorBo create(DirectorBo actorBo) throws AlreadyExistsExeption;
	DirectorBo createCriteria(DirectorBo directorBo) throws AlreadyExistsExeption, NotFoundException;

	void deleteById(long id) throws NotFoundException;
	
	void deleteByIdCriteria(long id) throws NotFoundException;

}
