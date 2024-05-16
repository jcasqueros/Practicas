package com.pracs.films.persistence.repositories.criteria;

import com.pracs.films.persistence.models.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Method for get director by name and age
     *
     * @param name
     * @param age
     * @return List of actor
     */
    List<Director> findByNameAndAge(String name, int age);

    /**
     * Method for get all directors paginated
     *
     * @return List of Director
     */
    Page<Director> findAllDirector(Pageable pageable);

    /**
     * Method for get all directors by differents atribbutes
     *
     * @param pageable
     * @param names
     * @param ages
     * @param nationalities
     * @return
     */
    Page<Director> findAllFilter(Pageable pageable, List<String> names, List<Integer> ages, List<String> nationalities);

    /**
     * Method for delete a director by his id
     *
     * @param director
     */
    void deleteDirectorById(Director director);
}
