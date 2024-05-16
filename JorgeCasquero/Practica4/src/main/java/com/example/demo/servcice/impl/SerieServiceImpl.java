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
import com.example.demo.model.Productora;
import com.example.demo.model.Serie;
import com.example.demo.repository.cb.SerieRepositoryCriteria;
import com.example.demo.repository.jpa.ActorRepository;
import com.example.demo.repository.jpa.DirectorRepository;
import com.example.demo.repository.jpa.ProductoraRepository;
import com.example.demo.repository.jpa.SerieRepository;
import com.example.demo.servcice.SerieService;
import com.example.demo.servcice.bo.SerieBo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SerieServiceImpl implements SerieService {

	private final SerieRepository serieRepository;
	private final DirectorRepository directorRepository;
	private final ActorRepository actorRepository;
	private final ProductoraRepository productoraRepository;
	private final SerieRepositoryCriteria serieRepositoryCriteria;

	private final ModelToBo modelToBo;

	private final BoToModel boToModel;

	@Override
	public Page<SerieBo> getAll(Pageable pageable) {
		try {
			Page<Serie> seriePage = serieRepository.findAll(pageable);
			if (seriePage.isEmpty()) {
				throw new EmptyException("No se encuentran productores");
			}
			List<SerieBo> peliculaBoList = seriePage.stream().map(modelToBo::serieToSerieBo).toList();
			return new PageImpl<>(peliculaBoList, seriePage.getPageable(), seriePage.getTotalPages());
		} catch (NestedRuntimeException e) {
			throw new ServiceException(e.getLocalizedMessage());
		}

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
	public Page<SerieBo> getAllCriteria(Pageable pageable) {
		try {
			Page<Serie> seriePage = serieRepositoryCriteria.getAll(pageable);
			if (seriePage.isEmpty()) {
				throw new EmptyException("No se encuentran series");
			}
			List<SerieBo> serieBoList = seriePage.stream().map(modelToBo::serieToSerieBo).toList();
			return new PageImpl<>(serieBoList, seriePage.getPageable(), seriePage.getTotalPages());
		} catch (NestedRuntimeException e) {
			throw new ServiceException(e.getLocalizedMessage());
		}
	}

	@Override
	public SerieBo getByIdCriteria(long id) throws NotFoundException {
		try {
			return modelToBo.serieToSerieBo(serieRepositoryCriteria.getById(id)
					.orElseThrow(() -> new EntityNotFoundException("productora no encontrada")));

		} catch (NestedRuntimeException e) {
			log.error("productor No encontrado");
			throw new ServiceException(e.getLocalizedMessage());
		}
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
	 public Page<SerieBo> findAllCriteriaFilter(Pageable pageable, List<String> titles, List<Integer> ages,
	            List<String> directors, List<String> producers, List<String> actors) throws ServiceException {
	        try {
	            //Búsqueda de los todos las series, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
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

	            Page<Serie> seriePage = serieRepositoryCriteria.findAllFilter(pageable, titles, ages, directorList,
	                    producerList, actorList);

	            if (seriePage.isEmpty()) {
	                throw new EmptyException("No films");
	            }

	            List<SerieBo> serieBOList = seriePage.stream().map(modelToBo::serieToSerieBo).toList();

	            return new PageImpl<>(serieBOList, seriePage.getPageable(), seriePage.getTotalPages());
	        } catch (NestedRuntimeException e) {
	            log.error("no se han econtrado series");
	            throw new ServiceException(e.getLocalizedMessage());
	        }
	    }
}
