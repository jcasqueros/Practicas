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
import com.example.demo.model.Productora;
import com.example.demo.model.Serie;
import com.example.demo.repository.cb.SerieRepositoryCriteria;
import com.example.demo.repository.jpa.SerieRepository;
import com.example.demo.servcice.bo.SerieBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;
import com.example.demo.servcice.impl.SerieServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SerieServiceImplTest {

	@InjectMocks
	private SerieServiceImpl serieServiceImpl;

	@Mock
	SerieRepository serieRepository;

	@Mock
	SerieRepositoryCriteria serieRepositoryCriteria;

	@Mock
	private BoToModel boToModel;

	@Mock
	private ModelToBo modelToBo;

	private Serie serie1;
	private Serie serie2;
	private SerieBo serieBo1;
	private SerieBo serieBo2;

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

		serieBo1 = new SerieBo();
		serieBo1.setIdSerie(1L);
		serieBo1.setTitulo("serie 1");
		serieBo1.setAnio(30);
		serieBo1.setProductora(productora);
		serieBo1.setDirector(director);
		serieBo1.setActores(actores);

		serie1 = new Serie();
		serie1.setIdSerie(1L);
		serie1.setTitulo("serie 1");
		serie1.setAnio(30);
		serie1.setProductora(productora);
		serie1.setDirector(director);
		serie1.setActores(actores);

		serieBo2 = new SerieBo();
		serieBo2.setIdSerie(2L);
		serieBo2.setTitulo("serie 2");
		serieBo2.setAnio(30);
		serieBo2.setProductora(productora);
		serieBo2.setDirector(director);
		serieBo2.setActores(actores);

		serie2 = new Serie();
		serie2.setIdSerie(2L);
		serie2.setTitulo("serie 2");
		serie2.setAnio(30);
		serie2.setProductora(productora);
		serie2.setDirector(director);
		serie2.setActores(actores);

	}

	@Test
	void testGetAll() {
		// Arrange
		List<Serie> series = new ArrayList<>();
		series.add(serie1);
		series.add(serie2);
		when(serieRepository.findAll()).thenReturn(series);
		when(modelToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);
		when(modelToBo.serieToSerieBo(serie2)).thenReturn(serieBo2);

		// Act
		List<SerieBo> result = serieServiceImpl.getAll();

		// Assert
		assertEquals(2, result.size());
		assertEquals(serieBo1, result.get(0));
		assertEquals(serieBo2, result.get(1));

	}

	@Test
	void testGetById() throws NotFoundException {
		when(serieRepository.findById(1L)).thenReturn(Optional.of(serie1));
		when(modelToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

		SerieBo result = serieServiceImpl.getById(1L);
		assertEquals(serieBo1, result);

	}

	@Test
	void testGetByIdExists() throws NotFoundException {
		when(serieRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> serieServiceImpl.getById(1L));
	}

	@Test
	void testCreate() throws AlreadyExistsExeption {

		when(boToModel.boToSerie(serieBo1)).thenReturn(serie1);
		when(serieRepository.save(serie1)).thenReturn(serie1);
		when(modelToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

		// Act
		SerieBo result = serieServiceImpl.create(serieBo1);

		// Assert
		assertEquals(serieBo1, result);

	}

	@Test
	void testCreateThrowAlreadyExists() throws AlreadyExistsExeption {

		when(serieRepository.existsById(serie1.getIdSerie())).thenReturn(true);
		assertThrows(AlreadyExistsExeption.class, () -> serieServiceImpl.create(serieBo1));

	}

	@Test
	void testDeleteById() throws NotFoundException {
		Long id = 1L;
		when(serieRepository.existsById(id)).thenReturn(true);

		// Act
		serieServiceImpl.deleteById(id);

		// Assert
		verify(serieRepository, times(1)).deleteById(id);
	}

	@Test
	void testDeleteByIdnotFound() throws NotFoundException {
		Long id = 1L;
		when(serieRepository.existsById(id)).thenReturn(false);

		// assert
		assertThrows(NotFoundException.class, () -> serieServiceImpl.deleteById(id));
	}

	@Test
	void testGetAllCriteria() {
		List<Serie> series = new ArrayList<>();
		series.add(serie1);
		series.add(serie2);
		when(serieRepositoryCriteria.getAll()).thenReturn(series);
		when(modelToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);
		when(modelToBo.serieToSerieBo(serie2)).thenReturn(serieBo2);

		// Act
		List<SerieBo> result = serieServiceImpl.getAllCriteria();

		// Assert
		assertEquals(2, result.size());
		assertEquals(serieBo1, result.get(0));
		assertEquals(serieBo2, result.get(1));
	}

	@Test
	void testGetByIdCriteria() throws NotFoundException {

		when(serieRepositoryCriteria.getById(1L)).thenReturn(serie1);
		when(modelToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

		SerieBo result = serieServiceImpl.getByIdCriteria(1L);
		assertEquals(serieBo1, result);
	}

	@Test
	void testCreateCriteria() throws AlreadyExistsExeption, NotFoundException {
		when(boToModel.boToSerie(serieBo1)).thenReturn(serie1);
		when(serieRepositoryCriteria.create(serie1)).thenReturn(serie1);
		when(modelToBo.serieToSerieBo(serie1)).thenReturn(serieBo1);

		// Act
		SerieBo result = serieServiceImpl.createCriteria(serieBo1);

//		// Assert
		assertEquals(serieBo1, result);

	}

	@Test
	void testCreateCriteriaAlreadyExistsExeption() throws AlreadyExistsExeption, NotFoundException {

		when(serieRepositoryCriteria.getById(serie1.getIdSerie())).thenReturn(serie1);
		assertThrows(AlreadyExistsExeption.class, () -> serieServiceImpl.createCriteria(serieBo1));

	}

	@Test
	void testDeleteByIdCriteria() throws NotFoundException {
		Long id = 1L;
		when(serieRepositoryCriteria.getById(id)).thenReturn(serie1);

		// Act
		serieServiceImpl.deleteByIdCriteria(id);

		// Assert
		verify(serieRepositoryCriteria, times(1)).deleteById(id);
	}

	@Test
	void testDeleteByIdCriteriaThrowNotFoundException() throws NotFoundException {
		Long id = 1l;
		when(serieRepositoryCriteria.getById(id)).thenReturn(null);

		// assert
		assertThrows(NotFoundException.class, () -> serieServiceImpl.deleteByIdCriteria(id));
	}
}
