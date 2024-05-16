package com.example.demo.servcice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.repository.cb.ActorRepositoryCriteria;
import com.example.demo.repository.jpa.ActorRepository;
import com.example.demo.servcice.ActorService;
import com.example.demo.servcice.bo.ActorBo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

	private final ActorRepository actorRepository;

	private final ActorRepositoryCriteria actorRepositoryCriteria;

	private final BoToModel boToModel;

	private final ModelToBo modelToBo;

	@Override
	public Page<ActorBo> getAll(Pageable pageable) {

		Page<Actor> actorPage = actorRepository.findAll(pageable);
		if (actorPage.isEmpty()) {
			throw new EmptyException("No hay registros para actores");
		}

		List<ActorBo> actorBoList = actorPage.stream().map(actor -> modelToBo.actorToActorBo(actor)).toList();
		return new PageImpl<>(actorBoList, actorPage.getPageable(), actorPage.getTotalPages());
	}

	@Override
	public ActorBo getById(long id) throws NotFoundException {

		return modelToBo.actorToActorBo(actorRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No se ha encontrado el actor con ese id:" + id)));
	}

	@Override
	public ActorBo create(ActorBo actorBo) throws AlreadyExistsExeption {
		if (actorRepository.existsById(actorBo.getIdActor())) {
			throw new AlreadyExistsExeption("El actor con el id:" + actorBo.getIdActor() + " ya existe");
		}
		Actor actor = boToModel.boToActor(actorBo);
		actor = actorRepository.save(actor);

		return modelToBo.actorToActorBo(actor);
	}

	@Override
	public void deleteById(long id) throws NotFoundException {
		if (actorRepository.existsById(id)) {
			actorRepository.deleteById(id);

		} else {
			throw new NotFoundException("no se ha encontrado el actor que quiere borrar con el id: " + id);
		}

	}

	@Override
	public Page<ActorBo> getAllCriteria(Pageable pageable) {
		Page<Actor> actorPage = actorRepositoryCriteria.getAll(pageable);

		if (actorPage.isEmpty()) {
			throw new EmptyException("No se ha encontrado ningun registro para Actores");
		}
		List<ActorBo> actorBoList = actorPage.stream().map(actor -> modelToBo.actorToActorBo(actor)).toList();

		return new PageImpl<>(actorBoList, actorPage.getPageable(), actorPage.getTotalPages());
	}

	@Override
	public ActorBo getByIdCriteria(long id) throws NotFoundException {
		return modelToBo.actorToActorBo(actorRepositoryCriteria.getById(id)
				.orElseThrow(() -> new NotFoundException("no se ha encontrado el actor con el id " + id)));
	}

	@Override
	public ActorBo createCriteria(ActorBo actorBo) throws AlreadyExistsExeption, NotFoundException {
		if (!actorRepositoryCriteria.getById(actorBo.getIdActor()).isEmpty()) {

			throw new AlreadyExistsExeption("El actor con el id:" + actorBo.getIdActor() + " ya existe");
		}
		return modelToBo.actorToActorBo(actorRepositoryCriteria.create(boToModel.boToActor(actorBo)));
	}

	@Override
	public void deleteByIdCriteria(long id) throws NotFoundException {
		if (actorRepositoryCriteria.getById(id) != null) {
			actorRepositoryCriteria.deleteById(id);
		} else {
			throw new NotFoundException("No se ha encontrado el actor que quiere borrar con el id: " + id);
		}
	}

	@Override
	public Page<ActorBo> getAllCriteriaFilter(Pageable pageable, List<String> nombres, List<Integer> edades,
			List<String> nacionalidades) {
		Page<Actor> actorPage = actorRepositoryCriteria.findAllFilter(pageable, nombres, edades, nacionalidades);

		if (actorPage.isEmpty()) {
			throw new EmptyException("no hay registros para actores y sus filtros");
		}

		List<ActorBo> actorBoList = actorPage.stream().map(modelToBo::actorToActorBo).toList();
		return new PageImpl<>(actorBoList, actorPage.getPageable(), actorPage.getTotalPages());

	}

}
