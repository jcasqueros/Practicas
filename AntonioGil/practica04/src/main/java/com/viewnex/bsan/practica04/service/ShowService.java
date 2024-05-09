package com.viewnex.bsan.practica04.service;

import com.viewnex.bsan.practica04.bo.ShowBo;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;

import java.util.List;

/**
 * The {@code ShowService} interface is a service class that defines business logic for operations with shows.
 *
 * @author Antonio Gil
 */
public interface ShowService {

    /**
     * Finds all shows registered in the system.
     *
     * @return A list containing all the shows registered in the system
     */
    List<ShowBo> getAll();

    /**
     * Finds all shows registered in the system. This method uses a custom repository implementation instead of the
     * standard Spring Data JPA implementation.
     *
     * @return A list containing all the shows registered in the system
     */
    List<ShowBo> customGetAll();

    /**
     * Finds the show with the given ID, if it exists.
     *
     * @param id The ID for the show
     * @return The show with the given ID
     * @throws ResourceNotFoundException if no show with the given ID is found
     */
    ShowBo getById(long id);

    /**
     * Finds the show with the given ID, if it exists. This method uses a custom repository implementation instead of
     * the standard Spring Data JPA implementation.
     *
     * @param id The ID for the show
     * @return The show with the given ID
     * @throws ResourceNotFoundException if no show with the given ID is found
     */
    ShowBo customGetById(long id);

    /**
     * Performs a validation check on the given show data. If any of the constraints are not met, an exception is
     * thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param title The show title to validate
     * @throws MissingRequiredFieldException if the title is either null or a blank string
     */
    void validateTitle(String title);

    /**
     * Performs a validation check on the given show release year. If any of the constraints are not met, an exception
     * is thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param year The release year to validate
     * @throws BadInputDataException if the year is a negative number
     */
    void validateYear(int year);

    /**
     * Performs a validation check on the given show data. If any of the constraints are not met, an exception is
     * thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param show The show data to validate
     * @throws BadInputDataException if the show is null, or if the title is null/blank, or if the year is a negative
     *                               number
     */
    void validateShow(ShowBo show);

    /**
     * Creates a new show with the given data, performing validation checks before creating it.
     *
     * @param show The show that shall be created
     * @return The show that was created
     * @throws DuplicateUniqueFieldException if a show with the given ID already exists
     * @throws BadInputDataException         if the show is null, or if the title is null/blank, or if the year is a
     *                                       negative number
     */
    ShowBo create(ShowBo show);

    /**
     * Creates a new show with the given data, performing validation checks before saving it. This method uses a
     * custom repository implementation instead of the standard Spring Data JPA implementation.
     *
     * @param show The show that shall be created
     * @return The show that was created
     * @throws DuplicateUniqueFieldException if a show with the given ID already exists
     * @throws BadInputDataException         if the show is null, or if the title is null/blank, or if the year is a
     *                                       negative number
     */
    ShowBo customCreate(ShowBo show);

    /**
     * Updates the show with the given ID.
     *
     * @param id      The ID for the show that shall be updated
     * @param newShow The new data for the show; this object's {@code id} is ignored for consistency reasons
     * @throws ResourceNotFoundException if no show with the given ID is found
     * @throws BadInputDataException     if the show is null, or if the title is null/blank, or if the year is a
     *                                   negative number
     */
    ShowBo update(long id, ShowBo newShow);

    /**
     * Updates the show with the given ID. This method uses a custom repository implementation instead of the standard
     * Spring Data JPA implementation.
     *
     * @param id      The ID for the show that shall be updated
     * @param newShow The new data for the show; this object's {@code id} is ignored for consistency reasons
     * @throws ResourceNotFoundException if no show with the given ID is found
     * @throws BadInputDataException     if the show is null, or if the title is null/blank, or if the year is a
     *                                   negative number
     */
    ShowBo customUpdate(long id, ShowBo newShow);

    /**
     * Deletes the show with the given ID.
     *
     * @param id The ID for the show that shall be deleted
     * @throws ResourceNotFoundException if no show with the given ID is found
     */
    void deleteById(long id);

    /**
     * Deletes the show with the given ID. This method uses a custom repository implementation instead of the standard
     * Spring Data JPA implementation.
     *
     * @param id The ID for the show that shall be deleted
     * @throws ResourceNotFoundException if no show with the given ID is found
     */
    void customDeleteById(long id);

}
