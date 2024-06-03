package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.model.Serie;
import com.example.demo.repository.cb.impl.SerieCriteriaImpl;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

@DataJpaTest
@ComponentScan(basePackages = "com.example.demo.repository.cb.impl")
class SerieCriteriaRepositoryTest {

	@Autowired
	private SerieCriteriaImpl serieCriteriaImpl;

	// Junit test for getAll serie
	@Test
	void testGetAll() throws AlreadyExistsExeption {

		// when -action or the behaivour that we are going test
		List<Serie> listaserie = serieCriteriaImpl.getAll();

		// then-verigy the output
		assertThat(listaserie).isNotNull();
		assertThat(listaserie.size()).isEqualTo(5);
	}

	// Junit test for
	@Test
	void testGetById() throws NotFoundException, AlreadyExistsExeption {

		// given-precondition or setup
		Serie serieToSave = new Serie();
		serieToSave.setIdSerie(1L);
		serieCriteriaImpl.create(serieToSave);
		// when -action or the behaivour taht we are going test
		Serie serie = serieCriteriaImpl.getById(1L);

		// then -verify the output
		assertThat(serie).isNotNull();
		assertThat(serie).isEqualTo(serieToSave);

	}

	// Junit test for save serie operation
	@Test
	void testCreate() throws AlreadyExistsExeption, NotFoundException {

		// given-precondition or setup
		Serie serie = new Serie();
		serie.setIdSerie(1L);
		// when -action or the behaivour taht we are going test
		Serie savedserie = serieCriteriaImpl.create(serie);

		// then -verify the output
		assertThat(savedserie).isNotNull();

		assertThat(savedserie).isEqualTo(serie);

		// when - action or the behavior that we are going to test

		// then - verify the output
		// Verificar que el serie se ha guardado correctamente en la base de datos
		Serie retrievedserie = serieCriteriaImpl.getById(serie.getIdSerie());
		assertNotNull(retrievedserie);
		assertEquals(serie, retrievedserie);

	}

	@Test
	void testCreateserieAlreadyExists() throws AlreadyExistsExeption, NotFoundException {
		// given-precondition or setup
		Serie serie = new Serie();
		serie.setIdSerie(1L);
		serieCriteriaImpl.create(serie); // crear el serie por primera vez

		// when - action or the behavior that we are going to test
		serieCriteriaImpl.create(serie); // intentar crear el serie de nuevo
	}

	// JUnit test for delete serie operation
	@Test
	void testDeleteById() throws AlreadyExistsExeption, NotFoundException {
		//
//				serieCriteriaImpl.deleteById(2L);
		//
//				Optional<serie> serieOptional = serieCriteriaImpl.getById(2L);
		//
//				assertThat(serieOptional).isEmpty();

	}
}
