package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.entity.Director;
import com.viewnext.films.persistencelayer.entity.Producer;
import com.viewnext.films.persistencelayer.entity.Serie;
import org.springframework.data.domain.Pageable;

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
     * Retrieves all {@link Serie} paginated and sorted.
     *
     * @param pageable
     *         {@link Pageable} the pagination and sorting information
     * @return a {@link List} of all {@link Serie} matching the pagination and sorting criteria
     */
    List<Serie> getAllSeries(Pageable pageable);

    /**
     * Retrieves an {@link Serie} by ID.
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

    /**
     * Filters series based on the provided criteria and returns a paginated list of results.
     *
     * @param titles
     *         a list of serie titles to filter by
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
     * @return a list of series that match the provided filter criteria, paginated according to the provided pageable
     *         object
     */
    List<Serie> filterSeries(List<String> titles, List<Integer> releaseYears, List<Director> directors,
            List<Producer> producers, List<Actor> actors, Pageable pageable);

}
