package com.viewnext.Practica4.backend.repository.custom.impl;

import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.business.model.Director;
import com.viewnext.Practica4.backend.business.model.Pelicula;
import com.viewnext.Practica4.backend.business.model.Productora;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
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
public class PeliculaCustomRepositoryImplTest {

    @InjectMocks
    private PeliculaCustomRepositoryImpl peliculaCustomRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;
    
    @Mock
    private CriteriaDelete criteriaDelete;

    @Mock
    private CriteriaQuery<Pelicula> criteriaQuery;

    @Mock
    private Root<Pelicula> root;

    private Pelicula pelicula;

    @BeforeEach
    public void init() {
        initMocks();
    }
    
    void initMocks() {
        pelicula = new Pelicula();
        pelicula.setId(1L);
        pelicula.setTitulo("Pelicula 1");
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
        Pelicula createdPelicula = peliculaCustomRepository.createCb(pelicula);

        verify(entityManager).persist(pelicula);
        assertEquals(pelicula, createdPelicula);
    }

    @Test
    public void testReadCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Pelicula.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Pelicula.class)).thenReturn(root);
        Path<Object> idPath = mock(Path.class);
        when(root.get("id")).thenReturn(idPath);
        when(criteriaBuilder.equal(idPath, 1L)).thenReturn(mock(Predicate.class));
        when(criteriaQuery.where((Predicate) any())).thenReturn(criteriaQuery);
        Query query = mock(Query.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(pelicula);

        Pelicula readPelicula = peliculaCustomRepository.readCb(1L);

        verify(entityManager).createQuery(criteriaQuery);
        assertEquals(pelicula, readPelicula);
    }

    @Test
    public void testGetPeliculasCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Pelicula.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Pelicula.class)).thenReturn(root);
        Query query = mock(Query.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(pelicula));

        List<Pelicula> peliculas = peliculaCustomRepository.getPeliculasCb();

        verify(entityManager).createQuery(criteriaQuery);
        assertEquals(Arrays.asList(pelicula), peliculas);
    }


    @Test
    public void testUpdateCb() {
        when(entityManager.merge(pelicula)).thenReturn(pelicula);

        Pelicula updatedPelicula = peliculaCustomRepository.updateCb(pelicula);

        verify(entityManager).merge(pelicula);
        assertEquals(pelicula, updatedPelicula);
    }

    @Test
    public void testDeleteCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createCriteriaDelete(Pelicula.class)).thenReturn(criteriaDelete);
        when(criteriaDelete.from(Pelicula.class)).thenReturn(root);
        when(root.get("id")).thenReturn(mock(Path.class));
        when(criteriaBuilder.equal(root.get("id"), 1L)).thenReturn(mock(Predicate.class));
        when(criteriaDelete.where((Predicate) any())).thenReturn(criteriaDelete);
        Query query = mock(Query.class);
        when(entityManager.createQuery(criteriaDelete)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        peliculaCustomRepository.deleteCb(1L);

        verify(entityManager).createQuery(criteriaDelete);
        verify(query).executeUpdate();
    }

}
