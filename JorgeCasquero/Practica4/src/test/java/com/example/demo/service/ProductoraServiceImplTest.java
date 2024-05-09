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
import com.example.demo.model.Productora;
import com.example.demo.repository.cb.ProductoraRepositoryCriteria;
import com.example.demo.repository.jpa.ProductoraRepository;
import com.example.demo.servcice.bo.ProductoraBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;
import com.example.demo.servcice.impl.ProductoraServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductoraServiceImplTest {

	@InjectMocks
	private ProductoraServiceImpl productoraServiceImpl;

	@Mock
	ProductoraRepository productoraRepository;

	@Mock
	ProductoraRepositoryCriteria productoraRepositoryCriteria;

	@Mock
	private BoToModel boToModel;

	@Mock
	private ModelToBo modelToBo;

	private Productora productora1;
	private Productora productora2;
	private ProductoraBo productoraBo1;
	private ProductoraBo productoraBo2;

	@BeforeEach
	public void init() {
		initMocks();
	}

	private void initMocks() {
		productoraBo1 = new ProductoraBo();
		productoraBo1.setIdProductora(1L);
		productoraBo1.setNombre("productora 1");
		productoraBo1.setAnioFundacion(30);

		productora1 = new Productora();
		productora1.setIdProductora(1L);
		productora1.setNombre("productora 1");
		productora1.setAnioFundacion(30);

		productoraBo2 = new ProductoraBo();
		productoraBo2.setIdProductora(2L);
		productoraBo2.setNombre("productora 2");
		productoraBo2.setAnioFundacion(25);

		productora2 = new Productora();
		productora2.setIdProductora(2L);
		productora2.setNombre("productora 2");
		productora2.setAnioFundacion(25);

	}

	@Test
	void testGetAll() {
		// Arrange
		List<Productora> productoras = new ArrayList<>();
		productoras.add(productora1);
		productoras.add(productora2);
		when(productoraRepository.findAll()).thenReturn(productoras);
		when(modelToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);
		when(modelToBo.productoraToProductoraBo(productora2)).thenReturn(productoraBo2);

		// Act
		List<ProductoraBo> result = productoraServiceImpl.getAll();

		// Assert
		assertEquals(2, result.size());
		assertEquals(productoraBo1, result.get(0));
		assertEquals(productoraBo2, result.get(1));

	}

	@Test
	void testGetById() throws NotFoundException {
		when(productoraRepository.findById(1L)).thenReturn(Optional.of(productora1));
		when(modelToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

		ProductoraBo result = productoraServiceImpl.getById(1L);
		assertEquals(productoraBo1, result);

	}

	@Test
	void testGetByIdExists() throws NotFoundException {
		when(productoraRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> productoraServiceImpl.getById(1L));
	}

	@Test
	void testCreate() throws AlreadyExistsExeption {

		when(boToModel.boToProductora(productoraBo1)).thenReturn(productora1);
		when(productoraRepository.save(productora1)).thenReturn(productora1);
		when(modelToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

		// Act
		ProductoraBo result = productoraServiceImpl.create(productoraBo1);

		// Assert
		assertEquals(productoraBo1, result);

	}

	@Test
	void testCreateThrowAlreadyExists() throws AlreadyExistsExeption {

		when(productoraRepository.existsById(productora1.getIdProductora())).thenReturn(true);
		assertThrows(AlreadyExistsExeption.class, () -> productoraServiceImpl.create(productoraBo1));

	}

	@Test
	void testDeleteById() throws NotFoundException {
		Long id = 1L;
		when(productoraRepository.existsById(id)).thenReturn(true);

		// Act
		productoraServiceImpl.deleteById(id);

		// Assert
		verify(productoraRepository, times(1)).deleteById(id);
	}

	@Test
	void testDeleteByIdnotFound() throws NotFoundException {
		Long id = 1L;
		when(productoraRepository.existsById(id)).thenReturn(false);

		// assert
		assertThrows(NotFoundException.class, () -> productoraServiceImpl.deleteById(id));
	}

	@Test
	void testGetAllCriteria() {
		List<Productora> productoras = new ArrayList<>();
		productoras.add(productora1);
		productoras.add(productora2);
		when(productoraRepositoryCriteria.getAll()).thenReturn(productoras);
		when(modelToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);
		when(modelToBo.productoraToProductoraBo(productora2)).thenReturn(productoraBo2);

		// Act
		List<ProductoraBo> result = productoraServiceImpl.getAllCriteria();

		// Assert
		assertEquals(2, result.size());
		assertEquals(productoraBo1, result.get(0));
		assertEquals(productoraBo2, result.get(1));
	}

	@Test
	void testGetByIdCriteria() throws NotFoundException {

		when(productoraRepositoryCriteria.getById(1L)).thenReturn(productora1);
		when(modelToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

		ProductoraBo result = productoraServiceImpl.getByIdCriteria(1L);
		assertEquals(productoraBo1, result);
	}

	@Test
	void testCreateCriteria() throws AlreadyExistsExeption, NotFoundException {
		when(boToModel.boToProductora(productoraBo1)).thenReturn(productora1);
		when(productoraRepositoryCriteria.create(productora1)).thenReturn(productora1);
		when(modelToBo.productoraToProductoraBo(productora1)).thenReturn(productoraBo1);

		// Act
		ProductoraBo result = productoraServiceImpl.createCriteria(productoraBo1);

		// Assert
		assertEquals(productoraBo1, result);
		verify(boToModel).boToProductora(productoraBo1);
		verify(productoraRepositoryCriteria).create(productora1);
		verify(modelToBo).productoraToProductoraBo(productora1);

	}

	@Test
	void testCreateCriteriaAlreadyExistsExeption() throws AlreadyExistsExeption, NotFoundException {

		when(productoraRepositoryCriteria.getById(productora1.getIdProductora())).thenReturn(productora1);
		assertThrows(AlreadyExistsExeption.class, () -> productoraServiceImpl.createCriteria(productoraBo1));

	}

	@Test
	void testDeleteByIdCriteria() throws NotFoundException {
		Long id = 1L;
		when(productoraRepositoryCriteria.getById(id)).thenReturn(productora1);

		// Act
		productoraServiceImpl.deleteByIdCriteria(id);

		// Assert
		verify(productoraRepositoryCriteria, times(1)).deleteById(id);
	}

	@Test
	void testDeleteByIdCriteriaThrowNotFoundException() throws NotFoundException {
		Long id = 1l;
		when(productoraRepositoryCriteria.getById(id)).thenReturn(null);

		// assert
		assertThrows(NotFoundException.class, () -> productoraServiceImpl.deleteByIdCriteria(id));
	}
}
