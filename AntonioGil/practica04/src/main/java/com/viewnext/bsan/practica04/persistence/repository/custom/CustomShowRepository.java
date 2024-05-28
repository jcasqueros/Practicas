package com.viewnext.bsan.practica04.persistence.repository.custom;

import com.viewnext.bsan.practica04.persistence.entity.Show;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * The {@code CustomShowRepository} interface is a custom repository class that uses the Criteria API to provide
 * data access logic for shows.
 *
 * @author Antonio Gil
 */
public interface CustomShowRepository {

    List<Show> findAll(Pageable pageable);

    List<Show> findAll(Specification<Show> spec, Pageable pageable);

    boolean existsById(long id);

    Optional<Show> findById(long id);

    Show save(Show show);

    boolean deleteById(long id);

}
