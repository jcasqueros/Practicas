package com.example.demo.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.converters.BoToDTo;
import com.example.demo.converters.DtoToBo;
import com.example.demo.presentation.dto.ActorDto;
import com.example.demo.servcice.ActorService;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/actor")
public class ActorController {

	@Autowired
	ActorService actorService;

	@Autowired
	BoToDTo boToDto;

	@Autowired
	DtoToBo dtoToBo;

	@GetMapping("/getAll")
	public ResponseEntity<List<ActorDto>> getAll(@RequestParam boolean metodo) {
		List<ActorDto> actores;
		if (metodo) {
			actores = actorService.getAll().stream().map(actor -> boToDto.boToActorDto(actor)).toList();
		} else {
			actores = actorService.getAllCriteria().stream().map(actor -> boToDto.boToActorDto(actor)).toList();
		}
		return ResponseEntity.ok(actores);
	}

	@GetMapping("getById/{id}")
	public ResponseEntity<ActorDto> getById(@PathVariable long id, @RequestParam boolean metodo)
			throws NotFoundException {
		log.debug("Solicitud de la consulta al actor con id: " + id + ".");
		ActorDto actorDto;
		if (metodo) {
			actorDto = boToDto.boToActorDto(actorService.getById(id));
		} else {
			actorDto = boToDto.boToActorDto(actorService.getByIdCriteria(id));
		}
		if (actorDto != null) {
			return ResponseEntity.ok(actorDto);
		} else {
			throw new NotFoundException("Actor no encontrado con el id: " + id);
		}
	}

	@PostMapping("/save")
	public ResponseEntity<ActorDto> save(@RequestBody ActorDto actor, @RequestParam boolean metodo)
			throws AlreadyExistsExeption, NotFoundException {
		ActorDto savedActor;
		if (metodo) {
			savedActor = boToDto.boToActorDto(actorService.create(dtoToBo.dtoToActorBo(actor)));

		} else {
			savedActor = boToDto.boToActorDto(actorService.createCriteria(dtoToBo.dtoToActorBo(actor)));
		}
		return ResponseEntity.ok(savedActor);

	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> deleteByid(@PathVariable long id, @RequestParam boolean metodo)
			throws NotFoundException {
		if (metodo) {
			actorService.deleteById(id);

		} else {
			actorService.deleteByIdCriteria(id);
		}

		return ResponseEntity.noContent().build();
	}
}
