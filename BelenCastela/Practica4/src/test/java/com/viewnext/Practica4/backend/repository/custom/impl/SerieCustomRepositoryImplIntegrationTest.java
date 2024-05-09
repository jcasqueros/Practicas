package com.viewnext.Practica4.backend.repository.custom.impl;

import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.business.model.Director;
import com.viewnext.Practica4.backend.business.model.Productora;
import com.viewnext.Practica4.backend.business.model.Serie;
import com.viewnext.Practica4.backend.repository.custom.SerieCustomRepository;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SerieCustomRepositoryImplIntegrationTest {

    @Mock
    private SerieCustomRepository serieCustomRepository;

    @InjectMocks
    private SerieCustomRepositoryImpl serieCustomRepositoryImpl;

    @Mock
    EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaDelete criteriaDelete;

    @Mock
    private CriteriaQuery<Serie> criteriaQuery;

    @Mock
    private Root<Serie> root;

    @Mock
    private TypedQuery<Serie> typedQuery;

    private Serie serie;

    @BeforeEach
    public void init() {
        serie = new Serie();
        serie.setId(6L);
        serie.setTitulo("Serie 6");
        serie.setAnho(2020);
        serie.setIdDirector(new Director());
        serie.setIdProductora(new Productora());
        List<Actor> actores1 = new ArrayList<>();
        actores1.add(new Actor());
        actores1.add(new Actor());
        serie.setActores(actores1);
    }

    @Test
    public void testCreateCb() {
        Serie createdSerie = serieCustomRepositoryImpl.createCb(serie);
        assertNotNull(createdSerie);
        assertEquals(serie, createdSerie);
    }

    @Test
    public void testReadCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Serie.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Serie.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(serie);
        Serie readSerie = serieCustomRepositoryImpl.readCb(6L);
        assertNotNull(readSerie);
        assertEquals(serie, readSerie);
    }

    @Test
    public void testGetSeriesCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Serie.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Serie.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        List<Serie> series = Arrays.asList(serie);
        when(typedQuery.getResultList()).thenReturn(series);
        List<Serie> retrievedSeries = serieCustomRepositoryImpl.getSeriesCb();
        assertNotNull(retrievedSeries);
        assertEquals(1, retrievedSeries.size());
        assertEquals(serie, retrievedSeries.get(0));
    }

    @Test
    public void testUpdateCb() {
        when(entityManager.merge(serie)).thenReturn(serie);
        Serie updatedSerie = serieCustomRepositoryImpl.updateCb(serie);
        assertNotNull(updatedSerie);
        assertEquals(serie, updatedSerie);
    }

    @Test
    public void testDeleteCb() {
        Query query = Mockito.mock(Query.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createCriteriaDelete(Serie.class)).thenReturn(criteriaDelete);
        when(criteriaDelete.from(Serie.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaDelete)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);
        serieCustomRepositoryImpl.deleteCb(6L);
    }
}
