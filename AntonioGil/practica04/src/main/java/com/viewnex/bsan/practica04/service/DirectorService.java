package com.viewnex.bsan.practica04.service;

import com.viewnex.bsan.practica04.bo.DirectorBo;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * The {@code DirectorService} interface is a service class that defines business logic for operations with directors.
 *
 * @author Antonio Gil
 */
public interface DirectorService {

    /**
     * Finds all directors registered in the system.
     *
     * @return A list containing all the directors registered in the system
     */
    List<DirectorBo> getAll();

    /**
     * Finds all directors registered in the system. This method uses a custom repository implementation instead of the
     * standard Spring Data JPA implementation.
     *
     * @return A list containing all the directors registered in the system
     */
    List<DirectorBo> customGetAll();

    /**
     * Finds the director with the given ID, if it exists.
     *
     * @param id The ID for the director
     * @return The director with the given ID
     * @throws ResourceNotFoundException if no director with the given ID is found
     */
    DirectorBo getById(long id);

    /**
     * Finds the director  with the given ID, if it exists. This method uses a custom repository implementation instead
     * of the standard Spring Data JPA implementation.
     *
     * @param id The ID for the director
     * @return The director with the given ID
     * @throws ResourceNotFoundException if no director with the given ID is found
     */
    DirectorBo customGetById(long id);

    /**
     * Performs a validation check on the given director name. If any of the constraints are not met, an exception is
     * thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param name The name to validate, which must not be null or a blank string
     * @throws MissingRequiredFieldException if the name is either null or blank
     */
    void validateName(String name);

    /**
     * Performs a validation check on the given director age. If any of the constraints are not met, an exception is
     * thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param age The age to validate, which must be positive or zero
     * @throws BadInputDataException if the age is a negative number
     */
    void validateAge(int age);

    /**
     * Performs a validation check on the given director nationality. If any of the constraints are not met, an
     * exception is thrown. The specific exception class that will be thrown depends on the validation check that fails,
     * but it is guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param nationality The nationality to validate, which must not be null or a blank string
     * @throws MissingRequiredFieldException if the nationality is null or blank
     */
    void validateNationality(String nationality);

    /**
     * Performs a validation check on the given director data. If any of the constraints are not met, an exception is
     * thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param director The director data to validate
     * @throws BadInputDataException if either the name or nationality are null/blank, or the age is a negative number
     */
    void validateDirector(DirectorBo director);

    /**
     * Creates a new director with the given data, performing validation checks before creating it.
     *
     * @param director The director that shall be created
     * @return The director that was created
     * @throws DuplicateUniqueFieldException if a director with the given ID already exists
     * @throws BadInputDataException         if the given director is null, or either the name or nationality are
     *                                       null/blank, or the age is a negative number
     */
    DirectorBo create(DirectorBo director);

    /**
     * Creates a new director with the given data, performing validation checks before saving it. This method uses a
     * custom repository implementation instead of the standard Spring Data JPA implementation.
     *
     * @param director The director that shall be created
     * @return The director that was created
     * @throws DuplicateUniqueFieldException if a director with the given ID already exists
     * @throws BadInputDataException         if the given director is null, or either the name or nationality are
     *                                       null/blank, or the age is a negative number
     */
    DirectorBo customCreate(DirectorBo director);

    /**
     * Updates the director with the given ID.
     *
     * @param id          The ID for the director that shall be updated
     * @param newDirector The new data for the director; this object's {@code id} is ignored for consistency reasons
     * @throws ResourceNotFoundException if no director with the given ID is found
     * @throws BadInputDataException     if the given director is null, or either the name or nationality are
     *                                   null/blank, or the age is a negative number
     */
    DirectorBo update(long id, DirectorBo newDirector);

    /**
     * Updates the director with the given ID. This method uses a custom repository implementation instead of the
     * standard Spring Data JPA implementation.
     *
     * @param id          The ID for the director that shall be updated
     * @param newDirector The new data for the director; this object's {@code id} is ignored for consistency reasons
     * @throws ResourceNotFoundException if no director with the given ID is found
     * @throws BadInputDataException     if the given director is null, or either the name or nationality are
     *                                   null/blank, or the age is a negative number
     */
    DirectorBo customUpdate(long id, DirectorBo newDirector);

    /**
     * Deletes the director with the given ID.
     *
     * @param id The ID for the director that shall be deleted
     * @throws ResourceNotFoundException if no director with the given ID is found
     */
    void deleteById(long id);

    /**
     * Deletes the director with the given ID. This method uses a custom repository implementation instead of the
     * standard Spring Data JPA implementation.
     *
     * @param id The ID for the director that shall be deleted
     * @throws ResourceNotFoundException if no director with the given ID is found
     */
    void customDeleteById(long id);

}
