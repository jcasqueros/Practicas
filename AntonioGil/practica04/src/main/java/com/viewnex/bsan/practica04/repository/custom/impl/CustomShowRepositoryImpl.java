package com.viewnex.bsan.practica04.repository.custom.impl;

import com.viewnex.bsan.practica04.entity.Show;
import com.viewnex.bsan.practica04.repository.custom.CustomShowRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code CustomShowRepository} interface.
 *
 * @author Antonio Gil
 */
@Repository
public class CustomShowRepositoryImpl implements CustomShowRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CustomShowRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Show> getAll() {
        CriteriaQuery<Show> query = criteriaBuilder.createQuery(Show.class);
        Root<Show> shows = query.from(Show.class);

        query.select(shows);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public boolean existsById(long id) {
        CriteriaQuery<Show> query = criteriaBuilder.createQuery(Show.class);
        Root<Show> shows = query.from(Show.class);

        Predicate idMatches = criteriaBuilder.equal(shows.get("id"), id);
        query.select(shows).where(idMatches);

        try {
            entityManager.createQuery(query).getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public Optional<Show> getById(long id) {
        CriteriaQuery<Show> query = criteriaBuilder.createQuery(Show.class);
        Root<Show> shows = query.from(Show.class);

        Predicate idMatches = criteriaBuilder.equal(shows.get("id"), id);
        query.select(shows).where(idMatches);

        try {
            Show foundEntity = entityManager.createQuery(query).getSingleResult();
            return Optional.of(foundEntity);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Show save(Show show) {
        entityManager.persist(show);
        return show;
    }

    @Override
    public boolean deleteById(long id) {
        CriteriaDelete<Show> query = criteriaBuilder.createCriteriaDelete(Show.class);
        Root<Show> shows = query.from(Show.class);

        Predicate idMatches = criteriaBuilder.equal(shows.get("id"), id);
        query.where(idMatches);

        int updatedEntityCount = entityManager.createQuery(query).executeUpdate();

        return updatedEntityCount > 0;
    }

}
