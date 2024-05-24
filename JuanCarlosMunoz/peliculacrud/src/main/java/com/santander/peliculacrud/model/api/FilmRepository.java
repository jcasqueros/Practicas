package com.santander.peliculacrud.model.api;

import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.model.entity.Film;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * The interface Film repository.
 */
@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    @NonNull
    Page<Film> findAll(@NonNull Pageable pageable);

    /**
     * Find all by title page.
     *
     * @param title
     *         the title
     * @param pageable
     *         the pageable
     * @return the page
     */
    Page<Film> findAllByTitle(String title, Pageable pageable);

    /**
     * Find all by age page.
     *
     * @param created
     *         the created
     * @param pageable
     *         the pageable
     * @return the page
     */
    Page<Film> findAllByCreated(int created, Pageable pageable);

    /**
     * Find all by actors page.
     *
     * @param actors
     *         the actors
     * @param pageable
     *         the pageable
     * @return the page
     */
    Page<Film> findAllByActorsIn(Collection<List<Actor>> actors, Pageable pageable);

    /**
     * Find all by directors in page.
     *
     * @param directors
     *         the directors
     * @param pageable
     *         the pageable
     * @return the page
     */
    Page<Film> findAllByDirectorIn(List<Director> directors, Pageable pageable);

}
