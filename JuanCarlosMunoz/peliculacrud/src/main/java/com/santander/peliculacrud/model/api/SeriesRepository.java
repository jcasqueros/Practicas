package com.santander.peliculacrud.model.api;

import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.model.entity.Series;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * The interface Series repository.
 */
@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {

    @NonNull
    Page<Series> findAll(@NonNull Pageable pageable);

    /**
     * Find all by title page.
     *
     * @param title
     *         the title
     * @param pageable
     *         the pageable
     * @return the page
     */
    Page<Series> findAllByTitle(String title, Pageable pageable);

    /**
     * Find all by age page.
     *
     * @param created
     *         the created
     * @param pageable
     *         the pageable
     * @return the page
     */
    Page<Series> findAllByCreated(int created, Pageable pageable);

    /**
     * Find all by actors page.
     *
     * @param actors
     *         the actors
     * @param pageable
     *         the pageable
     * @return the page
     */
    Page<Series> findAllByActorsIn(List<Actor> actors, Pageable pageable);

    /**
     * Find all by directors in page.
     *
     * @param directors
     *         the directors
     * @param pageable
     *         the pageable
     * @return the page
     */
    Page<Series> findAllByDirectorIn(List<Director> directors, Pageable pageable);

}
