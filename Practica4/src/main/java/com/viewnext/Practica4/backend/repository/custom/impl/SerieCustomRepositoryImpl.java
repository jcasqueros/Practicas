package com.viewnext.Practica4.backend.repository.custom.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viewnext.Practica4.backend.business.model.Serie;
import com.viewnext.Practica4.backend.repository.custom.SerieCustomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class SerieCustomRepositoryImpl implements SerieCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Serie createCb(Serie serie) {
        entityManager.persist(serie);
        return serie;
    }

    @Override
    public Serie readCb(long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Serie> cq = cb.createQuery(Serie.class);
        Root<Serie> root = cq.from(Serie.class);
        cq.where(cb.equal(root.get("id"), id));
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public List<Serie> getSeriesCb() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Serie> cq = cb.createQuery(Serie.class);
        Root<Serie> root = cq.from(Serie.class);
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Serie updateCb(Serie serie) {
        return entityManager.merge(serie);
    }

    @Override
    public void deleteCb(long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Serie> cd = cb.createCriteriaDelete(Serie.class);
        Root<Serie> root = cd.from(Serie.class);
        cd.where(cb.equal(root.get("id"), id));
        entityManager.createQuery(cd).executeUpdate();
    }
}