package com.example.demo.servcice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.converters.BoToModel;
import com.example.demo.converters.ModelToBo;
import com.example.demo.repository.cb.DirectorRepositoryCriteria;
import com.example.demo.repository.jpa.DirectorRepository;
import com.example.demo.servcice.DirectorService;
import com.example.demo.servcice.bo.DirectorBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

@Service
public class DirectorServiceImpl implements DirectorService {

	@Autowired
	DirectorRepository directorRepository;
	@Autowired
	DirectorRepositoryCriteria directorRepositoryCriteria;

	@Autowired
	BoToModel boToModel;

	@Autowired
	ModelToBo modelToBo;

	@Override
	public List<DirectorBo> getAll() {

		return directorRepository.findAll().stream().map(director -> modelToBo.directorToDirectorBo(director)).toList();
	}

	@Override
	public DirectorBo getById(long id) throws NotFoundException {

		return modelToBo.directorToDirectorBo(directorRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No se ha encontrado el director con ese id:" + id)));
	}

	@Override
	public DirectorBo create(DirectorBo directorBo) throws AlreadyExistsExeption {
		if (directorRepository.existsById(directorBo.getIdDirector())) {

			throw new AlreadyExistsExeption("El director con el id:" + directorBo.getIdDirector() + " ya existe");
		}
		return modelToBo.directorToDirectorBo(directorRepository.save(boToModel.boToDirector(directorBo)));
	}

	@Override
	public void deleteById(long id) throws NotFoundException {
		if (directorRepository.existsById(id)) {
			directorRepository.deleteById(id);

		} else {
			throw new NotFoundException("No se ha encontrado el director que quiere borrar con el id: " + id);
		}

	}

	@Override
	public List<DirectorBo> getAllCriteria() {
		return directorRepositoryCriteria.getAll().stream().map(director -> modelToBo.directorToDirectorBo(director))
				.toList();
	}

	@Override
	public DirectorBo getByIdCriteria(long id) throws NotFoundException {
		return modelToBo.directorToDirectorBo(directorRepositoryCriteria.getById(id));
	}

	@Override
	public DirectorBo createCriteria(DirectorBo directorBo) throws AlreadyExistsExeption, NotFoundException {
		if (directorRepositoryCriteria.getById(directorBo.getIdDirector()) != null) {

			throw new AlreadyExistsExeption("El director con el id:" + directorBo.getIdDirector() + " ya existe");
		}
		return modelToBo.directorToDirectorBo(directorRepositoryCriteria.create(boToModel.boToDirector(directorBo)));
	}

	@Override
	public void deleteByIdCriteria(long id) throws NotFoundException {
		if (directorRepositoryCriteria.getById(id) != null) {
			directorRepositoryCriteria.deleteById(id);

		} else {
			throw new NotFoundException("No se ha encontrado el director que quiere borrar con el id: " + id);
		}
	}
}
