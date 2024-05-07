package com.example.demo.servcice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.converters.BoToModel;
import com.example.demo.converters.ModelToBo;
import com.example.demo.repository.jpa.ActorRepository;
import com.example.demo.servcice.ActorService;
import com.example.demo.servcice.bo.ActorBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

@Service
public class ActorServiceImpl implements ActorService {

	@Autowired
	ActorRepository actorRepository;

	@Autowired
	BoToModel boToModel;

	@Autowired
	ModelToBo modelToBo;

	@Override
	public List<ActorBo> getAll() {

		return actorRepository.findAll().stream().map(actor -> modelToBo.actorToActorBo(actor)).toList();
	}

	@Override
	public ActorBo getById(long id) throws NotFoundException {

		return modelToBo.actorToActorBo(actorRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No se ha encontrado el actor con ese id:" + id)));
	}

	@Override
	public ActorBo create(ActorBo actorBo) throws AlreadyExistsExeption {
		if(actorRepository.existsById(actorBo.getIdActor())) {
			
			throw new AlreadyExistsExeption("El actor con el id:"+actorBo.getIdActor()+" ya existe");
		}
		return modelToBo.actorToActorBo(actorRepository.save(boToModel.boToActor(actorBo)));
	}

	@Override
	public void deleteById(long id) throws NotFoundException {
		if (actorRepository.existsById(id)) {
			actorRepository.deleteById(id);
			
		}else {
			throw new NotFoundException("no se ha encontrado el actor que quiere borrar con el id: "+id);
		}

	}

}