package com.pracs.films.persistence.repositories.jpa;

import com.pracs.films.persistence.models.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Long> {
}
