package com.pracs.films.persistence.repositories.jpa;

import com.pracs.films.persistence.models.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of the class {@link Film}
 *
 * @author Manuel Mateos de Torres
 * @see {@link JpaRepository}
 */
public interface FilmRepository extends JpaRepository<Film, Long> {

    /**
     * Method for get all films paginated
     *
     * @param pageable
     * @return
     */
    public Page<Film> findAll(Pageable pageable);
}
