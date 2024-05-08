package com.viewnex.bsan.practica04.repository.custom;

import com.viewnex.bsan.practica04.entity.Film;

import java.util.List;
import java.util.Optional;

/**
 * The {@code CustomFilmRepository} interface is a custom repository class that uses the Criteria API to provide
 * data access logic for films.
 *
 * @author Antonio Gil
 */
public interface CustomFilmRepository {

    List<Film> getAll();

    boolean existsById(long id);

    Optional<Film> getById(long id);

    Film save(Film film);

    boolean deleteById(long id);

}
