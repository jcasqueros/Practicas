package com.example.demo.servcice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.servcice.bo.DirectorBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

/**
 * @author Jorge Casquero Sancho
 */
public interface DirectorService {

	/**Metodo para encontrar todos los directores con paginacion
	 * @param pageable
	 * @return
	 */
	Page<DirectorBo> getAll(Pageable pageable);

	/**Metodo para encontrar todos los directores con Criteria y paginacion
	 * @param pageable
	 * @return
	 */
	Page<DirectorBo> getAllCriteria(Pageable pageable);

	/**
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	DirectorBo getById(long id) throws NotFoundException;

	/**
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	DirectorBo getByIdCriteria(long id) throws NotFoundException;

	/**
	 * @param actorBo
	 * @return
	 * @throws AlreadyExistsExeption
	 */
	/**
	 * @param actorBo
	 * @return
	 * @throws AlreadyExistsExeption
	 */
	DirectorBo create(DirectorBo actorBo) throws AlreadyExistsExeption;

	/**
	 * @param directorBo
	 * @return
	 * @throws AlreadyExistsExeption
	 * @throws NotFoundException
	 */
	DirectorBo createCriteria(DirectorBo directorBo) throws AlreadyExistsExeption, NotFoundException;

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
