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
import com.example.demo.repository.cb.ActorRepositoryCriteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
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

	@Override
	public Page<Actor> findAllFilter(Pageable pageable, List<String> nombres, List<Integer> edades,
			List<String> nacionalidades) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Actor> cq = cb.createQuery(Actor.class);
		Root<Actor> root = cq.from(Actor.class);

		List<Predicate> predicates = new ArrayList<>();
		if (nombres != null && nombres.isEmpty()) {

			predicates.add(root.get("nombre").in(nombres));

		}
		if (edades != null && edades.isEmpty()) {

			predicates.add(root.get("edad").in(edades));

		}
		if (nacionalidades != null && nacionalidades.isEmpty()) {

			predicates.add(root.get("nacionalidad").in(nacionalidades));

		}
		cq.where(cb.and(predicates.toArray(new Predicate[0])));
		for (Sort.Order order : pageable.getSort()) {
			if (order.getDirection().isAscending()) {

				cq.orderBy(cb.asc(root.get(order.getProperty())));

			} else {
				cq.orderBy(cb.desc(root.get(order.getProperty())));
			}
		}

		TypedQuery<Actor> typedQuery = entityManager.createQuery(cq);
		typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		typedQuery.setMaxResults(pageable.getPageSize());

		List<Actor> actores = typedQuery.getResultList();
		long totalElements = entityManager.createQuery(cq).getResultList().size();

		return new PageImpl<>(actores, pageable, totalElements);

	}

	@Override
	public List<Actor> findByNameAndAgeCriteria(String nombre, int name) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Actor> cq = cb.createQuery(Actor.class);
		Root<Actor> root = cq.from(Actor.class);
		Predicate nombrePredicado = cb.equal(root.get("nombre"), nombre);
		Predicate edadPredicado = cb.equal(root.get("edad"), nombre);
		cq.where(nombrePredicado, edadPredicado);

		TypedQuery<Actor> query = entityManager.createQuery(cq);

		return query.getResultStream().toList();
	}

}