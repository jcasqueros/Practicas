package com.viewnext.Practica4.backend.repository.custom.impl;

import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.business.model.Director;
import com.viewnext.Practica4.backend.business.model.Pelicula;
import com.viewnext.Practica4.backend.business.model.Productora;
import com.viewnext.Practica4.backend.repository.custom.PeliculaCustomRepository;

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
public class PeliculaCustomRepositoryImplIntegrationTest {

    @Mock
    private PeliculaCustomRepository peliculaCustomRepository;

    @InjectMocks
    private PeliculaCustomRepositoryImpl peliculaCustomRepositoryImpl;

    @Mock
    EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaDelete criteriaDelete;

    @Mock
    private CriteriaQuery<Pelicula> criteriaQuery;

    @Mock
    private Root<Pelicula> root;

    @Mock
    private TypedQuery<Pelicula> typedQuery;

    private Pelicula pelicula;

    @BeforeEach
    public void init() {
        pelicula = new Pelicula();
        pelicula.setId(6L);
        pelicula.setTitulo("Pelicula 6");
        pelicula.setAnho(2020);
        pelicula.setIdDirector(new Director());
        pelicula.setIdProductora(new Productora());
        List<Actor> actores1 = new ArrayList<>();
        actores1.add(new Actor());
        actores1.add(new Actor());
        pelicula.setActores(actores1);
    }

    @Test
    public void testCreateCb() {
        Pelicula createdPelicula = peliculaCustomRepositoryImpl.createCb(pelicula);
        assertNotNull(createdPelicula);
        assertEquals(pelicula, createdPelicula);
    }

    @Test
    public void testReadCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Pelicula.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Pelicula.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(pelicula);
        Pelicula readPelicula = peliculaCustomRepositoryImpl.readCb(6L);
        assertNotNull(readPelicula);
        assertEquals(pelicula, readPelicula);
    }

    @Test
    public void testGetPeliculasCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Pelicula.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Pelicula.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        List<Pelicula> peliculas = Arrays.asList(pelicula);
        when(typedQuery.getResultList()).thenReturn(peliculas);
        List<Pelicula> retrievedPeliculas = peliculaCustomRepositoryImpl.getPeliculasCb();
        assertNotNull(retrievedPeliculas);
        assertEquals(1, retrievedPeliculas.size());
        assertEquals(pelicula, retrievedPeliculas.get(0));
    }

    @Test
    public void testUpdateCb() {
        when(entityManager.merge(pelicula)).thenReturn(pelicula);
        Pelicula updatedPelicula = peliculaCustomRepositoryImpl.updateCb(pelicula);
        assertNotNull(updatedPelicula);
        assertEquals(pelicula, updatedPelicula);
    }

    @Test
    public void testDeleteCb() {
        Query query = Mockito.mock(Query.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createCriteriaDelete(Pelicula.class)).thenReturn(criteriaDelete);
        when(criteriaDelete.from(Pelicula.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaDelete)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);
        peliculaCustomRepositoryImpl.deleteCb(6L);
    }
}
