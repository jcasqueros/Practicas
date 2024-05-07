package com.pracs.films.bussiness.services;

import com.pracs.films.bussiness.bo.ProducerBO;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Producer;

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
     * Method for get all producers
     *
     * @return List of ProducerBO
     */
    List<ProducerBO> findAll() throws ServiceException;

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
     * Method for get all producers
     *
     * @return List of ProducerBO
     */
    List<ProducerBO> findAllCriteria() throws ServiceException;

    /**
     * Method for delete a producer by his id
     *
     * @param id
     */
    void deleteByIdCriteria(long id) throws ServiceException;
}
