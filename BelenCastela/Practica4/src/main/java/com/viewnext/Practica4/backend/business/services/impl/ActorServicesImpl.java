package com.viewnext.Practica4.backend.business.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viewnext.Practica4.backend.business.bo.ActorBo;
import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.business.services.ActorServices;
import com.viewnext.Practica4.backend.repository.ActorRepository;
import com.viewnext.Practica4.backend.repository.custom.ActorCustomRepository;
import com.viewnext.Practica4.util.converter.bo.BoToEntity;
import com.viewnext.Practica4.util.converter.bo.EntityToBo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ActorServicesImpl implements ActorServices{

	@Autowired
	ActorRepository actorRepository;

	@Autowired
	ActorCustomRepository actorCustomRepository;

	@Autowired
	EntityToBo entityToBo;

	@Autowired
	BoToEntity boToEntity;

	@Override
	public ActorBo create(ActorBo actorBo) {
		try {
			return entityToBo.actorToActorBo(actorRepository.save(boToEntity.actorBoToActor(actorBo)));
		} catch (Exception e) {
			throw new ServiceException("Error al crear actor", e);
		}
	}

	@Override
	public ActorBo read(long id) {
		return entityToBo.actorToActorBo(actorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Id no encontrado")));
	}

	@Override
	public List<ActorBo> getAll() {
		try {
			List<Actor> actorList = actorRepository.findAll();
			List<ActorBo> actorBoList = new ArrayList<>();
			actorList.forEach((actor) -> actorBoList.add(entityToBo.actorToActorBo(actor)));
			return actorBoList;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener lista de actores", e);
		}
	}

	@Override
	public void delete(long id) {
		if (!actorRepository.existsById(id)) {
	        throw new EntityNotFoundException("Actor no encontrado");
	    }
	    actorRepository.deleteById(id);
	}

	@Override
	public ActorBo update(ActorBo actorBo) {
		try {
			return entityToBo.actorToActorBo(actorRepository.save(boToEntity.actorBoToActor(actorBo)));
		} catch (Exception e) {
			throw new ServiceException("Error al actualizar actor", e);
		}
	}

	//CRITERIA BUILDER

	public List<ActorBo> getActoresCb() {
		try {
			List<Actor> actors = actorCustomRepository.getActoresCb();
			return actors.stream().map(entityToBo::actorToActorBo)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException("Error al obtener lista de actores con Criteria Builder", e);
		}
	}

	@Override
	public ActorBo createCb(ActorBo actorBo) {
		try {
			Actor actor = boToEntity.actorBoToActor(actorBo);
			return entityToBo.actorToActorBo(actorCustomRepository.createCb(actor));
		} catch (Exception e) {
			throw new ServiceException("Error al crear actor con Criteria Builder", e);
		}
	}

	@Override
	public ActorBo readCb(long id) {
		try {
			Actor actor = actorCustomRepository.readCb(id);
			if (actor == null) {
				throw new EntityNotFoundException("Id no encontrado");
			}
			return entityToBo.actorToActorBo(actor);
		} catch (Exception e) {
			throw new ServiceException("Error al leer actor con Criteria Builder", e);
		}
	}

	@Override
	public ActorBo updateCb(ActorBo actorBo) {
		try {
			Actor actor = boToEntity.actorBoToActor(actorBo);
			return entityToBo.actorToActorBo(actorCustomRepository.updateCb(actor));
		} catch (Exception e) {
			throw new ServiceException("Error al actualizar actor con Criteria Builder", e);
		}
	}

	@Override
	public void deleteCb(long id) {
		try {
			actorCustomRepository.deleteCb(id);
		} catch (Exception e) {
			throw new ServiceException("Error al eliminar actor con Criteria Builder", e);
		}
	}
}