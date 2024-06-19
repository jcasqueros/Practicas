package com.santander.peliculacrud.repository.criteria;

import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.model.entity.Series;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SeriesCriteriaRepository {

    @Transactional
    Page<Series> findAllFilter(List<String> title, List<Integer> created, List<Actor> actors, List<Director> director,
            Pageable pageable);
}
