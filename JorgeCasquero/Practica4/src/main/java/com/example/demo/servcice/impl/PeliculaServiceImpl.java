package com.example.demo.servcice.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.converters.BoToModel;
import com.example.demo.converters.ModelToBo;
import com.example.demo.exception.AlreadyExistsExeption;
import com.example.demo.exception.EmptyException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Actor;
import com.example.demo.model.Director;
import com.example.demo.model.Pelicula;
import com.example.demo.model.Productora;
import com.example.demo.repository.cb.PeliculaRepositoryCriteria;
import com.example.demo.repository.jpa.ActorRepository;
import com.example.demo.repository.jpa.DirectorRepository;
import com.example.demo.repository.jpa.PeliculaRepository;
import com.example.demo.repository.jpa.ProductoraRepository;
import com.example.demo.servcice.PeliculaService;
import com.example.demo.servcice.bo.PeliculaBo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PeliculaServiceImpl implements PeliculaService {

	private final PeliculaRepository peliculaRepository;
	private final DirectorRepository directorRepository;
	private final ActorRepository actorRepository;
	private final ProductoraRepository productoraRepository;

	private final PeliculaRepositoryCriteria peliculaRepositoryCriteria;

	private final BoToModel boToModel;

	private final ModelToBo modelToBo;

	@Override
	public Page<PeliculaBo> getAll(Pageable pageable) {
		try {
			Page<Pelicula> peliculaPage = peliculaRepository.findAll(pageable);
			if (peliculaPage.isEmpty()) {
				throw new EmptyException("No se encuentran productores");
			}
			List<PeliculaBo> peliculaBoList = peliculaPage.stream().map(modelToBo::peliculaToPeliculaBo).toList();
			return new PageImpl<>(peliculaBoList, peliculaPage.getPageable(), peliculaPage.getTotalPages());
		} catch (NestedRuntimeException e) {
			throw new ServiceException(e.getLocalizedMessage());
		}

	}

	@Override
	public PeliculaBo getById(long id) throws NotFoundException {
		return modelToBo.peliculaToPeliculaBo(peliculaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No se ha encontrado la película con ese id:" + id)));
	}

	@Override
	public PeliculaBo create(PeliculaBo peliculaBo) throws AlreadyExistsExeption {
		if (peliculaRepository.existsById(peliculaBo.getIdPelicula())) {

			throw new AlreadyExistsExeption("la película con el id:" + peliculaBo.getIdPelicula() + " ya existe");
		}
		return modelToBo.peliculaToPeliculaBo(peliculaRepository.save(boToModel.boToPelicula(peliculaBo)));
	}

	@Override
	public void deleteById(long id) throws NotFoundException {
		if (peliculaRepository.existsById(id)) {
			peliculaRepository.deleteById(id);

		} else {
			throw new NotFoundException("no se ha encontrado la película que quiere borrar con el id: " + id);
		}

	}

	@Override
	public Page<PeliculaBo> getAllCriteria(Pageable pageable) {
		try {
			Page<Pelicula> peliculaPage = peliculaRepositoryCriteria.getAll(pageable);
			if (peliculaPage.isEmpty()) {
				throw new EmptyException("No se encuentran productores");
			}
			List<PeliculaBo> peliculasBoList = peliculaPage.stream().map(modelToBo::peliculaToPeliculaBo).toList();
			return new PageImpl<>(peliculasBoList, peliculaPage.getPageable(), peliculaPage.getTotalPages());
		} catch (NestedRuntimeException e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
	}

	@Override
	public PeliculaBo getByIdCriteria(long id) throws NotFoundException {
		try {
			return modelToBo.peliculaToPeliculaBo(peliculaRepositoryCriteria.getById(id)
					.orElseThrow(() -> new EntityNotFoundException("productora no encontrada")));

		} catch (NestedRuntimeException e) {
			log.error("pelicula No encontrada");
			throw new ServiceException(e.getLocalizedMessage());
		}
	}

	@Override
	public PeliculaBo createCriteria(PeliculaBo peliculaBo) throws AlreadyExistsExeption, NotFoundException {
		if (peliculaRepositoryCriteria.getById(peliculaBo.getIdPelicula()) != null) {

			throw new AlreadyExistsExeption("El director con el id:" + peliculaBo.getIdPelicula() + " ya existe");
		}
		return modelToBo.peliculaToPeliculaBo(peliculaRepositoryCriteria.create(boToModel.boToPelicula(peliculaBo)));
	}

	@Override
	public void deleteByIdCriteria(long id) throws NotFoundException {
		if (peliculaRepositoryCriteria.getById(id) != null) {
			peliculaRepositoryCriteria.deleteById(id);
		} else {
			throw new NotFoundException("no se ha encontrado la película que quiere borrar con el id: " + id);
		}
	}

	@Override
	public Page<PeliculaBo> findAllCriteriaFilter(Pageable pageable, List<String> titles, List<Integer> ages,
			List<String> directors, List<String> producers, List<String> actors) throws ServiceException {
		try {
			// Búsqueda de los todos las peliculas, se recorre la lista, se mapea a objeto
			// bo y se convierte el resultado en lista
			List<Director> directorList = new ArrayList<>();
			List<Productora> producerList = new ArrayList<>();
			List<Actor> actorList = new ArrayList<>();

			if (directors != null && !directors.isEmpty()) {
				directors.forEach(d -> directorList.addAll(directorRepository.findByName(d)));
			}

			if (producers != null && !producers.isEmpty()) {
				producers.forEach(p -> producerList.addAll(productoraRepository.findByName(p)));
			}

			if (actors != null && !actors.isEmpty()) {
				actors.forEach(a -> actorList.addAll(actorRepository.findByName(a)));
			}

			Page<Pelicula> filmPage = peliculaRepositoryCriteria.findAllFilter(pageable, titles, ages, directorList,
					producerList, actorList);

			if (filmPage.isEmpty()) {
				throw new EmptyException("No se han encontrado peliculas");
			}

			List<PeliculaBo> filmsBOList = filmPage.stream().map(modelToBo::peliculaToPeliculaBo).toList();

			return new PageImpl<>(filmsBOList, filmPage.getPageable(), filmPage.getTotalPages());
		} catch (NestedRuntimeException e) {
			log.error(" No se han encontrado peliculas");
			throw new ServiceException(e.getLocalizedMessage());
		}
	}
}
