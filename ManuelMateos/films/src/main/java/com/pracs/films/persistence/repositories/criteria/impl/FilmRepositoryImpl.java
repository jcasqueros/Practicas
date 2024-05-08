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
    public List<Film> findAllFilm() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);

        Root<Film> film = criteriaQuery.from(Film.class);
        criteriaQuery.select(film);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    @Transactional
    public void deleteFilmById(Film film) {
        entityManager.remove(film);
        entityManager.flush();
    }
}
