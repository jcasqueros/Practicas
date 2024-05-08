package com.example.demo.repository.cb;

import java.util.List;

import com.example.demo.model.Serie;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface SerieRepositoryCriteria {
	List<Serie> getAll();

	Serie getById(long id) throws NotFoundException;

	Serie create(Serie serie) throws AlreadyExistsExeption;

	void deleteById(long id) throws NotFoundException;

}
