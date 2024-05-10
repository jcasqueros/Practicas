package com.pracs.films.persistence.repositories.jpa;

import com.pracs.films.persistence.models.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository of the class {@link Actor}
 *
 * @author Manuel Mateos de Torres
 * @see {@link JpaRepository}
 */
@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    /**
     * Method for get all actors paginated
     *
     * @param pageable
     * @return
     */
    public Page<Actor> findAll(Pageable pageable);
}
