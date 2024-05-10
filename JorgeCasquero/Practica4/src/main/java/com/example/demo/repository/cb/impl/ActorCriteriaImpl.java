package com.example.demo.repository.cb.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Actor;
import com.example.demo.repository.cb.ActorRepositoryCriteria;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class ActorCriteriaImpl implements ActorRepositoryCriteria {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Actor> getAll() {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Actor> cq = cb.createQuery(Actor.class);
		Root<Actor> root = cq.from(Actor.class);
		cq.select(root);
		return entityManager.createQuery(cq).getResultList();
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
	public Actor create(Actor actor) throws AlreadyExistsExeption, NotFoundException {

		if (getById(actor.getIdActor()).isEmpty()) {
			entityManager.persist(actor);
		} else {
			entityManager.merge(actor);
		}

		return actor;
	}

	@Override
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
