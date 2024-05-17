package com.example.demo.presentation.controllers;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
import com.example.demo.exception.AlreadyExistsExeption;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.PresentationException;
import com.example.demo.presentation.dto.ActorDto;
import com.example.demo.servcice.ActorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/actor")

public class ActorController {

	private final ActorService actorService;

	private final BoToDTo boToDto;

	private final DtoToBo dtoToBo;

	@GetMapping("/getAll")
	public ResponseEntity<List<ActorDto>> getAll(@RequestParam boolean metodo, @RequestParam int pagina,
			@RequestParam int tamano, @RequestParam String sort) {
		Pageable pageable = PageRequest.of(pagina, tamano, Sort.by(sort).ascending());

		if (metodo) {
			try {
				return new ResponseEntity<>(actorService.getAll(pageable).stream().map(boToDto::boToActorDto).toList(),
						HttpStatus.OK);
			} catch (ServiceException e) {
				return new ResponseEntity<>(
						actorService.getAllCriteria(pageable).stream().map(boToDto::boToActorDto).toList(),
						HttpStatus.OK);
			}

		} else {
			return new ResponseEntity<>(
					actorService.getAllCriteria(pageable).stream().map(boToDto::boToActorDto).toList(), HttpStatus.OK);
		}
	}

	@GetMapping("/findAllFilter")
	public ResponseEntity<List<ActorDto>> findAllFilter(@RequestParam(required = false) List<String> names,
			@RequestParam(required = false) List<Integer> ages,
			@RequestParam(required = false) List<String> nationalities, @RequestParam boolean method,
			@RequestParam(defaultValue = "id", name = "Variable to order the list") String sort,
			@RequestParam(defaultValue = "asc") String order) throws ServiceException, PresentationException {

		Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(0, 5, Sort.by(new Sort.Order(direction, sort)));

		if (method) {
			try {
				return new ResponseEntity<>(actorService.getAllCriteriaFilter(pageable, names, ages, nationalities)
						.stream().map(boToDto::boToActorDto).toList(), HttpStatus.OK);
			} catch (ServiceException e) {
				throw new PresentationException(e.getLocalizedMessage());
			}
		}
		try {
			return new ResponseEntity<>(actorService.getAll(pageable).stream().map(boToDto::boToActorDto).toList(),
					HttpStatus.OK);
		} catch (ServiceException e) {
			throw new PresentationException(e.getLocalizedMessage());
		}
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
			return ResponseEntity.notFound().build();
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
		return ResponseEntity.status(HttpStatus.CREATED).body(savedActor);

	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> deleteByid(@PathVariable long id, @RequestParam boolean metodo)
			throws NotFoundException {
		try {
			if (metodo) {
				actorService.deleteById(id);
			} else {
				actorService.deleteByIdCriteria(id);
			}
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}
}
