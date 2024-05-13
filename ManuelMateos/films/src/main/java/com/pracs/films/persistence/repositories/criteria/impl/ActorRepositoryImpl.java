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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

        TypedQuery<Actor> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Actor> actors = typedQuery.getResultList();

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
    public Page<Actor> findAllFilter(Pageable pageable, List<String> names, List<Integer> ages,
            List<String> nationalities) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Actor> query = criteriaBuilder.createQuery(Actor.class);
        Root<Actor> root = query.from(Actor.class);

        List<Predicate> predicates = new ArrayList<>();

        if (names != null && !names.isEmpty()) {
            predicates.add(root.get("name").in(names));
        }

        if (ages != null && !ages.isEmpty()) {
            predicates.add(root.get("age").in(ages));
        }

        if (nationalities != null && !nationalities.isEmpty()) {
            predicates.add(root.get("nationality").in(nationalities));
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        for (Sort.Order order : pageable.getSort()) {
            if (order.getDirection().isAscending()) {
                query.orderBy(criteriaBuilder.asc(root.get(order.getProperty())));
            } else {
                query.orderBy(criteriaBuilder.desc(root.get(order.getProperty())));
            }
        }

        TypedQuery<Actor> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Actor> actors = typedQuery.getResultList();

        long totalElements = entityManager.createQuery(query).getResultList().size();

        return new PageImpl<>(actors, pageable, totalElements);
    }

    @Override
    @Transactional
    public void deleteActorById(Actor actor) {
        entityManager.remove(actor);
        entityManager.flush();
    }
}
