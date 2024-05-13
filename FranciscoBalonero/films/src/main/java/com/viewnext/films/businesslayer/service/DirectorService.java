package com.viewnext.films.businesslayer.service;

import com.viewnext.films.businesslayer.bo.DirectorBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;

import java.util.List;

/**
 * Service interface containing methods for managing directors.
 *
 * <p>This interface declares methods for retrieving, creating, updating, and deleting directors using both Criteria
 * and
 * JPA.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // Obtain an instance of DirectorService
 * DirectorService directorService = // instantiate or inject the implementation
 *
 * // Retrieve an director by ID using Criteria
 * DirectorBO director = directorService.criteriaGetById(1L);
 *
 * // Retrieve all directors using Criteria
 * List<DirectorBO> directors = directorService.criteriaGetAll();
 *
 * // Delete an director by ID using Criteria
 * directorService.criteriaDeleteById(1L);
 *
 * // Update an director using Criteria
 * DirectorBO updatedDirector = directorService.criteriaUpdate(director);
 *
 * // Create a new director using Criteria
 * DirectorBO createdDirector = directorService.criteriaCreate(director);
 *
 * // Retrieve an director by ID using JPA
 * DirectorBO director = directorService.jpaGetById(1L);
 *
 * // Retrieve all directors using JPA
 * List<DirectorBO> directors = directorService.jpaGetAll();
 *
 * // Delete an director by ID using JPA
 * directorService.jpaDeleteById(1L);
 *
 * // Update an director using JPA
 * DirectorBO updatedDirector = directorService.jpaUpdate(director);
 *
 * // Create a new director using JPA
 * DirectorBO createdDirector = directorService.jpaCreate(director);
 * }
 * </pre>
 *
 * <p>Implementing classes should handle {@link ServiceException} and {@link NotFoundException} to appropriately manage
 * exceptions related to business operations on directors.</p>
 *
 * <p>Instances of this interface are typically used in the business layer of the application to provide
 * a clean separation between the business logic and persistence layer logic.</p>
 *
 * @author Francisco Balonero Olivera
 */
public interface DirectorService {
    /**
     * Retrieves a director by ID using Criteria.
     *
     * @param id
     *         The ID of the director to retrieve.
     * @return The retrieved {@link DirectorBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    DirectorBO criteriaGetById(long id) throws ServiceException;

    /**
     * Retrieves all directors using Criteria, paginated and sorted.
     *
     * @param pageNumber
     *         the page number to retrieve
     * @param pageSize
     *         the number of items per page
     * @param sortBy
     *         the property to sort by
     * @param sortOrder
     *         true for ascending order, false for descending order
     * @return A {@link List} of all {@link DirectorBO} matching the pagination and sorting criteria.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    List<DirectorBO> criteriaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder)
            throws ServiceException;

    /**
     * Deletes a director by ID using Criteria.
     *
     * @param id
     *         The ID of the director to delete.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    void criteriaDeleteById(Long id) throws ServiceException;

    /**
     * Updates a director using Criteria.
     *
     * @param directorBO
     *         The {@link DirectorBO} to update.
     * @return The updated {@link DirectorBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    DirectorBO criteriaUpdate(DirectorBO directorBO) throws ServiceException;

    /**
     * Creates a new director using Criteria.
     *
     * @param directorBO
     *         The {@link DirectorBO} to create.
     * @return The created {@link DirectorBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    DirectorBO criteriaCreate(DirectorBO directorBO) throws ServiceException;

    /**
     * Retrieves a director by ID using JPA.
     *
     * @param id
     *         The ID of the director to retrieve.
     * @return The retrieved {@link DirectorBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    DirectorBO jpaGetById(long id) throws ServiceException;

    /**
     * Retrieves all directors using JPA, paginated and sorted.
     *
     * @param pageNumber
     *         the page number to retrieve
     * @param pageSize
     *         the number of items per page
     * @param sortBy
     *         the property to sort by
     * @param sortOrder
     *         true for ascending order, false for descending order
     * @return A {@link List} of all {@link DirectorBO} matching the pagination and sorting criteria.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    List<DirectorBO> jpaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder) throws ServiceException;

    /**
     * Deletes a director by ID using JPA.
     *
     * @param id
     *         The ID of the director to delete.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    void jpaDeleteById(Long id) throws ServiceException;

    /**
     * Updates a director using JPA.
     *
     * @param directorBO
     *         The {@link DirectorBO} to update.
     * @return The updated {@link DirectorBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    DirectorBO jpaUpdate(DirectorBO directorBO) throws ServiceException;

    /**
     * Creates a new director using JPA.
     *
     * @param directorBO
     *         The {@link DirectorBO} to create.
     * @return The created {@link DirectorBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    DirectorBO jpaCreate(DirectorBO directorBO) throws ServiceException;
}
