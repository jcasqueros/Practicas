package com.viewnext.Practica4.backend.repository.custom.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viewnext.Practica4.backend.business.model.Productora;
import com.viewnext.Practica4.backend.repository.custom.ProductoraCustomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class ProductoraCustomRepositoryImpl implements ProductoraCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Productora createCb(Productora productora) {
        entityManager.persist(productora);
        return productora;
    }

    @Override
    public Productora readCb(long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Productora> cq = cb.createQuery(Productora.class);
        Root<Productora> root = cq.from(Productora.class);
        cq.where(cb.equal(root.get("id"), id));
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public List<Productora> getProductorasCb() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Productora> cq = cb.createQuery(Productora.class);
        Root<Productora> root = cq.from(Productora.class);
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Productora updateCb(Productora productora) {
        return entityManager.merge(productora);
    }

    @Override
    public void deleteCb(long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Productora> cd = cb.createCriteriaDelete(Productora.class);
        Root<Productora> root = cd.from(Productora.class);
        cd.where(cb.equal(root.get("id"), id));
        entityManager.createQuery(cd).executeUpdate();
    }
}