package com.viewnext.bsan.practica04.persistence.repository.custom.impl;

import com.viewnext.bsan.practica04.persistence.entity.Actor;
import com.viewnext.bsan.practica04.persistence.entity.Actor_;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomActorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code CustomActorRepository} interface.
 *
 * @author Antonio Gil
 */
@Repository
public class CustomActorRepositoryImpl implements CustomActorRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CustomActorRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Actor> findAll(Pageable pageable) {
        // Step 1. Create a CriteriaQuery
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
        Root<Actor> actors = criteriaQuery.from(Actor.class);
        criteriaQuery.select(actors);

        // Step 2. Create a TypedQuery from the CriteriaQuery above (this allows us to apply pagination)
        int pageNumber = Math.max(0, pageable.getPageNumber() - 1);
        TypedQuery<Actor> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageNumber * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Step 3. Return the result list
        return typedQuery.getResultList();
    }

    @Override
    public List<Actor> findAll(Specification<Actor> spec, Pageable pageable) {
        // Step 1. Create a CriteriaQuery with the given filtering criteria
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
        Root<Actor> actors = criteriaQuery.from(Actor.class);
        criteriaQuery.select(actors).where(spec.toPredicate(actors, criteriaQuery, criteriaBuilder));

        // Step 2. Create a TypedQuery from the CriteriaQuery above (this allows us to apply pagination)
        int pageNumber = Math.max(0, pageable.getPageNumber() - 1);
        TypedQuery<Actor> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageNumber * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Step 3. Return the result list
        return typedQuery.getResultList();
    }

    @Override
    public boolean existsById(long id) {
        // Step 1. Create a CriteriaQuery with the given filtering criteria
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
        Root<Actor> actors = criteriaQuery.from(Actor.class);
        Predicate idMatches = criteriaBuilder.equal(actors.get(Actor_.id), id);
        criteriaQuery.select(actors).where(idMatches);

        // Step 2. Try to execute the query and return an appropriate result
        try {
            entityManager.createQuery(criteriaQuery).getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public Optional<Actor> findById(long id) {
        // Step 1. Create a CriteriaQuery with the given filtering criteria
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
        Root<Actor> actors = criteriaQuery.from(Actor.class);
        Predicate idMatches = criteriaBuilder.equal(actors.get(Actor_.id), id);
        criteriaQuery.select(actors).where(idMatches);

        // Step 2. Try to execute the query and return the result
        try {
            Actor foundEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
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
        // Step 1. Create a CriteriaDelete with the given filtering criteria
        CriteriaDelete<Actor> criteriaDelete = criteriaBuilder.createCriteriaDelete(Actor.class);
        Root<Actor> actors = criteriaDelete.from(Actor.class);
        Predicate idMatches = criteriaBuilder.equal(actors.get(Actor_.id), id);
        criteriaDelete.where(idMatches);

        // Step 2. Execute the delete query and return an appropriate result
        int affectedEntityCount = entityManager.createQuery(criteriaDelete).executeUpdate();
        return (affectedEntityCount > 0);
    }

}
