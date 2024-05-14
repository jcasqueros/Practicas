package com.viewnext.Practica4.backend.repository.custom.impl;

import com.viewnext.Practica4.backend.business.model.Director;
import com.viewnext.Practica4.backend.repository.custom.DirectorCustomRepository;

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
public class DirectorCustomRepositoryImplIntegrationTest {

    @Mock
    private DirectorCustomRepository directorCustomRepository;

    @InjectMocks
    private DirectorCustomRepositoryImpl directorCustomRepositoryImpl;

    @Mock
    EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaDelete criteriaDelete;

    @Mock
    private CriteriaQuery<Director> criteriaQuery;

    @Mock
    private Root<Director> root;

    @Mock
    private TypedQuery<Director> typedQuery;

    private Director director;

    @BeforeEach
    public void init() {
        director = new Director();
        director.setId(6L);
        director.setNombre("Director 6");
        director.setEdad(30);
        director.setNacionalidad("Española");
    }

    @Test
    public void testCreateCb() {
        Director createdDirector = directorCustomRepositoryImpl.createCb(director);
        assertNotNull(createdDirector);
        assertEquals(director, createdDirector);
    }

    @Test
    public void testReadCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Director.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Director.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(director);
        Director readDirector = directorCustomRepositoryImpl.readCb(6L);
        assertNotNull(readDirector);
        assertEquals(director, readDirector);
    }

    @Test
    public void testGetDirectoresCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Director.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Director.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        List<Director> directors = Arrays.asList(director);
        when(typedQuery.getResultList()).thenReturn(directors);
        List<Director> retrievedDirectors = directorCustomRepositoryImpl.getDirectoresCb();
        assertNotNull(retrievedDirectors);
        assertEquals(1, retrievedDirectors.size());
        assertEquals(director, retrievedDirectors.get(0));
    }

    @Test
    public void testUpdateCb() {
        when(entityManager.merge(director)).thenReturn(director);
        Director updatedDirector = directorCustomRepositoryImpl.updateCb(director);
        assertNotNull(updatedDirector);
        assertEquals(director, updatedDirector);
    }

    @Test
    public void testDeleteCb() {
        Query query = Mockito.mock(Query.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createCriteriaDelete(Director.class)).thenReturn(criteriaDelete);
        when(criteriaDelete.from(Director.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaDelete)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);
        directorCustomRepositoryImpl.deleteCb(6L);
    }
}
