package com.pracs.films.persistence.repositories.jpa;

import com.pracs.films.persistence.models.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository of the class {@link Actor}
 *
 * @author Manuel Mateos de Torres
 * @see {@link JpaRepository}
 */
@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    /**
     * Method for get all actors paginated
     *
     * @param pageable
     * @return
     */
    public Page<Actor> findAll(Pageable pageable);

    /**
     * Method for get all actors by his name
     *
     * @param name
     * @return
     */
    public List<Actor> findByName(String name);

    /**
     * Method for get actors by name and age
     *
     * @param name
     * @param age
     * @return
     */
    List<Actor> findByNameAndAge(String name, int age);
}
