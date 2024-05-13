package com.example.demo.repository.cb.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Pelicula;
import com.example.demo.model.Serie;
import com.example.demo.repository.cb.SerieRepositoryCriteria;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class SerieCriteriaImpl implements SerieRepositoryCriteria {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Serie> getAll(Pageable pageable) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Serie> cq = cb.createQuery(Serie.class);

		Root<Serie> root = cq.from(Serie.class);
		cq.select(root);

		String sort = String.valueOf(pageable.getSort()).split(":")[0].trim();
		cq.orderBy(cb.asc(root.get(sort)));

		TypedQuery<Serie> query = entityManager.createQuery(cq);

		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<Serie> series = query.getResultList();

		long totalElements = countAllSerie();

		return new PageImpl<>(series, pageable, totalElements);
	}

	private long countAllSerie() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

		Root<Serie> film = countQuery.from(Serie.class);
		countQuery.select(criteriaBuilder.count(film));

		return entityManager.createQuery(countQuery).getSingleResult();
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
