package com.example.demo.servcice.impl;

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
import com.example.demo.model.Productora;
import com.example.demo.repository.cb.ProductoraRepositoryCriteria;
import com.example.demo.repository.jpa.ProductoraRepository;
import com.example.demo.servcice.ProductoraService;
import com.example.demo.servcice.bo.ProductoraBo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoraServiceImpl implements ProductoraService {

	private final ProductoraRepository productoraRepository;

	private final ProductoraRepositoryCriteria productoraRepositoryCriteria;

	private final ModelToBo modelToBo;

	private final BoToModel boToModel;

	@Override
	public Page<ProductoraBo> getAll(Pageable pageable) {
		try {
			Page<Productora> productoraPage = productoraRepository.findAll(pageable);
			if (productoraPage.isEmpty()) {
				throw new EmptyException("No se encuentran productores");
			}
			List<ProductoraBo> productoresBoList = productoraPage.stream().map(modelToBo::productoraToProductoraBo)
					.toList();
			return new PageImpl<>(productoresBoList, productoraPage.getPageable(), productoraPage.getTotalPages());
		} catch (NestedRuntimeException e) {
			throw new ServiceException(e.getLocalizedMessage());
		}

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
	public Page<ProductoraBo> getAllCriteria(Pageable pageable) {
		try {
			Page<Productora> productoraPage = productoraRepositoryCriteria.getAll(pageable);
			if (productoraPage.isEmpty()) {
				throw new EmptyException("No se encuentran productores");
			}
			List<ProductoraBo> productoresBoList = productoraPage.stream().map(modelToBo::productoraToProductoraBo)
					.toList();
			return new PageImpl<>(productoresBoList, productoraPage.getPageable(), productoraPage.getTotalPages());
		} catch (NestedRuntimeException e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
	}

	@Override
	public ProductoraBo getByIdCriteria(long id) throws NotFoundException {
		try {
			return modelToBo.productoraToProductoraBo(productoraRepositoryCriteria.getById(id)
					.orElseThrow(() -> new EntityNotFoundException("productora no encontrada")));

		} catch (NestedRuntimeException e) {
			log.error("productor No encontrado");
			throw new ServiceException(e.getLocalizedMessage());
		}
	}

	@Override
	public ProductoraBo createCriteria(ProductoraBo productoraBo) throws AlreadyExistsExeption, NotFoundException {
		if (productoraRepositoryCriteria.getById(productoraBo.getIdProductora()) != null) {
			throw new AlreadyExistsExeption("la productora con el id:" + productoraBo.getIdProductora() + " ya existe");
		}

		return modelToBo
				.productoraToProductoraBo(productoraRepositoryCriteria.create(boToModel.boToProductora(productoraBo)));
	}

	@Override
	public void deleteByIdCriteria(long id) throws NotFoundException {
		if (productoraRepositoryCriteria.getById(id) != null) {
			productoraRepositoryCriteria.deleteById(id);
		} else {
			throw new NotFoundException("no se ha encontrado la productora que quiere borrar con el id: " + id);
		}
	}
	
	@Override
	public Page<ProductoraBo> findAllCriteriaFilter(Pageable pageable, List<String> names, List<Integer> ages)
            throws ServiceException {
        try {
            //Búsqueda de los todos los productores, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
            Page<Productora> producerPage =productoraRepositoryCriteria.findAndFilter(pageable, names, ages);

            if (producerPage.isEmpty()) {
                throw new EmptyException("productoras no encontradas");
            }

            List<ProductoraBo> producerBOList = producerPage.stream().map(modelToBo::productoraToProductoraBo).toList();

            return new PageImpl<>(producerBOList, producerPage.getPageable(), producerPage.getTotalPages());
        } catch (NestedRuntimeException e) {
            log.error("Productoras no encontradas");
            throw new ServiceException(e.getLocalizedMessage());
        }
    }
}
