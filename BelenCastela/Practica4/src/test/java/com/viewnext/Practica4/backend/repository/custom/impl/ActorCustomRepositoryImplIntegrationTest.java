package com.viewnext.Practica4.backend.repository.custom.impl;

import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.repository.custom.ActorCustomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActorCustomRepositoryImplIntegrationTest {

	@Mock
	private ActorCustomRepository actorCustomRepository;

	@InjectMocks
	private ActorCustomRepositoryImpl actorCustomRepositoryImpl;

	@Mock
	EntityManager entityManager;

	@Mock
	private CriteriaBuilder criteriaBuilder;

	@Mock
	private CriteriaDelete criteriaDelete;

	@Mock
	private CriteriaQuery<Actor> criteriaQuery;

	@Mock
	private Root<Actor> root;

	@Mock
	private TypedQuery<Actor> typedQuery;

	private Actor actor;

	@BeforeEach
	public void init() {
		actor = new Actor();
		actor.setId(6L);
		actor.setNombre("Actor 6");
		actor.setEdad(30);
		actor.setNacionalidad("Española");
	}

	@Test
	public void testCreateCb() {
		Actor createdActor = actorCustomRepositoryImpl.createCb(actor);
		assertNotNull(createdActor);
		assertEquals(actor, createdActor);
	}

	@Test
	public void testReadCb() {
		when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
		when(criteriaBuilder.createQuery(Actor.class)).thenReturn(criteriaQuery);
		when(criteriaQuery.from(Actor.class)).thenReturn(root);
		when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(actor);
		Actor readActor = actorCustomRepositoryImpl.readCb(6L);
		assertNotNull(readActor);
		assertEquals(actor, readActor);
	}

	@Test
	public void testGetActoresCb() {
		when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
		when(criteriaBuilder.createQuery(Actor.class)).thenReturn(criteriaQuery);
		when(criteriaQuery.from(Actor.class)).thenReturn(root);
		when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
		List<Actor> actors = Arrays.asList(actor);
		when(typedQuery.getResultList()).thenReturn(actors);
		List<Actor> retrievedActors = actorCustomRepositoryImpl.getActoresCb();
		assertNotNull(retrievedActors);
		assertEquals(1, retrievedActors.size());
		assertEquals(actor, retrievedActors.get(0));
	}
	
	@Test
	public void testUpdateCb() {
		when(entityManager.merge(actor)).thenReturn(actor);
		Actor updatedActor = actorCustomRepositoryImpl.updateCb(actor);
		assertNotNull(updatedActor);
		assertEquals(actor, updatedActor);
	}

	@Test
	public void testDeleteCb() {
		Query query = Mockito.mock(Query.class);
		when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
		when(criteriaBuilder.createCriteriaDelete(Actor.class)).thenReturn(criteriaDelete);
		when(criteriaDelete.from(Actor.class)).thenReturn(root);
		when(entityManager.createQuery(criteriaDelete)).thenReturn(query);
		when(query.executeUpdate()).thenReturn(1);
		actorCustomRepositoryImpl.deleteCb(6L);
	}
}

