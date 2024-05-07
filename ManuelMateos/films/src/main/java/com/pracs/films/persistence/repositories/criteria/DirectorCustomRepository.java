package com.pracs.films.persistence.repositories.criteria;

import com.pracs.films.persistence.models.Director;

import java.util.List;
import java.util.Optional;

/**
 * Criteria Repository of the class {@link Director}
 *
 * @author Manuel Mateos de Torres
 */
public interface DirectorCustomRepository {

    /**
     * Method for create a director
     *
     * @param director
     * @return Optional Director
     */
    Director saveDirector(Director director);

    /**
     * Method for update a director
     *
     * @param director
     * @return Optional Director
     */
    Director updateDirector(Director director);

    /**
     * Method for find a director by his id
     *
     * @param id
     * @return Optional Director
     */
    Optional<Director> findDirectorById(long id);

    /**
     * Method for get all directors
     *
     * @return List of Director
     */
    List<Director> findAllDirector();

    /**
     * Method for delete a director by his id
     *
     * @param id
     */
    void deleteDirectorById(long id);
}
