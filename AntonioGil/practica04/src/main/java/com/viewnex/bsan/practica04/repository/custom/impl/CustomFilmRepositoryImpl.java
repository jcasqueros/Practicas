package com.viewnex.bsan.practica04.repository.custom.impl;

import com.viewnex.bsan.practica04.entity.Film;
import com.viewnex.bsan.practica04.repository.custom.CustomFilmRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code CustomFilmRepository} interface.
 *
 * @author Antonio Gil
 */
@Repository
public class CustomFilmRepositoryImpl implements CustomFilmRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CustomFilmRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Film> getAll() {
        CriteriaQuery<Film> query = criteriaBuilder.createQuery(Film.class);
        Root<Film> films = query.from(Film.class);

        query.select(films);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public boolean existsById(long id) {
        CriteriaQuery<Film> query = criteriaBuilder.createQuery(Film.class);
        Root<Film> films = query.from(Film.class);

        Predicate idMatches = criteriaBuilder.equal(films.get("id"), id);
        query.select(films).where(idMatches);

        try {
            entityManager.createQuery(query).getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public Optional<Film> getById(long id) {
        CriteriaQuery<Film> query = criteriaBuilder.createQuery(Film.class);
        Root<Film> films = query.from(Film.class);

        Predicate idMatches = criteriaBuilder.equal(films.get("id"), id);
        query.select(films).where(idMatches);

        try {
            Film foundEntity = entityManager.createQuery(query).getSingleResult();
            return Optional.of(foundEntity);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Film save(Film film) {
        entityManager.persist(film);
        return film;
    }

    @Override
    public boolean deleteById(long id) {
        CriteriaDelete<Film> query = criteriaBuilder.createCriteriaDelete(Film.class);
        Root<Film> films = query.from(Film.class);

        Predicate idMatches = criteriaBuilder.equal(films.get("id"), id);
        query.where(idMatches);

        int updatedEntityCount = entityManager.createQuery(query).executeUpdate();

        return updatedEntityCount > 0;
    }

}
