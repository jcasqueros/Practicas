package com.viewnext.films.businesslayer.service;

import com.viewnext.films.businesslayer.bo.FilmBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;

import java.util.List;

/**
 * Service interface containing methods for managing films.
 *
 * <p>This interface declares methods for retrieving, creating, updating, and deleting films using both Criteria
 * and JPA.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // Obtain an instance of FilmService
 * FilmService filmService = // instantiate or inject the implementation
 *
 * // Retrieve an film by ID using Criteria
 * FilmBO film = filmService.criteriaGetById(1L);
 *
 * // Retrieve all films using Criteria
 * List<FilmBO> films = filmService.criteriaGetAll();
 *
 * // Delete an film by ID using Criteria
 * filmService.criteriaDeleteById(1L);
 *
 * // Update an film using Criteria
 * FilmBO updatedFilm = filmService.criteriaUpdate(film);
 *
 * // Create a new film using Criteria
 * FilmBO createdFilm = filmService.criteriaCreate(film);
 *
 * // Retrieve an film by ID using JPA
 * FilmBO film = filmService.jpaGetById(1L);
 *
 * // Retrieve all films using JPA
 * List<FilmBO> films = filmService.jpaGetAll();
 *
 * // Delete an film by ID using JPA
 * filmService.jpaDeleteById(1L);
 *
 * // Update an film using JPA
 * FilmBO updatedFilm = filmService.jpaUpdate(film);
 *
 * // Create a new film using JPA
 * FilmBO createdFilm = filmService.jpaCreate(film);
 * }
 * </pre>
 *
 * <p>Implementing classes should handle {@link ServiceException} and {@link NotFoundException} to appropriately manage
 * exceptions related to business operations on films.</p>
 *
 * <p>Instances of this interface are typically used in the business layer of the application to provide
 * a clean separation between the business logic and persistence layer logic.</p>
 *
 * @author Francisco Balonero Olivera
 */
public interface FilmService {
    /**
     * Retrieves a film by ID using Criteria.
     *
     * @param id
     *         The ID of the film to retrieve.
     * @return The retrieved {@link FilmBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    FilmBO criteriaGetById(long id) throws ServiceException;

    /**
     * Retrieves all films using Criteria, paginated and sorted.
     *
     * @param pageNumber
     *         the page number to retrieve
     * @param pageSize
     *         the number of items per page
     * @param sortBy
     *         the property to sort by
     * @param sortOrder
     *         true for ascending order, false for descending order
     * @return A {@link List} of all {@link FilmBO} matching the pagination and sorting criteria.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    List<FilmBO> criteriaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder) throws ServiceException;

    /**
     * Deletes a film by ID using Criteria.
     *
     * @param id
     *         The ID of the film to delete.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    void criteriaDeleteById(Long id) throws ServiceException;

    /**
     * Updates a film using Criteria.
     *
     * @param filmBO
     *         The {@link FilmBO} to update.
     * @return The updated {@link FilmBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    FilmBO criteriaUpdate(FilmBO filmBO) throws ServiceException;

    /**
     * Creates a new film using Criteria.
     *
     * @param filmBO
     *         The {@link FilmBO} to create.
     * @return The created {@link FilmBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    FilmBO criteriaCreate(FilmBO filmBO) throws ServiceException;

    /**
     * Retrieves a film by ID using JPA.
     *
     * @param id
     *         The ID of the film to retrieve.
     * @return The retrieved {@link FilmBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    FilmBO jpaGetById(long id) throws ServiceException;

    /**
     * Retrieves all films using JPA, paginated and sorted.
     *
     * @param pageNumber
     *         the page number to retrieve
     * @param pageSize
     *         the number of items per page
     * @param sortBy
     *         the property to sort by
     * @param sortOrder
     *         true for ascending order, false for descending order
     * @return A {@link List} of all {@link FilmBO} matching the pagination and sorting criteria.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    List<FilmBO> jpaGetAll(int pageNumber, int pageSize, String sortBy, boolean sortOrder) throws ServiceException;

    /**
     * Deletes a film by ID using JPA.
     *
     * @param id
     *         The ID of the film to delete.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    void jpaDeleteById(Long id) throws ServiceException;

    /**
     * Updates a film using JPA.
     *
     * @param filmBO
     *         The {@link FilmBO} to update.
     * @return The updated {@link FilmBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    FilmBO jpaUpdate(FilmBO filmBO) throws ServiceException;

    /**
     * Creates a new film using JPA.
     *
     * @param filmBO
     *         The {@link FilmBO} to create.
     * @return The created {@link FilmBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    FilmBO jpaCreate(FilmBO filmBO) throws ServiceException;
}
