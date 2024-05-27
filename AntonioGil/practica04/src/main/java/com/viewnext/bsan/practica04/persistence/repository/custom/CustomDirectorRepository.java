package com.viewnext.bsan.practica04.persistence.repository.custom;

import com.viewnext.bsan.practica04.persistence.entity.Director;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * The {@code CustomDirectorRepository} interface is a custom repository class that uses the Criteria API to provide
 * data access logic for directors.
 *
 * @author Antonio Gil
 */
public interface CustomDirectorRepository {

    List<Director> findAll(Pageable pageable);

    List<Director> findAll(Specification<Director> spec, Pageable pageable);

    boolean existsById(long id);

    Optional<Director> findById(long id);

    Director save(Director director);

    boolean deleteById(long id);

}
