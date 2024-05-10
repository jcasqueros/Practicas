package com.pracs.films.persistence.repositories.criteria.impl;

import com.pracs.films.persistence.models.Film;
import com.pracs.films.persistence.repositories.criteria.FilmCustomRepository;
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
