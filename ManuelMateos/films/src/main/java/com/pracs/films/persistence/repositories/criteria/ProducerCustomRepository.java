package com.pracs.films.persistence.repositories.criteria;

import com.pracs.films.persistence.models.Producer;

import java.util.List;
import java.util.Optional;

/**
 * Criteria Repository of the class {@link Producer}
 *
 * @author Manuel Mateos de Torres
 */
public interface ProducerCustomRepository {

    /**
     * Method for create a producer
     *
     * @param producer
     * @return Optional Producer
     */
    Producer saveProducer(Producer producer);

    /**
     * Method for update a producer
     *
     * @param producer
     * @return Optional Producer
     */
    Producer updateProducer(Producer producer);

    /**
     * Method for find a producer by his id
     *
     * @param id
     * @return Optional Producer
     */
    Optional<Producer> findProducerById(long id);

    /**
     * Method for get all producers
     *
     * @return List of Producer
     */
    List<Producer> findAllProducer();

    /**
     * Method for delete a producer by his id
     *
     * @param id
     */
    void deleteProducerById(long id);
}
