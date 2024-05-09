package com.viewnext.Practica4.backend.repository.custom.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viewnext.Practica4.backend.business.model.Pelicula;
import com.viewnext.Practica4.backend.repository.custom.PeliculaCustomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class PeliculaCustomRepositoryImpl implements PeliculaCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Pelicula createCb(Pelicula pelicula) {
        entityManager.persist(pelicula);
        return pelicula;
    }

    @Override
    public Pelicula readCb(long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pelicula> cq = cb.createQuery(Pelicula.class);
        Root<Pelicula> root = cq.from(Pelicula.class);
        cq.where(cb.equal(root.get("id"), id));
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public List<Pelicula> getPeliculasCb() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pelicula> cq = cb.createQuery(Pelicula.class);
        Root<Pelicula> root = cq.from(Pelicula.class);
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Pelicula updateCb(Pelicula pelicula) {
        return entityManager.merge(pelicula);
    }

    @Override
    public void deleteCb(long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Pelicula> cd = cb.createCriteriaDelete(Pelicula.class);
        Root<Pelicula> root = cd.from(Pelicula.class);
        cd.where(cb.equal(root.get("id"), id));
        entityManager.createQuery(cd).executeUpdate();
    }
}