package com.viewnex.bsan.practica04.repository.custom;

import com.viewnex.bsan.practica04.entity.Director;

import java.util.List;
import java.util.Optional;

/**
 * The {@code CustomDirectorRepository} interface is a custom repository class that uses the Criteria API to provide
 * data access logic for directors.
 *
 * @author Antonio Gil
 */
public interface CustomDirectorRepository {

    List<Director> getAll();

    boolean existsById(long id);

    Optional<Director> getById(long id);

    Director save(Director director);

    boolean deleteById(long id);

}
