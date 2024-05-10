package com.santander.peliculacrud.service;

import com.santander.peliculacrud.model.input.Film;
import com.santander.peliculacrud.model.output.FilmModelController;
import com.santander.peliculacrud.model.output.FilmModelService;
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
     * @param filmModelController
     *         the film in
     * @return the boolean
     */
    boolean createFilm(FilmModelController filmModelController);

    /**
     * Update film boolean.
     *
     * @param id
     *         the id
     * @param filmModelController
     *         the film in
     * @return the boolean
     */
    boolean updateFilm(Long id, FilmModelController filmModelController);

    /**
     * Gets all film.
     *
     * @return the all film
     */
    List<FilmModelService> getAllFilm();

    /**
     * Film out film out.
     *
     * @param id
     *         the id
     * @return the film out
     */
    FilmModelService filmOut(Long id);

    /**
     * Delete film boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     */
    boolean deleteFilm(Long id);

    /**
     * Get las film film.
     *
     * @return the film
     */
    Film getLastFilm();

    /**
     * Gets list size.
     *
     * @return the list size
     */
    int getListSize();

    /**
     * Gets film by id.
     *
     * @param id
     *         the id
     * @return the film by id
     */
    Film getFilmById(Long id);

    /**
     * Exists film by id boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     */
    boolean existsFilmById(Long id);

}
