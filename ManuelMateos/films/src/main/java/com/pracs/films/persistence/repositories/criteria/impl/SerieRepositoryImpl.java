package com.pracs.films.persistence.repositories.criteria.impl;

import com.pracs.films.persistence.models.Serie;
import com.pracs.films.persistence.repositories.criteria.SerieCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link SerieCustomRepository}
 *
 * @author Manuel Mateos de Torres
 */
@Repository
public class SerieRepositoryImpl implements SerieCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Serie saveSerie(Serie serie) {
        entityManager.persist(serie);
        entityManager.flush();
        return serie;
    }

    @Override
    public Serie updateSerie(Serie serie) {
        entityManager.merge(serie);
        entityManager.flush();
        return serie;
    }

    @Override
    public Optional<Serie> findSerieById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Serie> criteriaQuery = criteriaBuilder.createQuery(Serie.class);

        Root<Serie> serie = criteriaQuery.from(Serie.class);
        Predicate serieIdPredicate = criteriaBuilder.equal(serie.get("id"), id);
        criteriaQuery.where(serieIdPredicate);

        TypedQuery<Serie> query = entityManager.createQuery(criteriaQuery);

        return query.getResultStream().findFirst();
    }

    @Override
    public List<Serie> findAllSerie() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Serie> criteriaQuery = criteriaBuilder.createQuery(Serie.class);

        Root<Serie> serie = criteriaQuery.from(Serie.class);
        criteriaQuery.select(serie);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public void deleteSerieById(long id) {
        entityManager.remove(findSerieById(id));
    }
}
