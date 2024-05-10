package com.pracs.films.persistence.repositories.jpa;

import com.pracs.films.persistence.models.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of the class {@link Producer}
 *
 * @author Manuel Mateos de Torres
 * @see {@link JpaRepository}
 */
public interface ProducerRepository extends JpaRepository<Producer, Long> {

    /**
     * Method for get all producers paginated
     *
     * @param pageable
     * @return
     */
    public Page<Producer> findAll(Pageable pageable);
}
