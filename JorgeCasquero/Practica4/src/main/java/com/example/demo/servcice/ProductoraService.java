package com.example.demo.servcice;

import java.util.List;

import com.example.demo.servcice.bo.ProductoraBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

public interface ProductoraService {
	List<ProductoraBo> getAll();

	ProductoraBo getById(long id) throws NotFoundException;

	ProductoraBo create(ProductoraBo productoraBoBo) throws AlreadyExistsExeption;

	void deleteById(long id) throws NotFoundException;

}
