package com.santander.peliculacrud.repository.criteria;

import com.santander.peliculacrud.model.entity.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DirectorCriteriaRepository {



    Page<Director> findAllFilter(List<String> name, List<Integer> age, List<String> nation, Pageable pageable);


}
