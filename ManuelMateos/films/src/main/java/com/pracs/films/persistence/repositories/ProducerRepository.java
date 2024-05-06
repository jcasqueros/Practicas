package com.pracs.films.persistence.repositories;

import com.pracs.films.persistence.models.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
}
