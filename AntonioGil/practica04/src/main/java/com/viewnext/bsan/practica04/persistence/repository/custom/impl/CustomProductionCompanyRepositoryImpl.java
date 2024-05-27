package com.viewnext.bsan.practica04.persistence.repository.custom.impl;

import com.viewnext.bsan.practica04.persistence.entity.ProductionCompany;
import com.viewnext.bsan.practica04.persistence.entity.ProductionCompany_;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomProductionCompanyRepository;
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
 * Implementation for the {@code CustomProductionCompanyRepository} interface.
 *
 * @author Antonio Gil
 */
@Repository
public class CustomProductionCompanyRepositoryImpl implements CustomProductionCompanyRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CustomProductionCompanyRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<ProductionCompany> findAll(Pageable pageable) {
        // Step 1. Create a CriteriaQuery
        CriteriaQuery<ProductionCompany> criteriaQuery = criteriaBuilder.createQuery(ProductionCompany.class);
        Root<ProductionCompany> companies = criteriaQuery.from(ProductionCompany.class);
        criteriaQuery.select(companies);

        // Step 2. Create a TypedQuery from the CriteriaQuery above (this allows us to apply pagination)
        TypedQuery<ProductionCompany> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Step 3. Return the result list
        return typedQuery.getResultList();
    }

    @Override
    public List<ProductionCompany> findAll(Specification<ProductionCompany> spec, Pageable pageable) {
        // Step 1. Create a CriteriaQuery with the given filtering criteria
        CriteriaQuery<ProductionCompany> criteriaQuery = criteriaBuilder.createQuery(ProductionCompany.class);
        Root<ProductionCompany> companies = criteriaQuery.from(ProductionCompany.class);
        criteriaQuery.select(companies).where(spec.toPredicate(companies, criteriaQuery, criteriaBuilder));

        // Step 2. Create a TypedQuery from the CriteriaQuery above (this allows us to apply pagination)
        TypedQuery<ProductionCompany> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Step 3. Return the result list
        return typedQuery.getResultList();
    }

    @Override
    public boolean existsById(long id) {
        // Step 1. Create a CriteriaQuery with the given filtering criteria
        CriteriaQuery<ProductionCompany> criteriaQuery = criteriaBuilder.createQuery(ProductionCompany.class);
        Root<ProductionCompany> companies = criteriaQuery.from(ProductionCompany.class);
        Predicate idMatches = criteriaBuilder.equal(companies.get(ProductionCompany_.id), id);
        criteriaQuery.select(companies).where(idMatches);

        // Step 2. Try to execute the query and return an appropriate result
        try {
            entityManager.createQuery(criteriaQuery).getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public Optional<ProductionCompany> findById(long id) {
        // Step 1. Create a CriteriaQuery with the given filtering criteria
        CriteriaQuery<ProductionCompany> criteriaQuery = criteriaBuilder.createQuery(ProductionCompany.class);
        Root<ProductionCompany> companies = criteriaQuery.from(ProductionCompany.class);
        Predicate idMatches = criteriaBuilder.equal(companies.get(ProductionCompany_.id), id);
        criteriaQuery.select(companies).where(idMatches);

        // Step 2. Try to execute the query and return the result
        try {
            ProductionCompany foundEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
            return Optional.of(foundEntity);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public ProductionCompany save(ProductionCompany company) {
        entityManager.persist(company);
        return company;
    }

    @Override
    public boolean deleteById(long id) {
        // Step 1. Create a CriteriaDelete with the given filtering criteria
        CriteriaDelete<ProductionCompany> criteriaDelete =
                criteriaBuilder.createCriteriaDelete(ProductionCompany.class);
        Root<ProductionCompany> companies = criteriaDelete.from(ProductionCompany.class);
        Predicate idMatches = criteriaBuilder.equal(companies.get(ProductionCompany_.id), id);
        criteriaDelete.where(idMatches);

        // Step 2. Execute the delete query and return an appropriate result
        int affectedEntityCount = entityManager.createQuery(criteriaDelete).executeUpdate();
        return affectedEntityCount > 0;
    }

}
