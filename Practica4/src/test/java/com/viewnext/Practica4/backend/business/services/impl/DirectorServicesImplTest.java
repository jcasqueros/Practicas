package com.viewnext.Practica4.backend.business.services.impl;

import org.hibernate.service.spi.ServiceException;
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

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DirectorServicesImplTest {

    private Director director1, director2;
    private DirectorBo directorBo1, directorBo2;

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

    @BeforeEach
    public void init() {
        initMocks();
    }

    private void initMocks() {
        director1 = new Director();
        director1.setId(1L);
        director1.setNombre("Director 1");
        director1.setEdad(40);
        director1.setNacionalidad("Española");

        director2 = new Director();
        director2.setId(2L);
        director2.setNombre("Director 2");
        director2.setEdad(45);
        director2.setNacionalidad("Francesa");

        directorBo1 = new DirectorBo();
        directorBo1.setId(1L);
        directorBo1.setNombre("Director 1");
        directorBo1.setEdad(40);
        directorBo1.setNacionalidad("Española");

        directorBo2 = new DirectorBo();
        directorBo2.setId(2L);
        directorBo2.setNombre("Director 2");
        directorBo2.setEdad(45);
        directorBo2.setNacionalidad("Francesa");
    }

    @Test
    void testCreate() {
        // Arrange
        when(boToEntity.directorBoToDirector(directorBo1)).thenReturn(director1);
        when(directorRepository.save(director1)).thenReturn(director1);
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

        // Act
        DirectorBo result = directorServices.create(directorBo1);

        // Assert
        assertEquals(directorBo1, result);
    }

    @Test
    void testRead() {
        // Arrange
        when(directorRepository.findById(1L)).thenReturn(Optional.of(director1));
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

        // Act
        DirectorBo result = directorServices.read(1L);

        // Assert
        assertEquals(directorBo1, result);
    }

    @Test
    void testGetAll() {
        // Arrange
        List<Director> directors = new ArrayList<>();
        directors.add(director1);
        directors.add(director2);
        when(directorRepository.findAll()).thenReturn(directors);
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);
        when(entityToBo.directorToDirectorBo(director2)).thenReturn(directorBo2);

        // Act
        List<DirectorBo> result = directorServices.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(directorBo1, result.get(0));
        assertEquals(directorBo2, result.get(1));
    }

    @Test
    void testDelete() {
        // Arrange
        when(directorRepository.existsById(1L)).thenReturn(true);
        // Act
        directorServices.delete(1L);

        // Assert
    }

    @Test
    void testUpdate() {
        // Arrange
        when(boToEntity.directorBoToDirector(directorBo1)).thenReturn(director1);
        when(directorRepository.save(director1)).thenReturn(director1);
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

        // Act
        DirectorBo result = directorServices.update(directorBo1);

        // Assert
        assertEquals(directorBo1, result);
    }

    @Test
    void testGetDirectoresCb() {
        // Arrange
        List<Director> directors = new ArrayList<>();
        directors.add(director1);
        directors.add(director2);
        when(directorCustomRepository.getDirectoresCb()).thenReturn(directors);
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);
        when(entityToBo.directorToDirectorBo(director2)).thenReturn(directorBo2);

        // Act
        List<DirectorBo> result = directorServices.getDirectoresCb();

        // Assert
        assertEquals(2, result.size());
        assertEquals(directorBo1, result.get(0));
        assertEquals(directorBo2, result.get(1));
    }

    @Test
    void testCreateCb() {
        // Arrange
        when(boToEntity.directorBoToDirector(directorBo1)).thenReturn(director1);
        when(directorCustomRepository.createCb(director1)).thenReturn(director1);
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

        // Act
        DirectorBo result = directorServices.createCb(directorBo1);

        // Assert
        assertEquals(directorBo1, result);
    }

    @Test
    void testReadCb() {
        // Arrange
        when(directorCustomRepository.readCb(1L)).thenReturn(director1);
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

        // Act
        DirectorBo result = directorServices.readCb(1L);

        // Assert
        assertEquals(directorBo1, result);
    }

    @Test
    void testUpdateCb() {
        // Arrange
        when(boToEntity.directorBoToDirector(directorBo1)).thenReturn(director1);
        when(directorCustomRepository.updateCb(director1)).thenReturn(director1);
        when(entityToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

        // Act
        DirectorBo result = directorServices.updateCb(directorBo1);

        // Assert
        assertEquals(directorBo1, result);
    }

    @Test
    void testDeleteCb() {
        //Arrange

        // Act
        directorServices.deleteCb(1L);

        // Assert

    }

    @Test
    void testRead_directorNoExiste() {
        // Arrange
        when(directorRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> directorServices.read(1L));
    }

    @Test
    void testGetAll_listaVacia() {
        // Arrange
        when(directorRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<DirectorBo> result = directorServices.getAll();

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    void testDelete_directorNoExiste() {
        // Arrange
        when(directorRepository.existsById(1L)).thenReturn(false);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> directorServices.delete(1L));
    }

    @Test
    void testCreate_directorError() {
        //Arrange
        when(directorRepository.save(any(Director.class))).thenThrow(new RuntimeException("Error al crear director"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> directorServices.create(directorBo1));
    }

    @Test
    void testGetAll_directorError() {
        // Arrange
        when(directorRepository.findAll()).thenThrow(new RuntimeException("Error al obtener lista de directores"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> directorServices.getAll());
    }

    @Test
    void testUpdate_directorError() {
        // Arrange
        when(directorRepository.save(any(Director.class))).thenThrow(new RuntimeException("Error al actualizar director"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> directorServices.update(directorBo1));
    }

    @Test
    void testGetDirectoresCb_directorError() {
        // Arrange
        when(directorCustomRepository.getDirectoresCb()).thenThrow(new RuntimeException("Error al obtener lista de directores con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> directorServices.getDirectoresCb());
    }

    @Test
    void testCreateCb_directorError() {
        // Arrange
        when(directorCustomRepository.createCb(any(Director.class))).thenThrow(new RuntimeException("Error al crear director con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> directorServices.createCb(directorBo1));
    }

    @Test
    void testReadCb_directorError() {
        // Arrange
        when(directorCustomRepository.readCb(1L)).thenThrow(new RuntimeException("Error al leer director con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> directorServices.readCb(1L));
    }

    @Test
    void testUpdateCb_directorError() {
        // Arrange
        when(directorCustomRepository.updateCb(any(Director.class))).thenThrow(new RuntimeException("Error al actualizar director con Criteria Builder"));

        // Act and Assert
        assertThrows(ServiceException.class, () -> directorServices.updateCb(directorBo1));
    }

    @Test
    void testReadCb_directorNotFound() {
        // Arrange
        when(directorCustomRepository.readCb(1L)).thenReturn(null);

        // Act and Assert
        assertThrows(ServiceException.class, () -> directorServices.readCb(1L));
    }
}
