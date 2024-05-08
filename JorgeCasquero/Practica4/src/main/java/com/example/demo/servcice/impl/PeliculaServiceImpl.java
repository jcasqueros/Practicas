package com.example.demo.servcice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.converters.BoToModel;
import com.example.demo.converters.ModelToBo;
import com.example.demo.repository.cb.PeliculaRepositoryCriteria;
import com.example.demo.repository.jpa.PeliculaRepository;
import com.example.demo.servcice.PeliculaService;
import com.example.demo.servcice.bo.PeliculaBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

@Service
public class PeliculaServiceImpl implements PeliculaService {

	@Autowired
	PeliculaRepository peliculaRepository;
	@Autowired
	PeliculaRepositoryCriteria peliculaRepositoryCriteria;

	@Autowired
	BoToModel boToModel;

	@Autowired
	ModelToBo modelToBo;

	@Override
	public List<PeliculaBo> getAll() {
		return peliculaRepository.findAll().stream().map(pelicula -> modelToBo.peliculaToPeliculaBo(pelicula)).toList();
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
	public List<PeliculaBo> getAllCriteria() {
		return peliculaRepositoryCriteria.getAll().stream().map(pelicula -> modelToBo.peliculaToPeliculaBo(pelicula))
				.toList();
	}

	@Override
	public PeliculaBo getByIdCriteria(long id) throws NotFoundException {
		return modelToBo.peliculaToPeliculaBo(peliculaRepositoryCriteria.getById(id));
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

}
