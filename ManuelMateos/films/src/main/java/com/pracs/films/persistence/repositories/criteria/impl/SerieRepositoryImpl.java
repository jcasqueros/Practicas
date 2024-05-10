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
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    @Transactional
    public Serie saveSerie(Serie serie) {
        entityManager.persist(serie);
        return serie;
    }

    @Override
    @Transactional
    public Serie updateSerie(Serie serie) {
        entityManager.merge(serie);
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
    public Page<Serie> findAllSerie(Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Serie> criteriaQuery = criteriaBuilder.createQuery(Serie.class);

        Root<Serie> serie = criteriaQuery.from(Serie.class);
        criteriaQuery.select(serie);

        String sort = String.valueOf(pageable.getSort()).split(":")[0].trim();
        criteriaQuery.orderBy(criteriaBuilder.asc(serie.get(sort)));

        TypedQuery<Serie> query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Serie> series = query.getResultList();

        long totalElements = countAllSeries();

        return new PageImpl<>(series, pageable, totalElements);
    }

    private long countAllSeries() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

        Root<Serie> serie = countQuery.from(Serie.class);
        countQuery.select(criteriaBuilder.count(serie));

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    @Override
    @Transactional
    public void deleteSerieById(Serie serie) {
        entityManager.remove(serie);
        entityManager.flush();
    }
}
