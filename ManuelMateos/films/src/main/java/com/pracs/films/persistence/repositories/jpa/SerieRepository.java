package com.pracs.films.persistence.repositories.jpa;

import com.pracs.films.persistence.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}