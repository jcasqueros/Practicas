package com.pracs.films.persistence.repositories.criteria.impl;

import com.pracs.films.persistence.models.Actor;
import com.pracs.films.persistence.repositories.criteria.ActorCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link ActorCustomRepository}
 *
 * @author Manuel Mateos de Torres
 */
@Repository
public class ActorRepositoryImpl implements ActorCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Actor saveActor(Actor actor) {
        entityManager.persist(actor);
        entityManager.flush();
        return actor;
    }

    @Override
    public Actor updateActor(Actor actor) {
        entityManager.merge(actor);
        entityManager.flush();
        return actor;
    }

    @Override
    public Optional<Actor> findActorById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);

        Root<Actor> actor = criteriaQuery.from(Actor.class);
        Predicate actorIdPredicate = criteriaBuilder.equal(actor.get("id"), id);
        criteriaQuery.where(actorIdPredicate);

        TypedQuery<Actor> query = entityManager.createQuery(criteriaQuery);

        return query.getResultStream().findFirst();
    }

    @Override
    public List<Actor> findAllActor() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);

        Root<Actor> actor = criteriaQuery.from(Actor.class);
        criteriaQuery.select(actor);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public void deleteActorById(long id) {
        entityManager.remove(findActorById(id));
    }
}
