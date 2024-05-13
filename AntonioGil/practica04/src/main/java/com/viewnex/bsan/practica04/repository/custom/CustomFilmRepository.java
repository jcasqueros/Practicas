package com.viewnex.bsan.practica04.repository.custom;

import com.viewnex.bsan.practica04.entity.Film;

import java.util.List;
import java.util.Optional;

/**
 * The {@code CustomFilmRepository} interface is a custom repository class that uses the Criteria API to provide
 * data access logic for films.
 *
 * @author Antonio Gil
 */
public interface CustomFilmRepository {

    /**
     * Finds all films registered in the system.
     *
     * @return A list containing all the films registered in the system
     */
    List<Film> findAll();

    /**
     * Checks whether a film with the given ID exists in the system.
     *
     * @param id The ID for the film
     * @return True if a film with the given ID exists, false otherwise
     */
    boolean existsById(long id);

    /**
     * Finds the film with the given ID, if it exists.
     *
     * @param id The ID for the film
     * @return An {@code Optional} containing the found film, or an empty {@code Optional} if it wasn't found
     */
    Optional<Film> findById(long id);

    /**
     * Saves the given film to the system.
     *
     * @param film The film that shall be saved
     * @return The film as it was saved
     */
    Film save(Film film);

    /**
     * Deletes the film with the given ID.
     *
     * @param id The ID for the film that shall be deleted
     * @return True if a film was deleted, false otherwise
     */
    boolean deleteById(long id);

}
