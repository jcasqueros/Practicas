package com.viewnext.films.persistencelayer.repository.criteria;

import com.viewnext.films.persistencelayer.entity.Producer;

import java.util.List;
import java.util.Optional;

/**
 * Interface for performing CRUD operations on {@link Producer} using
 * {@link jakarta.persistence.criteria.CriteriaBuilder}.
 *
 * <p>This interface provides methods for creating, reading, updating and deleting producers using criteria.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // Create a producer
 * Producer createdProducer = producerCriteriaRepository.createProducer(new Producer("John Doe", 30));
 *
 * // Get all producers
 * List<Producer> allProducers = producerCriteriaRepository.getAllProducers();
 *
 * // Get a producer by ID
 * Optional<Producer> producerById = producerCriteriaRepository.getProducerById(1L);
 *
 * // Update a producer
 * Producer updatedProducer = producerCriteriaRepository.updateProducer(new Producer("Jane Doe", 31));
 *
 * // Delete a producer
 * producerCriteriaRepository.deleteProducer(1L);
 * }
 * </pre>
 *
 * @author Francisco Balonero Olivera
 */
public interface ProducerCriteriaRepository {

    /**
     * Creates a new {@link Producer}.
     *
     * @param producer
     *         the {@link Producer} to be created
     * @return the created {@link Producer}
     */
    Producer createProducer(Producer producer);

    /**
     * Retrieves all {@link Producer}.
     *
     * @return a {@link List} of all {@link Producer}
     */
    List<Producer> getAllProducers();

    /**
     * Retrieves a {@link Producer} by ID.
     *
     * @param id
     *         {@link Long} the ID of the {@link Producer} to be retrieved
     * @return an {@link Optional} containing the {@link Producer} if found, or an empty optional if not found
     */
    Optional<Producer> getProducerById(Long id);

    /**
     * Updates an existing {@link Producer}.
     *
     * @param producer
     *         the {@link Producer} to be updated
     * @return the updated {@link Producer}
     */
    Producer updateProducer(Producer producer);

    /**
     * Deletes a {@link Producer} by ID.
     *
     * @param id
     *         {@link Long} the ID of the {@link Producer} to be deleted
     */
    void deleteProducer(Long id);
}
