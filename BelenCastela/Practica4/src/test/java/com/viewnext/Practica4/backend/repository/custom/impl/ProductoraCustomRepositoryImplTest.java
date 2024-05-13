package com.viewnext.Practica4.backend.repository.custom.impl;

import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.viewnext.Practica4.backend.business.model.Productora;

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
public class ProductoraCustomRepositoryImplTest {

    @InjectMocks
    private ProductoraCustomRepositoryImpl productoraCustomRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;
    
    @Mock
    private CriteriaDelete criteriaDelete;

    @Mock
    private CriteriaQuery<Productora> criteriaQuery;

    @Mock
    private Root<Productora> root;

    private Productora productora;

    @BeforeEach
    public void init() {
        initMocks();
    }
    
    void initMocks() {
        productora = new Productora();
        productora.setId(1L);
        productora.setNombre("Productora 1");
        productora.setAnhoFundacion(2000);
    }

    @Test
    public void testCreateCb() {
        Productora createdProductora = productoraCustomRepository.createCb(productora);

        verify(entityManager).persist(productora);
        assertEquals(productora, createdProductora);
    }

    @Test
    public void testReadCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Productora.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Productora.class)).thenReturn(root);
        Path<Object> idPath = mock(Path.class);
        when(root.get("id")).thenReturn(idPath);
        when(criteriaBuilder.equal(idPath, 1L)).thenReturn(mock(Predicate.class));
        when(criteriaQuery.where((Predicate) any())).thenReturn(criteriaQuery);
        Query query = mock(Query.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(productora);

        Productora readProductora = productoraCustomRepository.readCb(1L);

        verify(entityManager).createQuery(criteriaQuery);
        assertEquals(productora, readProductora);
    }

    @Test
    public void testGetProductorasCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Productora.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Productora.class)).thenReturn(root);
        Query query = mock(Query.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(productora));

        List<Productora> productoras = productoraCustomRepository.getProductorasCb();

        verify(entityManager).createQuery(criteriaQuery);
        assertEquals(Arrays.asList(productora), productoras);
    }


    @Test
    public void testUpdateCb() {
        when(entityManager.merge(productora)).thenReturn(productora);

        Productora updatedProductora = productoraCustomRepository.updateCb(productora);

        verify(entityManager).merge(productora);
        assertEquals(productora, updatedProductora);
    }

    @Test
    public void testDeleteCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createCriteriaDelete(Productora.class)).thenReturn(criteriaDelete);
        when(criteriaDelete.from(Productora.class)).thenReturn(root);
        when(root.get("id")).thenReturn(mock(Path.class));
        when(criteriaBuilder.equal(root.get("id"), 1L)).thenReturn(mock(Predicate.class));
        when(criteriaDelete.where((Predicate) any())).thenReturn(criteriaDelete);
        Query query = mock(Query.class);
        when(entityManager.createQuery(criteriaDelete)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        productoraCustomRepository.deleteCb(1L);

        verify(entityManager).createQuery(criteriaDelete);
        verify(query).executeUpdate();
    }

}
