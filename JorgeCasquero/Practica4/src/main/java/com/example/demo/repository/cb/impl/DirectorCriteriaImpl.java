package com.example.demo.repository.cb.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Director;
import com.example.demo.repository.cb.DirectorRepositoryCriteria;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


@Repository
public class DirectorCriteriaImpl implements DirectorRepositoryCriteria{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Director> getAll() {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Director> cq = cb.createQuery(Director.class);
		Root<Director> root = cq.from(Director.class);
		cq.select(root);
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public Director getById(long id) throws NotFoundException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Director> cq = cb.createQuery(Director.class);
		Root<Director> root = cq.from(Director.class);
		cq.where(cb.equal(root.get("id"), id));
		return entityManager.createQuery(cq).getSingleResult();
	}

	@Override
	public Director create(Director director) throws AlreadyExistsExeption {

		return entityManager.merge(director);
	}

	@Override
	public void deleteById(long id) throws NotFoundException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Director> cq = cb.createQuery(Director.class);
		Root<Director> root = cq.from(Director.class);
		cq.where(cb.equal(root.get("id"), id));
		entityManager.createQuery(cq).executeUpdate();
	}

}
