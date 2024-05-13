package com.pracs.films.persistence.repositories.jpa;

import com.pracs.films.persistence.models.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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

    /**
     * Method for get all producers by his name
     *
     * @param name
     * @return
     */
    public List<Producer> findByName(String name);
}
