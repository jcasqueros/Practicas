package com.viewnex.bsan.practica04.repository.custom.impl;

import com.viewnex.bsan.practica04.entity.Director;
import com.viewnex.bsan.practica04.repository.custom.CustomDirectorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code CustomDirectorRepository} interface.
 *
 * @author Antonio Gil
 */
@Repository
public class CustomDirectorRepositoryImpl implements CustomDirectorRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CustomDirectorRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Director> getAll() {
        CriteriaQuery<Director> query = criteriaBuilder.createQuery(Director.class);
        Root<Director> directors = query.from(Director.class);

        query.select(directors);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public boolean existsById(long id) {
        CriteriaQuery<Director> query = criteriaBuilder.createQuery(Director.class);
        Root<Director> directors = query.from(Director.class);

        Predicate idMatches = criteriaBuilder.equal(directors.get("id"), id);
        query.select(directors).where(idMatches);

        try {
            entityManager.createQuery(query).getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public Optional<Director> getById(long id) {
        CriteriaQuery<Director> query = criteriaBuilder.createQuery(Director.class);
        Root<Director> directors = query.from(Director.class);

        Predicate idMatches = criteriaBuilder.equal(directors.get("id"), id);
        query.select(directors).where(idMatches);

        try {
            Director foundEntity = entityManager.createQuery(query).getSingleResult();
            return Optional.of(foundEntity);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Director save(Director director) {
        entityManager.persist(director);
        return director;
    }

    @Override
    public boolean deleteById(long id) {
        CriteriaDelete<Director> query = criteriaBuilder.createCriteriaDelete(Director.class);
        Root<Director> directors = query.from(Director.class);

        Predicate idMatches = criteriaBuilder.equal(directors.get("id"), id);
        query.where(idMatches);

        int updatedEntityCount = entityManager.createQuery(query).executeUpdate();

        return updatedEntityCount > 0;
    }
}
