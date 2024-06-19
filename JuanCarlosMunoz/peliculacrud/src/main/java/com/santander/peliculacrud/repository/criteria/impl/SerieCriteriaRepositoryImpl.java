package com.santander.peliculacrud.repository.criteria.impl;

import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.model.entity.Series;
import com.santander.peliculacrud.repository.criteria.SeriesCriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SerieCriteriaRepositoryImpl implements SeriesCriteriaRepository {

    @PersistenceContext
    EntityManager em;


    @Override
    @Transactional
    public Page<Series> findAllFilter(List<String> title, List<Integer> createds, List<Actor> actors, List<Director> directors,
            Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Series> cq = cb.createQuery(Series.class);


        List<Predicate> predicates = addPredicade(cq,title,createds,actors,directors);
        cq.where(predicates.toArray(new Predicate[0]));

        return getSeriesPage(cq,pageable);
    }

    private List<Predicate> addPredicade(CriteriaQuery<Series> cq, List<String> title, List<Integer> createds, List<Actor> actors,
            List<Director> directors) {
        Root<Series> film = cq.from(Series.class);
        List<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(title)) {
            predicates.add(film.get("title").in(title));
        }


        if (!CollectionUtils.isEmpty(createds)) {
            for (Integer created : createds) {
                if (created > 0) {
                    predicates.add(film.get("created").in(created));
                }
            }
        }
        if (!CollectionUtils.isEmpty(actors)) {
            Join<Series, Actor> actorJoin = film.join("actors", JoinType.INNER);
            predicates.add(actorJoin.in(actors));
        }


        if (!CollectionUtils.isEmpty(directors)) {
            predicates.add(film.get("director").in(directors));

        }

        return predicates;
    }



    private Page<Series> getSeriesPage(CriteriaQuery<Series> cq, Pageable pageable){

        TypedQuery<Series> query = em.createQuery(cq);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Series> films = query.getResultList();

        long total = query.getResultList().size();

        return new PageImpl<>(films, pageable, total);
    }

}
