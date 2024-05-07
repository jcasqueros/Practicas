package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Film;

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
     * Retrieves all {@link Film}.
     *
     * @return a {@link List} of all {@link Film}
     */
    List<Film> getAllFilms();

    /**
     * Retrieves a {@link Film} by ID.
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
}
