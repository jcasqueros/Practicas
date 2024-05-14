package com.viewnext.films.persistencelayer.repository.jpa;

import com.viewnext.films.persistencelayer.entity.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface for performing CRUD operations on {@link com.viewnext.films.persistencelayer.entity.Producer}.
 *
 * <p>This interface extends the {@link JpaRepository} interface, providing methods for performing CRUD (Create, Read,
 * Update, Delete) operations on {@link Producer} entities.</p>
 *
 * @author Francisco Balonero Olivera
 * @see JpaRepository
 * @see Producer
 **/
@Repository
public interface ProducerJPARepository extends JpaRepository<Producer, Long> {

    /**
     * Retrieves a page of producers matching the given pagination and sorting criteria.
     *
     * @param pageable
     *         the pagination and sorting information
     * @return a page of producers matching the pagination and sorting criteria
     */

    Page<Producer> findAll(Pageable pageable);

    List<Producer> findByName(String name);
}
