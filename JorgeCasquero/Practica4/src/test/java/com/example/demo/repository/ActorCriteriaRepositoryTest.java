package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.exception.AlreadyExistsExeption;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Actor;
import com.example.demo.repository.cb.impl.ActorCriteriaImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@ExtendWith(MockitoExtension.class)
public class ActorCriteriaRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Actor> criteriaQuery;

    @Mock
    private Root<Actor> root;

    @Mock
    private TypedQuery<Actor> typedQuery;

    @InjectMocks
    private ActorCriteriaImpl actorCriteria;

    private Actor actor;
    private Pageable pageable;
    private List<Actor> actorList;
    private Page<Actor> actorPage;

    @BeforeEach
    void setUp() {
        actor = new Actor();
        actor.setIdActor(1L);
        actor.setNombre("Test Actor");
        actor.setEdad(30);
        actor.setNacionalidad("Test Nationality");

        pageable = PageRequest.of(0, 5);
        actorList = Collections.singletonList(actor);
        actorPage = new PageImpl<>(actorList, pageable, actorList.size());
    }

    @Test
    void testGetAll() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Actor.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Actor.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(actorList);

        Page<Actor> result = actorCriteria.getAll(pageable);
        assertEquals(actorPage.getContent(), result.getContent());
        assertEquals(actorPage.getTotalElements(), result.getTotalElements());
    }

    @Test
    void testGetById() throws NotFoundException {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Actor.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Actor.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(actor);

        Optional<Actor> result = actorCriteria.getById(1L);
        assertEquals(Optional.of(actor), result);
    }

    @Test
    void testCreate() throws AlreadyExistsExeption, NotFoundException {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Actor.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Actor.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        Actor result = actorCriteria.create(actor);
        assertEquals(actor, result);
        verify(entityManager).persist(actor);
    }

    @Test
    void testDeleteById() throws NotFoundException {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Actor.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Actor.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(actor);

        actorCriteria.deleteById(1L);
        verify(entityManager).remove(actor);
    }

    @Test
    void testFindAllFilter() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Actor.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Actor.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(actorList);

        Page<Actor> result = actorCriteria.findAllFilter(pageable, Collections.singletonList("Test Actor"), Collections.singletonList(30), Collections.singletonList("Test Nationality"));
        assertEquals(actorPage.getContent(), result.getContent());
        assertEquals(actorPage.getTotalElements(), result.getTotalElements());
    }

    @Test
    void testFindByNameAndAgeCriteria() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Actor.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Actor.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(actorList);

        List<Actor> result = actorCriteria.findByNameAndAgeCriteria("Test Actor", 30);
        assertEquals(actorList, result);
    }
}
