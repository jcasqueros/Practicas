package com.viewnext.bsan.practica04.persistence.repository;

import com.viewnext.bsan.practica04.persistence.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * The {@code FilmRepository} class is a JPA repository class that provides data access logic for actors.
 *
 * @author Antonio Gil
 */
@Repository
public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {
}