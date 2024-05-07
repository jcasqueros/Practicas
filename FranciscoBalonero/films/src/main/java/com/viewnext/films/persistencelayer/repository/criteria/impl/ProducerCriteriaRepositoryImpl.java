package com.viewnext.films.persistencelayer.repository.criteria.impl;

import com.viewnext.films.persistencelayer.entity.Producer;
import com.viewnext.films.persistencelayer.repository.criteria.ProducerCriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link ProducerCriteriaRepository} interface.
 *
 * <p>This class provides a implementation of the CRUD operations for producers using JPA Criteria API.</p>
 *
 * @author Francisco Balonero Olivera
 * @see ProducerCriteriaRepository
 */
@Repository
public class ProducerCriteriaRepositoryImpl implements ProducerCriteriaRepository {

    /**
     * The entity manager for performing database operations.
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Producer createProducer(Producer producer) {
        entityManager.persist(producer);
        return producer;
    }

    @Override
    public List<Producer> getAllProducers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Producer> criteriaQuery = criteriaBuilder.createQuery(Producer.class);
        Root<Producer> root = criteriaQuery.from(Producer.class);
        criteriaQuery.select(root);

        TypedQuery<Producer> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Optional<Producer> getProducerById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Producer> criteriaQuery = criteriaBuilder.createQuery(Producer.class);
        Root<Producer> root = criteriaQuery.from(Producer.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        TypedQuery<Producer> query = entityManager.createQuery(criteriaQuery);
        return query.getResultStream().findFirst();
    }

    @Override
    public Producer updateProducer(Producer producer) {
        return entityManager.merge(producer);
    }

    @Override
    public void deleteProducer(Long id) {
        Producer producer = entityManager.find(Producer.class, id);
        if (producer != null) {
            entityManager.remove(producer);
        }
    }
}
