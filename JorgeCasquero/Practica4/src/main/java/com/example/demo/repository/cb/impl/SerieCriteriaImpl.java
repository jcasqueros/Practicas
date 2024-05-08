package com.example.demo.repository.cb.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Serie;
import com.example.demo.repository.cb.SerieRepositoryCriteria;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class SerieCriteriaImpl implements SerieRepositoryCriteria {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Serie> getAll() {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Serie> cq = cb.createQuery(Serie.class);
		Root<Serie> root = cq.from(Serie.class);
		cq.select(root);
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public Serie getById(long id) throws NotFoundException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Serie> cq = cb.createQuery(Serie.class);
		Root<Serie> root = cq.from(Serie.class);
		cq.where(cb.equal(root.get("id"), id));
		return entityManager.createQuery(cq).getSingleResult();
	}

	@Override
	public Serie create(Serie serie) throws AlreadyExistsExeption {

		return entityManager.merge(serie);
	}

	@Override
	public void deleteById(long id) throws NotFoundException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Serie> cq = cb.createQuery(Serie.class);
		Root<Serie> root = cq.from(Serie.class);
		cq.where(cb.equal(root.get("id"), id));
		entityManager.createQuery(cq).executeUpdate();
	}

}
