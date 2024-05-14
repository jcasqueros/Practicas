package com.viewnext.bsan.practica04.repository.custom.impl;

import com.viewnext.bsan.practica04.entity.Director;
import com.viewnext.bsan.practica04.entity.Director_;
import com.viewnext.bsan.practica04.repository.custom.CustomDirectorRepository;
import com.viewnext.bsan.practica04.entity.Director;
import com.viewnext.bsan.practica04.entity.Director_;
import com.viewnext.bsan.practica04.repository.custom.CustomDirectorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
        // TODO: Re-do this method
        return List.of();
    }

    @Override
    public List<Director> findAll(Specification<Director> spec, Pageable pageable) {
        // TODO: Re-do this method
        return List.of();
    }

    @Override
    public boolean existsById(long id) {
        // TODO: Re-do this method
        return false;
    }

    @Override
    public Optional<Director> findById(long id) {
        // TODO: Re-do this method
        return Optional.empty();
    }

    @Override
    public Director save(Director director) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public boolean deleteById(long id) {
        // TODO: Re-do this method
        return false;
    }

}
