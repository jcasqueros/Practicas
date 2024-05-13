package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Director;
import org.springframework.data.domain.Pageable;

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
 * // Create a director
 * Director createdDirector = directorCriteriaRepository.createDirector(new Director("John Doe", 30));
 *
 * // Get all directors
 * List<Director> allDirectors = directorCriteriaRepository.getAllDirectors();
 *
 * // Get a director by ID
 * Optional<Director> directorById = directorCriteriaRepository.getDirectorById(1L);
 *
 * // Update a director
 * Director updatedDirector = directorCriteriaRepository.updateDirector(new Director("Jane Doe", 31));
 *
 * // Delete a director
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
     * Retrieves all {@link Director} paginated and sorted.
     *
     * @param pageable
     *         {@link Pageable} the pagination and sorting information
     * @return a {@link List} of all {@link Director} matching the pagination and sorting criteria
     */
    List<Director> getAllDirectors(Pageable pageable);

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
     * Deletes a {@link Director} by ID.
     *
     * @param id
     *         {@link Long} the ID of the {@link Director} to be deleted
     */
    void deleteDirector(Long id);
}
