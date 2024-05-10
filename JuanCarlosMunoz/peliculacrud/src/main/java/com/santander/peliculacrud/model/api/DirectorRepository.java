package com.santander.peliculacrud.model.api;

import com.santander.peliculacrud.model.input.Actor;
import com.santander.peliculacrud.model.input.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * The interface Director repository.
 */
public interface DirectorRepository extends JpaRepository<Director, Long> {

    /**
     * Find last actor list.
     *
     * @return the list
     */
    @Query("SELECT u FROM Director u ORDER BY u.id DESC")
    List<Director> findLastDirector();

}
