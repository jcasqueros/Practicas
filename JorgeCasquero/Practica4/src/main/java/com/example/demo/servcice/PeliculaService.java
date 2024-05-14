package com.example.demo.servcice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.servcice.bo.PeliculaBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

/**
 * @author Jorge Casquero Sancho
 */
public interface PeliculaService {
	/**
	 * @param pageable
	 * @return
	 */
	Page<PeliculaBo> getAll(Pageable pageable);

	/**
	 * @param pageable
	 * @return
	 */
	Page<PeliculaBo> getAllCriteria(Pageable pageable);

	/**
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	PeliculaBo getById(long id) throws NotFoundException;

	/**
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	PeliculaBo getByIdCriteria(long id) throws NotFoundException;

	/**
	 * @param pelicylaBo
	 * @return
	 * @throws AlreadyExistsExeption
	 */
	PeliculaBo create(PeliculaBo pelicylaBo) throws AlreadyExistsExeption;

	/**
	 * @param pelicylaBo
	 * @return
	 * @throws AlreadyExistsExeption
	 * @throws NotFoundException
	 */
	PeliculaBo createCriteria(PeliculaBo pelicylaBo) throws AlreadyExistsExeption, NotFoundException;

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
