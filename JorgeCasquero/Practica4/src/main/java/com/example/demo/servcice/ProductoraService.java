package com.example.demo.servcice;

import java.util.List;

import com.example.demo.servcice.bo.ProductoraBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface ProductoraService {
	List<ProductoraBo> getAll();

	List<ProductoraBo> getAllCriteria();

	ProductoraBo getById(long id) throws NotFoundException;

	ProductoraBo getByIdCriteria(long id) throws NotFoundException;

	ProductoraBo create(ProductoraBo productoraBo) throws AlreadyExistsExeption;

	ProductoraBo createCriteria(ProductoraBo productoraBo) throws AlreadyExistsExeption, NotFoundException;

	void deleteById(long id) throws NotFoundException;

	void deleteByIdCriteria(long id) throws NotFoundException;

}
