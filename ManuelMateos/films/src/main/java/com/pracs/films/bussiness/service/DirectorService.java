package com.pracs.films.bussiness.service;

import com.pracs.films.bussiness.bo.DirectorBO;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Director;

import java.util.List;

/**
 * Service of the model {@link Director}
 *
 * @author Manuel Mateos de Torres
 */
public interface DirectorService {

    /**
     * Method for create a director
     *
     * @param directorBO
     * @return DirectorBO
     */
    DirectorBO save(DirectorBO directorBO) throws ServiceException;

    /**
     * Method for update a director
     *
     * @param directorBO
     * @return DirectorBO
     */
    DirectorBO update(DirectorBO directorBO) throws ServiceException;

    /**
     * Method for find a director by his id
     *
     * @param id
     * @return DirectorBO
     */
    DirectorBO findById(long id) throws ServiceException;

    /**
     * Method for get all directors
     *
     * @return List of DirectorBO
     */
    List<DirectorBO> findAll() throws EmptyException, ServiceException;

    /**
     * Method for delete a director by his id
     *
     * @param id
     */
    void deleteById(long id) throws ServiceException;
}
