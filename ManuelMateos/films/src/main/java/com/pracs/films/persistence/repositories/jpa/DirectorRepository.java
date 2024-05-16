package com.pracs.films.persistence.repositories.jpa;

import com.pracs.films.persistence.models.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository of the class {@link Director}
 *
 * @author Manuel Mateos de Torres
 * @see {@link JpaRepository}
 */
public interface DirectorRepository extends JpaRepository<Director, Long> {

    /**
     * Method for get all directors paginated
     *
     * @param pageable
     * @return
     */
    public Page<Director> findAll(Pageable pageable);

    /**
     * Method for get all directors by his name
     *
     * @param name
     * @return
     */
    public List<Director> findByName(String name);

    /**
     * Method for get actors by name and age
     *
     * @param name
     * @param age
     * @return
     */
    List<Director> findByNameAndAge(String name, int age);
}
