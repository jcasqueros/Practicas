package com.example.demo.servcice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.servcice.bo.SerieBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface SerieService {
	Page<SerieBo> getAll(Pageable pageable);

	Page<SerieBo> getAllCriteria(Pageable pageable);

	SerieBo getById(long id) throws NotFoundException;

	SerieBo getByIdCriteria(long id) throws NotFoundException;

	SerieBo create(SerieBo serieBo) throws AlreadyExistsExeption;

	SerieBo createCriteria(SerieBo serieBo) throws AlreadyExistsExeption, NotFoundException;

	void deleteById(long id) throws NotFoundException;

	void deleteByIdCriteria(long id) throws NotFoundException;

}
