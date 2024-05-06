package com.pracs.films.persistence.repositories.criteria.impl;

import com.pracs.films.persistence.models.Actor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class ActorRepositoryImpl {

    EntityManager em;

    Actor findById(long id) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Actor> cq = cb.createQuery(Actor.class);

        Root<Actor> actor = cq.from(Actor.class);
        Predicate actorIdPredicate = cb.equal(actor.get("id"), id);
        cq.where(actorIdPredicate);

        TypedQuery<Actor> query = em.createQuery(cq);

        return query.getSingleResult();
    }
}
