package com.example.demo.repository.cb.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Productora;
import com.example.demo.repository.cb.ProductoraRepositoryCriteria;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class ProductoraCriteriaImpl implements ProductoraRepositoryCriteria {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Productora> getAll() {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Productora> cq = cb.createQuery(Productora.class);
		Root<Productora> root = cq.from(Productora.class);
		cq.select(root);
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public Productora getById(long id) throws NotFoundException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Productora> cq = cb.createQuery(Productora.class);
		Root<Productora> root = cq.from(Productora.class);
		cq.where(cb.equal(root.get("id"), id));
		return entityManager.createQuery(cq).getSingleResult();
	}

	@Override
	public Productora create(Productora productora) throws AlreadyExistsExeption {

		return entityManager.merge(productora);
	}

	@Override
	public void deleteById(long id) throws NotFoundException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Productora> cq = cb.createQuery(Productora.class);
		Root<Productora> root = cq.from(Productora.class);
		cq.where(cb.equal(root.get("id"), id));
		entityManager.createQuery(cq).executeUpdate();
	}

}
