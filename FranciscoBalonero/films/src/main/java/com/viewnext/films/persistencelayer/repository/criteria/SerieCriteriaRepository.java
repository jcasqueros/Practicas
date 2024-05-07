package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Serie;

import java.util.List;
import java.util.Optional;

/**
 * Interface for performing CRUD operations on {@link Serie} using
 * {@link jakarta.persistence.criteria.CriteriaBuilder}.
 *
 * <p>This interface provides methods for creating, reading, updating and deleting series using criteria.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // Create a serie
 * Serie createdSerie = serieCriteriaRepository.createSerie(new Serie("John Doe", 30));
 *
 * // Get all series
 * List<Serie> allSeries = serieCriteriaRepository.getAllSeries();
 *
 * // Get a serie by ID
 * Optional<Serie> serieById = serieCriteriaRepository.getSerieById(1L);
 *
 * // Update a serie
 * Serie updatedSerie = serieCriteriaRepository.updateSerie(new Serie("Jane Doe", 31));
 *
 * // Delete a serie
 * serieCriteriaRepository.deleteSerie(1L);
 * }
 * </pre>
 *
 * @author Francisco Balonero Olivera
 */
public interface SerieCriteriaRepository {

    /**
     * Creates a new {@link Serie}.
     *
     * @param serie
     *         the {@link Serie} to be created
     * @return the created {@link Serie}
     */
    Serie createSerie(Serie serie);

    /**
     * Retrieves all {@link Serie}.
     *
     * @return a {@link List} of all {@link Serie}
     */
    List<Serie> getAllSeries();

    /**
     * Retrieves a {@link Serie} by ID.
     *
     * @param id
     *         {@link Long} the ID of the {@link Serie} to be retrieved
     * @return an {@link Optional} containing the {@link Serie} if found, or an empty optional if not found
     */
    Optional<Serie> getSerieById(Long id);

    /**
     * Updates an existing {@link Serie}.
     *
     * @param serie
     *         the {@link Serie} to be updated
     * @return the updated {@link Serie}
     */
    Serie updateSerie(Serie serie);

    /**
     * Deletes a {@link Serie} by ID.
     *
     * @param id
     *         {@link Long} the ID of the {@link Serie} to be deleted
     */
    void deleteSerie(Long id);
}
