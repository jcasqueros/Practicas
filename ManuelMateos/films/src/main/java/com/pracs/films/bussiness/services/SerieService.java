package com.pracs.films.bussiness.services;

import com.pracs.films.bussiness.bo.SerieBO;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Serie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    SerieBO save(SerieBO serieBO, String port) throws ServiceException;

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
     * Method for get all series paginated
     *
     * @return List of SerieBO
     */
    Page<SerieBO> findAll(Pageable pageable) throws ServiceException;

    /**
     * Method for delete a serie by his id
     *
     * @param id
     */
    void deleteById(long id) throws ServiceException;

    /**
     * Method for create a serie
     *
     * @param serieBO
     * @return SerieBO
     */
    SerieBO saveCriteria(SerieBO serieBO, String port) throws ServiceException;

    /**
     * Method for update a serie
     *
     * @param serieBO
     * @return SerieBO
     */
    SerieBO updateCriteria(SerieBO serieBO) throws ServiceException;

    /**
     * Method for find a serie by his id
     *
     * @param id
     * @return SerieBO
     */
    SerieBO findByIdCriteria(long id) throws ServiceException;

    /**
     * Method for get all series paginated
     *
     * @return List of SerieBO
     */
    Page<SerieBO> findAllCriteria(Pageable pageable) throws ServiceException;

    /**
     * Method for get all series paginated and filtered
     *
     * @param pageable
     * @param titles
     * @param ages
     * @param directors
     * @param producers
     * @param actors
     * @return
     * @throws ServiceException
     */
    Page<SerieBO> findAllCriteriaFilter(Pageable pageable, List<String> titles, List<Integer> ages,
            List<String> directors, List<String> producers, List<String> actors) throws ServiceException;

    /**
     * Method for delete a serie by his id
     *
     * @param id
     */
    void deleteByIdCriteria(long id) throws ServiceException;
}
