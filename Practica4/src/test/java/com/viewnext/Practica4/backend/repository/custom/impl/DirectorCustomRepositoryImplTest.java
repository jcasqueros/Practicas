package com.viewnext.Practica4.backend.repository.custom.impl;

import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.viewnext.Practica4.backend.business.model.Director;

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
public class DirectorCustomRepositoryImplTest {

    @InjectMocks
    private DirectorCustomRepositoryImpl directorCustomRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;
    
    @Mock
    private CriteriaDelete criteriaDelete;

    @Mock
    private CriteriaQuery<Director> criteriaQuery;

    @Mock
    private Root<Director> root;

    private Director director;

    @BeforeEach
    public void init() {
        initMocks();
    }
    
    void initMocks() {
        director = new Director();
        director.setId(1L);
        director.setNombre("Director 1");
        director.setEdad(40);
        director.setNacionalidad("Española");
    }

    @Test
    public void testCreateCb() {
        Director createdDirector = directorCustomRepository.createCb(director);

        verify(entityManager).persist(director);
        assertEquals(director, createdDirector);
    }

    @Test
    public void testReadCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Director.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Director.class)).thenReturn(root);
        Path<Object> idPath = mock(Path.class);
        when(root.get("id")).thenReturn(idPath);
        when(criteriaBuilder.equal(idPath, 1L)).thenReturn(mock(Predicate.class));
        when(criteriaQuery.where((Predicate) any())).thenReturn(criteriaQuery);
        Query query = mock(Query.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(director);

        Director readDirector = directorCustomRepository.readCb(1L);

        verify(entityManager).createQuery(criteriaQuery);
        assertEquals(director, readDirector);
    }

    @Test
    public void testGetDirectoresCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Director.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Director.class)).thenReturn(root);
        Query query = mock(Query.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(director));

        List<Director> directors = directorCustomRepository.getDirectoresCb();

        verify(entityManager).createQuery(criteriaQuery);
        assertEquals(Arrays.asList(director), directors);
    }


    @Test
    public void testUpdateCb() {
        when(entityManager.merge(director)).thenReturn(director);

        Director updatedDirector = directorCustomRepository.updateCb(director);

        verify(entityManager).merge(director);
        assertEquals(director, updatedDirector);
    }

    @Test
    public void testDeleteCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createCriteriaDelete(Director.class)).thenReturn(criteriaDelete);
        when(criteriaDelete.from(Director.class)).thenReturn(root);
        when(root.get("id")).thenReturn(mock(Path.class));
        when(criteriaBuilder.equal(root.get("id"), 1L)).thenReturn(mock(Predicate.class));
        when(criteriaDelete.where((Predicate) any())).thenReturn(criteriaDelete);
        Query query = mock(Query.class);
        when(entityManager.createQuery(criteriaDelete)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        directorCustomRepository.deleteCb(1L);

        verify(entityManager).createQuery(criteriaDelete);
        verify(query).executeUpdate();
    }

}
