package com.viewnext.films.persistencelayer.repository.criteria.impl;

import com.viewnext.films.persistencelayer.entity.Director;
import com.viewnext.films.persistencelayer.repository.criteria.DirectorCriteriaRepository;
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
 * Implementation of the {@link DirectorCriteriaRepository} interface.
 *
 * <p>This class provides a implementation of the CRUD operations for directors using JPA Criteria API.</p>
 *
 * @author Francisco Balonero Olivera
 * @see DirectorCriteriaRepository
 */
@Repository
public class DirectorCriteriaRepositoryImpl implements DirectorCriteriaRepository {

    /**
     * The entity manager for performing database operations.
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Director createDirector(Director director) {
        entityManager.persist(director);
        return director;
    }

    @Override
    public List<Director> getAllDirectors() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Director> criteriaQuery = criteriaBuilder.createQuery(Director.class);
        Root<Director> root = criteriaQuery.from(Director.class);
        criteriaQuery.select(root);

        TypedQuery<Director> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Optional<Director> getDirectorById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Director> criteriaQuery = criteriaBuilder.createQuery(Director.class);
        Root<Director> root = criteriaQuery.from(Director.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        TypedQuery<Director> query = entityManager.createQuery(criteriaQuery);
        return query.getResultStream().findFirst();
    }

    @Override
    @Transactional
    public Director updateDirector(Director director) {
        return entityManager.merge(director);
    }

    @Override
    @Transactional
    public void deleteDirector(Long id) {
        Director director = entityManager.find(Director.class, id);
        if (director != null) {
            entityManager.remove(director);
        }
    }
}
