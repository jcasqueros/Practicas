package com.pracs.films.persistence.repositories.jpa;

import com.pracs.films.persistence.models.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of the class {@link Producer}
 *
 * @author Manuel Mateos de Torres
 * @see {@link JpaRepository}
 */
public interface ProducerRepository extends JpaRepository<Producer, Long> {
}
