package com.example.demo.servcice;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.exception.AlreadyExistsExeption;
import com.example.demo.exception.NotFoundException;
import com.example.demo.servcice.bo.ProductoraBo;

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
	
	/**
	 * @param pageable
	 * @param names
	 * @param ages
	 * @return
	 * @throws ServiceException
	 */
	Page<ProductoraBo> findAllCriteriaFilter(Pageable pageable, List<String> names, List<Integer> ages)
            throws ServiceException;
}
