package com.example.demo.servcice;

import java.util.List;

import com.example.demo.servcice.bo.SerieBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface SerieService {
	List<SerieBo> getAll();

	SerieBo getById(long id) throws NotFoundException;

	SerieBo create(SerieBo serieBo) throws AlreadyExistsExeption;

	void deleteById(long id) throws NotFoundException;
	

}
