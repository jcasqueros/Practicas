package com.pracs.films.persistence.repositories.criteria.impl;

import com.pracs.films.persistence.models.Director;
import com.pracs.films.persistence.repositories.criteria.DirectorCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link DirectorCustomRepository}
 *
 * @author Manuel Mateos de Torres
 */
@Repository
public class DirectorRepositoryImpl implements DirectorCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Director saveDirector(Director director) {
        entityManager.persist(director);
        return director;
    }

    @Override
    @Transactional
    public Director updateDirector(Director director) {
        entityManager.merge(director);
        return director;
    }

    @Override
    public Optional<Director> findDirectorById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Director> criteriaQuery = criteriaBuilder.createQuery(Director.class);

        Root<Director> director = criteriaQuery.from(Director.class);
        Predicate directorIdPredicate = criteriaBuilder.equal(director.get("id"), id);
        criteriaQuery.where(directorIdPredicate);

        TypedQuery<Director> query = entityManager.createQuery(criteriaQuery);

        return query.getResultStream().findFirst();
    }

    @Override
    public List<Director> findAllDirector() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Director> criteriaQuery = criteriaBuilder.createQuery(Director.class);

        Root<Director> director = criteriaQuery.from(Director.class);
        criteriaQuery.select(director);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    @Transactional
    public void deleteDirectorById(Director director) {
        entityManager.remove(director);
        entityManager.flush();
    }
}
