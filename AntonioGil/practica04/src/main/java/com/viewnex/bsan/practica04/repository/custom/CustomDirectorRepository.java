package com.viewnex.bsan.practica04.repository.custom;

import com.viewnex.bsan.practica04.entity.Director;

import java.util.List;
import java.util.Optional;

/**
 * The {@code CustomDirectorRepository} interface is a custom repository class that uses the Criteria API to provide
 * data access logic for directors.
 *
 * @author Antonio Gil
 */
public interface CustomDirectorRepository {

    /**
     * Finds all directors registered in the system.
     *
     * @return A list containing all the directors registered in the system
     */
    List<Director> getAll();

    /**
     * Checks whether a director with the given ID exists in the system.
     *
     * @param id The ID for the director
     * @return True if a director with the given ID exists, false otherwise
     */
    boolean existsById(long id);

    /**
     * Finds the director with the given ID, if it exists.
     *
     * @param id The ID for the director
     * @return An {@code Optional} containing the found director, or an empty {@code Optional} if it wasn't found
     */
    Optional<Director> getById(long id);

    /**
     * Saves the given director to the system.
     *
     * @param director The director that shall be saved
     * @return The director as it was saved
     */
    Director save(Director director);

    /**
     * Deletes the director with the given ID.
     *
     * @param id The ID for the director that shall be deleted
     * @return True if a director was deleted, false otherwise
     */
    boolean deleteById(long id);

}
