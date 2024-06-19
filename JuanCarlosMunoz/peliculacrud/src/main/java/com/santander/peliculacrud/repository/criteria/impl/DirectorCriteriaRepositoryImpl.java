package com.santander.peliculacrud.repository.criteria.impl;

import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.repository.criteria.DirectorCriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DirectorCriteriaRepositoryImpl implements DirectorCriteriaRepository {

    @PersistenceContext
    EntityManager em;


    @Override
    @Transactional
    public Page<Director> findAllFilter(List<String> name, List<Integer> age, List<String> nation, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Director> cq = cb.createQuery(Director.class);


        List<Predicate> predicates = addPredicade(cq,name,age,nation);
        cq.where(predicates.toArray(new Predicate[0]));

        return getDirectorsPage(cq,pageable);
    }



    private List<Predicate> addPredicade(CriteriaQuery<Director> cq, List<String> name, List<Integer> age, List<String> nation ) {
        Root<Director> director = cq.from(Director.class);
        List<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(name)) {
            predicates.add(director.get("name").in(name));
        }

        if (!CollectionUtils.isEmpty(nation)) {
            predicates.add(director.get("nation").in(nation));
        }
        if (!CollectionUtils.isEmpty(age)) {
            predicates.add(director.get("age").in(age));
        }
        return predicates;
    }



    private Page<Director> getDirectorsPage(CriteriaQuery<Director> cq, Pageable pageable){

        TypedQuery<Director> query = em.createQuery(cq);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Director> directors = query.getResultList();

        long total = query.getResultList().size();

        return new PageImpl<>(directors, pageable, total);
    }
}
