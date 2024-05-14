package com.viewnext.bsan.practica04.repository.custom.impl;

import com.viewnext.bsan.practica04.entity.Film;
import com.viewnext.bsan.practica04.entity.Film_;
import com.viewnext.bsan.practica04.repository.custom.CustomFilmRepository;
import com.viewnext.bsan.practica04.entity.Film;
import com.viewnext.bsan.practica04.entity.Film_;
import com.viewnext.bsan.practica04.repository.custom.CustomFilmRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
        // TODO: Re-do this method
        return List.of();
    }

    @Override
    public List<Film> findAll(Specification<Film> spec, Pageable pageable) {
        // TODO: Re-do this method
        return List.of();
    }

    @Override
    public boolean existsById(long id) {
        // TODO: Re-do this method
        return false;
    }

    @Override
    public Optional<Film> findById(long id) {
        // TODO: Re-do this method
        return Optional.empty();
    }

    @Override
    public Film save(Film film) {
        // TODO: Re-do this method
        return null;
    }

    @Override
    public boolean deleteById(long id) {
        // TODO: Re-do this method
        return false;
    }

}
