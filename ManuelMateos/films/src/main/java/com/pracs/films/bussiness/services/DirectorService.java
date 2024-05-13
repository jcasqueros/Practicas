package com.pracs.films.bussiness.services;

import com.pracs.films.bussiness.bo.DirectorBO;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Method for get all directors paginated
     *
     * @return List of DirectorBO
     */
    Page<DirectorBO> findAll(Pageable pageable) throws ServiceException;

    /**
     * Method for delete a director by his id
     *
     * @param id
     */
    void deleteById(long id) throws ServiceException;

    /**
     * Method for create a director
     *
     * @param directorBO
     * @return DirectorBO
     */
    DirectorBO saveCriteria(DirectorBO directorBO) throws ServiceException;

    /**
     * Method for update a director
     *
     * @param directorBO
     * @return DirectorBO
     */
    DirectorBO updateCriteria(DirectorBO directorBO) throws ServiceException;

    /**
     * Method for find a director by his id
     *
     * @param id
     * @return DirectorBO
     */
    DirectorBO findByIdCriteria(long id) throws ServiceException;

    /**
     * Method for get all directors paginated
     *
     * @return List of DirectorBO
     */
    Page<DirectorBO> findAllCriteria(Pageable pageable) throws ServiceException;

    /**
     * Method for get all directors paginated and filtered
     *
     * @param pageable
     * @param names
     * @param ages
     * @param nationalities
     * @return
     * @throws ServiceException
     */
    Page<DirectorBO> findAllCriteriaFilter(Pageable pageable, List<String> names, List<Integer> ages,
            List<String> nationalities) throws ServiceException;

    /**
     * Method for delete a director by his id
     *
     * @param id
     */
    void deleteByIdCriteria(long id) throws ServiceException;
}
