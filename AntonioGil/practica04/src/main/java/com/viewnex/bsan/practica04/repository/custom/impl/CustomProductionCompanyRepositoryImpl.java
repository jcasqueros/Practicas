package com.viewnex.bsan.practica04.repository.custom.impl;

import com.viewnex.bsan.practica04.entity.ProductionCompany;
import com.viewnex.bsan.practica04.repository.custom.CustomProductionCompanyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomProductionCompanyRepositoryImpl implements CustomProductionCompanyRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CustomProductionCompanyRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<ProductionCompany> getAll() {
        CriteriaQuery<ProductionCompany> query = criteriaBuilder.createQuery(ProductionCompany.class);
        Root<ProductionCompany> companies = query.from(ProductionCompany.class);

        query.select(companies);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public boolean existsById(long id) {
        CriteriaQuery<ProductionCompany> query = criteriaBuilder.createQuery(ProductionCompany.class);
        Root<ProductionCompany> companies = query.from(ProductionCompany.class);

        Predicate idMatches = criteriaBuilder.equal(companies.get("id"), id);
        query.select(companies).where(idMatches);

        try {
            entityManager.createQuery(query).getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public Optional<ProductionCompany> getById(long id) {
        CriteriaQuery<ProductionCompany> query = criteriaBuilder.createQuery(ProductionCompany.class);
        Root<ProductionCompany> companies = query.from(ProductionCompany.class);

        Predicate idMatches = criteriaBuilder.equal(companies.get("id"), id);
        query.select(companies).where(idMatches);

        try {
            ProductionCompany foundEntity = entityManager.createQuery(query).getSingleResult();
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
        CriteriaDelete<ProductionCompany> query = criteriaBuilder.createCriteriaDelete(ProductionCompany.class);
        Root<ProductionCompany> companies = query.from(ProductionCompany.class);

        Predicate idMatches = criteriaBuilder.equal(companies.get("id"), id);
        query.where(idMatches);

        int updatedEntityCount = entityManager.createQuery(query).executeUpdate();

        return updatedEntityCount > 0;
    }

}
