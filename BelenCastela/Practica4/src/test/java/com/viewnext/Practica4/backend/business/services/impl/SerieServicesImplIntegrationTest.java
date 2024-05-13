package com.viewnext.Practica4.backend.business.services.impl;

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
public class SerieServicesImplIntegrationTest {

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

    private Serie serie1;
    private Serie serie2;
    private SerieBo serieBo1;
    private SerieBo serieBo2;

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

    @Test
    public void testCreate() {
        when(boToEntity.serieBoToSerie(serieBo1)).thenReturn(serie1);
        when(serieRepository.save(serie1)).thenReturn(serie1);
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

        SerieBo createdSerie = serieServices.create(serieBo1);

        assertEquals(serieBo1, createdSerie);
    }

    @Test
    public void testRead() {
        when(serieRepository.findById(1L)).thenReturn(Optional.of(serie1));
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

        SerieBo readSerie = serieServices.read(1L);

        assertEquals(serieBo1, readSerie);
    }

    @Test
    public void testGetAll() {
        when(serieRepository.findAll()).thenReturn(Arrays.asList(serie1, serie2));
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);
        when(entityToBo.serieToSerieBo(serie2)).thenReturn(serieBo2);

        List<SerieBo> series = serieServices.getAll();

        assertEquals(Arrays.asList(serieBo1, serieBo2), series);
    }

    @Test
    public void testDelete() {
        when(serieRepository.existsById(1L)).thenReturn(true);
        doNothing().when(serieRepository).deleteById(1L);

        serieServices.delete(1L);

        verify(serieRepository).deleteById(1L);
    }

    @Test
    public void testUpdate() {
        when(boToEntity.serieBoToSerie(serieBo1)).thenReturn(serie1);
        when(serieRepository.save(serie1)).thenReturn(serie1);
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

        SerieBo updatedSerie = serieServices.update(serieBo1);

        assertEquals(serieBo1, updatedSerie);
    }

    @Test
    public void testGetSeriesCb() {
        when(serieCustomRepository.getSeriesCb()).thenReturn(Arrays.asList(serie1, serie2));
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);
        when(entityToBo.serieToSerieBo(serie2)).thenReturn(serieBo2);

        List<SerieBo> series = serieServices.getSeriesCb();

        assertEquals(Arrays.asList(serieBo1, serieBo2), series);
    }

    @Test
    public void testCreateCb() {
        when(boToEntity.serieBoToSerie(serieBo1)).thenReturn(serie1);
        when(serieCustomRepository.createCb(serie1)).thenReturn(serie1);
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

        SerieBo createdSerie = serieServices.createCb(serieBo1);

        assertEquals(serieBo1, createdSerie);
    }

    @Test
    public void testReadCb() {
        when(serieCustomRepository.readCb(1L)).thenReturn(serie1);
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

        SerieBo readSerie = serieServices.readCb(1L);

        assertEquals(serieBo1, readSerie);
    }

    @Test
    public void testUpdateCb() {
        when(boToEntity.serieBoToSerie(serieBo1)).thenReturn(serie1);
        when(serieCustomRepository.updateCb(serie1)).thenReturn(serie1);
        when(entityToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

        SerieBo updatedSerie = serieServices.updateCb(serieBo1);

        assertEquals(serieBo1, updatedSerie);
    }

    @Test
    public void testDeleteCb() {
        doNothing().when(serieCustomRepository).deleteCb(1L);

        serieServices.deleteCb(1L);

        verify(serieCustomRepository).deleteCb(1L);
    }
}
