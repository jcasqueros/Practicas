package com.pracs.films.persistence.repositories.criteria;

import com.pracs.films.persistence.models.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Method for get all producers paginated
     *
     * @return List of Producer
     */
    Page<Producer> findAllProducer(Pageable pageable);

    /**
     * Method for delete a producer by his id
     *
     * @param producer
     */
    void deleteProducerById(Producer producer);
}
