package com.example.demo.servcice;

import java.util.List;

import com.example.demo.servcice.bo.DirectorBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface DirectorService {
	List<DirectorBo> getAll();

	DirectorBo getById(long id) throws NotFoundException;

	DirectorBo create(DirectorBo actorBo) throws AlreadyExistsExeption;

	void deleteById(long id) throws NotFoundException;

}
