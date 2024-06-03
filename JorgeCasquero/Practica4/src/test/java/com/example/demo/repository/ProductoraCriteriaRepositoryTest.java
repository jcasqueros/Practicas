package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.model.Productora;
import com.example.demo.repository.cb.impl.ProductoraCriteriaImpl;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

@DataJpaTest
@ComponentScan(basePackages = "com.example.demo.repository.cb.impl")
class ProductoraCriterialRepositoryTest {

	@Autowired
	private ProductoraCriteriaImpl productoraCriteriaImpl;

	// Junit test for getAll productora
	@Test
	void testGetAll() throws AlreadyExistsExeption {

		// when -action or the behaivour that we are going test
		List<Productora> listaproductora = productoraCriteriaImpl.getAll();

		// then-verigy the output
		assertThat(listaproductora).isNotNull();
		assertThat(listaproductora.size()).isEqualTo(5);
	}

	// Junit test for
	@Test
	void testGetById() throws NotFoundException, AlreadyExistsExeption {

		// given-precondition or setup
		Productora productoraToSave = new Productora(1L, "Productora1", 1932);
		productoraCriteriaImpl.create(productoraToSave);
		// when -action or the behaivour taht we are going test
		Productora productora = productoraCriteriaImpl.getById(1L);

		// then -verify the output
		assertThat(productora).isNotNull();
		assertThat(productora).isEqualTo(productoraToSave);

	}

	// Junit test for save productora operation
	@Test
	void testCreate() throws AlreadyExistsExeption, NotFoundException {

		// given-precondition or setup
		Productora productora = new Productora(1L, "Productora1", 1932);

		// when -action or the behaivour taht we are going test
		Productora savedproductora = productoraCriteriaImpl.create(productora);

		// then -verify the output
		assertThat(savedproductora).isNotNull();

		assertThat(savedproductora).isEqualTo(productora);

		// when - action or the behavior that we are going to test

		// then - verify the output
		// Verificar que el productora se ha guardado correctamente en la base de datos
		Productora retrievedproductora = productoraCriteriaImpl.getById(productora.getIdProductora());
		assertNotNull(retrievedproductora);
		assertEquals(productora, retrievedproductora);

	}

	@Test
	void testCreateproductoraAlreadyExists() throws AlreadyExistsExeption, NotFoundException {
		// given-precondition or setup
		Productora productora = new Productora(1L, "Productora1", 1932);
		productoraCriteriaImpl.create(productora); // crear el productora por primera vez

		// when - action or the behavior that we are going to test
		productoraCriteriaImpl.create(productora); // intentar crear el productora de nuevo
	}

	// JUnit test for delete productora operation
	@Test
	void testDeleteById() throws AlreadyExistsExeption, NotFoundException {
		//
//			productoraCriteriaImpl.deleteById(2L);
		//
//			Optional<productora> productoraOptional = productoraCriteriaImpl.getById(2L);
		//
//			assertThat(productoraOptional).isEmpty();

	}
}
