package com.viewnext.Practica3Crud.backend.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.viewnext.Practica3Crud.backend.business.model.Usuario;
import com.viewnext.Practica3Crud.backend.repository.custom.UsuarioCustomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class UsuarioCustomRepositoryImpl implements UsuarioCustomRepository{
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<Usuario> getUsersByAgeName(List<Integer> edades, List<String> nombres) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
		Root<Usuario> root = cq.from(Usuario.class);
		
		List<Predicate> predicates = new ArrayList<>();
		if (edades != null && !edades.isEmpty()) {
			predicates.add(root.get("mAge").in(edades));
		}
		if (nombres != null && !nombres.isEmpty()) {
	        predicates.add(root.get("mName").in(nombres));
	    }
		
		cq.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Usuario> query = entityManager.createQuery(cq);
		
		return query.getResultList();
	}

}
