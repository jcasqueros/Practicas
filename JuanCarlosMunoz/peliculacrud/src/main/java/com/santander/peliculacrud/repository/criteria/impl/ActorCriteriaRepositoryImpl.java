package com.santander.peliculacrud.repository.criteria.impl;

import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.repository.criteria.ActorCriteriaRepository;
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
public class ActorCriteriaRepositoryImpl implements ActorCriteriaRepository {

    @PersistenceContext
    EntityManager em;


    @Override
    @Transactional
    public Page<Actor> findAllFilter(List<String> name, List<Integer> age, List<String> nation, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Actor> cq = cb.createQuery(Actor.class);


        List<Predicate> predicates = addPredicade(cq,name,age,nation);
        cq.where(predicates.toArray(new Predicate[0]));

        return getActorsPage(cq,pageable);
    }



    private List<Predicate> addPredicade(CriteriaQuery<Actor> cq, List<String> name, List<Integer> age, List<String> nation ) {
        Root<Actor> actor = cq.from(Actor.class);
        List<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(name)) {
            predicates.add(actor.get("name").in(name));
        }

        if (!CollectionUtils.isEmpty(nation)) {
            predicates.add(actor.get("nation").in(nation));
        }
        if (!CollectionUtils.isEmpty(age)) {
            predicates.add(actor.get("age").in(age));
        }
        return predicates;
    }



    private Page<Actor> getActorsPage(CriteriaQuery<Actor> cq, Pageable pageable){

        TypedQuery<Actor> query = em.createQuery(cq);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Actor> actors = query.getResultList();

        long total = query.getResultList().size();

        return new PageImpl<>(actors, pageable, total);
    }
}
