package com.santander.peliculacrud.repository.criteria;

import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.model.entity.Film;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilmCriteriaRepository {

    @Transactional
    Page<Film> findAllFilter(List<String> title, List<Integer> createds, List<Actor> actors, List<Director> directors,
            Pageable pageable);
}
