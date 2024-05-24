package com.santander.peliculacrud.service;

import com.santander.peliculacrud.model.bo.FilmBO;
import com.santander.peliculacrud.util.exception.GenericException;
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
    FilmBO createFilm(FilmBO filmBO) throws GenericException;

    /**
     * Update film boolean.
     *
     * @param id
     *         the id
     * @param filmBO
     *         the film in
     * @return the boolean
     */
    FilmBO updateFilm(Long id, FilmBO filmBO) throws GenericException;

    /**
     * Delete film boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     */
    boolean deleteFilm(Long id) throws GenericException;

    /**
     * Gets film by id.
     *
     * @param id
     *         the id
     * @return the film by id
     */
    FilmBO getFilmById(Long id);

    /**
     * Gets all film.
     *
     * @param page
     *         the page
     * @return the all film
     */
    List<FilmBO> getAllFilm(int page);

    /**
     * Gets film by title.
     *
     * @param title
     *         the title
     * @param page
     *         the page
     * @return the film by title
     */
    List<FilmBO> getFilmByTitle(String title, int page);

    /**
     * Gets film by created.
     *
     * @param created
     *         the created
     * @param page
     *         the page
     * @return the film by created
     */
    List<FilmBO> getFilmByCreated(int created, int page);

    /**
     * Gets film by actors.
     *
     * @param actorsName
     *         the actors name
     * @param page
     *         the page
     * @return the film by actors
     */
    List<FilmBO> getFilmByActors(List<String> actorsName, int page);

    /**
     * Gets film by directors.
     *
     * @param directorsName
     *         the directors name
     * @param page
     *         the page
     * @return the film by directors
     */
    List<FilmBO> getFilmByDirectors(List<String> directorsName, int page);

}
