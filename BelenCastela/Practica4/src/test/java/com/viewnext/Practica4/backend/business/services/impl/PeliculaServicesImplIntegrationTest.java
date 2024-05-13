package com.viewnext.Practica4.backend.business.services.impl;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PeliculaServicesImplIntegrationTest {

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

    private Pelicula pelicula1;
    private Pelicula pelicula2;
    private PeliculaBo peliculaBo1;
    private PeliculaBo peliculaBo2;

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
        peliculaBo1.setActores(actoresBo1);

        peliculaBo2 = new PeliculaBo();
        peliculaBo2.setId(2L);
        peliculaBo2.setTitulo("Pelicula 2");
        peliculaBo2.setAnho(2010);
        peliculaBo2.setIdDirector(new Director());
        peliculaBo2.setIdProductora(new Productora());
        List<Actor> actoresBo2 = new ArrayList<>();
        actoresBo2.add(new Actor());
        actoresBo2.add(new Actor());
        peliculaBo2.setActores(actoresBo2);
    }

    @Test
    public void testCreate() {
        when(boToEntity.peliculaBoToPelicula(peliculaBo1)).thenReturn(pelicula1);
        when(peliculaRepository.save(pelicula1)).thenReturn(pelicula1);
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

        PeliculaBo createdPelicula = peliculaServices.create(peliculaBo1);

        assertEquals(peliculaBo1, createdPelicula);
    }

    @Test
    public void testRead() {
        when(peliculaRepository.findById(1L)).thenReturn(Optional.of(pelicula1));
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

        PeliculaBo readPelicula = peliculaServices.read(1L);

        assertEquals(peliculaBo1, readPelicula);
    }

    @Test
    public void testGetAll() {
        when(peliculaRepository.findAll()).thenReturn(Arrays.asList(pelicula1, pelicula2));
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);
        when(entityToBo.peliculaToPeliculaBo(pelicula2)).thenReturn(peliculaBo2);

        List<PeliculaBo> peliculas = peliculaServices.getAll();

        assertEquals(Arrays.asList(peliculaBo1, peliculaBo2), peliculas);
    }

    @Test
    public void testDelete() {
        when(peliculaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(peliculaRepository).deleteById(1L);

        peliculaServices.delete(1L);

        verify(peliculaRepository).deleteById(1L);
    }

    @Test
    public void testUpdate() {
        when(boToEntity.peliculaBoToPelicula(peliculaBo1)).thenReturn(pelicula1);
        when(peliculaRepository.save(pelicula1)).thenReturn(pelicula1);
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

        PeliculaBo updatedPelicula = peliculaServices.update(peliculaBo1);

        assertEquals(peliculaBo1, updatedPelicula);
    }

    @Test
    public void testGetPeliculasCb() {
        when(peliculaCustomRepository.getPeliculasCb()).thenReturn(Arrays.asList(pelicula1, pelicula2));
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);
        when(entityToBo.peliculaToPeliculaBo(pelicula2)).thenReturn(peliculaBo2);

        List<PeliculaBo> peliculas = peliculaServices.getPeliculasCb();

        assertEquals(Arrays.asList(peliculaBo1, peliculaBo2), peliculas);
    }

    @Test
    public void testCreateCb() {
        when(boToEntity.peliculaBoToPelicula(peliculaBo1)).thenReturn(pelicula1);
        when(peliculaCustomRepository.createCb(pelicula1)).thenReturn(pelicula1);
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

        PeliculaBo createdPelicula = peliculaServices.createCb(peliculaBo1);

        assertEquals(peliculaBo1, createdPelicula);
    }

    @Test
    public void testReadCb() {
        when(peliculaCustomRepository.readCb(1L)).thenReturn(pelicula1);
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

        PeliculaBo readPelicula = peliculaServices.readCb(1L);

        assertEquals(peliculaBo1, readPelicula);
    }

    @Test
    public void testUpdateCb() {
        when(boToEntity.peliculaBoToPelicula(peliculaBo1)).thenReturn(pelicula1);
        when(peliculaCustomRepository.updateCb(pelicula1)).thenReturn(pelicula1);
        when(entityToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

        PeliculaBo updatedPelicula = peliculaServices.updateCb(peliculaBo1);

        assertEquals(peliculaBo1, updatedPelicula);
    }

    @Test
    public void testDeleteCb() {
        doNothing().when(peliculaCustomRepository).deleteCb(1L);

        peliculaServices.deleteCb(1L);

        verify(peliculaCustomRepository).deleteCb(1L);
    }
}
