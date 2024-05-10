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
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    @Transactional
    public Actor saveActor(Actor actor) {
        entityManager.persist(actor);
        entityManager.flush();
        return actor;
    }

    @Override
    @Transactional
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
    public Page<Actor> findAllActors(Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);

        Root<Actor> actor = criteriaQuery.from(Actor.class);
        criteriaQuery.select(actor);
        
        String sort = String.valueOf(pageable.getSort()).split(":")[0].trim();
        criteriaQuery.orderBy(criteriaBuilder.asc(actor.get(sort)));

        TypedQuery<Actor> query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Actor> actors = query.getResultList();

        long totalElements = countAllActors();

        return new PageImpl<>(actors, pageable, totalElements);
    }

    private long countAllActors() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

        Root<Actor> actor = countQuery.from(Actor.class);
        countQuery.select(criteriaBuilder.count(actor));

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    @Override
    @Transactional
    public void deleteActorById(Actor actor) {
        entityManager.remove(actor);
        entityManager.flush();
    }
}
