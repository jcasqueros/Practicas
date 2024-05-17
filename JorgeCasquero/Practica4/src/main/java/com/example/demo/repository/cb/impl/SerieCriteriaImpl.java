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
import com.example.demo.model.Productora;
import com.example.demo.model.Serie;
import com.example.demo.repository.cb.SerieRepositoryCriteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
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
	public Optional<Serie> getById(long id) throws NotFoundException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Serie> cq = cb.createQuery(Serie.class);
		Root<Serie> root = cq.from(Serie.class);
		cq.where(cb.equal(root.get("id"), id));
		TypedQuery<Serie> query = entityManager.createQuery(cq);

		return query.getResultStream().findFirst();
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

	@Override
	public Page<Serie> findAllFilter(Pageable pageable, List<String> titulos, List<Integer> anio,
			List<Director> directores, List<Productora> productora, List<Actor> actores) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Serie> query = criteriaBuilder.createQuery(Serie.class);
		Root<Serie> root = query.from(Serie.class);

		List<Predicate> predicates = new ArrayList<>();

		if (titulos != null && !titulos.isEmpty()) {
			predicates.add(root.get("title").in(titulos));
		}

		if (anio != null && !anio.isEmpty()) {
			predicates.add(root.get("debut").in(anio));
		}

		if (directores != null && !directores.isEmpty()) {
			Join<Serie, Director> directorJoin = root.join("directores", JoinType.INNER);
			predicates.add(directorJoin.in(directores));
		}

		if (productora != null && !productora.isEmpty()) {
			Join<Serie, Productora> producerJoin = root.join("producers", JoinType.INNER);
			predicates.add(producerJoin.in(productora));
		}

		if (actores != null && !actores.isEmpty()) {
			Join<Serie, Actor> actorJoin = root.join("actors", JoinType.INNER);
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

		TypedQuery<Serie> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		typedQuery.setMaxResults(pageable.getPageSize());

		List<Serie> Series= typedQuery.getResultList();

		long totalElements = entityManager.createQuery(query).getResultList().size();

		return new PageImpl<>(Series, pageable, totalElements);
	}

}
