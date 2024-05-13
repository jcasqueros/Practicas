package com.pracs.films.persistence.repositories.criteria.impl;

import com.pracs.films.persistence.models.Actor;
import com.pracs.films.persistence.models.Director;
import com.pracs.films.persistence.models.Film;
import com.pracs.films.persistence.models.Producer;
import com.pracs.films.persistence.repositories.criteria.FilmCustomRepository;
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
 * Implementation of {@link FilmCustomRepository}
 *
 * @author Manuel Mateos de Torres
 */
@Repository
public class FilmRepositoryImpl implements FilmCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Film saveFilm(Film film) {
        entityManager.persist(film);
        return film;
    }

    @Override
    @Transactional
    public Film updateFilm(Film film) {
        entityManager.merge(film);
        return film;
    }

    @Override
    public Optional<Film> findFilmById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);

        Root<Film> film = criteriaQuery.from(Film.class);
        Predicate filmIdPredicate = criteriaBuilder.equal(film.get("id"), id);
        criteriaQuery.where(filmIdPredicate);

        TypedQuery<Film> query = entityManager.createQuery(criteriaQuery);

        return query.getResultStream().findFirst();
    }

    @Override
    public Page<Film> findAllFilm(Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);

        Root<Film> film = criteriaQuery.from(Film.class);
        criteriaQuery.select(film);

        String sort = String.valueOf(pageable.getSort()).split(":")[0].trim();
        criteriaQuery.orderBy(criteriaBuilder.asc(film.get(sort)));

        TypedQuery<Film> query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Film> films = query.getResultList();

        long totalElements = countAllFilms();

        return new PageImpl<>(films, pageable, totalElements);
    }

    @Override
    public Page<Film> findAllFilter(Pageable pageable, List<String> titles, List<Integer> ages,
            List<Director> directors, List<Producer> producers, List<Actor> actors) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Film> query = criteriaBuilder.createQuery(Film.class);
        Root<Film> root = query.from(Film.class);

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

        TypedQuery<Film> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Film> films = typedQuery.getResultList();

        long totalElements = entityManager.createQuery(query).getResultList().size();

        return new PageImpl<>(films, pageable, totalElements);
    }

    private long countAllFilms() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

        Root<Film> film = countQuery.from(Film.class);
        countQuery.select(criteriaBuilder.count(film));

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    @Override
    @Transactional
    public void deleteFilmById(Film film) {
        entityManager.remove(film);
        entityManager.flush();
    }
}
