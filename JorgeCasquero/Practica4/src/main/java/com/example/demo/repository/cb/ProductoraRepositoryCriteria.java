package com.example.demo.repository.cb;

import java.util.List;

import com.example.demo.model.Productora;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface ProductoraRepositoryCriteria {
	List<Productora> getAll();

	Productora getById(long id) throws NotFoundException;

	Productora create(Productora productora) throws AlreadyExistsExeption;

	void deleteById(long id) throws NotFoundException;

}
