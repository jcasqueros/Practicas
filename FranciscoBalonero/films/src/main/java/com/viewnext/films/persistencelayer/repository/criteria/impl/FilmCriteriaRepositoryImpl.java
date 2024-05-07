package com.viewnext.films.persistencelayer.repository.criteria.impl;

import com.viewnext.films.persistencelayer.entity.Film;
import com.viewnext.films.persistencelayer.repository.criteria.FilmCriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link FilmCriteriaRepository} interface.
 *
 * <p>This class provides a implementation of the CRUD operations for films using JPA Criteria API.</p>
 *
 * @author Francisco Balonero Olivera
 * @see FilmCriteriaRepository
 */
@Repository
public class FilmCriteriaRepositoryImpl implements FilmCriteriaRepository {
    
    /**
     * The entity manager for performing database operations.
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Film createFilm(Film film) {
        entityManager.persist(film);
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);
        Root<Film> root = criteriaQuery.from(Film.class);
        criteriaQuery.select(root);

        TypedQuery<Film> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Optional<Film> getFilmById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);
        Root<Film> root = criteriaQuery.from(Film.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        TypedQuery<Film> query = entityManager.createQuery(criteriaQuery);
        return query.getResultStream().findFirst();
    }

    @Override
    public Film updateFilm(Film film) {
        return entityManager.merge(film);
    }

    @Override
    public void deleteFilm(Long id) {
        Film film = entityManager.find(Film.class, id);
        if (film != null) {
            entityManager.remove(film);
        }
    }
}
