package com.pracs.films.bussiness.services;

import com.pracs.films.bussiness.bo.SerieBO;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Serie;

import java.util.List;

/**
 * Service of the model {@link Serie}
 *
 * @author Manuel Mateos de Torres
 */
public interface SerieService {

    /**
     * Method for create a serie
     *
     * @param serieBO
     * @return SerieBO
     */
    SerieBO save(SerieBO serieBO) throws ServiceException;

    /**
     * Method for update a serie
     *
     * @param serieBO
     * @return SerieBO
     */
    SerieBO update(SerieBO serieBO) throws ServiceException;

    /**
     * Method for find a serie by his id
     *
     * @param id
     * @return SerieBO
     */
    SerieBO findById(long id) throws ServiceException;

    /**
     * Method for get all series
     *
     * @return List of SerieBO
     */
    List<SerieBO> findAll() throws ServiceException;

    /**
     * Method for delete a serie by his id
     *
     * @param id
     */
    void deleteById(long id) throws ServiceException;
}
