package com.viewnext.films.businesslayer.service;

import com.viewnext.films.businesslayer.bo.SerieBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;

import java.util.List;

/**
 * Service interface containing methods for managing series.
 *
 * <p>This interface declares methods for retrieving, creating, updating, and deleting series using both Criteria
 * and JPA.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // Obtain a instance of SerieService
 * SerieService serieService = // instantiate or inject the implementation
 *
 * // Retrieve a serie by ID using Criteria
 * SerieBO serie = serieService.criteriaGetById(1L);
 *
 * // Retrieve all series using Criteria
 * List<SerieBO> series = serieService.criteriaGetAll();
 *
 * // Delete a serie by ID using Criteria
 * serieService.criteriaDeleteById(1L);
 *
 * // Update a serie using Criteria
 * SerieBO updatedSerie = serieService.criteriaUpdate(serie);
 *
 * // Create a new serie using Criteria
 * SerieBO createdSerie = serieService.criteriaCreate(serie);
 *
 * // Retrieve a serie by ID using JPA
 * SerieBO serie = serieService.jpaGetById(1L);
 *
 * // Retrieve all series using JPA
 * List<SerieBO> series = serieService.jpaGetAll();
 *
 * // Delete a serie by ID using JPA
 * serieService.jpaDeleteById(1L);
 *
 * // Update a serie using JPA
 * SerieBO updatedSerie = serieService.jpaUpdate(serie);
 *
 * // Create a new serie using JPA
 * SerieBO createdSerie = serieService.jpaCreate(serie);
 * }
 * </pre>
 *
 * <p>Implementing classes should handle {@link ServiceException} and {@link NotFoundException} to appropriately manage
 * exceptions related to business operations on series.</p>
 *
 * <p>Instances of this interface are typically used in the business layer of the application to provide
 * a clean separation between the business logic and persistence layer logic.</p>
 *
 * @author Francisco Balonero Olivera
 */
public interface SerieService {
    /**
     * Retrieves a serie by ID using Criteria.
     *
     * @param id
     *         The ID of the serie to retrieve.
     * @return The retrieved {@link SerieBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    SerieBO criteriaGetById(long id) throws ServiceException;

    /**
     * Retrieves all series using Criteria.
     *
     * @return A {@link List} of all {@link SerieBO} .
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    List<SerieBO> criteriaGetAll() throws ServiceException;

    /**
     * Deletes a serie by ID using Criteria.
     *
     * @param id
     *         The ID of the serie to delete.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    void criteriaDeleteById(Long id) throws ServiceException;

    /**
     * Updates a serie using Criteria.
     *
     * @param serieBO
     *         The {@link SerieBO} to update.
     * @return The updated {@link SerieBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    SerieBO criteriaUpdate(SerieBO serieBO) throws ServiceException;

    /**
     * Creates a new serie using Criteria.
     *
     * @param serieBO
     *         The {@link SerieBO} to create.
     * @return The created {@link SerieBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    SerieBO criteriaCreate(SerieBO serieBO) throws ServiceException;

    /**
     * Retrieves a serie by ID using JPA.
     *
     * @param id
     *         The ID of the serie to retrieve.
     * @return The retrieved {@link SerieBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    SerieBO jpaGetById(long id) throws ServiceException;

    /**
     * Retrieves all series using JPA.
     *
     * @return A {@link List} of all {@link SerieBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    List<SerieBO> jpaGetAll() throws ServiceException;

    /**
     * Deletes a serie by ID using JPA.
     *
     * @param id
     *         The ID of the serie to delete.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    void jpaDeleteById(Long id) throws ServiceException;

    /**
     * Updates a serie using JPA.
     *
     * @param serieBO
     *         The {@link SerieBO} to update.
     * @return The updated {@link SerieBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    SerieBO jpaUpdate(SerieBO serieBO) throws ServiceException;

    /**
     * Creates a new serie using JPA.
     *
     * @param serieBO
     *         The {@link SerieBO} to create.
     * @return The created {@link SerieBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    SerieBO jpaCreate(SerieBO serieBO) throws ServiceException;
}
