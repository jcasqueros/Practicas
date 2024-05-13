package com.example.demo.repository.cb.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Actor;
import com.example.demo.model.Pelicula;
import com.example.demo.repository.cb.PeliculaRepositoryCriteria;
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
public class PeliculaCriteriaImpl implements PeliculaRepositoryCriteria {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Pelicula> getAll(Pageable pageable) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pelicula> cq = cb.createQuery(Pelicula.class);

		Root<Pelicula> root = cq.from(Pelicula.class);
		cq.select(root);

		String sort = String.valueOf(pageable.getSort()).split(":")[0].trim();
		cq.orderBy(cb.asc(root.get(sort)));

		TypedQuery<Pelicula> query = entityManager.createQuery(cq);

		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<Pelicula> peliculas = query.getResultList();

		long totalElements = countAllFilms();

		return new PageImpl<>(peliculas, pageable, totalElements);
	}

	private long countAllFilms() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

		Root<Pelicula> film = countQuery.from(Pelicula.class);
		countQuery.select(criteriaBuilder.count(film));

		return entityManager.createQuery(countQuery).getSingleResult();
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
	@Transactional
	public Pelicula create(Pelicula pelicula) throws AlreadyExistsExeption {

		return entityManager.merge(pelicula);
	}

	@Override
	@Transactional
	public void deleteById(long id) throws NotFoundException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Actor> cq = cb.createQuery(Actor.class);
		Root<Actor> root = cq.from(Actor.class);
		cq.where(cb.equal(root.get("id"), id));
		entityManager.createQuery(cq).executeUpdate();
	}

}
