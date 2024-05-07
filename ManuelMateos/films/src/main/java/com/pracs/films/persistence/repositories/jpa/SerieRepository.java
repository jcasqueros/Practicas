package com.pracs.films.persistence.repositories.jpa;

import com.pracs.films.persistence.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of the class {@link Serie}
 *
 * @author Manuel Mateos de Torres
 * @see {@link JpaRepository}
 */
public interface SerieRepository extends JpaRepository<Serie, Long> {
}
