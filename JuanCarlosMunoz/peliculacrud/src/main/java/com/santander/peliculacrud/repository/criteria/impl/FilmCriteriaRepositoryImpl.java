package com.santander.peliculacrud.repository.criteria.impl;

import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.model.entity.Film;
import com.santander.peliculacrud.model.entity.Series;
import com.santander.peliculacrud.repository.criteria.FilmCriteriaRepository;
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
public class FilmCriteriaRepositoryImpl implements FilmCriteriaRepository {

    @PersistenceContext
    EntityManager em;


    @Override
    @Transactional
    public Page<Film> findAllFilter(List<String> title, List<Integer> createds, List<Actor> actors, List<Director> directors,
            Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Film> cq = cb.createQuery(Film.class);


        List<Predicate> predicates = addPredicade(cq,title,createds,actors,directors);
        cq.where(predicates.toArray(new Predicate[0]));

        return getFilmsPage(cq,pageable);
    }

    private List<Predicate> addPredicade(CriteriaQuery<Film> cq, List<String> title, List<Integer> createds, List<Actor> actors,
            List<Director> directors) {
        Root<Film> film = cq.from(Film.class);
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



    private Page<Film> getFilmsPage(CriteriaQuery<Film> cq, Pageable pageable){

        TypedQuery<Film> query = em.createQuery(cq);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Film> films = query.getResultList();

        long total = query.getResultList().size();

        return new PageImpl<>(films, pageable, total);
    }

}
