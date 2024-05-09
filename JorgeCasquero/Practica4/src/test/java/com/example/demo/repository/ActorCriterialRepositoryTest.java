package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.model.Actor;
import com.example.demo.repository.cb.impl.ActorCriteriaImpl;
import com.example.demo.servcice.exception.AlreadyExistsExeption;

@DataJpaTest
@ComponentScan(basePackages = "com.example.demo.repository.cb.impl")
class ActorCriterialRepositoryTest {

	@Autowired
	private ActorCriteriaImpl actorCriteriaImpl;

	@Test
	void testGetAll() {
	}

	@Test
	void testGetById() {
	}

	// Junit test for save actor operation
	@Test
	void testCreate() throws AlreadyExistsExeption {

		// given-precondition or setup
		Actor actor = new Actor(1L, "Jorge", 27, "España");

		// when -action or the behaivour taht we are going test
		Actor savedActor = actorCriteriaImpl.create(actor);

		// then -verify the output
		assertThat(savedActor).isNotNull();
		assertThat(savedActor.getIdActor()).isGreaterThan(0);

	}

	@Test
	void testDeleteById() {
	}

}
