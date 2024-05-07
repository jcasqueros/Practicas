package com.example.demo.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.converters.BoToDTo;
import com.example.demo.converters.DtoToBo;
import com.example.demo.presentation.dto.ActorDto;
import com.example.demo.servcice.ActorService;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
	public List<ActorDto> getAll() {
		return actorService.getAll().stream().map(actor -> boToDto.boToActorDto(actor)).toList();
	}

	@GetMapping("getById/{id}")
	public ActorDto getById(@PathVariable long id) throws NotFoundException {
		log.debug("Solicitud de la consulta al actor con id: " + id + ".");
		return boToDto.boToActorDto(actorService.getById(id));
	}

	@PostMapping("/save")
	public ActorDto save(@RequestBody ActorDto actor) throws AlreadyExistsExeption {
		return boToDto.boToActorDto(actorService.create(dtoToBo.dtoToActorBo(actor)));
	}

	@DeleteMapping("delete/{id}")
	public void deleteByid(@PathVariable long id) throws NotFoundException {
		actorService.deleteById(id);

	}
}
