package com.viewnext.Practica4.backend.business.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
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

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProductoraServicesImplTest {

    private Productora productora1, productora2;
    private ProductoraBo productoraBo1, productoraBo2;

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
    void testCreate() {
        // Arrange
        when(boToEntity.productoraBoToProductora(productoraBo1)).thenReturn(productora1);
        when(productoraRepository.save(productora1)).thenReturn(productora1);
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

        // Act
        ProductoraBo result = productoraServices.create(productoraBo1);

        // Assert
        assertEquals(productoraBo1, result);
    }

    @Test
    void testRead() {
        // Arrange
        when(productoraRepository.findById(1L)).thenReturn(Optional.of(productora1));
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

        // Act
        ProductoraBo result = productoraServices.read(1L);

        // Assert
        assertEquals(productoraBo1, result);
    }

    @Test
    void testGetAll() {
        // Arrange
        List<Productora> productoras = new ArrayList<>();
        productoras.add(productora1);
        productoras.add(productora2);
        when(productoraRepository.findAll()).thenReturn(productoras);
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);
        when(entityToBo.productoraToProductoraBo(productora2)).thenReturn(productoraBo2);

        // Act
        List<ProductoraBo> result = productoraServices.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(productoraBo1, result.get(0));
        assertEquals(productoraBo2, result.get(1));
    }

    @Test
    void testDelete() {
        // Arrange
        when(productoraRepository.existsById(1L)).thenReturn(true);
        // Act
        productoraServices.delete(1L);

        // Assert
    }

    @Test
    void testUpdate() {
        // Arrange
        when(boToEntity.productoraBoToProductora(productoraBo1)).thenReturn(productora1);
        when(productoraRepository.save(productora1)).thenReturn(productora1);
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

        // Act
        ProductoraBo result = productoraServices.update(productoraBo1);

        // Assert
        assertEquals(productoraBo1, result);
    }

    @Test
    void testGetProductorasCb() {
        // Arrange
        List<Productora> productoras = new ArrayList<>();
        productoras.add(productora1);
        productoras.add(productora2);
        when(productoraCustomRepository.getProductorasCb()).thenReturn(productoras);
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);
        when(entityToBo.productoraToProductoraBo(productora2)).thenReturn(productoraBo2);

        // Act
        List<ProductoraBo> result = productoraServices.getProductorasCb();

        // Assert
        assertEquals(2, result.size());
        assertEquals(productoraBo1, result.get(0));
        assertEquals(productoraBo2, result.get(1));
    }

    @Test
    void testCreateCb() {
        // Arrange
        when(boToEntity.productoraBoToProductora(productoraBo1)).thenReturn(productora1);
        when(productoraCustomRepository.createCb(productora1)).thenReturn(productora1);
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

        // Act
        ProductoraBo result = productoraServices.createCb(productoraBo1);

        // Assert
        assertEquals(productoraBo1, result);
    }

    @Test
    void testReadCb() {
        // Arrange
        when(productoraCustomRepository.readCb(1L)).thenReturn(productora1);
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

        // Act
        ProductoraBo result = productoraServices.readCb(1L);

        // Assert
        assertEquals(productoraBo1, result);
    }

    @Test
    void testUpdateCb() {
        // Arrange
        when(boToEntity.productoraBoToProductora(productoraBo1)).thenReturn(productora1);
        when(productoraCustomRepository.updateCb(productora1)).thenReturn(productora1);
        when(entityToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

        // Act
        ProductoraBo result = productoraServices.updateCb(productoraBo1);

        // Assert
        assertEquals(productoraBo1, result);
    }

    @Test
    void testDeleteCb() {
        //Arrange

        // Act
        productoraServices.deleteCb(1L);

        // Assert

    }

    @Test
    void testRead_productoraNoExiste() {
        // Arrange
        when(productoraRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> productoraServices.read(1L));
    }

    @Test
    void testGetAll_listaVacia() {
        // Arrange
        when(productoraRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<ProductoraBo> result = productoraServices.getAll();

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    void testDelete_productoraNoExiste() {
        // Arrange
        when(productoraRepository.existsById(1L)).thenReturn(false);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> productoraServices.delete(1L));
    }

    @Test
    void testCreate_productoraError() {
        //Arrange
        when(productoraRepository.save(any(Productora.class))).thenThrow(new RuntimeException("Error al crear productora"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> productoraServices.create(productoraBo1));
    }

    @Test
    void testGetAll_productoraError() {
        // Arrange
        when(productoraRepository.findAll()).thenThrow(new RuntimeException("Error al obtener lista de productoras"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> productoraServices.getAll());
    }

    @Test
    void testUpdate_productoraError() {
        // Arrange
        when(productoraRepository.save(any(Productora.class))).thenThrow(new RuntimeException("Error al actualizar productora"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> productoraServices.update(productoraBo1));
    }

    @Test
    void testGetProductorasCb_productoraError() {
        // Arrange
        when(productoraCustomRepository.getProductorasCb()).thenThrow(new RuntimeException("Error al obtener lista de productoras con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> productoraServices.getProductorasCb());
    }

    @Test
    void testCreateCb_productoraError() {
        // Arrange
        when(productoraCustomRepository.createCb(any(Productora.class))).thenThrow(new RuntimeException("Error al crear productora con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> productoraServices.createCb(productoraBo1));
    }

    @Test
    void testReadCb_productoraError() {
        // Arrange
        when(productoraCustomRepository.readCb(1L)).thenThrow(new RuntimeException("Error al leer productora con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> productoraServices.readCb(1L));
    }

    @Test
    void testUpdateCb_productoraError() {
        // Arrange
        when(productoraCustomRepository.updateCb(any(Productora.class))).thenThrow(new RuntimeException("Error al actualizar productora con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> productoraServices.updateCb(productoraBo1));
    }

    @Test
    void testReadCb_productoraNotFound() {
        // Arrange
        when(productoraCustomRepository.readCb(1L)).thenReturn(null);

        // Act and Assert
        assertThrows(ServiceException.class, () -> productoraServices.readCb(1L));
    }
}