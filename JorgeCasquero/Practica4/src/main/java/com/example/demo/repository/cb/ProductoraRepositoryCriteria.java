package com.example.demo.repository.cb;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Productora;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface ProductoraRepositoryCriteria {
	Page<Productora> getAll(Pageable pageable);

	Productora getById(long id) throws NotFoundException;

	Productora create(Productora productora) throws AlreadyExistsExeption;

	void deleteById(long id) throws NotFoundException;

}
