package com.example.demo.repository.cb;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Productora;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

/**
 * @author Jorge Casquero Sancho
 */
public interface ProductoraRepositoryCriteria {

	/**
	 * @param pageable
	 * @return
	 */
	Page<Productora> getAll(Pageable pageable);

	/**
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	Optional<Productora> getById(long id) throws NotFoundException;

	/**
	 * @param productora
	 * @return
	 * @throws AlreadyExistsExeption
	 */
	Productora create(Productora productora) throws AlreadyExistsExeption;

	/**
	 * @param id
	 * @throws NotFoundException
	 */
	void deleteById(long id) throws NotFoundException;

	/**
	 * @param pageable
	 * @param nombres
	 * @param anios
	 * @return
	 */
	Page<Productora> findAndFilter(Pageable pageable, List<String> nombres, List<Integer> anios);
}
