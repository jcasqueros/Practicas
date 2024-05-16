package com.santander.peliculacrud.service;

import com.santander.peliculacrud.model.bo.FilmBO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The interface Film service interface.
 */
@Service
public interface FilmServiceInterface {

    /**
     * Create film boolean.
     *
     * @param filmBO
     *         the film in
     * @return the boolean
     */
    FilmBO createFilm(FilmBO filmBO);

    /**
     * Update film boolean.
     *
     * @param id
     *         the id
     * @param filmBO
     *         the film in
     * @return the boolean
     */
    FilmBO updateFilm(Long id, FilmBO filmBO);

    /**
     * Gets all film.
     *
     * @return the all film
     */
    List<FilmBO> getAllFilm();

    /**
     * Delete film boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     */
    boolean deleteFilm(Long id);

    /**
     * Gets film by id.
     *
     * @param id
     *         the id
     * @return the film by id
     */
    FilmBO getFilmById(Long id);

}
