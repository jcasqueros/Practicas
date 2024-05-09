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

import com.viewnext.Practica4.backend.business.bo.DirectorBo;
import com.viewnext.Practica4.backend.business.services.DirectorServices;
import com.viewnext.Practica4.backend.presentation.controller.DirectorController;
import com.viewnext.Practica4.backend.presentation.dto.DirectorDto;
import com.viewnext.Practica4.util.converter.dto.BoToDto;
import com.viewnext.Practica4.util.converter.dto.DtoToBo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DirectorControllerTest {

    @InjectMocks
    private DirectorController directorController;

    @Mock
    private DirectorServices directorServices;

    @Mock
    private DtoToBo dtoToBo;

    @Mock
    private BoToDto boToDto;

    private DirectorBo directorBo1;
    private DirectorBo directorBo2;
    private DirectorDto directorDto1;
    private DirectorDto directorDto2;

    @BeforeEach
    public void init() {
        initMocks();
    }

    private void initMocks() {
    	directorBo1 = new DirectorBo();
        directorBo1.setId(1L);
        directorBo1.setNombre("Director 1");
        directorBo1.setEdad(40);
        directorBo1.setNacionalidad("Español");

        directorBo2 = new DirectorBo();
        directorBo2.setId(2L);
        directorBo2.setNombre("Director 2");
        directorBo2.setEdad(45);
        directorBo2.setNacionalidad("Americano");

        directorDto1 = new DirectorDto();
        directorDto1.setId(1L);
        directorDto1.setNombre("Director 1");
        directorDto1.setEdad(40);
        directorDto1.setNacionalidad("Español");

        directorDto2 = new DirectorDto();
        directorDto2.setId(2L);
        directorDto2.setNombre("Director 2");
        directorDto2.setEdad(45);
        directorDto2.setNacionalidad("Americano");
    }

    @Test
    public void testGetDirectors() throws ServiceException {
        // Given
        List<DirectorBo> directorBoList = new ArrayList<>();
        directorBoList.add(directorBo1);
        directorBoList.add(directorBo2);
        when(directorServices.getAll()).thenReturn(directorBoList);

        // When
        ResponseEntity<List<DirectorDto>> response = directorController.getDirectores();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetDirectorByCode() throws ServiceException {
        // Given
        long id = 1L;
        when(directorServices.read(id)).thenReturn(directorBo1);

        // When
        ResponseEntity<DirectorDto> response = directorController.getDirectorByCode(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateDirector() throws ServiceException {
        // Given
        when(dtoToBo.directorDtoToBo(directorDto1)).thenReturn(directorBo1);
        when(directorServices.create(directorBo1)).thenReturn(directorBo1);

        // When
        ResponseEntity<DirectorDto> response = directorController.createDirector(directorDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateDirector() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.directorDtoToBo(directorDto1)).thenReturn(directorBo1);
        when(directorServices.read(id)).thenReturn(directorBo1);
        when(directorServices.update(directorBo1)).thenReturn(directorBo1);

        // When
        ResponseEntity<DirectorDto> response = directorController.updateDirector(id, directorDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    // Criteria Builder tests

    @Test
    public void testGetDirectorsCb() throws ServiceException {
        // Given
        List<DirectorBo> directorBoList = new ArrayList<>();
        directorBoList.add(directorBo1);
        directorBoList.add(directorBo2);
        when(directorServices.getDirectoresCb()).thenReturn(directorBoList);

        // When
        ResponseEntity<List<DirectorDto>> response = directorController.getDirectoresCb();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testReadDirectorCb() throws ServiceException {
        // Given
        long id = 1L;
        when(directorServices.readCb(id)).thenReturn(directorBo1);

        // When
        ResponseEntity<DirectorDto> response = directorController.readDirectorCb(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateDirectorCb() throws ServiceException {
        // Given
        when(dtoToBo.directorDtoToBo(directorDto1)).thenReturn(directorBo1);
        when(directorServices.createCb(directorBo1)).thenReturn(directorBo1);

        // When
        ResponseEntity<DirectorDto> response = directorController.createDirectorCb(directorDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateDirectorCb() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.directorDtoToBo(directorDto1)).thenReturn(directorBo1);
        when(directorServices.readCb(id)).thenReturn(directorBo1);
        when(directorServices.updateCb(directorBo1)).thenReturn(directorBo1);

        // When
        ResponseEntity<DirectorDto> response = directorController.updateDirectorCb(id, directorDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testDeleteDirector_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        doThrow(new ServiceException("Error deleting director")).when(directorServices).delete(id);

        // When
        ResponseEntity<Void> response = directorController.deleteDirector(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteDirectorCb_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        doThrow(new ServiceException("Error deleting director")).when(directorServices).deleteCb(id);

        // When
        ResponseEntity<Void> response = directorController.deleteDirectorCb(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetDirectors_ThrowsServiceException() throws ServiceException {
        // Given
        when(directorServices.getAll()).thenThrow(new ServiceException("Error getting directors"));

        // When
        ResponseEntity<List<DirectorDto>> response = directorController.getDirectores();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetDirectorByCode_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(directorServices.read(id)).thenThrow(new ServiceException("Error getting director"));

        // When
        ResponseEntity<DirectorDto> response = directorController.getDirectorByCode(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateDirector_ThrowsServiceException() throws ServiceException {
        // Given
        when(dtoToBo.directorDtoToBo(directorDto1)).thenReturn(directorBo1);
        when(directorServices.create(directorBo1)).thenThrow(new ServiceException("Error creating director"));

        // When
        ResponseEntity<DirectorDto> response = directorController.createDirector(directorDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateDirector_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.directorDtoToBo(directorDto1)).thenReturn(directorBo1);
        when(directorServices.read(id)).thenReturn(directorBo1);
        when(directorServices.update(directorBo1)).thenThrow(new ServiceException("Error updating director"));

        // When
        ResponseEntity<DirectorDto> response = directorController.updateDirector(id, directorDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetDirectorsCb_ThrowsServiceException() throws ServiceException {
        // Given
        when(directorServices.getDirectoresCb()).thenThrow(new ServiceException("Error getting directors"));

        // When
        ResponseEntity<List<DirectorDto>> response = directorController.getDirectoresCb();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testReadDirectorCb_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(directorServices.readCb(id)).thenThrow(new ServiceException("Error getting director"));

        // When
        ResponseEntity<DirectorDto> response = directorController.readDirectorCb(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateDirectorCb_ThrowsServiceException() throws ServiceException {
        // Given
        when(dtoToBo.directorDtoToBo(directorDto1)).thenReturn(directorBo1);
        when(directorServices.createCb(directorBo1)).thenThrow(new ServiceException("Error creating director"));

        // When
        ResponseEntity<DirectorDto> response = directorController.createDirectorCb(directorDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateDirectorCb_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.directorDtoToBo(directorDto1)).thenReturn(directorBo1);
        when(directorServices.readCb(id)).thenReturn(directorBo1);
        when(directorServices.updateCb(directorBo1)).thenThrow(new ServiceException("Error updating director"));

        // When
        ResponseEntity<DirectorDto> response = directorController.updateDirectorCb(id, directorDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    public void testGetDirectores_EmptyList() {
        // Given
        when(directorServices.getAll()).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<DirectorDto>> response = directorController.getDirectores();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void testGetDirectoresCb_EmptyList() {
        // Given
        when(directorServices.getDirectoresCb()).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<DirectorDto>> response = directorController.getDirectoresCb();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
    
    @Test
    public void testDeleteDirector() {
        // Arrange
        // Act
        directorController.deleteDirector(1L);
        // Assert
    }
     
    @Test
    public void testDeleteDirectorCb() {
        directorController.deleteDirectorCb(2L);
    }
     
    @Test
    public void testDeleteDirector_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        doThrow(new NullPointerException("Error deleting director")).when(directorServices).delete(id);
     
        // When
        ResponseEntity<Void> response = directorController.deleteDirector(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testDeleteDirectorCb_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        doThrow(new NullPointerException("Error deleting director")).when(directorServices).deleteCb(id);
     
        // When
        ResponseEntity<Void> response = directorController.deleteDirectorCb(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testGetDirectorByCode_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(directorServices.read(id)).thenThrow(new NullPointerException("Error getting director"));
     
        // When
        ResponseEntity<DirectorDto> response = directorController.getDirectorByCode(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testUpdateDirector_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(dtoToBo.directorDtoToBo(directorDto1)).thenReturn(directorBo1);
        when(directorServices.read(id)).thenReturn(directorBo1);
        when(directorServices.update(directorBo1)).thenThrow(new NullPointerException("Error updating director"));
     
        // When
        ResponseEntity<DirectorDto> response = directorController.updateDirector(id, directorDto1);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testReadDirectorCb_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(directorServices.readCb(id)).thenThrow(new NullPointerException("Error getting director"));
     
        // When
        ResponseEntity<DirectorDto> response = directorController.readDirectorCb(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testUpdateDirectorCb_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(dtoToBo.directorDtoToBo(directorDto1)).thenReturn(directorBo1);
        when(directorServices.readCb(id)).thenReturn(directorBo1);
        when(directorServices.updateCb(directorBo1)).thenThrow(new NullPointerException("Error updating director"));
     
        // When
        ResponseEntity<DirectorDto> response = directorController.updateDirectorCb(id, directorDto1);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
