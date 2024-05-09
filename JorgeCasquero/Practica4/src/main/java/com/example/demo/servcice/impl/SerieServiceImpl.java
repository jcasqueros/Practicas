package com.example.demo.servcice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.converters.BoToModel;
import com.example.demo.converters.ModelToBo;
import com.example.demo.repository.cb.SerieRepositoryCriteria;
import com.example.demo.repository.jpa.SerieRepository;
import com.example.demo.servcice.SerieService;
import com.example.demo.servcice.bo.SerieBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

@Service
public class SerieServiceImpl implements SerieService {

	@Autowired
	SerieRepository serieRepository;

	@Autowired
	SerieRepositoryCriteria serieRepositoryCriteria;

	@Autowired
	ModelToBo modelToBo;

	@Autowired
	BoToModel boToModel;

	@Override
	public List<SerieBo> getAll() {
		return serieRepository.findAll().stream().map(serie -> modelToBo.serieToSerieBo(serie)).toList();
	}

	@Override
	public SerieBo getById(long id) throws NotFoundException {
		return modelToBo.serieToSerieBo(serieRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No se ha encontrado la serie con ese id:" + id)));
	}

	@Override
	public SerieBo create(SerieBo serieBo) throws AlreadyExistsExeption {
		if (serieRepository.existsById(serieBo.getIdSerie())) {
			throw new AlreadyExistsExeption("la serie con el id:" + serieBo.getIdSerie() + " ya existe");
		}

		return modelToBo.serieToSerieBo(serieRepository.save(boToModel.boToSerie(serieBo)));
	}

	@Override
	public void deleteById(long id) throws NotFoundException {
		if (serieRepository.existsById(id)) {
			serieRepository.deleteById(id);

		} else {
			throw new NotFoundException("no se ha encontrado la serie que quiere borrar con el id: " + id);
		}

	}

	@Override
	public List<SerieBo> getAllCriteria() {
		return serieRepositoryCriteria.getAll().stream().map(serie -> modelToBo.serieToSerieBo(serie)).toList();
	}

	@Override
	public SerieBo getByIdCriteria(long id) throws NotFoundException {
		return modelToBo.serieToSerieBo(serieRepositoryCriteria.getById(id));
	}

	@Override
	public SerieBo createCriteria(SerieBo serieBo) throws AlreadyExistsExeption, NotFoundException {
		if (serieRepositoryCriteria.getById(serieBo.getIdSerie()) != null) {
			throw new AlreadyExistsExeption("la serie con el id:" + serieBo.getIdSerie() + " ya existe");
		}
		return modelToBo.serieToSerieBo(serieRepositoryCriteria.create(boToModel.boToSerie(serieBo)));
	}

	@Override
	public void deleteByIdCriteria(long id) throws NotFoundException {
		if (serieRepositoryCriteria.getById(id) != null) {
			serieRepositoryCriteria.deleteById(id);

		} else {
			throw new NotFoundException("no se ha encontrado la serie que quiere borrar con el id: " + id);
		}
	}
}
