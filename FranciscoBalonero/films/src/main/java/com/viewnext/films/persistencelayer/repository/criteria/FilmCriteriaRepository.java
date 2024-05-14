package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.entity.Director;
import com.viewnext.films.persistencelayer.entity.Film;
import com.viewnext.films.persistencelayer.entity.Producer;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interface for performing CRUD operations on {@link Film} using {@link jakarta.persistence.criteria.CriteriaBuilder}.
 *
 * <p>This interface provides methods for creating, reading, updating and deleting films using criteria.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // Create a film
 * Film createdFilm = filmCriteriaRepository.createFilm(new Film("John Doe", 30));
 *
 * // Get all films
 * List<Film> allFilms = filmCriteriaRepository.getAllFilms();
 *
 * // Get a film by ID
 * Optional<Film> filmById = filmCriteriaRepository.getFilmById(1L);
 *
 * // Update a film
 * Film updatedFilm = filmCriteriaRepository.updateFilm(new Film("Jane Doe", 31));
 *
 * // Delete a film
 * filmCriteriaRepository.deleteFilm(1L);
 * }
 * </pre>
 *
 * @author Francisco Balonero Olivera
 */
public interface FilmCriteriaRepository {

    /**
     * Creates a new {@link Film}.
     *
     * @param film
     *         the {@link Film} to be created
     * @return the created {@link Film}
     */
    Film createFilm(Film film);

    /**
     * Retrieves all {@link Film} paginated and sorted.
     *
     * @param pageable
     *         {@link Pageable} the pagination and sorting information
     * @return a {@link List} of all {@link Film} matching the pagination and sorting criteria
     */
    List<Film> getAllFilms(Pageable pageable);

    /**
     * Retrieves an {@link Film} by ID.
     *
     * @param id
     *         {@link Long} the ID of the {@link Film} to be retrieved
     * @return an {@link Optional} containing the {@link Film} if found, or an empty optional if not found
     */
    Optional<Film> getFilmById(Long id);

    /**
     * Updates an existing {@link Film}.
     *
     * @param film
     *         the {@link Film} to be updated
     * @return the updated {@link Film}
     */
    Film updateFilm(Film film);

    /**
     * Deletes a {@link Film} by ID.
     *
     * @param id
     *         {@link Long} the ID of the {@link Film} to be deleted
     */
    void deleteFilm(Long id);

    /**
     * Filters films based on the provided criteria and returns a paginated list of results.
     *
     * @param titles
     *         a list of film titles to filter by
     * @param releaseYears
     *         a list of release years to filter by
     * @param directors
     *         a list of directors to filter by
     * @param producers
     *         a list of producers to filter by
     * @param actors
     *         a list of actors to filter by
     * @param pageable
     *         pagination information, including page number and page size
     * @return a list of films that match the provided filter criteria, paginated according to the provided pageable
     *         object
     */
    List<Film> filterFilms(List<String> titles, List<Integer> releaseYears, List<Director> directors,
            List<Producer> producers, List<Actor> actors, Pageable pageable);
}
