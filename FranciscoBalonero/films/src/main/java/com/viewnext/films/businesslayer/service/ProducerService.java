package com.viewnext.films.businesslayer.service;

import com.viewnext.films.businesslayer.bo.ProducerBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;

import java.util.List;

/**
 * Service interface containing methods for managing producers.
 *
 * <p>This interface declares methods for retrieving, creating, updating, and deleting producers using both Criteria
 * and JPA.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // Obtain an instance of ProducerService
 * ProducerService producerService = // instantiate or inject the implementation
 *
 * // Retrieve an producer by ID using Criteria
 * ProducerBO producer = producerService.criteriaGetById(1L);
 *
 * // Retrieve all producers using Criteria
 * List<ProducerBO> producers = producerService.criteriaGetAll();
 *
 * // Delete an producer by ID using Criteria
 * producerService.criteriaDeleteById(1L);
 *
 * // Update an producer using Criteria
 * ProducerBO updatedProducer = producerService.criteriaUpdate(producer);
 *
 * // Create a new producer using Criteria
 * ProducerBO createdProducer = producerService.criteriaCreate(producer);
 *
 * // Retrieve an producer by ID using JPA
 * ProducerBO producer = producerService.jpaGetById(1L);
 *
 * // Retrieve all producers using JPA
 * List<ProducerBO> producers = producerService.jpaGetAll();
 *
 * // Delete an producer by ID using JPA
 * producerService.jpaDeleteById(1L);
 *
 * // Update an producer using JPA
 * ProducerBO updatedProducer = producerService.jpaUpdate(producer);
 *
 * // Create a new producer using JPA
 * ProducerBO createdProducer = producerService.jpaCreate(producer);
 * }
 * </pre>
 *
 * <p>Implementing classes should handle {@link ServiceException} and {@link NotFoundException} to appropriately manage
 * exceptions related to business operations on producers.</p>
 *
 * <p>Instances of this interface are typically used in the business layer of the application to provide
 * a clean separation between the business logic and persistence layer logic.</p>
 *
 * @author Francisco Balonero Olivera
 */
public interface ProducerService {
    /**
     * Retrieves a producer by ID using Criteria.
     *
     * @param id
     *         The ID of the producer to retrieve.
     * @return The retrieved {@link ProducerBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    ProducerBO criteriaGetById(long id) throws ServiceException;

    /**
     * Retrieves all producers using Criteria, paginated and sorted.
     *
     * @param pageNumber
     *         the page number to retrieve
     * @param pageSize
     *         the number of items per page
     * @param sortBy
     *         the property to sort by
     * @param sortOrder
     *         false for ascending order, true for descending order
     * @return A {@link List} of all {@link ProducerBO} matching the pagination and sorting criteria.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    List<ProducerBO> criteriaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder)
            throws ServiceException;

    /**
     * Deletes a producer by ID using Criteria.
     *
     * @param id
     *         The ID of the producer to delete.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    void criteriaDeleteById(Long id) throws ServiceException;

    /**
     * Updates a producer using Criteria.
     *
     * @param producerBO
     *         The {@link ProducerBO} to update.
     * @return The updated {@link ProducerBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    ProducerBO criteriaUpdate(ProducerBO producerBO) throws ServiceException;

    /**
     * Creates a new producer using Criteria.
     *
     * @param producerBO
     *         The {@link ProducerBO} to create.
     * @return The created {@link ProducerBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    ProducerBO criteriaCreate(ProducerBO producerBO) throws ServiceException;

    /**
     * Retrieves a producer by ID using JPA.
     *
     * @param id
     *         The ID of the producer to retrieve.
     * @return The retrieved {@link ProducerBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    ProducerBO jpaGetById(long id) throws ServiceException;

    /**
     * Retrieves all producers using JPA, paginated and sorted.
     *
     * @param pageNumber
     *         the page number to retrieve
     * @param pageSize
     *         the number of items per page
     * @param sortBy
     *         the property to sort by
     * @param sortOrder
     *         false for ascending order, true for descending order
     * @return A {@link List} of all {@link ProducerBO} matching the pagination and sorting criteria.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    List<ProducerBO> jpaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder) throws ServiceException;

    /**
     * Deletes a producer by ID using JPA.
     *
     * @param id
     *         The ID of the producer to delete.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    void jpaDeleteById(Long id) throws ServiceException;

    /**
     * Updates a producer using JPA.
     *
     * @param producerBO
     *         The {@link ProducerBO} to update.
     * @return The updated {@link ProducerBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    ProducerBO jpaUpdate(ProducerBO producerBO) throws ServiceException;

    /**
     * Creates a new producer using JPA.
     *
     * @param producerBO
     *         The {@link ProducerBO} to create.
     * @return The created {@link ProducerBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    ProducerBO jpaCreate(ProducerBO producerBO) throws ServiceException;

    /**
     * Filters producers based on the provided filters and pagination.
     *
     * @param names
     *         a list of names to filter by
     * @param foundationYears
     *         a list of foundation years to filter by
     * @param pageNumber
     *         the page number to retrieve
     * @param pageSize
     *         the number of items per page
     * @param sortBy
     *         the property to sort by
     * @param sortOrder
     *         false for ascending order, true for descending order
     * @return a list of ProducerBO objects that match the filters and pagination
     * @throws NotFoundException
     *         if no producers are found that match the filters
     */
    List<ProducerBO> filterProducers(List<String> names, List<Integer> foundationYears, int pageNumber, int pageSize,
            String sortBy, boolean sortOrder) throws ServiceException;
}
