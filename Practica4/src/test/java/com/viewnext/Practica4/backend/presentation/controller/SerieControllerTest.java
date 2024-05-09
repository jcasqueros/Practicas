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

import com.viewnext.Practica4.backend.business.bo.SerieBo;
import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.business.model.Director;
import com.viewnext.Practica4.backend.business.model.Productora;
import com.viewnext.Practica4.backend.business.services.SerieServices;
import com.viewnext.Practica4.backend.presentation.controller.SerieController;
import com.viewnext.Practica4.backend.presentation.dto.SerieDto;
import com.viewnext.Practica4.util.converter.dto.BoToDto;
import com.viewnext.Practica4.util.converter.dto.DtoToBo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SerieControllerTest {

    @InjectMocks
    private SerieController serieController;

    @Mock
    private SerieServices serieServices;

    @Mock
    private DtoToBo dtoToBo;

    @Mock
    private BoToDto boToDto;

    private SerieBo serieBo1;
    private SerieBo serieBo2;
    private SerieDto serieDto1;
    private SerieDto serieDto2;

    @BeforeEach
    public void init() {
        initMocks();
    }

    private void initMocks() {
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

        serieDto1 = new SerieDto();
        serieDto1.setId(1L);
        serieDto1.setTitulo("Serie 1");
        serieDto1.setAnho(2000);
        serieDto1.setIdDirector(new Director());
        serieDto1.setIdProductora(new Productora());
        List<Actor> actoresDto1 = new ArrayList<>();
        actoresDto1.add(new Actor());
        actoresDto1.add(new Actor());
        serieDto1.setActores(actoresDto1);

        serieDto2 = new SerieDto();
        serieDto2.setId(2L);
        serieDto2.setTitulo("Serie 2");
        serieDto2.setAnho(2010);
        serieDto2.setIdDirector(new Director());
        serieDto2.setIdProductora(new Productora());
        List<Actor> actoresDto2 = new ArrayList<>();
        actoresDto2.add(new Actor());
        actoresDto2.add(new Actor());
        serieDto2.setActores(actoresDto2);
    }

    @Test
    public void testGetSeries() throws ServiceException {
        // Given
        List<SerieBo> serieBoList = new ArrayList<>();
        serieBoList.add(serieBo1);
        serieBoList.add(serieBo2);
        when(serieServices.getAll()).thenReturn(serieBoList);

        // When
        ResponseEntity<List<SerieDto>> response = serieController.getSeries();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetSerieByCode() throws ServiceException {
        // Given
        long id = 1L;
        when(serieServices.read(id)).thenReturn(serieBo1);

        // When
        ResponseEntity<SerieDto> response = serieController.getSerieByCode(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateSerie() throws ServiceException {
        // Given
        when(dtoToBo.serieDtoToBo(serieDto1)).thenReturn(serieBo1);
        when(serieServices.create(serieBo1)).thenReturn(serieBo1);

        // When
        ResponseEntity<SerieDto> response = serieController.createSerie(serieDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateSerie() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.serieDtoToBo(serieDto1)).thenReturn(serieBo1);
        when(serieServices.read(id)).thenReturn(serieBo1);
        when(serieServices.update(serieBo1)).thenReturn(serieBo1);

        // When
        ResponseEntity<SerieDto> response = serieController.updateSerie(id, serieDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    // Criteria Builder tests

    @Test
    public void testGetSeriesCb() throws ServiceException {
        // Given
        List<SerieBo> serieBoList = new ArrayList<>();
        serieBoList.add(serieBo1);
        serieBoList.add(serieBo2);
        when(serieServices.getSeriesCb()).thenReturn(serieBoList);

        // When
        ResponseEntity<List<SerieDto>> response = serieController.getSerieCb();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testReadSerieCb() throws ServiceException {
        // Given
        long id = 1L;
        when(serieServices.readCb(id)).thenReturn(serieBo1);

        // When
        ResponseEntity<SerieDto> response = serieController.readSerieCb(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateSerieCb() throws ServiceException {
        // Given
        when(dtoToBo.serieDtoToBo(serieDto1)).thenReturn(serieBo1);
        when(serieServices.createCb(serieBo1)).thenReturn(serieBo1);

        // When
        ResponseEntity<SerieDto> response = serieController.createSerieCb(serieDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateSerieCb() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.serieDtoToBo(serieDto1)).thenReturn(serieBo1);
        when(serieServices.readCb(id)).thenReturn(serieBo1);
        when(serieServices.updateCb(serieBo1)).thenReturn(serieBo1);

        // When
        ResponseEntity<SerieDto> response = serieController.updateSerieCb(id, serieDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testDeleteSerie_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        doThrow(new ServiceException("Error deleting serie")).when(serieServices).delete(id);

        // When
        ResponseEntity<Void> response = serieController.deleteSerie(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteSerieCb_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        doThrow(new ServiceException("Error deleting serie")).when(serieServices).deleteCb(id);

        // When
        ResponseEntity<Void> response = serieController.deleteSerieCb(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetSeries_ThrowsServiceException() throws ServiceException {
        // Given
        when(serieServices.getAll()).thenThrow(new ServiceException("Error getting series"));

        // When
        ResponseEntity<List<SerieDto>> response = serieController.getSeries();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetSerieByCode_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(serieServices.read(id)).thenThrow(new ServiceException("Error getting serie"));

        // When
        ResponseEntity<SerieDto> response = serieController.getSerieByCode(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateSerie_ThrowsServiceException() throws ServiceException {
        // Given
        when(dtoToBo.serieDtoToBo(serieDto1)).thenReturn(serieBo1);
        when(serieServices.create(serieBo1)).thenThrow(new ServiceException("Error creating serie"));

        // When
        ResponseEntity<SerieDto> response = serieController.createSerie(serieDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateSerie_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.serieDtoToBo(serieDto1)).thenReturn(serieBo1);
        when(serieServices.read(id)).thenReturn(serieBo1);
        when(serieServices.update(serieBo1)).thenThrow(new ServiceException("Error updating serie"));

        // When
        ResponseEntity<SerieDto> response = serieController.updateSerie(id, serieDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetSeriesCb_ThrowsServiceException() throws ServiceException {
        // Given
        when(serieServices.getSeriesCb()).thenThrow(new ServiceException("Error getting series"));

        // When
        ResponseEntity<List<SerieDto>> response = serieController.getSerieCb();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testReadSerieCb_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(serieServices.readCb(id)).thenThrow(new ServiceException("Error getting serie"));

        // When
        ResponseEntity<SerieDto> response = serieController.readSerieCb(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateSerieCb_ThrowsServiceException() throws ServiceException {
        // Given
        when(dtoToBo.serieDtoToBo(serieDto1)).thenReturn(serieBo1);
        when(serieServices.createCb(serieBo1)).thenThrow(new ServiceException("Error creating serie"));

        // When
        ResponseEntity<SerieDto> response = serieController.createSerieCb(serieDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateSerieCb_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.serieDtoToBo(serieDto1)).thenReturn(serieBo1);
        when(serieServices.readCb(id)).thenReturn(serieBo1);
        when(serieServices.updateCb(serieBo1)).thenThrow(new ServiceException("Error updating serie"));

        // When
        ResponseEntity<SerieDto> response = serieController.updateSerieCb(id, serieDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetSeries_EmptyList() {
        // Given
        when(serieServices.getAll()).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<SerieDto>> response = serieController.getSeries();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void testGetSeriesCb_EmptyList() {

        // When
        ResponseEntity<List<SerieDto>> response = serieController.getSerieCb();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
    
    @Test
    public void testDeleteSerie() {
        // Arrange
        // Act
        serieController.deleteSerie(1L);
        // Assert
    }
     
    @Test
    public void testDeleteSerieCb() {
        serieController.deleteSerieCb(2L);
    }
     
    @Test
    public void testDeleteSerie_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        doThrow(new NullPointerException("Error deleting serie")).when(serieServices).delete(id);
     
        // When
        ResponseEntity<Void> response = serieController.deleteSerie(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testDeleteSerieCb_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        doThrow(new NullPointerException("Error deleting serie")).when(serieServices).deleteCb(id);
     
        // When
        ResponseEntity<Void> response = serieController.deleteSerieCb(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testGetSerieByCode_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(serieServices.read(id)).thenThrow(new NullPointerException("Error getting serie"));
     
        // When
        ResponseEntity<SerieDto> response = serieController.getSerieByCode(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testUpdateSerie_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(dtoToBo.serieDtoToBo(serieDto1)).thenReturn(serieBo1);
        when(serieServices.read(id)).thenReturn(serieBo1);
        when(serieServices.update(serieBo1)).thenThrow(new NullPointerException("Error updating serie"));
     
        // When
        ResponseEntity<SerieDto> response = serieController.updateSerie(id, serieDto1);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testReadSerieCb_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(serieServices.readCb(id)).thenThrow(new NullPointerException("Error getting serie"));
     
        // When
        ResponseEntity<SerieDto> response = serieController.readSerieCb(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testUpdateSerieCb_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(dtoToBo.serieDtoToBo(serieDto1)).thenReturn(serieBo1);
        when(serieServices.readCb(id)).thenReturn(serieBo1);
        when(serieServices.updateCb(serieBo1)).thenThrow(new NullPointerException("Error updating serie"));
     
        // When
        ResponseEntity<SerieDto> response = serieController.updateSerieCb(id, serieDto1);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
