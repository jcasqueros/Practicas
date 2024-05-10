package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.example.demo.converters.BoToModel;
import com.example.demo.converters.ModelToBo;
import com.example.demo.model.Actor;
import com.example.demo.repository.cb.ActorRepositoryCriteria;
import com.example.demo.repository.jpa.ActorRepository;
import com.example.demo.servcice.bo.ActorBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;
import com.example.demo.servcice.impl.ActorServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ActorServicesImplTest {

	@InjectMocks
	private ActorServiceImpl actorServiceImpl;

	@Mock
	ActorRepository actorRepository;

	@Mock
	ActorRepositoryCriteria actorRepositoryCriteria;

	@Mock
	private BoToModel boToModel;

	@Mock
	private ModelToBo modelToBo;

	private Actor actor1;
	private Actor actor2;
	private ActorBo actorBo1;
	private ActorBo actorBo2;

	@BeforeEach
	public void init() {
		initMocks();
	}

	private void initMocks() {
		actorBo1 = new ActorBo();
		actorBo1.setIdActor(1L);
		actorBo1.setNombre("Actor 1");
		actorBo1.setEdad(30);
		actorBo1.setNacionalidad("Español");

		actor1 = new Actor();
		actor1.setIdActor(1L);
		actor1.setNombre("Actor 1");
		actor1.setEdad(30);
		actor1.setNacionalidad("Español");

		actorBo2 = new ActorBo();
		actorBo2.setIdActor(2L);
		actorBo2.setNombre("Actor 2");
		actorBo2.setEdad(25);
		actorBo2.setNacionalidad("inglés");

		actor2 = new Actor();
		actor2.setIdActor(2L);
		actor2.setNombre("Actor 2");
		actor2.setEdad(25);
		actor2.setNacionalidad("inglés");

	}

	@Test
	void testGetAll() {
		// Arrange
		List<Actor> actors = new ArrayList<>();
		actors.add(actor1);
		actors.add(actor2);
		when(actorRepository.findAll()).thenReturn(actors);
		when(modelToBo.actorToActorBo(actor1)).thenReturn(actorBo1);
		when(modelToBo.actorToActorBo(actor2)).thenReturn(actorBo2);

		// Act
		List<ActorBo> result = actorServiceImpl.getAll();

		// Assert
		assertEquals(2, result.size());
		assertEquals(actorBo1, result.get(0));
		assertEquals(actorBo2, result.get(1));

	}

	@Test
	void testGetById() throws NotFoundException {
		when(actorRepository.findById(1L)).thenReturn(Optional.of(actor1));
		when(modelToBo.actorToActorBo(actor1)).thenReturn(actorBo1);

		ActorBo result = actorServiceImpl.getById(1L);
		assertEquals(actorBo1, result);

	}

	@Test
	void testGetByIdExists() throws NotFoundException {
		when(actorRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> actorServiceImpl.getById(1L));
	}

	@Test
	void testCreate() throws AlreadyExistsExeption {

		when(boToModel.boToActor(actorBo1)).thenReturn(actor1);
		when(actorRepository.save(actor1)).thenReturn(actor1);
		when(modelToBo.actorToActorBo(actor1)).thenReturn(actorBo1);

		// Act
		ActorBo result = actorServiceImpl.create(actorBo1);

		// Assert
		assertEquals(actorBo1, result);

	}

	@Test
	void testCreateThrowAlreadyExists() throws AlreadyExistsExeption {

		when(actorRepository.existsById(actor1.getIdActor())).thenReturn(true);
		assertThrows(AlreadyExistsExeption.class, () -> actorServiceImpl.create(actorBo1));

	}

	@Test
	void testDeleteById() throws NotFoundException {
		Long id = 1L;
		when(actorRepository.existsById(id)).thenReturn(true);

		// Act
		actorServiceImpl.deleteById(id);

		// Assert
		verify(actorRepository, times(1)).deleteById(id);
	}

	@Test
	void testDeleteByIdnotFound() throws NotFoundException {
		Long id = 1L;
		when(actorRepository.existsById(id)).thenReturn(false);

		// assert
		assertThrows(NotFoundException.class, () -> actorServiceImpl.deleteById(id));
	}

	@Test
	void testGetAllCriteria() {
		List<Actor> actors = new ArrayList<>();
		actors.add(actor1);
		actors.add(actor2);
		when(actorRepositoryCriteria.getAll()).thenReturn(actors);
		when(modelToBo.actorToActorBo(actor1)).thenReturn(actorBo1);
		when(modelToBo.actorToActorBo(actor2)).thenReturn(actorBo2);

		// Act
		List<ActorBo> result = actorServiceImpl.getAllCriteria();

		// Assert
		assertEquals(2, result.size());
		assertEquals(actorBo1, result.get(0));
		assertEquals(actorBo2, result.get(1));
	}

	@Test
	void testGetByIdCriteria() throws NotFoundException {

		when(actorRepositoryCriteria.getById(1L)).thenReturn(Optional.of(actor1));
		when(modelToBo.actorToActorBo(actor1)).thenReturn(actorBo1);

		ActorBo result = actorServiceImpl.getByIdCriteria(1L);
		assertEquals(actorBo1, result);
	}

	@Test
	void testCreateCriteria() throws AlreadyExistsExeption, NotFoundException {
		when(boToModel.boToActor(actorBo1)).thenReturn(actor1);
		when(actorRepositoryCriteria.create(actor1)).thenReturn(actor1);
		when(modelToBo.actorToActorBo(actor1)).thenReturn(actorBo1);
		when(actorRepositoryCriteria.getById(actor1.getIdActor())).thenReturn(Optional.empty());

		// Act
		ActorBo result = actorServiceImpl.createCriteria(actorBo1);

		// Assert
		assertEquals(actorBo1, result);
		verify(boToModel).boToActor(actorBo1);
		verify(actorRepositoryCriteria).create(actor1);
		verify(modelToBo).actorToActorBo(actor1);

	}

	@Test
	void testCreateCriteriaAlreadyExistsExeption() throws AlreadyExistsExeption, NotFoundException {

		when(actorRepositoryCriteria.getById(actor1.getIdActor())).thenReturn(Optional.of(actor1));
		assertThrows(AlreadyExistsExeption.class, () -> actorServiceImpl.createCriteria(actorBo1));

	}

	@Test
	void testDeleteByIdCriteria() throws NotFoundException {
		Long id = 1L;
		when(actorRepositoryCriteria.getById(id)).thenReturn(Optional.of(actor1));

		// Act
		actorServiceImpl.deleteByIdCriteria(id);

		// Assert
		verify(actorRepositoryCriteria, times(1)).deleteById(id);
	}

	@Test
	void testDeleteByIdCriteriaThrowNotFoundException() throws NotFoundException {
		Long id = 1l;
		when(actorRepositoryCriteria.getById(id)).thenReturn(null);

		// assert
		assertThrows(NotFoundException.class, () -> actorServiceImpl.deleteByIdCriteria(id));
	}
}
