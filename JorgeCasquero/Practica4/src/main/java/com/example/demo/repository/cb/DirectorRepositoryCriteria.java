package com.example.demo.repository.cb;

import java.util.List;

import com.example.demo.model.Director;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface DirectorRepositoryCriteria {
	
	List<Director> getAll();

	Director getById(long id) throws NotFoundException;

	Director create(Director director) throws AlreadyExistsExeption;

	void deleteById(long id) throws NotFoundException;

}
