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
import com.example.demo.model.Director;
import com.example.demo.model.Pelicula;
import com.example.demo.model.Productora;
import com.example.demo.repository.cb.PeliculaRepositoryCriteria;
import com.example.demo.repository.jpa.PeliculaRepository;
import com.example.demo.servcice.bo.PeliculaBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;
import com.example.demo.servcice.impl.PeliculaServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class peliculaServicesImplTest {

	@InjectMocks
	private PeliculaServiceImpl peliculaServiceImpl;

	@Mock
	PeliculaRepository peliculaRepository;

	@Mock
	PeliculaRepositoryCriteria peliculaRepositoryCriteria;

	@Mock
	private BoToModel boToModel;

	@Mock
	private ModelToBo modelToBo;

	private Pelicula pelicula1;
	private Pelicula pelicula2;
	private PeliculaBo peliculaBo1;
	private PeliculaBo peliculaBo2;

	private List<Actor> actores;
	private Director director;
	private Productora productora;

	@BeforeEach
	public void init() {
		initMocks();
	}

	private void initMocks() {
		actores = new ArrayList<Actor>();
		director = new Director();
		productora = new Productora();

		peliculaBo1 = new PeliculaBo();
		peliculaBo1.setIdPelicula(1L);
		peliculaBo1.setTitulo("pelicula 1");
		peliculaBo1.setAnio(30);
		peliculaBo1.setProductora(productora);
		peliculaBo1.setDirector(director);
		peliculaBo1.setActor(actores);

		pelicula1 = new Pelicula();
		pelicula1.setIdPelicula(1L);
		pelicula1.setTitulo("pelicula 1");
		pelicula1.setAnio(30);
		pelicula1.setProductora(productora);
		pelicula1.setDirector(director);
		pelicula1.setActores(actores);

		peliculaBo2 = new PeliculaBo();
		peliculaBo2.setIdPelicula(1L);
		peliculaBo2.setTitulo("pelicula 1");
		peliculaBo2.setAnio(30);
		peliculaBo2.setProductora(productora);
		peliculaBo2.setDirector(director);
		peliculaBo2.setActor(actores);

		pelicula2 = new Pelicula();
		pelicula2.setIdPelicula(1L);
		pelicula2.setTitulo("pelicula 1");
		pelicula2.setAnio(30);
		pelicula2.setProductora(productora);
		pelicula2.setDirector(director);
		pelicula2.setActores(actores);

	}

	@Test
	void testGetAll() {
		// Arrange
		List<Pelicula> peliculas = new ArrayList<>();
		peliculas.add(pelicula1);
		peliculas.add(pelicula2);
		when(peliculaRepository.findAll()).thenReturn(peliculas);
		when(modelToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);
		when(modelToBo.peliculaToPeliculaBo(pelicula2)).thenReturn(peliculaBo2);

		// Act
		List<PeliculaBo> result = peliculaServiceImpl.getAll();

		// Assert
		assertEquals(2, result.size());
		assertEquals(peliculaBo1, result.get(0));
		assertEquals(peliculaBo2, result.get(1));

	}

	@Test
	void testGetById() throws NotFoundException {
		when(peliculaRepository.findById(1L)).thenReturn(Optional.of(pelicula1));
		when(modelToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

		PeliculaBo result = peliculaServiceImpl.getById(1L);
		assertEquals(peliculaBo1, result);

	}

	@Test
	void testGetByIdExists() throws NotFoundException {
		when(peliculaRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> peliculaServiceImpl.getById(1L));
	}

	@Test
	void testCreate() throws AlreadyExistsExeption {

		when(boToModel.boToPelicula(peliculaBo1)).thenReturn(pelicula1);
		when(peliculaRepository.save(pelicula1)).thenReturn(pelicula1);
		when(modelToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

		// Act
		PeliculaBo result = peliculaServiceImpl.create(peliculaBo1);

		// Assert
		assertEquals(peliculaBo1, result);

	}

	@Test
	void testCreateThrowAlreadyExists() throws AlreadyExistsExeption {

		when(peliculaRepository.existsById(pelicula1.getIdPelicula())).thenReturn(true);
		assertThrows(AlreadyExistsExeption.class, () -> peliculaServiceImpl.create(peliculaBo1));

	}

	@Test
	void testDeleteById() throws NotFoundException {
		Long id = 1L;
		when(peliculaRepository.existsById(id)).thenReturn(true);

		// Act
		peliculaServiceImpl.deleteById(id);

		// Assert
		verify(peliculaRepository, times(1)).deleteById(id);
	}

	@Test
	void testDeleteByIdnotFound() throws NotFoundException {
		Long id = 1L;
		when(peliculaRepository.existsById(id)).thenReturn(false);

		// assert
		assertThrows(NotFoundException.class, () -> peliculaServiceImpl.deleteById(id));
	}

	@Test
	void testGetAllCriteria() {
		List<Pelicula> peliculas = new ArrayList<>();
		peliculas.add(pelicula1);
		peliculas.add(pelicula2);
		when(peliculaRepositoryCriteria.getAll()).thenReturn(peliculas);
		when(modelToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);
		when(modelToBo.peliculaToPeliculaBo(pelicula2)).thenReturn(peliculaBo2);

		// Act
		List<PeliculaBo> result = peliculaServiceImpl.getAllCriteria();

		// Assert
		assertEquals(2, result.size());
		assertEquals(peliculaBo1, result.get(0));
		assertEquals(peliculaBo2, result.get(1));
	}

	@Test
	void testGetByIdCriteria() throws NotFoundException {

		when(peliculaRepositoryCriteria.getById(1L)).thenReturn(pelicula1);
		when(modelToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

		PeliculaBo result = peliculaServiceImpl.getByIdCriteria(1L);
		assertEquals(peliculaBo1, result);
	}

	@Test
	void testCreateCriteria() throws AlreadyExistsExeption, NotFoundException {
		when(boToModel.boToPelicula(peliculaBo1)).thenReturn(pelicula1);
		when(peliculaRepositoryCriteria.create(pelicula1)).thenReturn(pelicula1);
		when(modelToBo.peliculaToPeliculaBo(pelicula1)).thenReturn(peliculaBo1);

		// Act
		PeliculaBo result = peliculaServiceImpl.createCriteria(peliculaBo1);

		// Assert
		assertEquals(peliculaBo1, result);
		verify(boToModel).boToPelicula(peliculaBo1);
		verify(peliculaRepositoryCriteria).create(pelicula1);
		verify(modelToBo).peliculaToPeliculaBo(pelicula1);

	}

	@Test
	void testCreateCriteriaAlreadyExistsExeption() throws AlreadyExistsExeption, NotFoundException {

		when(peliculaRepositoryCriteria.getById(pelicula1.getIdPelicula())).thenReturn(pelicula1);
		assertThrows(AlreadyExistsExeption.class, () -> peliculaServiceImpl.createCriteria(peliculaBo1));

	}

	@Test
	void testDeleteByIdCriteria() throws NotFoundException {
		Long id = 1L;
		when(peliculaRepositoryCriteria.getById(id)).thenReturn(pelicula1);

		// Act
		peliculaServiceImpl.deleteByIdCriteria(id);

		// Assert
		verify(peliculaRepositoryCriteria, times(1)).deleteById(id);
	}

	@Test
	void testDeleteByIdCriteriaThrowNotFoundException() throws NotFoundException {
		Long id = 1l;
		when(peliculaRepositoryCriteria.getById(id)).thenReturn(null);

		// assert
		assertThrows(NotFoundException.class, () -> peliculaServiceImpl.deleteByIdCriteria(id));
	}
}