package com.viewnex.bsan.practica04.service;

import com.viewnex.bsan.practica04.bo.FilmBo;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;

import java.util.List;

/**
 * The {@code FilmService} interface is a service class that defines business logic for operations with films.
 *
 * @author Antonio Gil
 */
public interface FilmService {

    /**
     * Finds all films registered in the system.
     *
     * @return A list containing all the films registered in the system
     */
    List<FilmBo> getAll();

    /**
     * Finds all films registered in the system. This method uses a custom repository implementation instead of the
     * standard Spring Data JPA implementation.
     *
     * @return A list containing all the films registered in the system
     */
    List<FilmBo> customGetAll();

    /**
     * Finds the film with the given ID, if it exists.
     *
     * @param id The ID for the film
     * @return The film with the given ID
     * @throws ResourceNotFoundException if no film with the given ID is found
     */
    FilmBo getById(long id);

    /**
     * Finds the film with the given ID, if it exists. This method uses a custom repository implementation instead of
     * the standard Spring Data JPA implementation.
     *
     * @param id The ID for the film
     * @return The film with the given ID
     * @throws ResourceNotFoundException if no film with the given ID is found
     */
    FilmBo customGetById(long id);

    /**
     * Performs a validation check on the given film title. If any of the constraints are not met, an exception is
     * thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param title The film title to validate
     * @throws MissingRequiredFieldException if the title is either null or a blank string
     */
    void validateTitle(String title);

    /**
     * Performs a validation check on the given film title. If any of the constraints are not met, an exception is
     * thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param year The film release year to validate
     * @throws BadInputDataException if the year is a negative number
     */
    void validateYear(int year);

    /**
     * Performs a validation check on the given film data. If any of the constraints are not met, an exception is
     * thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param film The film data to validate
     * @throws BadInputDataException if the given film is null, or the title is null/blank, or the year is a negative
     *                               number
     */
    void validateFilm(FilmBo film);

    /**
     * Creates a new film with the given data, performing validation checks before creating it.
     *
     * @param film The film that shall be created
     * @return The film that was created
     * @throws DuplicateUniqueFieldException if a film with the given ID already exists
     * @throws BadInputDataException         if the given film is null, or the title is null/blank, or the year is a
     *                                       negative number
     */
    FilmBo create(FilmBo film);

    /**
     * Creates a new film with the given data, performing validation checks before saving it. This method uses a
     * custom repository implementation instead of the standard Spring Data JPA implementation.
     *
     * @param film The film that shall be created
     * @return The film that was created
     * @throws DuplicateUniqueFieldException if a film with the given ID already exists
     * @throws BadInputDataException         if the given film is null, or the title is null/blank, or the year is a
     *                                       negative number
     */
    FilmBo customCreate(FilmBo film);

    /**
     * Updates the film with the given ID.
     *
     * @param id      The ID for the film that shall be updated
     * @param newFilm The new data for the film; this object's {@code id} is ignored for consistency reasons
     * @throws ResourceNotFoundException if no film with the given ID is found
     * @throws BadInputDataException     if the given film is null, or the title is null/blank, or the year is a
     *                                   negative number
     */
    FilmBo update(long id, FilmBo newFilm);

    /**
     * Updates the film with the given ID. This method uses a custom repository implementation instead of the standard
     * Spring Data JPA implementation.
     *
     * @param id      The ID for the film that shall be updated
     * @param newFilm The new data for the film; this object's {@code id} is ignored for consistency reasons
     * @throws ResourceNotFoundException if no film with the given ID is found
     * @throws BadInputDataException     if the given film is null, or the title is null/blank, or the year is a
     *                                   negative number
     */
    FilmBo customUpdate(long id, FilmBo newFilm);

    /**
     * Deletes the film with the given ID.
     *
     * @param id The ID for the film that shall be deleted
     * @throws ResourceNotFoundException if no film with the given ID is found
     */
    void deleteById(long id);

    /**
     * Deletes the film with the given ID. This method uses a custom repository implementation instead of the standard
     * Spring Data JPA implementation.
     *
     * @param id The ID for the film that shall be deleted
     * @throws ResourceNotFoundException if no film with the given ID is found
     */
    void customDeleteById(long id);

}
