package com.pracs.films.persistence.repositories.criteria.impl;

import com.pracs.films.persistence.models.Actor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ActorRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    Optional<Actor> findById(long id) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);

        Root<Actor> actor = criteriaQuery.from(Actor.class);
        Predicate actorIdPredicate = criteriaBuilder.equal(actor.get("id"), id);
        criteriaQuery.where(actorIdPredicate);

        TypedQuery<Actor> query = entityManager.createQuery(criteriaQuery);

        return query.getResultStream().findFirst();
    }
}
