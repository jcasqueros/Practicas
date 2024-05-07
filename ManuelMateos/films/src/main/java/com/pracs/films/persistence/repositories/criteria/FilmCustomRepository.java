package com.pracs.films.persistence.repositories.criteria;

import com.pracs.films.persistence.models.Film;

import java.util.List;
import java.util.Optional;

/**
 * Criteria Repository of the class {@link Film}
 *
 * @author Manuel Mateos de Torres
 */
public interface FilmCustomRepository {

    /**
     * Method for create a Film
     *
     * @param film
     * @return Optional Film
     */
    Film saveFilm(Film film);

    /**
     * Method for update a film
     *
     * @param film
     * @return Optional Film
     */
    Film updateFilm(Film film);

    /**
     * Method for find a film by his id
     *
     * @param id
     * @return Optional Film
     */
    Optional<Film> findFilmById(long id);

    /**
     * Method for get all films
     *
     * @return List of Film
     */
    List<Film> findAllFilm();

    /**
     * Method for delete a film by his id
     *
     * @param id
     */
    void deleteFilmById(long id);
}
