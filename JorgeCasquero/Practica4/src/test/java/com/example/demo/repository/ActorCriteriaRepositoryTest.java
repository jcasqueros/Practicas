package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.model.Actor;
import com.example.demo.repository.cb.impl.ActorCriteriaImpl;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

@DataJpaTest
@ComponentScan(basePackages = "com.example.demo.repository.cb.impl")
class ActorCriterialRepositoryTest {

	@Autowired
	private ActorCriteriaImpl actorCriteriaImpl;

	// Junit test for getAll actor
	@Test
	void testGetAll() throws AlreadyExistsExeption {

		// when -action or the behaivour that we are going test
		List<Actor> listaActor = actorCriteriaImpl.getAll();

		// then-verigy the output
		assertThat(listaActor).isNotNull();
		assertThat(listaActor.size()).isEqualTo(5);
	}

	// Junit test for
	@Test
	void testGetById() throws NotFoundException, AlreadyExistsExeption {

		// given-precondition or setup
		Actor actorToSave = new Actor(1L, "Peter Dinklage", 32, "China");
		actorCriteriaImpl.create(actorToSave);
		// when -action or the behaivour taht we are going test
		Optional<Actor> actor = actorCriteriaImpl.getById(1L);

		// then -verify the output
		assertThat(actor).isNotNull();
		assertThat(actor.get()).isEqualTo(actorToSave);

	}

	// Junit test for save actor operation
	@Test
	void testCreate() throws AlreadyExistsExeption, NotFoundException {

		// given-precondition or setup
		Actor actor = new Actor(1L, "Jorge", 27, "España");

		// when -action or the behaivour taht we are going test
		Actor savedActor = actorCriteriaImpl.create(actor);

		// then -verify the output
		assertThat(savedActor).isNotNull();

		assertThat(savedActor).isEqualTo(actor);

		// when - action or the behavior that we are going to test

		// then - verify the output
		// Verificar que el actor se ha guardado correctamente en la base de datos
		Optional<Actor> retrievedActor = actorCriteriaImpl.getById(actor.getIdActor());
		assertNotNull(retrievedActor);
		assertEquals(actor, retrievedActor.get());

	}

	@Test
	void testCreateActorAlreadyExists() throws AlreadyExistsExeption, NotFoundException {
		// given-precondition or setup
		Actor actor = new Actor(1L, "Jorge", 27, "España");
		actorCriteriaImpl.create(actor); // crear el actor por primera vez

		// when - action or the behavior that we are going to test
		actorCriteriaImpl.create(actor); // intentar crear el actor de nuevo
	}

	// JUnit test for delete actor operation
	@Test
	void testDeleteById() throws AlreadyExistsExeption, NotFoundException {
//
//		actorCriteriaImpl.deleteById(2L);
//
//		Optional<Actor> actorOptional = actorCriteriaImpl.getById(2L);
//
//		assertThat(actorOptional).isEmpty();

	}

}
