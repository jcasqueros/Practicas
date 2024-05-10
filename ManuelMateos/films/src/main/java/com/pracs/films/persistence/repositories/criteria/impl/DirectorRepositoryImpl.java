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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<Director> findAllDirector(Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Director> criteriaQuery = criteriaBuilder.createQuery(Director.class);

        Root<Director> director = criteriaQuery.from(Director.class);
        criteriaQuery.select(director);

        String sort = String.valueOf(pageable.getSort()).split(":")[0].trim();
        criteriaQuery.orderBy(criteriaBuilder.asc(director.get(sort)));

        TypedQuery<Director> query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Director> directors = query.getResultList();

        long totalElements = countAllDirectors();

        return new PageImpl<>(directors, pageable, totalElements);
    }

    private long countAllDirectors() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

        Root<Director> director = countQuery.from(Director.class);
        countQuery.select(criteriaBuilder.count(director));

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    @Override
    @Transactional
    public void deleteDirectorById(Director director) {
        entityManager.remove(director);
        entityManager.flush();
    }
}
