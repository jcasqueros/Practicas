package com.pracs.films.persistence.repositories.jpa;

import com.pracs.films.persistence.models.Serie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of the class {@link Serie}
 *
 * @author Manuel Mateos de Torres
 * @see {@link JpaRepository}
 */
public interface SerieRepository extends JpaRepository<Serie, Long> {

    /**
     * Method for get all series paginated
     *
     * @param pageable
     * @return
     */
    public Page<Serie> findAll(Pageable pageable);
}
