package com.example.demo.servcice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.converters.BoToModel;
import com.example.demo.converters.ModelToBo;
import com.example.demo.repository.cb.ProductoraRepositoryCriteria;
import com.example.demo.repository.jpa.ProductoraRepository;
import com.example.demo.servcice.ProductoraService;
import com.example.demo.servcice.bo.ProductoraBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

@Service
public class ProductoraServiceImpl implements ProductoraService {

	@Autowired
	ProductoraRepository productoraRepository;

	@Autowired
	ProductoraRepositoryCriteria productoraRepositoryCriteria;

	@Autowired
	ModelToBo modelToBo;

	@Autowired
	BoToModel boToModel;

	@Override
	public List<ProductoraBo> getAll() {
		return productoraRepository.findAll().stream().map(productora -> modelToBo.productoraToProductoraBo(productora))
				.toList();
	}

	@Override
	public ProductoraBo getById(long id) throws NotFoundException {
		return modelToBo.productoraToProductoraBo(productoraRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No se ha encontrado la productora con ese id:" + id)));
	}

	@Override
	public ProductoraBo create(ProductoraBo productoraBo) throws AlreadyExistsExeption {
		if (productoraRepository.existsById(productoraBo.getIdProductora())) {
			throw new AlreadyExistsExeption("la productora con el id:" + productoraBo.getIdProductora() + " ya existe");
		}

		return modelToBo.productoraToProductoraBo(productoraRepository.save(boToModel.boToProductora(productoraBo)));
	}

	@Override
	public void deleteById(long id) throws NotFoundException {
		if (productoraRepository.existsById(id)) {
			productoraRepository.deleteById(id);

		} else {
			throw new NotFoundException("no se ha encontrado la productora que quiere borrar con el id: " + id);
		}

	}

	@Override
	public List<ProductoraBo> getAllCriteria() {
		return productoraRepositoryCriteria.getAll().stream().map(productora -> modelToBo.productoraToProductoraBo(productora))
				.toList();
	}

	@Override
	public ProductoraBo getByIdCriteria(long id) throws NotFoundException {
		return modelToBo.productoraToProductoraBo(productoraRepositoryCriteria.getById(id));
	}

	@Override
	public ProductoraBo createCriteria(ProductoraBo productoraBo) throws AlreadyExistsExeption, NotFoundException {
		if (productoraRepositoryCriteria.getById(productoraBo.getIdProductora()) != null) {
			throw new AlreadyExistsExeption("la productora con el id:" + productoraBo.getIdProductora() + " ya existe");
		}

		return modelToBo.productoraToProductoraBo(productoraRepositoryCriteria.create(boToModel.boToProductora(productoraBo)));
	}

	@Override
	public void deleteByIdCriteria(long id) throws NotFoundException {
		if (productoraRepositoryCriteria.getById(id) != null) {
			productoraRepositoryCriteria.deleteById(id);
		} else {
			throw new NotFoundException("no se ha encontrado la productora que quiere borrar con el id: " + id);
		}
	}
}
