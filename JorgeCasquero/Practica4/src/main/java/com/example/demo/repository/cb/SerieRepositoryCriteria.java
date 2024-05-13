package com.example.demo.repository.cb;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Serie;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface SerieRepositoryCriteria {
	Page<Serie> getAll(Pageable pageable);

	Serie getById(long id) throws NotFoundException;

	Serie create(Serie serie) throws AlreadyExistsExeption;

	void deleteById(long id) throws NotFoundException;

}
