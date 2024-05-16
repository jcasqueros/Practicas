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
import com.example.demo.model.Pelicula;
import com.example.demo.model.Productora;
import com.example.demo.repository.cb.ProductoraRepositoryCriteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class ProductoraCriteriaImpl implements ProductoraRepositoryCriteria {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Productora> getAll(Pageable pageable) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Productora> cq = cb.createQuery(Productora.class);
		Root<Productora> root = cq.from(Productora.class);
		String sort = String.valueOf(pageable.getSort()).split(":")[0].trim();
		cq.orderBy(cb.asc(root.get(sort)));

		TypedQuery<Productora> query = entityManager.createQuery(cq);

		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<Productora> productoras = query.getResultList();

		long totalElements = countAllProductoras();

		return new PageImpl<>(productoras, pageable, totalElements);
	}

	private long countAllProductoras() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

		Root<Pelicula> film = countQuery.from(Pelicula.class);
		countQuery.select(criteriaBuilder.count(film));

		return entityManager.createQuery(countQuery).getSingleResult();
	}

	@Override
	public Optional<Productora> getById(long id) throws NotFoundException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Productora> cq = cb.createQuery(Productora.class);
		Root<Productora> root = cq.from(Productora.class);
		cq.where(cb.equal(root.get("id"), id));
		TypedQuery<Productora> query = entityManager.createQuery(cq);

		return query.getResultStream().findFirst();
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

	@Override
	public Page<Productora> findAndFilter(Pageable pageable, List<String> nombres, List<Integer> anios) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Productora> cq = cb.createQuery(Productora.class);

		Root<Productora> root = cq.from(Productora.class);

		List<Predicate> predicates = new ArrayList<>();

		if (nombres != null && !nombres.isEmpty()) {
			predicates.add(root.get("nombre").in(nombres));
		}
		if (anios != null && !anios.isEmpty()) {
			predicates.add(root.get("anioFundacion").in(anios));
		}

		cq.where(cb.and(predicates.toArray(new Predicate[0])));

		for (Sort.Order order : pageable.getSort()) {
			if (order.getDirection().isAscending()) {
				cq.orderBy(cb.asc(root.get(order.getProperty())));
			}
		}

		TypedQuery<Productora> typedQuery = entityManager.createQuery(cq);
		typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		typedQuery.setMaxResults(pageable.getPageSize());

		List<Productora> productoras = typedQuery.getResultList();

		// long totalElements = entityManager.createQuery(query).getResultList().size();
		long totalElements = typedQuery.getResultList().size();

		return new PageImpl<>(productoras, pageable, totalElements);
	}
}
