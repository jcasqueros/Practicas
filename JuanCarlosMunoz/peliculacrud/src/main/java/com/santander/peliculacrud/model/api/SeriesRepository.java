package com.santander.peliculacrud.model.api;

import com.santander.peliculacrud.model.input.Series;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * The interface Series repository.
 */
public interface SeriesRepository extends JpaRepository<Series, Long> {


    @EntityGraph(attributePaths = { "actors", "director" })
    @Query("SELECT u FROM Series u ORDER BY u.id DESC")
    List<Series> findLastSeries();
}
