package com.viewnex.bsan.practica04.service;

import com.viewnex.bsan.practica04.bo.ActorBo;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;

import java.util.List;

/**
 * The {@code ActorService} interface is a service class that defines business logic for operations with actors.
 *
 * @author Antonio Gil
 */
public interface ActorService {

    /**
     * Finds all actors registered in the system.
     *
     * @return A list containing all the actors registered in the system
     */
    List<ActorBo> getAll();

    /**
     * Finds all actors registered in the system. This method uses a custom repository implementation instead of the
     * standard Spring Data JPA implementation.
     *
     * @return A list containing all the actors registered in the system
     */
    List<ActorBo> customGetAll();

    /**
     * Finds the actor with the given ID, if it exists.
     *
     * @param id The ID for the actor
     * @return The actor with the given ID
     * @throws ResourceNotFoundException if no actor with the given ID is found
     */
    ActorBo getById(long id);

    /**
     * Finds the actor with the given ID, if it exists. This method uses a custom repository implementation instead of
     * the standard Spring Data JPA implementation.
     *
     * @param id The ID for the actor
     * @return The actor with the given ID
     * @throws ResourceNotFoundException if no actor with the given ID is found
     */
    ActorBo customGetById(long id);

    /**
     * Performs a validation check on the given actor name. If any of the constraints are not met, an exception is
     * thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param name The name to validate, which must not be null or a blank string
     * @throws MissingRequiredFieldException if the name is either null or blank
     */
    void validateName(String name);

    /**
     * Performs a validation check on the given actor age. If any of the constraints are not met, an exception is
     * thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param age The age to validate, which must be positive or zero
     * @throws BadInputDataException if the age is a negative number
     */
    void validateAge(int age);

    /**
     * Performs a validation check on the given actor nationality. If any of the constraints are not met, an exception
     * is thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param nationality The nationality to validate, which must not be null or a blank string
     * @throws MissingRequiredFieldException if the nationality is null or blank
     */
    void validateNationality(String nationality);

    /**
     * Performs a validation check on the given actor data. If any of the constraints are not met, an exception is
     * thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param actor The actor data to validate
     * @throws BadInputDataException if the given actor is null, or either the name or nationality are null/blank, or
     *                               the age is a negative number
     */
    void validateActor(ActorBo actor);

    /**
     * Creates a new actor with the given data, performing validation checks before creating it.
     *
     * @param actor The actor that shall be created
     * @return The actor that was created
     * @throws DuplicateUniqueFieldException if an actor with the given ID already exists
     * @throws BadInputDataException         if the given actor is null, or either the name or nationality are
     *                                       null/blank, or the age is a negative number
     */
    ActorBo create(ActorBo actor);

    /**
     * Creates a new actor with the given data, performing validation checks before saving it. This method uses a
     * custom repository implementation instead of the standard Spring Data JPA implementation.
     *
     * @param actor The actor that shall be created
     * @return The actor that was created
     * @throws DuplicateUniqueFieldException if an actor with the given ID already exists
     * @throws BadInputDataException         if the given actor is null, or either the name or nationality are
     *                                       null/blank, or the age is a negative number
     */
    ActorBo customCreate(ActorBo actor);

    /**
     * Updates the actor with the given ID.
     *
     * @param id       The ID for the actor that shall be updated
     * @param newActor The new data for the actor; this object's {@code id} is ignored for consistency reasons
     * @throws ResourceNotFoundException if no actor with the given ID is found
     * @throws BadInputDataException     if the actor is null, or either the name or nationality are null/blank, or the
     *                                   age is a negative number
     */
    ActorBo update(long id, ActorBo newActor);

    /**
     * Updates the actor with the given ID. This method uses a custom repository implementation instead of the standard
     * Spring Data JPA implementation.
     *
     * @param id       The ID for the actor that shall be updated
     * @param newActor The new data for the actor; this object's {@code id} is ignored for consistency reasons
     * @throws ResourceNotFoundException if no actor with the given ID is found
     * @throws BadInputDataException     if the actor is null, or either the name or nationality are null/blank, or the
     *                                   age is a negative number
     */
    ActorBo customUpdate(long id, ActorBo newActor);

    /**
     * Deletes the actor with the given ID.
     *
     * @param id The ID for the actor that shall be deleted
     * @throws ResourceNotFoundException if no actor with the given ID is found
     */
    void deleteById(long id);

    /**
     * Deletes the actor with the given ID. This method uses a custom repository implementation instead of the standard
     * Spring Data JPA implementation.
     *
     * @param id The ID for the actor that shall be deleted
     * @throws ResourceNotFoundException if no actor with the given ID is found
     */
    void customDeleteById(long id);

}
