package com.viewnext.Practica4.backend.business.services.impl;

import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.viewnext.Practica4.backend.business.bo.ActorBo;
import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.repository.ActorRepository;
import com.viewnext.Practica4.backend.repository.custom.ActorCustomRepository;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActorServiceImplTest {

	private Actor actor1, actor2;
	private ActorBo actorBo1, actorBo2;

	@InjectMocks
	private ActorServicesImpl actorServices;

	@Mock
	private ActorRepository actorRepository;

	@Mock
	private ActorCustomRepository actorCustomRepository;

	@Mock
	private EntityToBo entityToBo;

	@Mock
	private BoToEntity boToEntity;

	@BeforeEach
	public void init() {
		initMocks();
	}

	private void initMocks() {
		actor1 = new Actor();
		actor1.setId(1L);
		actor1.setNombre("Actor 1");
		actor1.setEdad(30);
		actor1.setNacionalidad("Española");

		actor2 = new Actor();
		actor2.setId(2L);
		actor2.setNombre("Actor 2");
		actor2.setEdad(35);
		actor2.setNacionalidad("Francesa");

		actorBo1 = new ActorBo();
		actorBo1.setId(1L);
		actorBo1.setNombre("Actor 1");
		actorBo1.setEdad(30);
		actorBo1.setNacionalidad("Española");

		actorBo2 = new ActorBo();
		actorBo2.setId(2L);
		actorBo2.setNombre("Actor 2");
		actorBo2.setEdad(35);
		actorBo2.setNacionalidad("Francesa");
	}

	@Test
	void testCreate() {
		// Arrange
		when(boToEntity.actorBoToActor(actorBo1)).thenReturn(actor1);
		when(actorRepository.save(actor1)).thenReturn(actor1);
		when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);

		// Act
		ActorBo result = actorServices.create(actorBo1);

		// Assert
		assertEquals(actorBo1, result);
	}

	@Test
	void testRead() {
		// Arrange
		when(actorRepository.findById(1L)).thenReturn(Optional.of(actor1));
		when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);

		// Act
		ActorBo result = actorServices.read(1L);

		// Assert
		assertEquals(actorBo1, result);
	}

	@Test
	void testGetAll() {
		// Arrange
		List<Actor> actors = new ArrayList<>();
		actors.add(actor1);
		actors.add(actor2);
		when(actorRepository.findAll()).thenReturn(actors);
		when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);
		when(entityToBo.actorToActorBo(actor2)).thenReturn(actorBo2);

		// Act
		List<ActorBo> result = actorServices.getAll();

		// Assert
		assertEquals(2, result.size());
		assertEquals(actorBo1, result.get(0));
		assertEquals(actorBo2, result.get(1));
	}

	@Test
	void testDelete() {
		// Arrange
		when(actorRepository.existsById(1L)).thenReturn(true);
		// Act
		actorServices.delete(1L);

		// Assert
	}

	@Test
	void testUpdate() {
		// Arrange
		when(boToEntity.actorBoToActor(actorBo1)).thenReturn(actor1);
		when(actorRepository.save(actor1)).thenReturn(actor1);
		when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);

		// Act
		ActorBo result = actorServices.update(actorBo1);

		// Assert
		assertEquals(actorBo1, result);
	}

	@Test
	void testGetActoresCb() {
		// Arrange
		List<Actor> actors = new ArrayList<>();
		actors.add(actor1);
		actors.add(actor2);
		when(actorCustomRepository.getActoresCb()).thenReturn(actors);
		when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);
		when(entityToBo.actorToActorBo(actor2)).thenReturn(actorBo2);

		// Act
		List<ActorBo> result = actorServices.getActoresCb();

		// Assert
		assertEquals(2, result.size());
		assertEquals(actorBo1, result.get(0));
		assertEquals(actorBo2, result.get(1));
	}

	@Test
	void testCreateCb() {
		// Arrange
		when(boToEntity.actorBoToActor(actorBo1)).thenReturn(actor1);
		when(actorCustomRepository.createCb(actor1)).thenReturn(actor1);
		when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);

		// Act
		ActorBo result = actorServices.createCb(actorBo1);

		// Assert
		assertEquals(actorBo1, result);
	}

	@Test
	void testReadCb() {
		// Arrange
		when(actorCustomRepository.readCb(1L)).thenReturn(actor1);
		when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);

		// Act
		ActorBo result = actorServices.readCb(1L);

		// Assert
		assertEquals(actorBo1, result);
	}

	@Test
	void testUpdateCb() {
		// Arrange
		when(boToEntity.actorBoToActor(actorBo1)).thenReturn(actor1);
		when(actorCustomRepository.updateCb(actor1)).thenReturn(actor1);
		when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);

		// Act
		ActorBo result = actorServices.updateCb(actorBo1);

		// Assert
		assertEquals(actorBo1, result);
	}

	@Test
	void testDeleteCb() {
		//Arrange

		// Act
		actorServices.deleteCb(1L);

		// Assert

	}

	@Test
	void testRead_actorNoExiste() {
		// Arrange
		when(actorRepository.findById(1L)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(EntityNotFoundException.class, () -> actorServices.read(1L));
	}

	@Test
	void testGetAll_listaVacia() {
		// Arrange
		when(actorRepository.findAll()).thenReturn(new ArrayList<>());

		// Act
		List<ActorBo> result = actorServices.getAll();

		// Assert
		assertEquals(0, result.size());
	}

	@Test
	void testDelete_actorNoExiste() {
		// Arrange
		when(actorRepository.existsById(1L)).thenReturn(false);

		// Act and Assert
		assertThrows(EntityNotFoundException.class, () -> actorServices.delete(1L));
	}
	
	@Test
	void testDeleteCb_actorNoExiste() {
	    // Arrange
	    doThrow(new RuntimeException("Error al borrar actor con Criteria Builder")).when(actorCustomRepository).deleteCb(1L);
 
	    // Act and Assert
	    assertThrows(ServiceException.class, () -> actorServices.deleteCb(1L));
	}


	@Test
	void testCreate_actorError() {
	    // Arrange
	    when(actorRepository.save(actor1)).thenThrow(new RuntimeException("Error al crear actor"));

	    // Act and Assert
	    assertThrows(ServiceException.class, () -> actorServices.create(actorBo1));
	}


	@Test
	void testGetAll_actorError() {
	    // Arrange
	    when(actorRepository.findAll()).thenThrow(new RuntimeException("Error al obtener lista de actores"));

	    // Act and Assert
	    assertThrows(ServiceException.class, () -> actorServices.getAll());
	}
	
	@Test
	void testUpdate_actorError() {
	    // Arrange
	    when(actorRepository.save(any(Actor.class))).thenThrow(new RuntimeException("Error al actualizar actor"));

	    // Act and Assert
	    assertThrows(ServiceException.class, () -> actorServices.update(actorBo1));
	}

	@Test
	void testGetActoresCb_actorError() {
	    // Arrange
	    when(actorCustomRepository.getActoresCb()).thenThrow(new RuntimeException("Error al obtener lista de actores con Criteria Builder"));

	    // Act and Assert
	    assertThrows(ServiceException.class, () -> actorServices.getActoresCb());
	}

	@Test
	void testCreateCb_actorError() {
	    // Arrange
	    when(actorCustomRepository.createCb(any(Actor.class))).thenThrow(new RuntimeException("Error al crear actor con Criteria Builder"));

	    // Act and Assert
	    assertThrows(ServiceException.class, () -> actorServices.createCb(actorBo1));
	}

	@Test
	void testReadCb_actorError() {
	    // Arrange
	    when(actorCustomRepository.readCb(1L)).thenThrow(new RuntimeException("Error al leer actor con Criteria Builder"));

	    // Act and Assert
	    assertThrows(ServiceException.class, () -> actorServices.readCb(1L));
	}

	@Test
	void testUpdateCb_actorError() {
	    // Arrange
	    when(actorCustomRepository.updateCb(any(Actor.class))).thenThrow(new RuntimeException("Error al actualizar actor con Criteria Builder"));

	    // Act and Assert
	    assertThrows(ServiceException.class, () -> actorServices.updateCb(actorBo1));
	}
	
	@Test
	void testReadCb_actorNotFound() {
	    // Arrange
	    when(actorCustomRepository.readCb(1L)).thenReturn(null);

	    // Act and Assert
	    assertThrows(ServiceException.class, () -> actorServices.readCb(1L));
	}
}