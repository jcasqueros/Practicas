package com.viewnex.bsan.practica04.repository.custom;

import com.viewnex.bsan.practica04.entity.ProductionCompany;

import java.util.List;
import java.util.Optional;

/**
 * The {@code CustomProductionCompanyRepository} interface is a custom repository class that uses the Criteria API to
 * provide data access logic for production companies.
 *
 * @author Antonio Gil
 */
public interface CustomProductionCompanyRepository {

    /**
     * Finds all production companies registered in the system.
     *
     * @return A list containing all the companies registered in the system
     */
    List<ProductionCompany> getAll();

    /**
     * Checks whether a production company with the given ID exists in the system.
     *
     * @param id The ID for the company
     * @return True if a company with the given ID exists, false otherwise
     */
    boolean existsById(long id);

    /**
     * Finds the production company with the given ID, if it exists.
     *
     * @param id The ID for the company
     * @return An {@code Optional} containing the found company, or an empty {@code Optional} if it wasn't found
     */
    Optional<ProductionCompany> getById(long id);

    /**
     * Saves the given production company to the system.
     *
     * @param company The company that shall be saved
     * @return The company as it was saved
     */
    ProductionCompany save(ProductionCompany company);

    /**
     * Deletes the production company with the given ID.
     *
     * @param id The ID for the company that shall be deleted
     * @return True if a company was deleted, false otherwise
     */
    boolean deleteById(long id);

}
