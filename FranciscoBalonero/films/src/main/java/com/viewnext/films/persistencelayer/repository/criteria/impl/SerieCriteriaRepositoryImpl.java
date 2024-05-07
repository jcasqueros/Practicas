package com.viewnext.films.persistencelayer.repository.criteria.impl;

import com.viewnext.films.persistencelayer.entity.Serie;
import com.viewnext.films.persistencelayer.repository.criteria.SerieCriteriaRepository;
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
 * Implementation of the {@link SerieCriteriaRepository} interface.
 *
 * <p>This class provides a implementation of the CRUD operations for series using JPA Criteria API.</p>
 *
 * @author Francisco Balonero Olivera
 * @see SerieCriteriaRepository
 */
@Repository
public class SerieCriteriaRepositoryImpl implements SerieCriteriaRepository {

    /**
     * The entity manager for performing database operations.
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Serie createSerie(Serie serie) {
        entityManager.persist(serie);
        return serie;
    }

    @Override
    public List<Serie> getAllSeries() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Serie> criteriaQuery = criteriaBuilder.createQuery(Serie.class);
        Root<Serie> root = criteriaQuery.from(Serie.class);
        criteriaQuery.select(root);

        TypedQuery<Serie> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Optional<Serie> getSerieById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Serie> criteriaQuery = criteriaBuilder.createQuery(Serie.class);
        Root<Serie> root = criteriaQuery.from(Serie.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        TypedQuery<Serie> query = entityManager.createQuery(criteriaQuery);
        return query.getResultStream().findFirst();
    }

    @Override
    public Serie updateSerie(Serie serie) {
        return entityManager.merge(serie);
    }

    @Override
    public void deleteSerie(Long id) {
        Serie serie = entityManager.find(Serie.class, id);
        if (serie != null) {
            entityManager.remove(serie);
        }
    }
}
