package com.example.demo.servcice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.servcice.bo.ProductoraBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

/**
 * @author Jorge Casquero Sancho
 */
public interface ProductoraService {
	/**
	 * @param pageable
	 * @return
	 */
	Page<ProductoraBo> getAll(Pageable pageable);

	/**
	 * @param pageable
	 * @return
	 */
	Page<ProductoraBo> getAllCriteria(Pageable pageable);

	/**
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	ProductoraBo getById(long id) throws NotFoundException;

	/**
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	ProductoraBo getByIdCriteria(long id) throws NotFoundException;

	/**
	 * @param productoraBo
	 * @return
	 * @throws AlreadyExistsExeption
	 */
	ProductoraBo create(ProductoraBo productoraBo) throws AlreadyExistsExeption;

	/**
	 * @param productoraBo
	 * @return
	 * @throws AlreadyExistsExeption
	 * @throws NotFoundException
	 */
	ProductoraBo createCriteria(ProductoraBo productoraBo) throws AlreadyExistsExeption, NotFoundException;

	/**
	 * @param id
	 * @throws NotFoundException
	 */
	void deleteById(long id) throws NotFoundException;

	/**
	 * @param id
	 * @throws NotFoundException
	 */
	void deleteByIdCriteria(long id) throws NotFoundException;

}
