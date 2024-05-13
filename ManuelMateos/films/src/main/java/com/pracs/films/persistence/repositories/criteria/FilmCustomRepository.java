package com.pracs.films.persistence.repositories.criteria;

import com.pracs.films.persistence.models.Actor;
import com.pracs.films.persistence.models.Director;
import com.pracs.films.persistence.models.Film;
import com.pracs.films.persistence.models.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Criteria Repository of the class {@link Film}
 *
 * @author Manuel Mateos de Torres
 */
public interface FilmCustomRepository {

    /**
     * Method for create a Film
     *
     * @param film
     * @return Optional Film
     */
    Film saveFilm(Film film);

    /**
     * Method for update a film
     *
     * @param film
     * @return Optional Film
     */
    Film updateFilm(Film film);

    /**
     * Method for find a film by his id
     *
     * @param id
     * @return Optional Film
     */
    Optional<Film> findFilmById(long id);

    /**
     * Method for get all films paginated
     *
     * @return List of Film
     */
    Page<Film> findAllFilm(Pageable pageable);

    /**
     * Method for get all films by differents atribbutes
     *
     * @param pageable
     * @param titles
     * @param ages
     * @param directors
     * @param producers
     * @param actors
     * @return
     */
    Page<Film> findAllFilter(Pageable pageable, List<String> titles, List<Integer> ages, List<Director> directors,
            List<Producer> producers, List<Actor> actors);

    /**
     * Method for delete a film by his id
     *
     * @param film
     */
    void deleteFilmById(Film film);
}
