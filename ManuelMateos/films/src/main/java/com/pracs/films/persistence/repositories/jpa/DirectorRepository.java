package com.pracs.films.persistence.repositories.jpa;

import com.pracs.films.persistence.models.Director;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of the class {@link Director}
 *
 * @author Manuel Mateos de Torres
 * @see {@link JpaRepository}
 */
public interface DirectorRepository extends JpaRepository<Director, Long> {
}
