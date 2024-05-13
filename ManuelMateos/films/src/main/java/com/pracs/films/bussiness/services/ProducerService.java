package com.pracs.films.bussiness.services;

import com.pracs.films.bussiness.bo.ProducerBO;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service of the model {@link Producer}
 *
 * @author Manuel Mateos de Torres
 */
public interface ProducerService {

    /**
     * Method for create a producer
     *
     * @param producerBO
     * @return ProducerBO
     */
    ProducerBO save(ProducerBO producerBO) throws ServiceException;

    /**
     * Method for update a producer
     *
     * @param producerBO
     * @return ProducerBO
     */
    ProducerBO update(ProducerBO producerBO) throws ServiceException;

    /**
     * Method for find a producer by his id
     *
     * @param id
     * @return ProducerBO
     */
    ProducerBO findById(long id) throws ServiceException;

    /**
     * Method for get all producers paginated
     *
     * @return List of ProducerBO
     */
    Page<ProducerBO> findAll(Pageable pageable) throws ServiceException;

    /**
     * Method for get all directors paginated and filtered
     *
     * @param pageable
     * @param names
     * @param ages
     * @return
     * @throws ServiceException
     */
    Page<ProducerBO> findAllCriteriaFilter(Pageable pageable, List<String> names, List<Integer> ages)
            throws ServiceException;

    /**
     * Method for delete a producer by his id
     *
     * @param id
     */
    void deleteById(long id) throws ServiceException;

    /**
     * Method for create a producer
     *
     * @param producerBO
     * @return ProducerBO
     */
    ProducerBO saveCriteria(ProducerBO producerBO) throws ServiceException;

    /**
     * Method for update a producer
     *
     * @param producerBO
     * @return ProducerBO
     */
    ProducerBO updateCriteria(ProducerBO producerBO) throws ServiceException;

    /**
     * Method for find a producer by his id
     *
     * @param id
     * @return ProducerBO
     */
    ProducerBO findByIdCriteria(long id) throws ServiceException;

    /**
     * Method for get all producers paginated
     *
     * @return List of ProducerBO
     */
    Page<ProducerBO> findAllCriteria(Pageable pageable) throws ServiceException;

    /**
     * Method for delete a producer by his id
     *
     * @param id
     */
    void deleteByIdCriteria(long id) throws ServiceException;
}
