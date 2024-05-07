package com.viewnext.films.persistencelayer.repository.jpa;

import com.viewnext.films.persistencelayer.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
