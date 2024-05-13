package com.viewnext.films.persistencelayer.repository.jpa;

import com.viewnext.films.persistencelayer.entity.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for performing CRUD operations on {@link com.viewnext.films.persistencelayer.entity.Director}.
 *
 * <p>This interface extends the {@link JpaRepository} interface, providing methods for performing CRUD (Create, Read,
 * Update, Delete) operations on {@link Director} entities.</p>
 *
 * @author Francisco Balonero Olivera
 * @see JpaRepository
 * @see Director
 **/
@Repository
public interface DirectorJPARepository extends JpaRepository<Director, Long> {

    /**
     * Retrieves a page of directors matching the given pagination and sorting criteria.
     *
     * @param pageable
     *         the pagination and sorting information
     * @return a page of directors matching the pagination and sorting criteria
     */

    Page<Director> findAll(Pageable pageable);
}
