package com.pracs.films.persistence.repositories.criteria.impl;

import com.pracs.films.persistence.models.*;
import com.pracs.films.persistence.repositories.criteria.SerieCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link SerieCustomRepository}
 *
 * @author Manuel Mateos de Torres
 */
@Repository
public class SerieRepositoryImpl implements SerieCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Serie saveSerie(Serie serie) {
        entityManager.persist(serie);
        return serie;
    }

    @Override
    @Transactional
    public Serie updateSerie(Serie serie) {
        entityManager.merge(serie);
        return serie;
    }

    @Override
    public Optional<Serie> findSerieById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Serie> criteriaQuery = criteriaBuilder.createQuery(Serie.class);

        Root<Serie> serie = criteriaQuery.from(Serie.class);
        Predicate serieIdPredicate = criteriaBuilder.equal(serie.get("id"), id);
        criteriaQuery.where(serieIdPredicate);

        TypedQuery<Serie> query = entityManager.createQuery(criteriaQuery);

        return query.getResultStream().findFirst();
    }

    @Override
    public Page<Serie> findAllSerie(Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Serie> criteriaQuery = criteriaBuilder.createQuery(Serie.class);

        Root<Serie> serie = criteriaQuery.from(Serie.class);
        criteriaQuery.select(serie);

        String sort = String.valueOf(pageable.getSort()).split(":")[0].trim();
        criteriaQuery.orderBy(criteriaBuilder.asc(serie.get(sort)));

        TypedQuery<Serie> query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Serie> series = query.getResultList();

        long totalElements = countAllSeries();

        return new PageImpl<>(series, pageable, totalElements);
    }

    @Override
    public Page<Serie> findAllFilter(Pageable pageable, List<String> titles, List<Integer> ages,
            List<Director> directors, List<Producer> producers, List<Actor> actors) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Serie> query = criteriaBuilder.createQuery(Serie.class);
        Root<Serie> root = query.from(Serie.class);

        List<Predicate> predicates = new ArrayList<>();

        if (titles != null && !titles.isEmpty()) {
            predicates.add(root.get("title").in(titles));
        }

        if (ages != null && !ages.isEmpty()) {
            predicates.add(root.get("debut").in(ages));
        }

        if (directors != null && !directors.isEmpty()) {
            Join<Film, Director> directorJoin = root.join("directors", JoinType.INNER);
            predicates.add(directorJoin.in(directors));
        }

        if (producers != null && !producers.isEmpty()) {
            Join<Film, Producer> producerJoin = root.join("producers", JoinType.INNER);
            predicates.add(producerJoin.in(producers));
        }

        if (actors != null && !actors.isEmpty()) {
            Join<Film, Actor> actorJoin = root.join("actors", JoinType.INNER);
            predicates.add(actorJoin.in(actors));
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        for (Sort.Order order : pageable.getSort()) {
            if (order.getDirection().isAscending()) {
                query.orderBy(criteriaBuilder.asc(root.get(order.getProperty())));
            } else {
                query.orderBy(criteriaBuilder.desc(root.get(order.getProperty())));
            }
        }

        TypedQuery<Serie> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Serie> series = typedQuery.getResultList();

        long totalElements = entityManager.createQuery(query).getResultList().size();

        return new PageImpl<>(series, pageable, totalElements);
    }

    private long countAllSeries() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

        Root<Serie> serie = countQuery.from(Serie.class);
        countQuery.select(criteriaBuilder.count(serie));

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    @Override
    @Transactional
    public void deleteSerieById(Serie serie) {
        entityManager.remove(serie);
        entityManager.flush();
    }
}
