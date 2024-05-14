package com.viewnext.bsan.practica04.repository.custom;

import com.viewnext.bsan.practica04.entity.Actor;
import com.viewnext.bsan.practica04.entity.Film;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * The {@code CustomFilmRepository} interface is a custom repository class that uses the Criteria API to provide
 * data access logic for films.
 *
 * @author Antonio Gil
 */
public interface CustomFilmRepository {

    List<Film> findAll(Pageable pageable);

    List<Film> findAll(Specification<Film> spec, Pageable pageable);

    boolean existsById(long id);

    Optional<Film> findById(long id);

    Film save(Film film);

    boolean deleteById(long id);

}
