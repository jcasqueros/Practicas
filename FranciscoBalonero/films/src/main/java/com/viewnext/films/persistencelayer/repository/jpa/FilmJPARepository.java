package com.viewnext.films.persistencelayer.repository.jpa;

import com.viewnext.films.persistencelayer.entity.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for performing CRUD operations on {@link com.viewnext.films.persistencelayer.entity.Film}.
 *
 * <p>This interface extends the {@link JpaRepository} interface, providing methods for performing CRUD (Create, Read,
 * Update, Delete) operations on {@link Film} entities.</p>
 *
 * @author Francisco Balonero Olivera
 * @see JpaRepository
 * @see Film
 **/
@Repository
public interface FilmJPARepository extends JpaRepository<Film, Long> {
    /**
     * Retrieves a page of films matching the given pagination and sorting criteria.
     *
     * @param pageable
     *         the pagination and sorting information
     * @return a page of films matching the pagination and sorting criteria
     */

    Page<Film> findAll(Pageable pageable);
}
