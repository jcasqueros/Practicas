package com.santander.peliculacrud.model.api;

import com.santander.peliculacrud.model.entity.Film;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * The interface Film repository.
 */
public interface FilmRepository extends JpaRepository<Film, Long> {

    /**
     * Find last film list.
     *
     * @return the list
     */
    @EntityGraph(attributePaths = { "actors", "director" })
    @Query("SELECT u FROM Film u ORDER BY u.id DESC")
    List<Film> findLastFilm();
}
