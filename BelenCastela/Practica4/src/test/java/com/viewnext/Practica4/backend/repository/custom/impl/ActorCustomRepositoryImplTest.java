package com.viewnext.Practica4.backend.repository.custom.impl;

import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.viewnext.Practica4.backend.business.model.Actor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActorCustomRepositoryImplTest {

    @InjectMocks
    private ActorCustomRepositoryImpl actorCustomRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;
    
    @Mock
    private CriteriaDelete criteriaDelete;

    @Mock
    private CriteriaQuery<Actor> criteriaQuery;

    @Mock
    private Root<Actor> root;

    private Actor actor;

    @BeforeEach
    public void init() {
        initMocks();
    }
    
    void initMocks() {
    	actor = new Actor();
        actor.setId(1L);
        actor.setNombre("Actor 1");
        actor.setEdad(30);
        actor.setNacionalidad("Española");
    }

    @Test
    public void testCreateCb() {
        Actor createdActor = actorCustomRepository.createCb(actor);

        verify(entityManager).persist(actor);
        assertEquals(actor, createdActor);
    }

    @Test
    public void testReadCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Actor.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Actor.class)).thenReturn(root);
        Path<Object> idPath = mock(Path.class);
        when(root.get("id")).thenReturn(idPath);
        when(criteriaBuilder.equal(idPath, 1L)).thenReturn(mock(Predicate.class));
        when(criteriaQuery.where((Predicate) any())).thenReturn(criteriaQuery);
        Query query = mock(Query.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(actor);

        Actor readActor = actorCustomRepository.readCb(1L);

        verify(entityManager).createQuery(criteriaQuery);
        assertEquals(actor, readActor);
    }

    @Test
    public void testGetActoresCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Actor.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Actor.class)).thenReturn(root);
        Query query = mock(Query.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(actor));

        List<Actor> actors = actorCustomRepository.getActoresCb();

        verify(entityManager).createQuery(criteriaQuery);
        assertEquals(Arrays.asList(actor), actors);
    }


    @Test
    public void testUpdateCb() {
        when(entityManager.merge(actor)).thenReturn(actor);

        Actor updatedActor = actorCustomRepository.updateCb(actor);

        verify(entityManager).merge(actor);
        assertEquals(actor, updatedActor);
    }

    @Test
    public void testDeleteCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createCriteriaDelete(Actor.class)).thenReturn(criteriaDelete);
        when(criteriaDelete.from(Actor.class)).thenReturn(root);
        when(root.get("id")).thenReturn(mock(Path.class));
        when(criteriaBuilder.equal(root.get("id"), 1L)).thenReturn(mock(Predicate.class));
        when(criteriaDelete.where((Predicate) any())).thenReturn(criteriaDelete);
        Query query = mock(Query.class);
        when(entityManager.createQuery(criteriaDelete)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        actorCustomRepository.deleteCb(1L);

        verify(entityManager).createQuery(criteriaDelete);
        verify(query).executeUpdate();
    }

}
