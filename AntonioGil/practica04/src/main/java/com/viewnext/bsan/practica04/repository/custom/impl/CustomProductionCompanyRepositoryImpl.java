package com.viewnext.bsan.practica04.repository.custom.impl;

import com.viewnext.bsan.practica04.entity.ProductionCompany;
import com.viewnext.bsan.practica04.entity.ProductionCompany_;
import com.viewnext.bsan.practica04.repository.custom.CustomProductionCompanyRepository;
import com.viewnext.bsan.practica04.entity.ProductionCompany;
import com.viewnext.bsan.practica04.entity.ProductionCompany_;
import com.viewnext.bsan.practica04.repository.custom.CustomProductionCompanyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
        // TODO: Re-do this method
        return List.of();
    }

    @Override
    public List<ProductionCompany> findAll(Specification<ProductionCompany> spec, Pageable pageable) {
        // TODO: Re-do this method
        return List.of();
    }

    @Override
    public boolean existsById(long id) {
        // TODO: Re-do this method
        return false;
    }

    @Override
    public Optional<ProductionCompany> findById(long id) {
        // TODO: Re-do this method
        return Optional.empty();
    }

    @Override
    public ProductionCompany save(ProductionCompany company) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public boolean deleteById(long id) {
        // TODO: Re-do this method
        return false;
    }

}
