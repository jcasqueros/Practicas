package com.santander.peliculacrud.model.api;

import com.santander.peliculacrud.model.input.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Actor repository.
 */
@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    /**
     * Find by id in list.
     *
     * @param ids
     *         the ids
     * @return the list
     */
    List<Actor> findByIdIn(List<Long> ids);

    /**
     * Find last actor list.
     *
     * @return the list
     */
    @Query("SELECT u FROM Actor u ORDER BY u.id DESC")
    List<Actor> findLastActor();
}
