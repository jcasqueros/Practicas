package com.example.demo.repository.cb;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Director;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface DirectorRepositoryCriteria {
	
	Page<Director> getAll(Pageable pageable);

	Director getById(long id) throws NotFoundException;

	Director create(Director director) throws AlreadyExistsExeption;

	void deleteById(long id) throws NotFoundException;

}
