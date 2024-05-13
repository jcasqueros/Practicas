package com.example.demo.repository.cb.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Director;
import com.example.demo.repository.cb.DirectorRepositoryCriteria;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
public class DirectorCriteriaImpl implements DirectorRepositoryCriteria {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Director> getAll(Pageable pageable) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Director> cq = cb.createQuery(Director.class);

		Root<Director> root = cq.from(Director.class);
		cq.select(root);

		String sort = String.valueOf(pageable.getSort()).split(":")[0].trim();
		cq.orderBy(cb.asc(root.get(sort)));

		TypedQuery<Director> typeQuery = entityManager.createQuery(cq);

		typeQuery.setFirstResult((int) pageable.getOffset());
		typeQuery.setMaxResults(pageable.getPageSize());

		List<Director> directores = typeQuery.getResultList();
		long totalElements = countAllDirectors();

		return new PageImpl<>(directores, pageable, totalElements);

	}

	private long countAllDirectors() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

		Root<Director> director = countQuery.from(Director.class);
		countQuery.select(criteriaBuilder.count(director));

		return entityManager.createQuery(countQuery).getSingleResult();
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
	@Transactional
	public Director create(Director director) throws AlreadyExistsExeption {

		return entityManager.merge(director);
	}

	@Override
	@Transactional
	public void deleteById(long id) throws NotFoundException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Director> cq = cb.createQuery(Director.class);
		Root<Director> root = cq.from(Director.class);
		cq.where(cb.equal(root.get("id"), id));
		entityManager.createQuery(cq).executeUpdate();
	}

}
