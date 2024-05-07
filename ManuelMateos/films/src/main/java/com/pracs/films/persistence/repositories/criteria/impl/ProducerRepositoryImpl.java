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
import org.springframework.stereotype.Repository;

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
    public Producer saveProducer(Producer producer) {
        entityManager.persist(producer);
        entityManager.flush();
        return producer;
    }

    @Override
    public Producer updateProducer(Producer producer) {
        entityManager.merge(producer);
        entityManager.flush();
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
    public List<Producer> findAllProducer() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Producer> criteriaQuery = criteriaBuilder.createQuery(Producer.class);

        Root<Producer> producer = criteriaQuery.from(Producer.class);
        criteriaQuery.select(producer);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public void deleteProducerById(long id) {
        entityManager.remove(findProducerById(id));
    }
}
