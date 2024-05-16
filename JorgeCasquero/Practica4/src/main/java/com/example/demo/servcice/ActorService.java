package com.example.demo.servcice;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.exception.AlreadyExistsExeption;
import com.example.demo.exception.NotFoundException;
import com.example.demo.servcice.bo.ActorBo;

/**
 * 
 */
public interface ActorService {

	/**
	 * @param pageable
	 * @return
	 */
	Page<ActorBo> getAll(Pageable pageable);

	/**
	 * @param pageable
	 * @return
	 */
	Page<ActorBo> getAllCriteria(Pageable pageable);

	/**
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	ActorBo getById(long id) throws NotFoundException;

	/**
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	ActorBo getByIdCriteria(long id) throws NotFoundException;

	/**
	 * @param actorBo
	 * @return
	 * @throws AlreadyExistsExeption
	 */
	ActorBo create(ActorBo actorBo) throws AlreadyExistsExeption;

	/**
	 * @param actorBo
	 * @return
	 * @throws AlreadyExistsExeption
	 * @throws NotFoundException
	 */
	ActorBo createCriteria(ActorBo actorBo) throws AlreadyExistsExeption, NotFoundException;

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

	Page<ActorBo> getAllCriteriaFilter(Pageable pageable, List<String> nombres, List<Integer> edades,
			List<String> nacionalidades);
}
