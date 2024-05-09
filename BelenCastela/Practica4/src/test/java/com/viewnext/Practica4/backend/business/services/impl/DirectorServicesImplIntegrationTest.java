package com.viewnext.Practica4.backend.business.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.viewnext.Practica4.backend.business.bo.DirectorBo;
import com.viewnext.Practica4.backend.business.model.Director;
import com.viewnext.Practica4.backend.repository.DirectorRepository;
import com.viewnext.Practica4.backend.repository.custom.DirectorCustomRepository;
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
public class DirectorServicesImplIntegrationTest {

    @InjectMocks
    private DirectorServicesImpl directorServices;

    @Mock
    private DirectorRepository directorRepository;

    @Mock
    private DirectorCustomRepository directorCustomRepository;

    @Mock
    private EntityToBo entityToBo;

    @Mock
    private BoToEntity boToEntity;

    private Director director1;
    private Director director2;
    private DirectorBo directorBo1;
    private DirectorBo directorBo2;

    @BeforeEach
    public void init() {
        initMocks();
    }

    private void initMocks() {
        director1 = new Director();
        director1.setId(1L);
        director1.setNombre("Director 1");
        director1.setEdad(30);
        director1.setNacionalidad("Española");

        director2 = new Director();
        director2.setId(2L);
        director2.setNombre("Director 2");
        director2.setEdad(35);
        director2.setNacionalidad("Francesa");

        directorBo1 = new DirectorBo();
        directorBo1.setId(1L);
        directorBo1.setNombre("Director 1");
        directorBo1.setEdad(30);
        directorBo1.setNacionalidad("Española");

        directorBo2 = new DirectorBo();
        directorBo2.setId(2L);
        directorBo2.setNombre("Director 2");
        directorBo2.setEdad(35);
        directorBo2.setNacionalidad("Francesa");
    }

    @Test
    public void testCreate() {
        when(boToEntity.directorBoToDirector(directorBo1)).thenReturn(director1);
        when(directorRepository.save(director1)).thenReturn(director1);
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

        DirectorBo createdDirector = directorServices.create(directorBo1);

        assertEquals(directorBo1, createdDirector);
    }

    @Test
    public void testRead() {
        when(directorRepository.findById(1L)).thenReturn(Optional.of(director1));
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

        DirectorBo readDirector = directorServices.read(1L);

        assertEquals(directorBo1, readDirector);
    }

    @Test
    public void testGetAll() {
        when(directorRepository.findAll()).thenReturn(Arrays.asList(director1, director2));
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);
        when(entityToBo.directorToDirectorBo(director2)).thenReturn(directorBo2);

        List<DirectorBo> directors = directorServices.getAll();

        assertEquals(Arrays.asList(directorBo1, directorBo2), directors);
    }

    @Test
    public void testDelete() {
        when(directorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(directorRepository).deleteById(1L);

        directorServices.delete(1L);

        verify(directorRepository).deleteById(1L);
    }

    @Test
    public void testUpdate() {
        when(boToEntity.directorBoToDirector(directorBo1)).thenReturn(director1);
        when(directorRepository.save(director1)).thenReturn(director1);
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

        DirectorBo updatedDirector = directorServices.update(directorBo1);

        assertEquals(directorBo1, updatedDirector);
    }

    @Test
    public void testGetDirectoresCb() {
        when(directorCustomRepository.getDirectoresCb()).thenReturn(Arrays.asList(director1, director2));
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);
        when(entityToBo.directorToDirectorBo(director2)).thenReturn(directorBo2);

        List<DirectorBo> directors = directorServices.getDirectoresCb();

        assertEquals(Arrays.asList(directorBo1, directorBo2), directors);
    }

    @Test
    public void testCreateCb() {
        when(boToEntity.directorBoToDirector(directorBo1)).thenReturn(director1);
        when(directorCustomRepository.createCb(director1)).thenReturn(director1);
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

        DirectorBo createdDirector = directorServices.createCb(directorBo1);

        assertEquals(directorBo1, createdDirector);
    }

    @Test
    public void testReadCb() {
        when(directorCustomRepository.readCb(1L)).thenReturn(director1);
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

        DirectorBo readDirector = directorServices.readCb(1L);

        assertEquals(directorBo1, readDirector);
    }

    @Test
    public void testUpdateCb() {
        when(boToEntity.directorBoToDirector(directorBo1)).thenReturn(director1);
        when(directorCustomRepository.updateCb(director1)).thenReturn(director1);
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

        DirectorBo updatedDirector = directorServices.updateCb(directorBo1);

        assertEquals(directorBo1, updatedDirector);
    }

    @Test
    public void testDeleteCb() {
        doNothing().when(directorCustomRepository).deleteCb(1L);

        directorServices.deleteCb(1L);

        verify(directorCustomRepository).deleteCb(1L);
    }
}
