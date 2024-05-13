package com.viewnex.bsan.practica04.repository.custom;

import com.viewnex.bsan.practica04.entity.Show;

import java.util.List;
import java.util.Optional;

/**
 * The {@code CustomShowRepository} interface is a custom repository class that uses the Criteria API to provide
 * data access logic for shows.
 *
 * @author Antonio Gil
 */
public interface CustomShowRepository {

    /**
     * Finds all shows registered in the system.
     *
     * @return A list containing all the shows registered in the system
     */
    List<Show> findAll();

    /**
     * Checks whether a show with the given ID exists in the system.
     *
     * @param id The ID for the show
     * @return True if a show with the given ID exists, false otherwise
     */
    boolean existsById(long id);

    /**
     * Finds the show with the given ID, if it exists.
     *
     * @param id The ID for the show
     * @return An {@code Optional} containing the found show, or an empty {@code Optional} if it wasn't found
     */
    Optional<Show> findById(long id);

    /**
     * Saves the given show to the system.
     *
     * @param show The show that shall be saved
     * @return The show as it was saved
     */
    Show save(Show show);

    /**
     * Deletes the show with the given ID.
     *
     * @param id The ID for the show that shall be deleted
     * @return True if a show was deleted, false otherwise
     */
    boolean deleteById(long id);

}
