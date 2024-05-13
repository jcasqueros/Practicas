package com.viewnext.Practica4.backend.presentation.controller;

import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.viewnext.Practica4.backend.business.bo.PeliculaBo;
import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.business.model.Director;
import com.viewnext.Practica4.backend.business.model.Productora;
import com.viewnext.Practica4.backend.business.services.PeliculaServices;
import com.viewnext.Practica4.backend.presentation.controller.PeliculaController;
import com.viewnext.Practica4.backend.presentation.dto.PeliculaDto;
import com.viewnext.Practica4.util.converter.dto.BoToDto;
import com.viewnext.Practica4.util.converter.dto.DtoToBo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PeliculaControllerTest {

    @InjectMocks
    private PeliculaController peliculaController;

    @Mock
    private PeliculaServices peliculaServices;

    @Mock
    private DtoToBo dtoToBo;

    @Mock
    private BoToDto boToDto;

    private PeliculaBo peliculaBo1;
    private PeliculaBo peliculaBo2;
    private PeliculaDto peliculaDto1;
    private PeliculaDto peliculaDto2;

    @BeforeEach
    public void init() {
        initMocks();
    }

    private void initMocks() {
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

        peliculaDto1 = new PeliculaDto();
        peliculaDto1.setId(1L);
        peliculaDto1.setTitulo("Pelicula 1");
        peliculaDto1.setAnho(2000);
        peliculaDto1.setIdDirector(new Director());
        peliculaDto1.setIdProductora(new Productora());
        List<Actor> actoresDto1 = new ArrayList<>();
        actoresDto1.add(new Actor());
        actoresDto1.add(new Actor());
        peliculaDto1.setActores(actoresDto1);

        peliculaDto2 = new PeliculaDto();
        peliculaDto2.setId(2L);
        peliculaDto2.setTitulo("Pelicula 2");
        peliculaDto2.setAnho(2010);
        peliculaDto2.setIdDirector(new Director());
        peliculaDto2.setIdProductora(new Productora());
        List<Actor> actoresDto2 = new ArrayList<>();
        actoresDto2.add(new Actor());
        actoresDto2.add(new Actor());
        peliculaDto2.setActores(actoresDto2);
    }


    @Test
    public void testGetPeliculas() throws ServiceException {
        // Given
        List<PeliculaBo> peliculaBoList = new ArrayList<>();
        peliculaBoList.add(peliculaBo1);
        peliculaBoList.add(peliculaBo2);
        when(peliculaServices.getAll()).thenReturn(peliculaBoList);

        // When
        ResponseEntity<List<PeliculaDto>> response = peliculaController.getPeliculas();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetPeliculaByCode() throws ServiceException {
        // Given
        long id = 1L;
        when(peliculaServices.read(id)).thenReturn(peliculaBo1);

        // When
        ResponseEntity<PeliculaDto> response = peliculaController.getPeliculaByCode(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreatePelicula() throws ServiceException {
        // Given
        when(dtoToBo.peliculaDtoToBo(peliculaDto1)).thenReturn(peliculaBo1);
        when(peliculaServices.create(peliculaBo1)).thenReturn(peliculaBo1);

        // When
        ResponseEntity<PeliculaDto> response = peliculaController.createPelicula(peliculaDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdatePelicula() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.peliculaDtoToBo(peliculaDto1)).thenReturn(peliculaBo1);
        when(peliculaServices.read(id)).thenReturn(peliculaBo1);
        when(peliculaServices.update(peliculaBo1)).thenReturn(peliculaBo1);

        // When
        ResponseEntity<PeliculaDto> response = peliculaController.updatePelicula(id, peliculaDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    // Criteria Builder tests

    @Test
    public void testGetPeliculasCb() throws ServiceException {
        // Given
        List<PeliculaBo> peliculaBoList = new ArrayList<>();
        peliculaBoList.add(peliculaBo1);
        peliculaBoList.add(peliculaBo2);
        when(peliculaServices.getPeliculasCb()).thenReturn(peliculaBoList);

        // When
        ResponseEntity<List<PeliculaDto>> response = peliculaController.getPeliculaCb();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testReadPeliculaCb() throws ServiceException {
        // Given
        long id = 1L;
        when(peliculaServices.readCb(id)).thenReturn(peliculaBo1);

        // When
        ResponseEntity<PeliculaDto> response = peliculaController.readPeliculaCb(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreatePeliculaCb() throws ServiceException {
        // Given
        when(dtoToBo.peliculaDtoToBo(peliculaDto1)).thenReturn(peliculaBo1);
        when(peliculaServices.createCb(peliculaBo1)).thenReturn(peliculaBo1);

        // When
        ResponseEntity<PeliculaDto> response = peliculaController.createPeliculaCb(peliculaDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdatePeliculaCb() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.peliculaDtoToBo(peliculaDto1)).thenReturn(peliculaBo1);
        when(peliculaServices.readCb(id)).thenReturn(peliculaBo1);
        when(peliculaServices.updateCb(peliculaBo1)).thenReturn(peliculaBo1);

        // When
        ResponseEntity<PeliculaDto> response = peliculaController.updatePeliculaCb(id, peliculaDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testDeletePelicula_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        doThrow(new ServiceException("Error deleting pelicula")).when(peliculaServices).delete(id);

        // When
        ResponseEntity<Void> response = peliculaController.deletePelicula(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeletePeliculaCb_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        doThrow(new ServiceException("Error deleting pelicula")).when(peliculaServices).deleteCb(id);

        // When
        ResponseEntity<Void> response = peliculaController.deletePeliculaCb(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetPeliculas_ThrowsServiceException() throws ServiceException {
        // Given
        when(peliculaServices.getAll()).thenThrow(new ServiceException("Error getting peliculas"));

        // When
        ResponseEntity<List<PeliculaDto>> response = peliculaController.getPeliculas();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetPeliculaByCode_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(peliculaServices.read(id)).thenThrow(new ServiceException("Error getting pelicula"));

        // When
        ResponseEntity<PeliculaDto> response = peliculaController.getPeliculaByCode(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreatePelicula_ThrowsServiceException() throws ServiceException {
        // Given
        when(dtoToBo.peliculaDtoToBo(peliculaDto1)).thenReturn(peliculaBo1);
        when(peliculaServices.create(peliculaBo1)).thenThrow(new ServiceException("Error creating pelicula"));

        // When
        ResponseEntity<PeliculaDto> response = peliculaController.createPelicula(peliculaDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdatePelicula_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.peliculaDtoToBo(peliculaDto1)).thenReturn(peliculaBo1);
        when(peliculaServices.read(id)).thenReturn(peliculaBo1);
        when(peliculaServices.update(peliculaBo1)).thenThrow(new ServiceException("Error updating pelicula"));

        // When
        ResponseEntity<PeliculaDto> response = peliculaController.updatePelicula(id, peliculaDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetPeliculaCb_ThrowsServiceException() throws ServiceException {
        // Given
        when(peliculaServices.getPeliculasCb()).thenThrow(new ServiceException("Error getting peliculas"));

        // When
        ResponseEntity<List<PeliculaDto>> response = peliculaController.getPeliculaCb();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    public void testReadPeliculaCb_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(peliculaServices.readCb(id)).thenThrow(new ServiceException("Error getting pelicula"));

        // When
        ResponseEntity<PeliculaDto> response = peliculaController.readPeliculaCb(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreatePeliculaCb_ThrowsServiceException() throws ServiceException {
        // Given
        when(dtoToBo.peliculaDtoToBo(peliculaDto1)).thenReturn(peliculaBo1);
        when(peliculaServices.createCb(peliculaBo1)).thenThrow(new ServiceException("Error creating pelicula"));

        // When
        ResponseEntity<PeliculaDto> response = peliculaController.createPeliculaCb(peliculaDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdatePeliculaCb_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.peliculaDtoToBo(peliculaDto1)).thenReturn(peliculaBo1);
        when(peliculaServices.readCb(id)).thenReturn(peliculaBo1);
        when(peliculaServices.updateCb(peliculaBo1)).thenThrow(new ServiceException("Error updating pelicula"));

        // When
        ResponseEntity<PeliculaDto> response = peliculaController.updatePeliculaCb(id, peliculaDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetPeliculas_EmptyList() {
        // Given
        when(peliculaServices.getAll()).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<PeliculaDto>> response = peliculaController.getPeliculas();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void testGetPeliculasCb_EmptyList() {

        // When
        ResponseEntity<List<PeliculaDto>> response = peliculaController.getPeliculas();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void testDeletePelicula() {
        // Arrange
        // Act
        peliculaController.deletePelicula(1L);
        // Assert
    }
     
    @Test
    public void testDeletePeliculaCb() {
        peliculaController.deletePeliculaCb(2L);
    }
     
    @Test
    public void testDeletePelicula_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        doThrow(new NullPointerException("Error deleting pelicula")).when(peliculaServices).delete(id);
     
        // When
        ResponseEntity<Void> response = peliculaController.deletePelicula(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testDeletePeliculaCb_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        doThrow(new NullPointerException("Error deleting pelicula")).when(peliculaServices).deleteCb(id);
     
        // When
        ResponseEntity<Void> response = peliculaController.deletePeliculaCb(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testGetPeliculaByCode_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(peliculaServices.read(id)).thenThrow(new NullPointerException("Error getting pelicula"));
     
        // When
        ResponseEntity<PeliculaDto> response = peliculaController.getPeliculaByCode(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testUpdatePelicula_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(dtoToBo.peliculaDtoToBo(peliculaDto1)).thenReturn(peliculaBo1);
        when(peliculaServices.read(id)).thenReturn(peliculaBo1);
        when(peliculaServices.update(peliculaBo1)).thenThrow(new NullPointerException("Error updating pelicula"));
     
        // When
        ResponseEntity<PeliculaDto> response = peliculaController.updatePelicula(id, peliculaDto1);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testReadPeliculaCb_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(peliculaServices.readCb(id)).thenThrow(new NullPointerException("Error getting pelicula"));
     
        // When
        ResponseEntity<PeliculaDto> response = peliculaController.readPeliculaCb(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testUpdatePeliculaCb_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(dtoToBo.peliculaDtoToBo(peliculaDto1)).thenReturn(peliculaBo1);
        when(peliculaServices.readCb(id)).thenReturn(peliculaBo1);
        when(peliculaServices.updateCb(peliculaBo1)).thenThrow(new NullPointerException("Error updating pelicula"));
     
        // When
        ResponseEntity<PeliculaDto> response = peliculaController.updatePeliculaCb(id, peliculaDto1);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
