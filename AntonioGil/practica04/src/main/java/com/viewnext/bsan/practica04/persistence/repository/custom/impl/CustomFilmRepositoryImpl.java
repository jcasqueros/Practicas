package com.viewnext.bsan.practica04.persistence.repository.custom.impl;

import com.viewnext.bsan.practica04.persistence.entity.*;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomFilmRepository;
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
 * Implementation for the {@code CustomFilmRepository} interface.
 *
 * @author Antonio Gil
 */
@Repository
public class CustomFilmRepositoryImpl implements CustomFilmRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CustomFilmRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Film> findAll(Pageable pageable) {
        // Step 1. Create a CriteriaQuery
        CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);
        Root<Film> films = criteriaQuery.from(Film.class);
        criteriaQuery.select(films);

        // Step 2. Create a TypedQuery from the CriteriaQuery above (this allows us to apply pagination)
        int pageNumber = Math.max(0, pageable.getPageNumber() - 1);
        TypedQuery<Film> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageNumber * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Step 3. Return the result list
        return typedQuery.getResultList();
    }

    @Override
    public List<Film> findAll(Specification<Film> spec, Pageable pageable) {
        // Step 1. Create a CriteriaQuery with the given filtering criteria
        CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);
        Root<Film> films = criteriaQuery.from(Film.class);
        criteriaQuery.select(films).where(spec.toPredicate(films, criteriaQuery, criteriaBuilder));

        // Step 2. Create a TypedQuery from the CriteriaQuery above (this allows us to apply pagination)
        int pageNumber = Math.max(0, pageable.getPageNumber() - 1);
        TypedQuery<Film> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageNumber * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Step 3. Return the result list
        return typedQuery.getResultList();
    }

    @Override
    public boolean existsById(long id) {
        // Step 1. Create a CriteriaQuery with the given filtering criteria
        CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);
        Root<Film> films = criteriaQuery.from(Film.class);
        Predicate idMatches = criteriaBuilder.equal(films.get(Film_.id), id);
        criteriaQuery.select(films).where(idMatches);

        // Step 2. Try to execute the query and return an appropriate result
        try {
            entityManager.createQuery(criteriaQuery).getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public Optional<Film> findById(long id) {
        // Step 1. Create a CriteriaQuery with the given filtering criteria
        CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);
        Root<Film> films = criteriaQuery.from(Film.class);
        Predicate idMatches = criteriaBuilder.equal(films.get(Film_.id), id);
        criteriaQuery.select(films).where(idMatches);

        // Step 2. Try to execute the query and return the result
        try {
            Film foundEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
            return Optional.of(foundEntity);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Film save(Film film) {
        entityManager.persist(film);
        return film;
    }

    @Override
    public boolean deleteById(long id) {
        // Step 1. Create a CriteriaDelete with the given filtering criteria
        CriteriaDelete<Film> criteriaDelete = criteriaBuilder.createCriteriaDelete(Film.class);
        Root<Film> films = criteriaDelete.from(Film.class);
        Predicate idMatches = criteriaBuilder.equal(films.get(Film_.id), id);
        criteriaDelete.where(idMatches);

        // Step 2. Execute the delete query and return an appropriate result
        int affectedEntityCount = entityManager.createQuery(criteriaDelete).executeUpdate();
        return (affectedEntityCount > 0);
    }

}
