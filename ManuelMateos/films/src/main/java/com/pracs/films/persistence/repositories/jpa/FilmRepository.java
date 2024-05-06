package com.pracs.films.persistence.repositories.jpa;

import com.pracs.films.persistence.models.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
