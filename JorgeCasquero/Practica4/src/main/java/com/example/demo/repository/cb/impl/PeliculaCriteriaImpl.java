package com.example.demo.repository.cb.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Actor;
import com.example.demo.model.Pelicula;
import com.example.demo.repository.cb.PeliculaRepositoryCriteria;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class PeliculaCriteriaImpl implements PeliculaRepositoryCriteria {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Pelicula> getAll() {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pelicula> cq = cb.createQuery(Pelicula.class);
		Root<Pelicula> root = cq.from(Pelicula.class);
		cq.select(root);
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public Pelicula getById(long id) throws NotFoundException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pelicula> cq = cb.createQuery(Pelicula.class);
		Root<Pelicula> root = cq.from(Pelicula.class);
		cq.where(cb.equal(root.get("id"), id));
		return entityManager.createQuery(cq).getSingleResult();
	}

	@Override
	public Pelicula create(Pelicula pelicula) throws AlreadyExistsExeption {

		return entityManager.merge(pelicula);
	}

	@Override
	public void deleteById(long id) throws NotFoundException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Actor> cq = cb.createQuery(Actor.class);
		Root<Actor> root = cq.from(Actor.class);
		cq.where(cb.equal(root.get("id"), id));
		entityManager.createQuery(cq).executeUpdate();
	}

}
