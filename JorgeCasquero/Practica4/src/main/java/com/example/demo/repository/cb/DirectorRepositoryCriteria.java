package com.example.demo.repository.cb;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Director;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

/**
 * @author Jorge Casquero Sancho
 */
public interface DirectorRepositoryCriteria {

	/**
	 * @param pageable
	 * @return
	 */
	Page<Director> getAll(Pageable pageable);

	/**
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	Optional<Director> getById(long id) throws NotFoundException;

	/**
	 * @param director
	 * @return
	 * @throws AlreadyExistsExeption
	 */
	Director create(Director director) throws AlreadyExistsExeption;

	/**
	 * @param id
	 * @throws NotFoundException
	 */
	void deleteById(long id) throws NotFoundException;

	/**
	 * @param pageable
	 * @param nombres
	 * @param edades
	 * @param nacionalidad
	 * @return
	 */
	Page<Director> findAndFilter(Pageable pageable, List<String> nombres, List<Integer> edades,
			List<String> nacionalidad);

}
