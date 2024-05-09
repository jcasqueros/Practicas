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

import com.viewnext.Practica4.backend.business.bo.PeliculaBo;
import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.business.model.Director;
import com.viewnext.Practica4.backend.business.model.Pelicula;
import com.viewnext.Practica4.backend.business.model.Productora;
import com.viewnext.Practica4.backend.repository.PeliculaRepository;
import com.viewnext.Practica4.backend.repository.custom.PeliculaCustomRepository;
import com.viewnext.Practica4.util.converter.bo.BoToEntity;
import com.viewnext.Practica4.util.converter.bo.EntityToBo;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class PeliculaServicesImplTest {

    private Pelicula pelicula1, pelicula2;
    private PeliculaBo peliculaBo1, peliculaBo2;

    @InjectMocks
    private PeliculaServicesImpl peliculaServices;

    @Mock
    private PeliculaRepository peliculaRepository;

    @Mock
    private PeliculaCustomRepository peliculaCustomRepository;

    @Mock
    private EntityToBo entityToBo;

    @Mock
    private BoToEntity boToEntity;

    @BeforeEach
    public void init() {
        initMocks();
    }

    private void initMocks() {
        pelicula1 = new Pelicula();
        pelicula1.setId(1L);
        pelicula1.setTitulo("Pelicula 1");
        pelicula1.setAnho(2000);
        pelicula1.setIdDirector(new Director());
        pelicula1.setIdProductora(new Productora());
        List<Actor> actores1 = new ArrayList<>();
        actores1.add(new Actor());
        actores1.add(new Actor());
        pelicula1.setActores(actores1);

        pelicula2 = new Pelicula();
        pelicula2.setId(2L);
        pelicula2.setTitulo("Pelicula 2");
        pelicula2.setAnho(2010);
        pelicula2.setIdDirector(new Director());
        pelicula2.setIdProductora(new Productora());
        List<Actor> actores2 = new ArrayList<>();
        actores2.add(new Actor());
        actores2.add(new Actor());
        pelicula2.setActores(actores2);

        peliculaBo1 = new PeliculaBo();
        peliculaBo1.setId(1L);
        peliculaBo1.setTitulo("Pelicula 1");
        peliculaBo1.setAnho(2000);
        peliculaBo1.setIdDirector(new Director());
        peliculaBo1.setIdProductora(new Productora());
        List<Actor> actoresBo1 = new ArrayList<>();
        actoresBo1.add(new Actor());
        actoresBo1.add(new Actor());
        pelicula1.setActores(actoresBo1);

        peliculaBo2 = new PeliculaBo();
        peliculaBo2.setId(2L);
        peliculaBo2.setTitulo("Pelicula 2");
        peliculaBo2.setAnho(2010);
        peliculaBo2.setIdDirector(new Director());
        peliculaBo2.setIdProductora(new Productora());
        List<Actor> actoresBo2 = new ArrayList<>();
        actoresBo2.add(new Actor());
        actoresBo2.add(new Actor());
        pelicula1.setActores(actoresBo2);
    }

    // Tests para los métodos de PeliculaServicesImpl
    @Test
    void testCreate() {
        // Arrange
        when(boToEntity.peliculaBoToPelicula(peliculaBo1)).thenReturn(pelicula1);
        when(peliculaRepository.save(pelicula1)).thenReturn(pelicula1);
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

        // Act
        PeliculaBo result = peliculaServices.create(peliculaBo1);

        // Assert
        assertEquals(peliculaBo1, result);
    }

    @Test
    void testRead() {
        // Arrange
        when(peliculaRepository.findById(1L)).thenReturn(Optional.of(pelicula1));
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

        // Act
        PeliculaBo result = peliculaServices.read(1L);

        // Assert
        assertEquals(peliculaBo1, result);
    }

    @Test
    void testGetAll() {
        // Arrange
        List<Pelicula> peliculas = new ArrayList<>();
        peliculas.add(pelicula1);
        peliculas.add(pelicula2);
        when(peliculaRepository.findAll()).thenReturn(peliculas);
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);
        when(entityToBo.peliculaToPeliculaBo(pelicula2)).thenReturn(peliculaBo2);

        // Act
        List<PeliculaBo> result = peliculaServices.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(peliculaBo1, result.get(0));
        assertEquals(peliculaBo2, result.get(1));
    }

    @Test
    void testDelete() {
        // Arrange
        when(peliculaRepository.existsById(1L)).thenReturn(true);
        // Act
        peliculaServices.delete(1L);

        // Assert
    }

    @Test
    void testUpdate() {
        // Arrange
        when(boToEntity.peliculaBoToPelicula(peliculaBo1)).thenReturn(pelicula1);
        when(peliculaRepository.save(pelicula1)).thenReturn(pelicula1);
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

        // Act
        PeliculaBo result = peliculaServices.update(peliculaBo1);

        // Assert
        assertEquals(peliculaBo1, result);
    }

    // Tests para los métodos de PeliculaServicesImpl con Criteria Builder
    @Test
    void testGetPeliculasCb() {
        // Arrange
        List<Pelicula> peliculas = new ArrayList<>();
        peliculas.add(pelicula1);
        peliculas.add(pelicula2);
        when(peliculaCustomRepository.getPeliculasCb()).thenReturn(peliculas);
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);
        when(entityToBo.peliculaToPeliculaBo(pelicula2)).thenReturn(peliculaBo2);

        // Act
        List<PeliculaBo> result = peliculaServices.getPeliculasCb();

        // Assert
        assertEquals(2, result.size());
        assertEquals(peliculaBo1, result.get(0));
        assertEquals(peliculaBo2, result.get(1));
    }

    @Test
    void testCreateCb() {
        // Arrange
        when(boToEntity.peliculaBoToPelicula(peliculaBo1)).thenReturn(pelicula1);
        when(peliculaCustomRepository.createCb(pelicula1)).thenReturn(pelicula1);
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

        // Act
        PeliculaBo result = peliculaServices.createCb(peliculaBo1);

        // Assert
        assertEquals(peliculaBo1, result);
    }

    @Test
    void testReadCb() {
        // Arrange
        when(peliculaCustomRepository.readCb(1L)).thenReturn(pelicula1);
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

        // Act
        PeliculaBo result = peliculaServices.readCb(1L);

        // Assert
        assertEquals(peliculaBo1, result);
    }

    @Test
    void testUpdateCb() {
        // Arrange
        when(boToEntity.peliculaBoToPelicula(peliculaBo1)).thenReturn(pelicula1);
        when(peliculaCustomRepository.updateCb(pelicula1)).thenReturn(pelicula1);
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

        // Act
        PeliculaBo result = peliculaServices.updateCb(peliculaBo1);

        // Assert
        assertEquals(peliculaBo1, result);
    }

    @Test
    void testDeleteCb() {
        //Arrange

        // Act
        peliculaServices.deleteCb(1L);

        // Assert

    }

    // Tests para los métodos de PeliculaServicesImpl con errores
    @Test
    void testRead_peliculaNoExiste() {
        // Arrange
        when(peliculaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> peliculaServices.read(1L));
    }

    @Test
    void testGetAll_listaVacia() {
        // Arrange
        when(peliculaRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<PeliculaBo> result = peliculaServices.getAll();

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    void testDelete_peliculaNoExiste() {
        // Arrange
        when(peliculaRepository.existsById(1L)).thenReturn(false);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> peliculaServices.delete(1L));
    }

    @Test
    void testCreate_peliculaError() {
        //Arrange
        when(peliculaRepository.save(any(Pelicula.class))).thenThrow(new RuntimeException("Error al crear pelicula"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> peliculaServices.create(peliculaBo1));
    }

    @Test
    void testGetAll_peliculaError() {
        // Arrange
        when(peliculaRepository.findAll()).thenThrow(new RuntimeException("Error al obtener lista de peliculas"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> peliculaServices.getAll());
    }

    @Test
    void testUpdate_peliculaError() {
        // Arrange
        when(peliculaRepository.save(any(Pelicula.class))).thenThrow(new RuntimeException("Error al actualizar pelicula"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> peliculaServices.update(peliculaBo1));
    }

    @Test
    void testGetPeliculasCb_peliculaError() {
        // Arrange
        when(peliculaCustomRepository.getPeliculasCb()).thenThrow(new RuntimeException("Error al obtener lista de peliculas con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> peliculaServices.getPeliculasCb());
    }

    @Test
    void testCreateCb_peliculaError() {
        // Arrange
        when(peliculaCustomRepository.createCb(any(Pelicula.class))).thenThrow(new RuntimeException("Error al crear pelicula con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> peliculaServices.createCb(peliculaBo1));
    }

    @Test
    void testReadCb_peliculaError() {
        // Arrange
        when(peliculaCustomRepository.readCb(1L)).thenThrow(new RuntimeException("Error al leer pelicula con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> peliculaServices.readCb(1L));
    }

    @Test
    void testUpdateCb_peliculaError() {
        // Arrange
        when(peliculaCustomRepository.updateCb(any(Pelicula.class))).thenThrow(new RuntimeException("Error al actualizar pelicula con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> peliculaServices.updateCb(peliculaBo1));
    }

    @Test
    void testReadCb_peliculaNotFound() {
        // Arrange
        when(peliculaCustomRepository.readCb(1L)).thenReturn(null);

        // Act and Assert
        assertThrows(ServiceException.class, () -> peliculaServices.readCb(1L));
    }
}
