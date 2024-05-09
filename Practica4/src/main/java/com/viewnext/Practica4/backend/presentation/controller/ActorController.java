package com.viewnext.Practica4.backend.presentation.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.Practica4.backend.business.bo.ActorBo;
import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.business.services.ActorServices;
import com.viewnext.Practica4.backend.presentation.dto.ActorDto;
import com.viewnext.Practica4.util.converter.dto.BoToDto;
import com.viewnext.Practica4.util.converter.dto.DtoToBo;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@RestController
@RequestMapping("/actor")
public class ActorController {

	@Autowired
	ActorServices actorServices;
	
	@Autowired
	DtoToBo dtoToBo;

	@Autowired
	BoToDto boToDto;
	
	
	
	@GetMapping("/jpa/all")
	public ResponseEntity<List<ActorDto>> getActors() {
		try {
			List<ActorBo> actorBo = actorServices.getAll();
			System.out.println(actorBo);
			List<ActorDto> actorDto = actorBo.stream().map(boToDto::actorBoToActorDto)
					.collect(Collectors.toList());
			System.out.println(actorDto);
			log.info("Obteniendo lista de actores");
			return ResponseEntity.ok(actorDto);
		} catch (ServiceException se) {
			log.error("Error al obtener la lista de actores");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}
	}

	@GetMapping("/jpa/{id}")
	public ResponseEntity<ActorDto> getActorByCode(@PathVariable long id) {
		try {
			ActorBo actorBo = actorServices.read(id);
			ActorDto actorDto = boToDto.actorBoToActorDto(actorBo);
			return ResponseEntity.ok(actorDto);
		} catch (ServiceException se) {
			log.error("Error de servicio al intentar buscar un actor por codigo: "+id);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (NullPointerException npe) {
			log.error("Error de lectura - codigo "+id+" no encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/jpa/create")
	public ResponseEntity<ActorDto> createActor(@RequestBody ActorDto actorDto) {
		try {
			ActorBo actorBo = dtoToBo.actorDtoToBo(actorDto);
			actorServices.create(actorBo);
			ActorDto savedActorDto = boToDto.actorBoToActorDto(actorBo);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedActorDto);
		} catch (ServiceException se) {
			log.error("Error de servicio al intentar crear un nuevo actor");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
	}

	@PutMapping("/jpa/{id}")
	public ResponseEntity<ActorDto> updateActor(@PathVariable long id,
			@RequestBody ActorDto actorDto) {
		try {
			ActorBo actorBo = dtoToBo.actorDtoToBo(actorDto);
			actorBo = actorServices.read(id);
			actorBo.setNombre(actorDto.getNombre());
			actorBo.setEdad(actorDto.getEdad());
			actorBo.setNacionalidad(actorDto.getNacionalidad());
			actorBo.setId(actorDto.getId());
			actorServices.update(actorBo);
			ActorDto updateActorDto = boToDto.actorBoToActorDto(actorBo);
			return ResponseEntity.status(HttpStatus.CREATED).body(updateActorDto);
		} catch (ServiceException e) {
			log.error("Error de servicio al intentar buscar un actor por codigo: "+id);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (NullPointerException npe) {
			log.error("Error de lectura - codigo "+id+" no encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/jpa/{id}")
	public ResponseEntity<Void> deleteActor(@PathVariable long id) {
		try {
			actorServices.delete(id);
			return ResponseEntity.ok().build();
		} catch (ServiceException e) {
			log.error("Error de servicio al intentar buscar un actor por codigo: "+id);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (NullPointerException npe) {
			log.error("Error de lectura - codigo "+id+" no encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	//CRITERIA BUILDER
	
	@GetMapping("/cb/all")
	public ResponseEntity<List<ActorDto>> getActoresCb() {
	    try {
	        List<ActorBo> actors = actorServices.getActoresCb();
	        List<ActorDto> actorDtos = actors.stream().map(boToDto::actorBoToActorDto)
	                .collect(Collectors.toList());
	        log.info("Obteniendo lista de actores");
	        return ResponseEntity.ok(actorDtos);
	    } catch (ServiceException se) {
	        log.error("Error al obtener la lista de actores");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	@GetMapping("/cb/{id}")
	public ResponseEntity<ActorDto> readActorCb(@PathVariable long id) {
		try {
			ActorBo actorBo = actorServices.readCb(id);
			ActorDto actorDto = boToDto.actorBoToActorDto(actorBo);
			return ResponseEntity.ok(actorDto);
		} catch (ServiceException se) {
			log.error("Error de servicio al intentar buscar un actor por codigo: "+id);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (NullPointerException npe) {
			log.error("Error de lectura - codigo "+id+" no encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Transactional
	@PostMapping("/cb/create")
	public ResponseEntity<ActorDto> createActorCb(@RequestBody ActorDto actorDto) {
		try {
			ActorBo actorBo = dtoToBo.actorDtoToBo(actorDto);
			actorServices.createCb(actorBo);
			ActorDto savedActorDto = boToDto.actorBoToActorDto(actorBo);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedActorDto);
		} catch (ServiceException se) {
			log.error("Error de servicio al intentar crear un nuevo actor");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
	}

	@Transactional
	@PutMapping("/cb/{id}")
	public ResponseEntity<ActorDto> updateActorCb(@PathVariable long id,
			@RequestBody ActorDto actorDto) {
		try {
			ActorBo actorBo = dtoToBo.actorDtoToBo(actorDto);
			actorBo = actorServices.readCb(id);
			actorBo.setNombre(actorDto.getNombre());
			actorBo.setEdad(actorDto.getEdad());
			actorBo.setNacionalidad(actorDto.getNacionalidad());
			actorBo.setId(actorDto.getId());
			actorServices.updateCb(actorBo);
			ActorDto updateActorDto = boToDto.actorBoToActorDto(actorBo);
			return ResponseEntity.status(HttpStatus.CREATED).body(updateActorDto);
		} catch (ServiceException e) {
			log.error("Error de servicio al intentar buscar un actor por codigo: "+id);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (NullPointerException npe) {
			log.error("Error de lectura - codigo "+id+" no encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Transactional
	@DeleteMapping("/cb/{id}")
	public ResponseEntity<Void> deleteActorCb(@PathVariable long id) {
		try {
			actorServices.deleteCb(id);
			return ResponseEntity.ok().build();
		} catch (ServiceException e) {
			log.error("Error de servicio al intentar buscar un actor por codigo: "+id);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (NullPointerException npe) {
			log.error("Error de lectura - codigo "+id+" no encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
