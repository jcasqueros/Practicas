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

import com.viewnext.Practica4.backend.business.bo.ActorBo;
import com.viewnext.Practica4.backend.business.services.ActorServices;
import com.viewnext.Practica4.backend.presentation.controller.ActorController;
import com.viewnext.Practica4.backend.presentation.dto.ActorDto;
import com.viewnext.Practica4.util.converter.dto.BoToDto;
import com.viewnext.Practica4.util.converter.dto.DtoToBo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActorControllerTest {

	@InjectMocks
	private ActorController actorController;

	@Mock
	private ActorServices actorServices;

	@Mock
	private DtoToBo dtoToBo;

	@Mock
	private BoToDto boToDto;

	private ActorBo actorBo1;
	private ActorBo actorBo2;
	private ActorDto actorDto1;
	private ActorDto actorDto2;

	@BeforeEach
	public void init() {
		initMocks();
	}

	private void initMocks() {
		actorBo1 = new ActorBo();
		actorBo1.setId(1L);
		actorBo1.setNombre("Actor 1");
		actorBo1.setEdad(30);
		actorBo1.setNacionalidad("Español");

		actorBo2 = new ActorBo();
		actorBo2.setId(2L);
		actorBo2.setNombre("Actor 2");
		actorBo2.setEdad(35);
		actorBo2.setNacionalidad("Americano");

		actorDto1 = new ActorDto();
		actorDto1.setId(1L);
		actorDto1.setNombre("Actor 1");
		actorDto1.setEdad(30);
		actorDto1.setNacionalidad("Español");

		actorDto2 = new ActorDto();
		actorDto2.setId(2L);
		actorDto2.setNombre("Actor 2");
		actorDto2.setEdad(35);
		actorDto2.setNacionalidad("Americano");
	}

	@Test
	public void testGetActors() throws ServiceException {
		// Given
		List<ActorBo> actorBoList = new ArrayList<>();
		actorBoList.add(actorBo1);
		actorBoList.add(actorBo2);
		when(actorServices.getAll()).thenReturn(actorBoList);

		// When
		ResponseEntity<List<ActorDto>> response = actorController.getActors();

		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetActorByCode() throws ServiceException {
		// Given
		long id = 1L;
		when(actorServices.read(id)).thenReturn(actorBo1);

		// When
		ResponseEntity<ActorDto> response = actorController.getActorByCode(id);

		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testCreateActor() throws ServiceException {
		// Given
		when(dtoToBo.actorDtoToBo(actorDto1)).thenReturn(actorBo1);
		when(actorServices.create(actorBo1)).thenReturn(actorBo1);

		// When
		ResponseEntity<ActorDto> response = actorController.createActor(actorDto1);

		// Then
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void testUpdateActor() throws ServiceException {
		// Given
		long id = 1L;
		when(dtoToBo.actorDtoToBo(actorDto1)).thenReturn(actorBo1);
		when(actorServices.read(id)).thenReturn(actorBo1);
		when(actorServices.update(actorBo1)).thenReturn(actorBo1);

		// When
		ResponseEntity<ActorDto> response = actorController.updateActor(id, actorDto1);

		// Then
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	public void testDeleteActor() {
		// Arrange
		// Act
		actorController.deleteActor(1L);
		// Assert
	}

	// Criteria Builder tests

	@Test
	public void testGetActorsCb() throws ServiceException {
		// Given
		List<ActorBo> actorBoList = new ArrayList<>();
		actorBoList.add(actorBo1);
		actorBoList.add(actorBo2);
		when(actorServices.getActoresCb()).thenReturn(actorBoList);

		// When
		ResponseEntity<List<ActorDto>> response = actorController.getActoresCb();

		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testReadActorCb() throws ServiceException {
		// Given
		long id = 1L;
		when(actorServices.readCb(id)).thenReturn(actorBo1);

		// When
		ResponseEntity<ActorDto> response = actorController.readActorCb(id);

		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testCreateActorCb() throws ServiceException {
		// Given
		when(dtoToBo.actorDtoToBo(actorDto1)).thenReturn(actorBo1);
		when(actorServices.createCb(actorBo1)).thenReturn(actorBo1);

		// When
		ResponseEntity<ActorDto> response = actorController.createActorCb(actorDto1);

		// Then
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void testUpdateActorCb() throws ServiceException {
		// Given
		long id = 1L;
		when(dtoToBo.actorDtoToBo(actorDto1)).thenReturn(actorBo1);
		when(actorServices.readCb(id)).thenReturn(actorBo1);
		when(actorServices.updateCb(actorBo1)).thenReturn(actorBo1);

		// When
		ResponseEntity<ActorDto> response = actorController.updateActorCb(id, actorDto1);

		// Then
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	public void testDeleteActorCb() {
		actorController.deleteActorCb(2L);
	}
	
	@Test
	public void testDeleteActor_ThrowsServiceException() throws ServiceException {
		// Given
		long id = 1L;
		doThrow(new ServiceException("Error deleting actor")).when(actorServices).delete(id);

		// When
		ResponseEntity<Void> response = actorController.deleteActor(id);

		// Then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void testDeleteActor_ThrowsNullPointerException() throws NullPointerException {
		// Given
		long id = 1L;
		doThrow(new NullPointerException("Error deleting actor")).when(actorServices).delete(id);

		// When
		ResponseEntity<Void> response = actorController.deleteActor(id);

		// Then
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testDeleteActorCb_ThrowsServiceException() throws ServiceException {
		// Given
		long id = 1L;
		doThrow(new ServiceException("Error deleting actor")).when(actorServices).deleteCb(id);

		// When
		ResponseEntity<Void> response = actorController.deleteActorCb(id);

		// Then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void testDeleteActorCb_ThrowsNullPointerException() throws NullPointerException {
		// Given
		long id = 1L;
		doThrow(new NullPointerException("Error deleting actor")).when(actorServices).deleteCb(id);

		// When
		ResponseEntity<Void> response = actorController.deleteActorCb(id);

		// Then
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}


	@Test
	public void testGetActors_ThrowsServiceException() throws ServiceException {
		// Given
		when(actorServices.getAll()).thenThrow(new ServiceException("Error getting actors"));

		// When
		ResponseEntity<List<ActorDto>> response = actorController.getActors();

		// Then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testGetActorByCode_ThrowsServiceException() throws ServiceException {
		// Given
		long id = 1L;
		when(actorServices.read(id)).thenThrow(new ServiceException("Error getting actor"));

		// When
		ResponseEntity<ActorDto> response = actorController.getActorByCode(id);

		// Then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testGetActorByCode_ThrowsNullPointerException() throws NullPointerException {
		// Given
		long id = 1L;
		when(actorServices.read(id)).thenThrow(new NullPointerException("Error getting actor"));

		// When
		ResponseEntity<ActorDto> response = actorController.getActorByCode(id);

		// Then
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testCreateActor_ThrowsServiceException() throws ServiceException {
		// Given
		when(dtoToBo.actorDtoToBo(actorDto1)).thenReturn(actorBo1);
		when(actorServices.create(actorBo1)).thenThrow(new ServiceException("Error creating actor"));

		// When
		ResponseEntity<ActorDto> response = actorController.createActor(actorDto1);

		// Then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testUpdateActor_ThrowsServiceException() throws ServiceException {
		// Given
		long id = 1L;
		when(dtoToBo.actorDtoToBo(actorDto1)).thenReturn(actorBo1);
		when(actorServices.read(id)).thenReturn(actorBo1);
		when(actorServices.update(actorBo1)).thenThrow(new ServiceException("Error updating actor"));

		// When
		ResponseEntity<ActorDto> response = actorController.updateActor(id, actorDto1);

		// Then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testUpdateActor_ThrowsNullPointerException() throws NullPointerException {
		// Given
		long id = 1L;
		when(dtoToBo.actorDtoToBo(actorDto1)).thenReturn(actorBo1);
		when(actorServices.read(id)).thenReturn(actorBo1);
		when(actorServices.update(actorBo1)).thenThrow(new NullPointerException("Error updating actor"));

		// When
		ResponseEntity<ActorDto> response = actorController.updateActor(id, actorDto1);

		// Then
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testGetActorsCb_ThrowsServiceException() throws ServiceException {
		// Given
		when(actorServices.getActoresCb()).thenThrow(new ServiceException("Error getting actors"));

		// When
		ResponseEntity<List<ActorDto>> response = actorController.getActoresCb();

		// Then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testReadActorCb_ThrowsServiceException() throws ServiceException {
		// Given
		long id = 1L;
		when(actorServices.readCb(id)).thenThrow(new ServiceException("Error getting actor"));

		// When
		ResponseEntity<ActorDto> response = actorController.readActorCb(id);

		// Then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testReadActorCb_ThrowsNullPointerException() throws NullPointerException {
		// Given
		long id = 1L;
		when(actorServices.readCb(id)).thenThrow(new NullPointerException("Error getting actor"));

		// When
		ResponseEntity<ActorDto> response = actorController.readActorCb(id);

		// Then
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testCreateActorCb_ThrowsServiceException() throws ServiceException {
		// Given
		when(dtoToBo.actorDtoToBo(actorDto1)).thenReturn(actorBo1);
		when(actorServices.createCb(actorBo1)).thenThrow(new ServiceException("Error creating actor"));

		// When
		ResponseEntity<ActorDto> response = actorController.createActorCb(actorDto1);

		// Then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testUpdateActorCb_ThrowsServiceException() throws ServiceException {
		// Given
		long id = 1L;
		when(dtoToBo.actorDtoToBo(actorDto1)).thenReturn(actorBo1);
		when(actorServices.readCb(id)).thenReturn(actorBo1);
		when(actorServices.updateCb(actorBo1)).thenThrow(new ServiceException("Error updating actor"));

		// When
		ResponseEntity<ActorDto> response = actorController.updateActorCb(id, actorDto1);

		// Then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testUpdateActorCb_ThrowsNullPointerException() throws NullPointerException {
		// Given
		long id = 1L;
		when(dtoToBo.actorDtoToBo(actorDto1)).thenReturn(actorBo1);
		when(actorServices.readCb(id)).thenReturn(actorBo1);
		when(actorServices.updateCb(actorBo1)).thenThrow(new NullPointerException("Error updating actor"));

		// When
		ResponseEntity<ActorDto> response = actorController.updateActorCb(id, actorDto1);

		// Then
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testGetActores_EmptyList() {
		// Given
		when(actorServices.getAll()).thenReturn(new ArrayList<>());

		// When
		ResponseEntity<List<ActorDto>> response = actorController.getActors();

		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0, response.getBody().size());
	}

	@Test
	public void testGetActoresCb_EmptyList() {
		// Given
		when(actorServices.getActoresCb()).thenReturn(new ArrayList<>());

		// When
		ResponseEntity<List<ActorDto>> response = actorController.getActoresCb();

		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0, response.getBody().size());
	}
}
