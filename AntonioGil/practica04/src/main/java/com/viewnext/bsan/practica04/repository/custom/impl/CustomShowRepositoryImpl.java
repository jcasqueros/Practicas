package com.viewnext.bsan.practica04.repository.custom.impl;

import com.viewnext.bsan.practica04.entity.Show;
import com.viewnext.bsan.practica04.entity.Show_;
import com.viewnext.bsan.practica04.repository.custom.CustomShowRepository;
import com.viewnext.bsan.practica04.entity.Show;
import com.viewnext.bsan.practica04.entity.Show_;
import com.viewnext.bsan.practica04.repository.custom.CustomShowRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
        // TODO: Re-do this method
        return List.of();
    }

    @Override
    public List<Show> findAll(Specification<Show> spec, Pageable pageable) {
        // TODO: Re-do this method
        return List.of();
    }

    @Override
    public boolean existsById(long id) {
        // TODO: Re-do this method
        return false;
    }

    @Override
    public Optional<Show> findById(long id) {
        // TODO: Re-do this method
        return Optional.empty();
    }

    @Override
    public Show save(Show show) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public boolean deleteById(long id) {
        // TODO: Re-do this method
        return false;
    }

}
