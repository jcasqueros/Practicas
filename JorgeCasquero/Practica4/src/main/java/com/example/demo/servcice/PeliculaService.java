package com.example.demo.servcice;

import java.util.List;

import com.example.demo.servcice.bo.PeliculaBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface PeliculaService {
	List<PeliculaBo> getAll();

	PeliculaBo getById(long id) throws NotFoundException;

	PeliculaBo create(PeliculaBo pelicylaBo) throws AlreadyExistsExeption;

	void deleteById(long id) throws NotFoundException;

}
