package com.viewnext.bsan.practica04.persistence.repository.custom.impl;

import com.viewnext.bsan.practica04.persistence.entity.Show;
import com.viewnext.bsan.practica04.persistence.entity.Show_;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomShowRepository;
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
 * Implementation for the {@code CustomShowRepository} interface.
 *
 * @author Antonio Gil
 */
@Repository
public class CustomShowRepositoryImpl implements CustomShowRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CustomShowRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Show> findAll(Pageable pageable) {
        // Step 1. Create a CriteriaQuery
        CriteriaQuery<Show> criteriaQuery = criteriaBuilder.createQuery(Show.class);
        Root<Show> shows = criteriaQuery.from(Show.class);
        criteriaQuery.select(shows);

        // Step 2. Create a TypedQuery from the CriteriaQuery above (this allows us to apply pagination)
        int pageNumber = Math.max(0, pageable.getPageNumber() - 1);
        TypedQuery<Show> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageNumber * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Step 3. Return the result list
        return typedQuery.getResultList();
    }

    @Override
    public List<Show> findAll(Specification<Show> spec, Pageable pageable) {
        // Step 1. Create a CriteriaQuery with the given filtering criteria
        CriteriaQuery<Show> criteriaQuery = criteriaBuilder.createQuery(Show.class);
        Root<Show> shows = criteriaQuery.from(Show.class);
        criteriaQuery.select(shows).where(spec.toPredicate(shows, criteriaQuery, criteriaBuilder));

        // Step 2. Create a TypedQuery from the CriteriaQuery above (this allows us to apply pagination)
        int pageNumber = Math.max(0, pageable.getPageNumber() - 1);
        TypedQuery<Show> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageNumber * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Step 3. Return the result list
        return typedQuery.getResultList();
    }

    @Override
    public boolean existsById(long id) {
        // Step 1. Create a CriteriaQuery with the given filtering criteria
        CriteriaQuery<Show> criteriaQuery = criteriaBuilder.createQuery(Show.class);
        Root<Show> shows = criteriaQuery.from(Show.class);
        Predicate idMatches = criteriaBuilder.equal(shows.get(Show_.id), id);
        criteriaQuery.select(shows).where(idMatches);

        // Step 2. Try to execute the query and return an appropriate result
        try {
            entityManager.createQuery(criteriaQuery).getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public Optional<Show> findById(long id) {
        // Step 1. Create a CriteriaQuery with the given filtering criteria
        CriteriaQuery<Show> criteriaQuery = criteriaBuilder.createQuery(Show.class);
        Root<Show> films = criteriaQuery.from(Show.class);
        Predicate idMatches = criteriaBuilder.equal(films.get(Show_.id), id);
        criteriaQuery.select(films).where(idMatches);

        // Step 2. Try to execute the query and return the result
        try {
            Show foundEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
            return Optional.of(foundEntity);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Show save(Show show) {
        entityManager.persist(show);
        return show;
    }

    @Override
    public boolean deleteById(long id) {
        // Step 1. Create a CriteriaDelete with the given filtering criteria
        CriteriaDelete<Show> criteriaDelete = criteriaBuilder.createCriteriaDelete(Show.class);
        Root<Show> films = criteriaDelete.from(Show.class);
        Predicate idMatches = criteriaBuilder.equal(films.get(Show_.id), id);
        criteriaDelete.where(idMatches);

        // Step 2. Execute the delete query and return an appropriate result
        int affectedEntityCount = entityManager.createQuery(criteriaDelete).executeUpdate();
        return (affectedEntityCount > 0);
    }

}
