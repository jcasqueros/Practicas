package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Director;

import java.util.List;
import java.util.Optional;

/**
 * Interface for performing CRUD operations on {@link Director} using
 * {@link jakarta.persistence.criteria.CriteriaBuilder}.
 *
 * <p>This interface provides methods for creating, reading, updating and deleting directors using criteria.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // Create an director
 * Director createdDirector = directorCriteriaRepository.createDirector(new Director("John Doe", 30));
 *
 * // Get all directors
 * List<Director> allDirectors = directorCriteriaRepository.getAllDirectors();
 *
 * // Get an director by ID
 * Optional<Director> directorById = directorCriteriaRepository.getDirectorById(1L);
 *
 * // Update an director
 * Director updatedDirector = directorCriteriaRepository.updateDirector(new Director("Jane Doe", 31));
 *
 * // Delete an director
 * directorCriteriaRepository.deleteDirector(1L);
 * }
 * </pre>
 *
 * @author Francisco Balonero Olivera
 */
public interface DirectorCriteriaRepository {

    /**
     * Creates a new {@link Director}.
     *
     * @param director
     *         the {@link Director} to be created
     * @return the created {@link Director}
     */
    Director createDirector(Director director);

    /**
     * Retrieves all {@link Director}.
     *
     * @return a {@link List} of all {@link Director}
     */
    List<Director> getAllDirectors();

    /**
     * Retrieves an {@link Director} by ID.
     *
     * @param id
     *         {@link Long} the ID of the {@link Director} to be retrieved
     * @return an {@link Optional} containing the {@link Director} if found, or an empty optional if not found
     */
    Optional<Director> getDirectorById(Long id);

    /**
     * Updates an existing {@link Director}.
     *
     * @param director
     *         the {@link Director} to be updated
     * @return the updated {@link Director}
     */
    Director updateDirector(Director director);

    /**
     * Deletes an {@link Director} by ID.
     *
     * @param id
     *         {@link Long} the ID of the {@link Director} to be deleted
     */
    void deleteDirector(Long id);
}
