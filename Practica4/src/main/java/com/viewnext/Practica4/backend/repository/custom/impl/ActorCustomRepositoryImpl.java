package com.viewnext.Practica4.backend.repository.custom.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viewnext.Practica4.backend.business.bo.ActorBo;
import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.repository.custom.ActorCustomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class ActorCustomRepositoryImpl implements ActorCustomRepository{

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Actor createCb(Actor actor) {
		entityManager.persist(actor);
		return actor;
	}

	@Override
	public Actor readCb(long id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Actor> cq = cb.createQuery(Actor.class);
		Root<Actor> root = cq.from(Actor.class);
		cq.where(cb.equal(root.get("id"), id));
		return entityManager.createQuery(cq).getSingleResult();
	}

	@Override
	public List<Actor> getActoresCb() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Actor> cq = cb.createQuery(Actor.class);
		Root<Actor> root = cq.from(Actor.class);
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public Actor updateCb(Actor actor) {
		return entityManager.merge(actor);
	}

	@Override
	public void deleteCb(long id) {
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaDelete<Actor> cd = cb.createCriteriaDelete(Actor.class);
	    Root<Actor> root = cd.from(Actor.class);
	    cd.where(cb.equal(root.get("id"), id));
	    entityManager.createQuery(cd).executeUpdate();
	}
}


