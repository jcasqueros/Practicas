package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.model.Director;
import com.example.demo.repository.cb.impl.DirectorCriteriaImpl;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

@DataJpaTest
@ComponentScan(basePackages = "com.example.demo.repository.cb.impl")
class DirectorCriteriaRepositoryTest {

	@Autowired
	private DirectorCriteriaImpl directorCriteriaImpl;

	// Junit test for getAll director
	@Test
	void testGetAll() throws AlreadyExistsExeption {

		// when -action or the behaivour that we are going test
		List<Director> listadirector = directorCriteriaImpl.getAll();

		// then-verigy the output
		assertThat(listadirector).isNotNull();
		assertThat(listadirector.size()).isEqualTo(5);
	}

	// Junit test for
	@Test
	void testGetById() throws NotFoundException {

		// given-precondition or setup

		// when -action or the behaivour taht we are going test
		Director director = directorCriteriaImpl.getById(1L);

		// then -verify the output
		assertThat(director).isNotNull();
		assertThat(director.getNombre()).isEqualTo("Prudence");

	}

	// Junit test for save director operation
	@Test
	void testCreate() throws AlreadyExistsExeption, NotFoundException {

		// given-precondition or setup
		Director director = new Director(1L, "Jorge", 27, "España");

		// when -action or the behaivour taht we are going test
		Director saveddirector = directorCriteriaImpl.create(director);

		// then -verify the output
		assertThat(saveddirector).isNotNull();

		assertThat(saveddirector).isEqualTo(director);
		assertThat(saveddirector.getIdDirector()).isGreaterThan(0);

		// when - action or the behavior that we are going to test

		// then - verify the output
		// Verificar que el director se ha guardado correctamente en la base de datos
		Director retrieveddirector = directorCriteriaImpl.getById(director.getIdDirector());
		assertNotNull(retrieveddirector);
		assertEquals(director, retrieveddirector);

	}

	@Test
	void testCreatedirectorAlreadyExists() throws AlreadyExistsExeption, NotFoundException {
		// given-precondition or setup
		Director director = new Director(1L, "Jorge", 27, "España");
		directorCriteriaImpl.create(director); // crear el director por primera vez

		// when - action or the behavior that we are going to test
		directorCriteriaImpl.create(director); // intentar crear el director de nuevo
	}

	// JUnit test for delete director operation
	@Test
	void testDeleteById() throws AlreadyExistsExeption, NotFoundException {

//			directorCriteriaImpl.deleteById(2L);
		//
//			Optional<director> directorOptional = Optional.of(directorCriteriaImpl.getById(2L));
//			
//			assertThat(directorOptional).isEmpty();

	}

}
