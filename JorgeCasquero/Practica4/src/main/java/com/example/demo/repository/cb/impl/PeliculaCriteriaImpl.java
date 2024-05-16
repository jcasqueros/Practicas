package com.example.demo.repository.cb.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.demo.exception.AlreadyExistsExeption;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Actor;
import com.example.demo.model.Director;
import com.example.demo.model.Pelicula;
import com.example.demo.model.Productora;
import com.example.demo.repository.cb.PeliculaRepositoryCriteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
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

		long totalElements = countAllPeliculas();

		return new PageImpl<>(peliculas, pageable, totalElements);
	}

	private long countAllPeliculas() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

		Root<Pelicula> Pelicula = countQuery.from(Pelicula.class);
		countQuery.select(criteriaBuilder.count(Pelicula));

		return entityManager.createQuery(countQuery).getSingleResult();
	}

	@Override
	public Optional<Pelicula> getById(long id) throws NotFoundException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pelicula> cq = cb.createQuery(Pelicula.class);
		Root<Pelicula> root = cq.from(Pelicula.class);
		cq.where(cb.equal(root.get("id"), id));
		entityManager.createQuery(cq).getSingleResult();
		TypedQuery<Pelicula> query = entityManager.createQuery(cq);

		return query.getResultStream().findFirst();

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

	@Override
	public Page<Pelicula> findAllFilter(Pageable pageable, List<String> titulos, List<Integer> anios,
			List<Director> directores, List<Productora> productoras, List<Actor> actores) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pelicula> query = criteriaBuilder.createQuery(Pelicula.class);
		Root<Pelicula> root = query.from(Pelicula.class);

		List<Predicate> predicates = new ArrayList<>();

		if (titulos != null && !titulos.isEmpty()) {
			predicates.add(root.get("title").in(titulos));
		}

		if (anios != null && !anios.isEmpty()) {
			predicates.add(root.get("debut").in(anios));
		}

		if (directores != null && !directores.isEmpty()) {
			Join<Pelicula, Director> directorJoin = root.join("directores", JoinType.INNER);
			predicates.add(directorJoin.in(directores));
		}

		if (productoras != null && !productoras.isEmpty()) {
			Join<Pelicula, Productora> producerJoin = root.join("producers", JoinType.INNER);
			predicates.add(producerJoin.in(productoras));
		}

		if (actores != null && !actores.isEmpty()) {
			Join<Pelicula, Actor> actorJoin = root.join("actors", JoinType.INNER);
			predicates.add(actorJoin.in(actores));
		}

		query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

		for (Sort.Order order : pageable.getSort()) {
			if (order.getDirection().isAscending()) {
				query.orderBy(criteriaBuilder.asc(root.get(order.getProperty())));
			} else {
				query.orderBy(criteriaBuilder.desc(root.get(order.getProperty())));
			}
		}

		TypedQuery<Pelicula> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		typedQuery.setMaxResults(pageable.getPageSize());

		List<Pelicula> Peliculas = typedQuery.getResultList();

		long totalElements = entityManager.createQuery(query).getResultList().size();

		return new PageImpl<>(Peliculas, pageable, totalElements);
	}

}
