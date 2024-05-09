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

    List<Show> getAll();

    boolean existsById(long id);

    Optional<Show> getById(long id);

    Show save(Show show);

    boolean deleteById(long id);

}
