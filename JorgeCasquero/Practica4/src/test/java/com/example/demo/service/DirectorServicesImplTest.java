package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
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
import com.example.demo.model.Director;
import com.example.demo.repository.cb.DirectorRepositoryCriteria;
import com.example.demo.repository.jpa.DirectorRepository;
import com.example.demo.servcice.bo.DirectorBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;
import com.example.demo.servcice.impl.DirectorServiceImpl;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DirectorServicesImplTest {

	@InjectMocks
	private DirectorServiceImpl directorServiceImpl;

	@Mock
	DirectorRepository directorRepository;

	@Mock
	DirectorRepositoryCriteria directorRepositoryCriteria;

	@Mock
	private BoToModel boToModel;

	@Mock
	private ModelToBo modelToBo;

	private Director director1;
	private Director director2;
	private DirectorBo directorBo1;
	private DirectorBo directorBo2;

	@BeforeEach
	public void init() {
		initMocks();
	}

	private void initMocks() {
		directorBo1 = new DirectorBo();
		directorBo1.setIdDirector(1L);
		directorBo1.setNombre("Director 1");
		directorBo1.setEdad(30);

		director1 = new Director();
		director1.setIdDirector(1L);
		director1.setNombre("Director 1");
		director1.setEdad(30);

		directorBo2 = new DirectorBo();
		directorBo2.setIdDirector(2L);
		directorBo2.setNombre("Director 2");
		directorBo2.setEdad(25);
		directorBo2.setNacionalidad("inglés");

		director2 = new Director();
		director2.setIdDirector(2L);
		director2.setNombre("Director 2");
		director2.setEdad(25);
		director2.setNacionalidad("inglés");

	}

	@Test
	void testGetAll() {
		// Arrange
		List<Director> Directors = new ArrayList<>();
		Directors.add(director1);
		Directors.add(director2);
		when(directorRepository.findAll()).thenReturn(Directors);
		when(modelToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);
		when(modelToBo.directorToDirectorBo(director2)).thenReturn(directorBo2);

		// Act
		List<DirectorBo> result = directorServiceImpl.getAll();

		// Assert
		assertEquals(2, result.size());
		assertEquals(directorBo1, result.get(0));
		assertEquals(directorBo2, result.get(1));

	}

	@Test
	void testGetById() throws NotFoundException {
		when(directorRepository.findById(1L)).thenReturn(Optional.of(director1));
		when(modelToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

		DirectorBo result = directorServiceImpl.getById(1L);
		assertEquals(directorBo1, result);

	}

	@Test
	void testGetByIdExists() throws NotFoundException {
		when(directorRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> directorServiceImpl.getById(1L));
	}

	@Test
	void testCreate() throws AlreadyExistsExeption {

		when(boToModel.boToDirector(directorBo1)).thenReturn(director1);
		when(directorRepository.save(director1)).thenReturn(director1);
		when(modelToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

		// Act
		DirectorBo result = directorServiceImpl.create(directorBo1);

		// Assert
		assertEquals(directorBo1, result);

	}

	@Test
	void testCreateThrowAlreadyExists() throws AlreadyExistsExeption {

		when(directorRepository.existsById(director1.getIdDirector())).thenReturn(true);
		assertThrows(AlreadyExistsExeption.class, () -> directorServiceImpl.create(directorBo1));

	}

	@Test
	void testDeleteById() throws NotFoundException {
		Long id = 1L;
		when(directorRepository.existsById(id)).thenReturn(true);

		// Act
		directorServiceImpl.deleteById(id);

		// Assert
		verify(directorRepository, times(1)).deleteById(id);
	}

	@Test
	void testDeleteByIdnotFound() throws NotFoundException {
		Long id = 1L;
		when(directorRepository.existsById(id)).thenReturn(false);

		// assert
		assertThrows(NotFoundException.class, () -> directorServiceImpl.deleteById(id));
	}

	@Test
	void testGetAllCriteria() {
		List<Director> Directors = new ArrayList<>();
		Directors.add(director1);
		Directors.add(director2);
		when(directorRepositoryCriteria.getAll()).thenReturn(Directors);
		when(modelToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);
		when(modelToBo.directorToDirectorBo(director2)).thenReturn(directorBo2);

		// Act
		List<DirectorBo> result = directorServiceImpl.getAllCriteria();

		// Assert
		assertEquals(2, result.size());
		assertEquals(directorBo1, result.get(0));
		assertEquals(directorBo2, result.get(1));
	}

	@Test
	void testGetByIdCriteria() throws NotFoundException {

		when(directorRepositoryCriteria.getById(1L)).thenReturn(director1);
		when(modelToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

		DirectorBo result = directorServiceImpl.getByIdCriteria(1L);
		assertEquals(directorBo1, result);
	}

	@Test
	void testCreateCriteria() throws AlreadyExistsExeption, NotFoundException {
		when(boToModel.boToDirector(directorBo1)).thenReturn(director1);
		when(directorRepositoryCriteria.create(director1)).thenReturn(director1);
		when(modelToBo.directorToDirectorBo(director1)).thenReturn(directorBo1);

		// Act
		DirectorBo result = directorServiceImpl.createCriteria(directorBo1);

		// Assert
		assertEquals(directorBo1, result);
		verify(boToModel).boToDirector(directorBo1);
		verify(directorRepositoryCriteria).create(director1);
		verify(modelToBo).directorToDirectorBo(director1);

	}

	@Test
	void testCreateCriteriaAlreadyExistsExeption() throws AlreadyExistsExeption, NotFoundException {

		when(directorRepositoryCriteria.getById(director1.getIdDirector())).thenReturn(director1);
		assertThrows(AlreadyExistsExeption.class, () -> directorServiceImpl.createCriteria(directorBo1));

	}

	@Test
	void testDeleteByIdCriteria() throws NotFoundException {
		Long id = 1L;
		when(directorRepositoryCriteria.getById(id)).thenReturn(director1);

		// Act
		directorServiceImpl.deleteByIdCriteria(id);

		// Assert
		verify(directorRepositoryCriteria, times(1)).deleteById(id);
	}

	@Test
	void testDeleteByIdCriteriaThrowNotFoundException() throws NotFoundException {
		Long id = 1l;
		when(directorRepositoryCriteria.getById(id)).thenReturn(null);

		// assert
		assertThrows(NotFoundException.class, () -> directorServiceImpl.deleteByIdCriteria(id));
	}

}
