package com.viewnext.Practica4.backend.business.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.viewnext.Practica4.backend.business.bo.ProductoraBo;
import com.viewnext.Practica4.backend.business.model.Productora;
import com.viewnext.Practica4.backend.repository.ProductoraRepository;
import com.viewnext.Practica4.backend.repository.custom.ProductoraCustomRepository;
import com.viewnext.Practica4.util.converter.bo.BoToEntity;
import com.viewnext.Practica4.util.converter.bo.EntityToBo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductoraServicesImplIntegrationTest {

    @InjectMocks
    private ProductoraServicesImpl productoraServices;

    @Mock
    private ProductoraRepository productoraRepository;

    @Mock
    private ProductoraCustomRepository productoraCustomRepository;

    @Mock
    private EntityToBo entityToBo;

    @Mock
    private BoToEntity boToEntity;

    private Productora productora1;
    private Productora productora2;
    private ProductoraBo productoraBo1;
    private ProductoraBo productoraBo2;

    @BeforeEach
    public void init() {
        initMocks();
    }

    private void initMocks() {
        productora1 = new Productora();
        productora1.setId(1L);
        productora1.setNombre("Productora 1");
        productora1.setAnhoFundacion(2000);

        productora2 = new Productora();
        productora2.setId(2L);
        productora2.setNombre("Productora 2");
        productora2.setAnhoFundacion(2010);

        productoraBo1 = new ProductoraBo();
        productoraBo1.setId(1L);
        productoraBo1.setNombre("Productora 1");
        productoraBo1.setAnhoFundacion(2000);

        productoraBo2 = new ProductoraBo();
        productoraBo2.setId(2L);
        productoraBo2.setNombre("Productora 2");
        productoraBo2.setAnhoFundacion(2010);
    }

    @Test
    public void testCreate() {
        when(boToEntity.productoraBoToProductora(productoraBo1)).thenReturn(productora1);
        when(productoraRepository.save(productora1)).thenReturn(productora1);
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

        ProductoraBo createdProductora = productoraServices.create(productoraBo1);

        assertEquals(productoraBo1, createdProductora);
    }

    @Test
    public void testRead() {
        when(productoraRepository.findById(1L)).thenReturn(Optional.of(productora1));
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

        ProductoraBo readProductora = productoraServices.read(1L);

        assertEquals(productoraBo1, readProductora);
    }

    @Test
    public void testGetAll() {
        when(productoraRepository.findAll()).thenReturn(Arrays.asList(productora1, productora2));
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);
        when(entityToBo.productoraToProductoraBo(productora2)).thenReturn(productoraBo2);

        List<ProductoraBo> productoras = productoraServices.getAll();

        assertEquals(Arrays.asList(productoraBo1, productoraBo2), productoras);
    }

    @Test
    public void testDelete() {
        when(productoraRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productoraRepository).deleteById(1L);

        productoraServices.delete(1L);

        verify(productoraRepository).deleteById(1L);
    }

    @Test
    public void testUpdate() {
        when(boToEntity.productoraBoToProductora(productoraBo1)).thenReturn(productora1);
        when(productoraRepository.save(productora1)).thenReturn(productora1);
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

        ProductoraBo updatedProductora = productoraServices.update(productoraBo1);

        assertEquals(productoraBo1, updatedProductora);
    }

    @Test
    public void testGetProductorasCb() {
        when(productoraCustomRepository.getProductorasCb()).thenReturn(Arrays.asList(productora1, productora2));
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);
        when(entityToBo.productoraToProductoraBo(productora2)).thenReturn(productoraBo2);

        List<ProductoraBo> productoras = productoraServices.getProductorasCb();

        assertEquals(Arrays.asList(productoraBo1, productoraBo2), productoras);
    }

    @Test
    public void testCreateCb() {
        when(boToEntity.productoraBoToProductora(productoraBo1)).thenReturn(productora1);
        when(productoraCustomRepository.createCb(productora1)).thenReturn(productora1);
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

        ProductoraBo createdProductora = productoraServices.createCb(productoraBo1);

        assertEquals(productoraBo1, createdProductora);
    }

    @Test
    public void testReadCb() {
        when(productoraCustomRepository.readCb(1L)).thenReturn(productora1);
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

        ProductoraBo readProductora = productoraServices.readCb(1L);

        assertEquals(productoraBo1, readProductora);
    }

    @Test
    public void testUpdateCb() {
        when(boToEntity.productoraBoToProductora(productoraBo1)).thenReturn(productora1);
        when(productoraCustomRepository.updateCb(productora1)).thenReturn(productora1);
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

        ProductoraBo updatedProductora = productoraServices.updateCb(productoraBo1);

        assertEquals(productoraBo1, updatedProductora);
    }

    @Test
    public void testDeleteCb() {
        doNothing().when(productoraCustomRepository).deleteCb(1L);

        productoraServices.deleteCb(1L);

        verify(productoraCustomRepository).deleteCb(1L);
    }
}
