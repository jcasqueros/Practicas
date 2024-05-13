package com.example.demo.repository.cb.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Actor;
import com.example.demo.repository.cb.ActorRepositoryCriteria;
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
public class ActorCriteriaImpl implements ActorRepositoryCriteria {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Actor> getAll(Pageable pageable) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Actor> cq = cb.createQuery(Actor.class);

		Root<Actor> root = cq.from(Actor.class);
		cq.select(root);

		String sort = String.valueOf(pageable.getSort()).split(":")[0].trim();
		cq.orderBy(cb.asc(root.get(sort)));

		TypedQuery<Actor> typeQuery = entityManager.createQuery(cq);

		typeQuery.setFirstResult((int) pageable.getOffset());
		typeQuery.setMaxResults(pageable.getPageSize());

		List<Actor> actores = typeQuery.getResultList();
		long totalElements = countAllActors();

		return new PageImpl<>(actores, pageable, totalElements);
	}

	private long countAllActors() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

		Root<Actor> actor = countQuery.from(Actor.class);
		countQuery.select(criteriaBuilder.count(actor));

		return entityManager.createQuery(countQuery).getSingleResult();
	}

	@Override
	public Optional<Actor> getById(long id) throws NotFoundException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Actor> cq = cb.createQuery(Actor.class);
		Root<Actor> root = cq.from(Actor.class);
		cq.where(cb.equal(root.get("id"), id));
		return Optional.of(entityManager.createQuery(cq).getSingleResult());
	}

	@Override
	@Transactional
	public Actor create(Actor actor) throws AlreadyExistsExeption, NotFoundException {

		if (getById(actor.getIdActor()).isEmpty()) {
			entityManager.persist(actor);
		} else {
			entityManager.merge(actor);
		}

		return actor;
	}

	@Override
	@Transactional
	public void deleteById(long id) throws NotFoundException {
		Optional<Actor> actor = getById(id);
		if (actor != null) {
			entityManager.remove(actor.get());
			entityManager.flush();
		} else {
			throw new NotFoundException("No se encontró un actor con el ID " + id);
		}

	}

}
