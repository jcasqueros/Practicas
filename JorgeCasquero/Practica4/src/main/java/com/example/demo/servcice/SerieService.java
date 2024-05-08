package com.example.demo.servcice;

import java.util.List;

import com.example.demo.servcice.bo.SerieBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface SerieService {
	List<SerieBo> getAll();

	List<SerieBo> getAllCriteria();

	SerieBo getById(long id) throws NotFoundException;

	SerieBo getByIdCriteria(long id) throws NotFoundException;

	SerieBo create(SerieBo serieBo) throws AlreadyExistsExeption;

	SerieBo createCriteria(SerieBo serieBo) throws AlreadyExistsExeption, NotFoundException;

	void deleteById(long id) throws NotFoundException;

	void deleteByIdCriteria(long id) throws NotFoundException;

}
