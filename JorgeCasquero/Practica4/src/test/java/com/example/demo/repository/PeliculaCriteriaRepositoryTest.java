package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.model.Pelicula;
import com.example.demo.repository.cb.impl.PeliculaCriteriaImpl;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

@DataJpaTest
@ComponentScan(basePackages = "com.example.demo.repository.cb.impl")
class PeliculaCriteriaRepositoryTest {

	@Autowired
	private PeliculaCriteriaImpl peliculaCriteriaImpl;

	// Junit test for getAll pelicula
	@Test
	void testGetAll() throws AlreadyExistsExeption {

		// when -action or the behaivour that we are going test
		List<Pelicula> listapelicula = peliculaCriteriaImpl.getAll();

		// then-verigy the output
		assertThat(listapelicula).isNotNull();
		assertThat(listapelicula.size()).isEqualTo(5);
	}

	// Junit test for
	@Test
	void testGetById() throws NotFoundException, AlreadyExistsExeption {

		// given-precondition or setup
		Pelicula peliculaToSave = new Pelicula();
		peliculaToSave.setIdPelicula(1L);
		peliculaCriteriaImpl.create(peliculaToSave);
		// when -action or the behaivour taht we are going test
		Pelicula pelicula = peliculaCriteriaImpl.getById(1L);

		// then -verify the output
		assertThat(pelicula).isNotNull();
		assertThat(pelicula).isEqualTo(peliculaToSave);

	}

	// Junit test for save pelicula operation
	@Test
	void testCreate() throws AlreadyExistsExeption, NotFoundException {

		// given-precondition or setup
		Pelicula pelicula = new Pelicula();
		pelicula.setIdPelicula(1L);
		// when -action or the behaivour taht we are going test
		Pelicula savedpelicula = peliculaCriteriaImpl.create(pelicula);

		// then -verify the output
		assertThat(savedpelicula).isNotNull();

		assertThat(savedpelicula).isEqualTo(pelicula);

		// when - action or the behavior that we are going to test

		// then - verify the output
		// Verificar que el pelicula se ha guardado correctamente en la base de datos
		Pelicula retrievedpelicula = peliculaCriteriaImpl.getById(pelicula.getIdPelicula());
		assertNotNull(retrievedpelicula);
		assertEquals(pelicula, retrievedpelicula);

	}

	@Test
	void testCreatepeliculaAlreadyExists() throws AlreadyExistsExeption, NotFoundException {
		// given-precondition or setup
		Pelicula pelicula = new Pelicula();
		pelicula.setIdPelicula(1L);
		peliculaCriteriaImpl.create(pelicula); // crear el pelicula por primera vez

		// when - action or the behavior that we are going to test
		peliculaCriteriaImpl.create(pelicula); // intentar crear el pelicula de nuevo
	}

	// JUnit test for delete pelicula operation
	@Test
	void testDeleteById() throws AlreadyExistsExeption, NotFoundException {
		//
//					peliculaCriteriaImpl.deleteById(2L);
		//
//					Optional<pelicula> peliculaOptional = peliculaCriteriaImpl.getById(2L);
		//
//					assertThat(peliculaOptional).isEmpty();

	}
}
