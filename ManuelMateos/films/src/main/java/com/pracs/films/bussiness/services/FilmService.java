package com.pracs.films.bussiness.services;

import com.pracs.films.bussiness.bo.FilmBO;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Film;

import java.util.List;

/**
 * Service of the model {@link Film}
 *
 * @author Manuel Mateos de Torres
 */
public interface FilmService {

    /**
     * Method for create a film
     *
     * @param filmBO
     * @return FilmBO
     */
    FilmBO save(FilmBO filmBO) throws ServiceException;

    /**
     * Method for update a film
     *
     * @param filmBO
     * @return FilmBO
     */
    FilmBO update(FilmBO filmBO) throws ServiceException;

    /**
     * Method for find a film by his id
     *
     * @param id
     * @return FilmBO
     */
    FilmBO findById(long id) throws ServiceException;

    /**
     * Method for get all films
     *
     * @return List of FilmBO
     */
    List<FilmBO> findAll() throws ServiceException;

    /**
     * Method for delete a film by his id
     *
     * @param id
     */
    void deleteById(long id) throws ServiceException;
}
