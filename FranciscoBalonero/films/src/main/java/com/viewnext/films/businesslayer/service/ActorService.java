package com.viewnext.films.businesslayer.service;

import com.viewnext.films.businesslayer.bo.ActorBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;

import java.util.List;

/**
 * Service interface containing methods for managing actors.
 *
 * <p>This interface declares methods for retrieving, creating, updating, and deleting actors using both Criteria and
 * JPA.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // Obtain an instance of ActorService
 * ActorService actorService = // instantiate or inject the implementation
 *
 * // Retrieve an actor by ID using Criteria
 * ActorBO actor = actorService.criteriaGetById(1L);
 *
 * // Retrieve all actors using Criteria
 * List<ActorBO> actors = actorService.criteriaGetAll();
 *
 * // Delete an actor by ID using Criteria
 * actorService.criteriaDeleteById(1L);
 *
 * // Update an actor using Criteria
 * ActorBO updatedActor = actorService.criteriaUpdate(actor);
 *
 * // Create a new actor using Criteria
 * ActorBO createdActor = actorService.criteriaCreate(actor);
 *
 * // Retrieve an actor by ID using JPA
 * ActorBO actor = actorService.jpaGetById(1L);
 *
 * // Retrieve all actors using JPA
 * List<ActorBO> actors = actorService.jpaGetAll();
 *
 * // Delete an actor by ID using JPA
 * actorService.jpaDeleteById(1L);
 *
 * // Update an actor using JPA
 * ActorBO updatedActor = actorService.jpaUpdate(actor);
 *
 * // Create a new actor using JPA
 * ActorBO createdActor = actorService.jpaCreate(actor);
 * }
 * </pre>
 *
 * <p>Implementing classes should handle {@link ServiceException} and {@link NotFoundException} to appropriately manage
 * exceptions related to business operations on actors.</p>
 *
 * <p>Instances of this interface are typically used in the business layer of the application to provide
 * a clean separation between the business logic and persistence layer logic.</p>
 *
 * @author Francisco Balonero Olivera
 */
public interface ActorService {
    /**
     * Retrieves an actor by ID using Criteria.
     *
     * @param id
     *         The ID of the actor to retrieve.
     * @return The retrieved {@link ActorBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    ActorBO criteriaGetById(long id) throws ServiceException;

    /**
     * Retrieves all actors using Criteria.
     *
     * @return A {@link List} of all {@link ActorBO} .
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    List<ActorBO> criteriaGetAll() throws ServiceException;

    /**
     * Deletes an actor by ID using Criteria.
     *
     * @param id
     *         The ID of the actor to delete.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    void criteriaDeleteById(Long id) throws ServiceException;

    /**
     * Updates an actor using Criteria.
     *
     * @param actorBO
     *         The {@link ActorBO} to update.
     * @return The updated {@link ActorBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    ActorBO criteriaUpdate(ActorBO actorBO) throws ServiceException;

    /**
     * Creates a new actor using Criteria.
     *
     * @param actorBO
     *         The {@link ActorBO} to create.
     * @return The created {@link ActorBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    ActorBO criteriaCreate(ActorBO actorBO) throws ServiceException;

    /**
     * Retrieves an actor by ID using JPA.
     *
     * @param id
     *         The ID of the actor to retrieve.
     * @return The retrieved {@link ActorBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    ActorBO jpaGetById(long id) throws ServiceException;

    /**
     * Retrieves all actors using JPA.
     *
     * @return A {@link List} of all {@link ActorBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    List<ActorBO> jpaGetAll() throws ServiceException;

    /**
     * Deletes an actor by ID using JPA.
     *
     * @param id
     *         The ID of the actor to delete.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    void jpaDeleteById(Long id) throws ServiceException;

    /**
     * Updates an actor using JPA.
     *
     * @param actorBO
     *         The {@link ActorBO} to update.
     * @return The updated {@link ActorBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    ActorBO jpaUpdate(ActorBO actorBO) throws ServiceException;

    /**
     * Creates a new actor using JPA.
     *
     * @param actorBO
     *         The {@link ActorBO} to create.
     * @return The created {@link ActorBO}.
     * @throws ServiceException
     *         If an error occurs during the operation.
     */
    ActorBO jpaCreate(ActorBO actorBO) throws ServiceException;
}
