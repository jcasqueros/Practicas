package com.viewnex.bsan.practica04.repository.custom.impl;

import com.viewnex.bsan.practica04.entity.Actor;
import com.viewnex.bsan.practica04.repository.custom.CustomActorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomActorRepositoryImpl implements CustomActorRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CustomActorRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Actor> getAll() {
        CriteriaQuery<Actor> query = criteriaBuilder.createQuery(Actor.class);
        Root<Actor> actors = query.from(Actor.class);

        query.select(actors);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public boolean existsById(long id) {
        CriteriaQuery<Actor> query = criteriaBuilder.createQuery(Actor.class);
        Root<Actor> actors = query.from(Actor.class);

        Predicate idMatches = criteriaBuilder.equal(actors.get("id"), id);
        query.select(actors).where(idMatches);

        try {
            entityManager.createQuery(query).getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public Optional<Actor> getById(long id) {
        CriteriaQuery<Actor> query = criteriaBuilder.createQuery(Actor.class);
        Root<Actor> actors = query.from(Actor.class);

        Predicate idMatches = criteriaBuilder.equal(actors.get("id"), id);
        query.select(actors).where(idMatches);

        try {
            Actor foundEntity = entityManager.createQuery(query).getSingleResult();
            return Optional.of(foundEntity);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Actor save(Actor actor) {
        entityManager.persist(actor);
        return actor;
    }

    @Override
    public boolean deleteById(long id) {
        CriteriaDelete<Actor> query = criteriaBuilder.createCriteriaDelete(Actor.class);
        Root<Actor> actors = query.from(Actor.class);

        Predicate idMatches = criteriaBuilder.equal(actors.get("id"), id);
        query.where(idMatches);

        int updatedEntityCount = entityManager.createQuery(query).executeUpdate();

        return updatedEntityCount > 0;
    }

}
