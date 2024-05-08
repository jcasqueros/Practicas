package com.viewnext.films.persistencelayer.repository.criteria.impl;

import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.repository.criteria.ActorCriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link ActorCriteriaRepository} interface.
 *
 * <p>This class provides a implementation of the CRUD operations for actors using JPA Criteria API.</p>
 *
 * @author Francisco Balonero Olivera
 * @see ActorCriteriaRepository
 */
@Repository
public class ActorCriteriaRepositoryImpl implements ActorCriteriaRepository {

    /**
     * The entity manager for performing database operations.
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Actor createActor(Actor actor) {
        entityManager.persist(actor);
        return actor;
    }

    @Override
    public List<Actor> getAllActors() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
        Root<Actor> root = criteriaQuery.from(Actor.class);
        criteriaQuery.select(root);

        TypedQuery<Actor> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Optional<Actor> getActorById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
        Root<Actor> root = criteriaQuery.from(Actor.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        TypedQuery<Actor> query = entityManager.createQuery(criteriaQuery);
        return query.getResultStream().findFirst();
    }

    @Override
    @Transactional
    public Actor updateActor(Actor actor) {
        return entityManager.merge(actor);
    }

    @Override
    @Transactional
    public void deleteActor(Long id) {
        Actor actor = entityManager.find(Actor.class, id);
        if (actor != null) {
            entityManager.remove(actor);
        }
    }
}
