package com.viewnext.films.persistencelayer.repository.jpa;

import com.viewnext.films.persistencelayer.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for performing CRUD operations on {@link com.viewnext.films.persistencelayer.entity.Serie}.
 *
 * <p>This interface extends the {@link JpaRepository} interface, providing methods for performing CRUD (Create, Read,
 * Update, Delete) operations on {@link Serie} entities.</p>
 *
 * @author Francisco Balonero Olivera
 * @see JpaRepository
 * @see Serie
 **/
@Repository
public interface SerieJPARepository extends JpaRepository<Serie, Long> {
}
