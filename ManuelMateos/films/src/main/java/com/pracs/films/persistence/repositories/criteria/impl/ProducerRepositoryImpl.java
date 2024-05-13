package com.pracs.films.persistence.repositories.criteria.impl;

import com.pracs.films.persistence.models.Producer;
import com.pracs.films.persistence.repositories.criteria.ProducerCustomRepository;
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
 * Implementation of {@link ProducerCustomRepository}
 *
 * @author Manuel Mateos de Torres
 */
@Repository
public class ProducerRepositoryImpl implements ProducerCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Producer saveProducer(Producer producer) {
        entityManager.persist(producer);
        return producer;
    }

    @Override
    @Transactional
    public Producer updateProducer(Producer producer) {
        entityManager.merge(producer);
        return producer;
    }

    @Override
    public Optional<Producer> findProducerById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Producer> criteriaQuery = criteriaBuilder.createQuery(Producer.class);

        Root<Producer> producer = criteriaQuery.from(Producer.class);
        Predicate producerIdPredicate = criteriaBuilder.equal(producer.get("id"), id);
        criteriaQuery.where(producerIdPredicate);

        TypedQuery<Producer> query = entityManager.createQuery(criteriaQuery);

        return query.getResultStream().findFirst();
    }

    @Override
    public Page<Producer> findAllProducer(Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Producer> criteriaQuery = criteriaBuilder.createQuery(Producer.class);

        Root<Producer> producer = criteriaQuery.from(Producer.class);
        criteriaQuery.select(producer);

        String sort = String.valueOf(pageable.getSort()).split(":")[0].trim();
        criteriaQuery.orderBy(criteriaBuilder.asc(producer.get(sort)));

        TypedQuery<Producer> query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Producer> producers = query.getResultList();

        long totalElements = countAllProducers();

        return new PageImpl<>(producers, pageable, totalElements);
    }

    @Override
    public Page<Producer> findAllFilter(Pageable pageable, List<String> names, List<Integer> ages) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Producer> query = criteriaBuilder.createQuery(Producer.class);
        Root<Producer> root = query.from(Producer.class);

        List<Predicate> predicates = new ArrayList<>();

        if (names != null && !names.isEmpty()) {
            predicates.add(root.get("name").in(names));
        }

        if (ages != null && !ages.isEmpty()) {
            predicates.add(root.get("debut").in(ages));
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        for (Sort.Order order : pageable.getSort()) {
            if (order.getDirection().isAscending()) {
                query.orderBy(criteriaBuilder.asc(root.get(order.getProperty())));
            } else {
                query.orderBy(criteriaBuilder.desc(root.get(order.getProperty())));
            }
        }

        TypedQuery<Producer> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Producer> producers = typedQuery.getResultList();

        long totalElements = entityManager.createQuery(query).getResultList().size();

        return new PageImpl<>(producers, pageable, totalElements);
    }

    private long countAllProducers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

        Root<Producer> producer = countQuery.from(Producer.class);
        countQuery.select(criteriaBuilder.count(producer));

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    @Override
    @Transactional
    public void deleteProducerById(Producer producer) {
        entityManager.remove(producer);
        entityManager.flush();
    }
}
