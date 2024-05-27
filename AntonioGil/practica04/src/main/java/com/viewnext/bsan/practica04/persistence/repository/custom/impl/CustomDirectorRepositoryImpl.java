package com.viewnext.bsan.practica04.persistence.repository.custom.impl;

import com.viewnext.bsan.practica04.persistence.entity.Director;
import com.viewnext.bsan.practica04.persistence.entity.Director_;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomDirectorRepository;
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
 * Implementation for the {@code CustomDirectorRepository} interface.
 *
 * @author Antonio Gil
 */
@Repository
public class CustomDirectorRepositoryImpl implements CustomDirectorRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CustomDirectorRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Director> findAll(Pageable pageable) {
        // Step 1. Create a CriteriaQuery
        CriteriaQuery<Director> criteriaQuery = criteriaBuilder.createQuery(Director.class);
        Root<Director> directors = criteriaQuery.from(Director.class);
        criteriaQuery.select(directors);

        // Step 2. Create a TypedQuery from the CriteriaQuery above (this allows us to apply pagination)
        TypedQuery<Director> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Step 3. Return the result list
        return typedQuery.getResultList();
    }

    @Override
    public List<Director> findAll(Specification<Director> spec, Pageable pageable) {
        // Step 1. Create a CriteriaQuery
        CriteriaQuery<Director> criteriaQuery = criteriaBuilder.createQuery(Director.class);
        Root<Director> directors = criteriaQuery.from(Director.class);
        criteriaQuery.select(directors).where(spec.toPredicate(directors, criteriaQuery, criteriaBuilder));

        // Step 2. Create a TypedQuery from the CriteriaQuery above (this allows us to apply pagination)
        TypedQuery<Director> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Step 3. Return the result list
        return typedQuery.getResultList();
    }

    @Override
    public boolean existsById(long id) {
        // Step 1. Create a CriteriaQuery with the given filtering criteria
        CriteriaQuery<Director> criteriaQuery = criteriaBuilder.createQuery(Director.class);
        Root<Director> directors = criteriaQuery.from(Director.class);
        Predicate idMatches = criteriaBuilder.equal(directors.get(Director_.id), id);
        criteriaQuery.select(directors).where(idMatches);

        // Step 2. Try to execute the query and return an appropriate result
        try {
            entityManager.createQuery(criteriaQuery).getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public Optional<Director> findById(long id) {
        // Step 1. Create a CriteriaQuery with the given filtering criteria
        CriteriaQuery<Director> criteriaQuery = criteriaBuilder.createQuery(Director.class);
        Root<Director> directors = criteriaQuery.from(Director.class);
        Predicate idMatches = criteriaBuilder.equal(directors.get(Director_.id), id);
        criteriaQuery.select(directors).where(idMatches);

        // Step 2. Try to execute the query and return the result
        try {
            Director foundEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
            return Optional.of(foundEntity);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Director save(Director director) {
        entityManager.persist(director);
        return director;
    }

    @Override
    public boolean deleteById(long id) {
        // Step 1. Create a CriteriaDelete with the given filtering criteria
        CriteriaDelete<Director> criteriaDelete = criteriaBuilder.createCriteriaDelete(Director.class);
        Root<Director> directors = criteriaDelete.from(Director.class);
        Predicate idMatches = criteriaBuilder.equal(directors.get("id"), id);
        criteriaDelete.where(idMatches);

        // Step 2. Execute the delete query and return an appropriate result
        int affectedEntityCount = entityManager.createQuery(criteriaDelete).executeUpdate();
        return (affectedEntityCount > 0);
    }

}
