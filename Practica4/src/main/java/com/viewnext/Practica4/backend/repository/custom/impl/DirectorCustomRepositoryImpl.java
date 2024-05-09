package com.viewnext.Practica4.backend.repository.custom.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viewnext.Practica4.backend.business.model.Director;
import com.viewnext.Practica4.backend.repository.custom.DirectorCustomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class DirectorCustomRepositoryImpl implements DirectorCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Director createCb(Director director) {
        entityManager.persist(director);
        return director;
    }

    @Override
    public Director readCb(long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Director> cq = cb.createQuery(Director.class);
        Root<Director> root = cq.from(Director.class);
        cq.where(cb.equal(root.get("id"), id));
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public List<Director> getDirectoresCb() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Director> cq = cb.createQuery(Director.class);
        Root<Director> root = cq.from(Director.class);
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Director updateCb(Director director) {
        return entityManager.merge(director);
    }

    @Override
    public void deleteCb(long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Director> cd = cb.createCriteriaDelete(Director.class);
        Root<Director> root = cd.from(Director.class);
        cd.where(cb.equal(root.get("id"), id));
        entityManager.createQuery(cd).executeUpdate();
    }
}