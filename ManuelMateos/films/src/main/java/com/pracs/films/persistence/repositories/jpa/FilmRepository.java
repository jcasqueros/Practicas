package com.pracs.films.persistence.repositories.jpa;

import com.pracs.films.persistence.models.Film;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of the class {@link Film}
 *
 * @author Manuel Mateos de Torres
 * @see {@link JpaRepository}
 */
public interface FilmRepository extends JpaRepository<Film, Long> {
}
