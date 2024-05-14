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

import com.viewnext.Practica4.backend.business.bo.SerieBo;
import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.business.model.Director;
import com.viewnext.Practica4.backend.business.model.Productora;
import com.viewnext.Practica4.backend.business.model.Serie;
import com.viewnext.Practica4.backend.repository.SerieRepository;
import com.viewnext.Practica4.backend.repository.custom.SerieCustomRepository;
import com.viewnext.Practica4.util.converter.bo.BoToEntity;
import com.viewnext.Practica4.util.converter.bo.EntityToBo;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class SerieServicesImplTest {

    private Serie serie1, serie2;
    private SerieBo serieBo1, serieBo2;

    @InjectMocks
    private SerieServicesImpl serieServices;

    @Mock
    private SerieRepository serieRepository;

    @Mock
    private SerieCustomRepository serieCustomRepository;

    @Mock
    private EntityToBo entityToBo;

    @Mock
    private BoToEntity boToEntity;

    @BeforeEach
    public void init() {
        initMocks();
    }

    private void initMocks() {
        serie1 = new Serie();
        serie1.setId(1L);
        serie1.setTitulo("Serie 1");
        serie1.setAnho(2000);
        serie1.setIdDirector(new Director());
        serie1.setIdProductora(new Productora());
        List<Actor> actores1 = new ArrayList<>();
        actores1.add(new Actor());
        actores1.add(new Actor());
        serie1.setActores(actores1);

        serie2 = new Serie();
        serie2.setId(2L);
        serie2.setTitulo("Serie 2");
        serie2.setAnho(2010);
        serie2.setIdDirector(new Director());
        serie2.setIdProductora(new Productora());
        List<Actor> actores2 = new ArrayList<>();
        actores2.add(new Actor());
        actores2.add(new Actor());
        serie2.setActores(actores2);

        serieBo1 = new SerieBo();
        serieBo1.setId(1L);
        serieBo1.setTitulo("Serie 1");
        serieBo1.setAnho(2000);
        serieBo1.setIdDirector(new Director());
        serieBo1.setIdProductora(new Productora());
        List<Actor> actoresBo1 = new ArrayList<>();
        actoresBo1.add(new Actor());
        actoresBo1.add(new Actor());
        serieBo1.setActores(actoresBo1);

        serieBo2 = new SerieBo();
        serieBo2.setId(2L);
        serieBo2.setTitulo("Serie 2");
        serieBo2.setAnho(2010);
        serieBo2.setIdDirector(new Director());
        serieBo2.setIdProductora(new Productora());
        List<Actor> actoresBo2 = new ArrayList<>();
        actoresBo2.add(new Actor());
        actoresBo2.add(new Actor());
        serieBo2.setActores(actoresBo2);
    }

    // Tests para los métodos de SerieServicesImpl
    @Test
    void testCreate() {
        // Arrange
        when(boToEntity.serieBoToSerie(serieBo1)).thenReturn(serie1);
        when(serieRepository.save(serie1)).thenReturn(serie1);
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

        // Act
        SerieBo result = serieServices.create(serieBo1);

        // Assert
        assertEquals(serieBo1, result);
    }

    @Test
    void testRead() {
        // Arrange
        when(serieRepository.findById(1L)).thenReturn(Optional.of(serie1));
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

        // Act
        SerieBo result = serieServices.read(1L);

        // Assert
        assertEquals(serieBo1, result);
    }

    @Test
    void testGetAll() {
        // Arrange
        List<Serie> series = new ArrayList<>();
        series.add(serie1);
        series.add(serie2);
        when(serieRepository.findAll()).thenReturn(series);
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);
        when(entityToBo.serieToSerieBo(serie2)).thenReturn(serieBo2);

        // Act
        List<SerieBo> result = serieServices.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(serieBo1, result.get(0));
        assertEquals(serieBo2, result.get(1));
    }

    @Test
    void testDelete() {
        // Arrange
        when(serieRepository.existsById(1L)).thenReturn(true);
        // Act
        serieServices.delete(1L);

        // Assert
    }

    @Test
    void testUpdate() {
        // Arrange
        when(boToEntity.serieBoToSerie(serieBo1)).thenReturn(serie1);
        when(serieRepository.save(serie1)).thenReturn(serie1);
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

        // Act
        SerieBo result = serieServices.update(serieBo1);

        // Assert
        assertEquals(serieBo1, result);
    }

    // Tests para los métodos de SerieServicesImpl con Criteria Builder
    @Test
    void testGetSeriesCb() {
        // Arrange
        List<Serie> series = new ArrayList<>();
        series.add(serie1);
        series.add(serie2);
        when(serieCustomRepository.getSeriesCb()).thenReturn(series);
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);
        when(entityToBo.serieToSerieBo(serie2)).thenReturn(serieBo2);

        // Act
        List<SerieBo> result = serieServices.getSeriesCb();

        // Assert
        assertEquals(2, result.size());
        assertEquals(serieBo1, result.get(0));
        assertEquals(serieBo2, result.get(1));
    }

    @Test
    void testCreateCb() {
        // Arrange
        when(boToEntity.serieBoToSerie(serieBo1)).thenReturn(serie1);
        when(serieCustomRepository.createCb(serie1)).thenReturn(serie1);
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

        // Act
        SerieBo result = serieServices.createCb(serieBo1);

        // Assert
        assertEquals(serieBo1, result);
    }

    @Test
    void testReadCb() {
        // Arrange
        when(serieCustomRepository.readCb(1L)).thenReturn(serie1);
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

        // Act
        SerieBo result = serieServices.readCb(1L);

        // Assert
        assertEquals(serieBo1, result);
    }

    @Test
    void testUpdateCb() {
        // Arrange
        when(boToEntity.serieBoToSerie(serieBo1)).thenReturn(serie1);
        when(serieCustomRepository.updateCb(serie1)).thenReturn(serie1);
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

        // Act
        SerieBo result = serieServices.updateCb(serieBo1);

        // Assert
        assertEquals(serieBo1, result);
    }

    @Test
    void testDeleteCb() {
        //Arrange

        // Act
        serieServices.deleteCb(1L);

        // Assert

    }

    // Tests para los métodos de SerieServicesImpl con errores
    @Test
    void testRead_serieNoExiste() {
        // Arrange
        when(serieRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> serieServices.read(1L));
    }

    @Test
    void testGetAll_listaVacia() {
        // Arrange
        when(serieRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<SerieBo> result = serieServices.getAll();

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    void testDelete_serieNoExiste() {
        // Arrange
        when(serieRepository.existsById(1L)).thenReturn(false);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> serieServices.delete(1L));
    }

    @Test
    void testCreate_serieError() {
        //Arrange
        when(serieRepository.save(any(Serie.class))).thenThrow(new RuntimeException("Error al crear serie"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> serieServices.create(serieBo1));
    }

    @Test
    void testGetAll_serieError() {
        // Arrange
        when(serieRepository.findAll()).thenThrow(new RuntimeException("Error al obtener lista de series"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> serieServices.getAll());
    }

    @Test
    void testUpdate_serieError() {
        // Arrange
        when(serieRepository.save(any(Serie.class))).thenThrow(new RuntimeException("Error al actualizar serie"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> serieServices.update(serieBo1));
    }

    @Test
    void testGetSeriesCb_serieError() {
        // Arrange
        when(serieCustomRepository.getSeriesCb()).thenThrow(new RuntimeException("Error al obtener lista de series con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> serieServices.getSeriesCb());
    }

    @Test
    void testCreateCb_serieError() {
        // Arrange
        when(serieCustomRepository.createCb(any(Serie.class))).thenThrow(new RuntimeException("Error al crear serie con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> serieServices.createCb(serieBo1));
    }

    @Test
    void testReadCb_serieError() {
        // Arrange
        when(serieCustomRepository.readCb(1L)).thenThrow(new RuntimeException("Error al leer serie con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> serieServices.readCb(1L));
    }

    @Test
    void testUpdateCb_serieError() {
        // Arrange
        when(serieCustomRepository.updateCb(any(Serie.class))).thenThrow(new RuntimeException("Error al actualizar serie con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> serieServices.updateCb(serieBo1));
    }

    @Test
    void testReadCb_serieNotFound() {
        // Arrange
        when(serieCustomRepository.readCb(1L)).thenReturn(null);

        // Act and Assert
        assertThrows(ServiceException.class, () -> serieServices.readCb(1L));
    }
}
