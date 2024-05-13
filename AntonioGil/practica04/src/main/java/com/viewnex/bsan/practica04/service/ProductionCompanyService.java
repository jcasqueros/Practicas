package com.viewnex.bsan.practica04.service;

import com.viewnex.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnex.bsan.practica04.exception.service.BadInputDataException;
import com.viewnex.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnex.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnex.bsan.practica04.exception.service.ResourceNotFoundException;

import java.util.List;

/**
 * The {@code ProductionCompanyService} interface is a service class that defines business logic for operations with
 * production companies.
 *
 * @author Antonio Gil
 */
public interface ProductionCompanyService {

    /**
     * Finds all companies registered in the system.
     *
     * @return A list containing all the companies registered in the system
     */
    List<ProductionCompanyBo> getAll();

    /**
     * Finds all companies registered in the system. This method uses a custom repository implementation instead of the
     * standard Spring Data JPA implementation.
     *
     * @return A list containing all the companies registered in the system
     */
    List<ProductionCompanyBo> customGetAll();

    /**
     * Finds the company with the given ID, if it exists.
     *
     * @param id The ID for the company
     * @return The company with the given ID
     * @throws ResourceNotFoundException if no company with the given ID is found
     */
    ProductionCompanyBo getById(long id);

    /**
     * Finds the company with the given ID, if it exists. This method uses a custom repository implementation instead of
     * the standard Spring Data JPA implementation.
     *
     * @param id The ID for the company
     * @return The company with the given ID
     * @throws ResourceNotFoundException if no company with the given ID is found
     */
    ProductionCompanyBo customGetById(long id);

    /**
     * Performs a validation check on the given company name. If any of the constraints are not met, an exception is
     * thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param name The company name to validate
     * @throws MissingRequiredFieldException if the name is either null or a blank string
     */
    void validateName(String name);

    /**
     * Performs a validation check on the given company data. If any of the constraints are not met, an exception is
     * thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param year The year to validate
     * @throws BadInputDataException if the year is a negative number
     */
    void validateYear(int year);

    /**
     * Performs a validation check on the given company data. If any of the constraints are not met, an exception is
     * thrown. The specific exception class that will be thrown depends on the validation check that fails, but it is
     * guaranteed that all thrown exceptions will be a {@code BadInputDataException} or any of its subclasses.
     *
     * @param company The company data to validate
     * @throws BadInputDataException if the given company is null, or if the title is null/blank, or if the year is a
     *                               negative number
     */
    void validateCompany(ProductionCompanyBo company);

    /**
     * Creates a new company with the given data, performing validation checks before creating it.
     *
     * @param company The company that shall be created
     * @return The company that was created
     * @throws DuplicateUniqueFieldException if a company with the given ID already exists
     * @throws BadInputDataException         if the given company is null, or if the title is null/blank, or if the year
     *                                       is a negative number
     */
    ProductionCompanyBo create(ProductionCompanyBo company);

    /**
     * Creates a new company with the given data, performing validation checks before saving it. This method uses a
     * custom repository implementation instead of the standard Spring Data JPA implementation.
     *
     * @param company The company that shall be created
     * @return The company that was created
     * @throws DuplicateUniqueFieldException if a company with the given ID already exists
     * @throws BadInputDataException         if the given company is null, or if the title is null/blank, or if the year
     *                                       is a negative number
     */
    ProductionCompanyBo customCreate(ProductionCompanyBo company);

    /**
     * Updates the company with the given ID.
     *
     * @param id         The ID for the company that shall be updated
     * @param newCompany The new data for the company; this object's {@code id} is ignored for consistency reasons
     * @throws ResourceNotFoundException if no company with the given ID is found
     * @throws BadInputDataException     if the given company is null, or if the title is null/blank, or if the year is
     *                                   a negative number
     */
    ProductionCompanyBo update(long id, ProductionCompanyBo newCompany);

    /**
     * Updates the company with the given ID. This method uses a custom repository implementation instead of the
     * standard Spring Data JPA implementation.
     *
     * @param id         The ID for the company that shall be updated
     * @param newCompany The new data for the company; this object's {@code id} is ignored for consistency reasons
     * @throws ResourceNotFoundException if no company with the given ID is found
     * @throws BadInputDataException     if the given company is null, or if the title is null/blank, or if the year is
     *                                   a negative number
     */
    ProductionCompanyBo customUpdate(long id, ProductionCompanyBo newCompany);

    /**
     * Deletes the company with the given ID.
     *
     * @param id The ID for the company that shall be deleted
     * @throws ResourceNotFoundException if no company with the given ID is found
     */
    void deleteById(long id);

    /**
     * Deletes the company with the given ID. This method uses a custom repository implementation instead of the
     * standard Spring Data JPA implementation.
     *
     * @param id The ID for the company that shall be deleted
     * @throws ResourceNotFoundException if no company with the given ID is found
     */
    void customDeleteById(long id);

}
