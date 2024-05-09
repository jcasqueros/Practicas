package com.viewnext.Practica4.backend.repository.custom.impl;

import com.viewnext.Practica4.backend.business.model.Productora;
import com.viewnext.Practica4.backend.repository.custom.ProductoraCustomRepository;

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
public class ProductoraCustomRepositoryImplIntegrationTest {

    @Mock
    private ProductoraCustomRepository productoraCustomRepository;

    @InjectMocks
    private ProductoraCustomRepositoryImpl productoraCustomRepositoryImpl;

    @Mock
    EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaDelete criteriaDelete;

    @Mock
    private CriteriaQuery<Productora> criteriaQuery;

    @Mock
    private Root<Productora> root;

    @Mock
    private TypedQuery<Productora> typedQuery;

    private Productora productora;

    @BeforeEach
    public void init() {
        productora = new Productora();
        productora.setId(6L);
        productora.setNombre("Productora 6");
        productora.setAnhoFundacion(1990);
    }

    @Test
    public void testCreateCb() {
        Productora createdProductora = productoraCustomRepositoryImpl.createCb(productora);
        assertNotNull(createdProductora);
        assertEquals(productora, createdProductora);
    }

    @Test
    public void testReadCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Productora.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Productora.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(productora);
        Productora readProductora = productoraCustomRepositoryImpl.readCb(6L);
        assertNotNull(readProductora);
        assertEquals(productora, readProductora);
    }

    @Test
    public void testGetProductorasCb() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Productora.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Productora.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        List<Productora> productoras = Arrays.asList(productora);
        when(typedQuery.getResultList()).thenReturn(productoras);
        List<Productora> retrievedProductoras = productoraCustomRepositoryImpl.getProductorasCb();
        assertNotNull(retrievedProductoras);
        assertEquals(1, retrievedProductoras.size());
        assertEquals(productora, retrievedProductoras.get(0));
    }

    @Test
    public void testUpdateCb() {
        when(entityManager.merge(productora)).thenReturn(productora);
        Productora updatedProductora = productoraCustomRepositoryImpl.updateCb(productora);
        assertNotNull(updatedProductora);
        assertEquals(productora, updatedProductora);
    }

    @Test
    public void testDeleteCb() {
        Query query = Mockito.mock(Query.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createCriteriaDelete(Productora.class)).thenReturn(criteriaDelete);
        when(criteriaDelete.from(Productora.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaDelete)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);
        productoraCustomRepositoryImpl.deleteCb(6L);
    }
}
