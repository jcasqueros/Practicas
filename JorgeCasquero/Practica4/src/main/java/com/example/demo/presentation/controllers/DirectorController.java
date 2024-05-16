package com.example.demo.presentation.controllers;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.example.demo.presentation.dto.DirectorDto;
import com.example.demo.servcice.DirectorService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/director")
public class DirectorController {

	private final DirectorService directorService;

	private final BoToDTo boToDto;

	private final DtoToBo dtoToBo;

	@GetMapping("/getAll")
	public ResponseEntity<List<DirectorDto>> getAll(@RequestParam boolean metodo,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int tamano,
			@RequestParam(defaultValue = "0", value = "Variable para el orden de la lista") String sort,
			@RequestParam(defaultValue = "asc") String orden) throws PresentationException {

		Pageable pageable = PageRequest.of(page, tamano, Sort.by(sort).ascending());

		if (metodo) {
			try {
				return new ResponseEntity<>(
						directorService.getAll(pageable).stream().map(boToDto::boToDirectorDto).toList(),
						HttpStatus.OK);
			} catch (ServiceException e) {
				throw new PresentationException(e.getLocalizedMessage());
			}

		}

		try {
			return new ResponseEntity<>(
					directorService.getAllCriteria(pageable).stream().map(boToDto::boToDirectorDto).toList(),
					HttpStatus.OK);
		} catch (ServiceException e) {
			throw new PresentationException(e.getLocalizedMessage());
		}

	}

	@PostMapping("/save")
	public ResponseEntity<DirectorDto> save(@RequestBody DirectorDto director, @RequestParam boolean metodo)
			throws AlreadyExistsExeption, NotFoundException {
		DirectorDto savedActor;
		if (metodo) {
			savedActor = boToDto.boToDirectorDto(directorService.create(dtoToBo.dtoToDirectorBo(director)));

		} else {
			savedActor = boToDto.boToDirectorDto(directorService.createCriteria(dtoToBo.dtoToDirectorBo(director)));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(savedActor);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> deleteByid(@PathVariable long id, @RequestParam boolean metodo)
			throws NotFoundException {
		try {
			if (metodo) {
				directorService.deleteById(id);
			} else {
				directorService.deleteByIdCriteria(id);
			}
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/findAllFilter")
	public ResponseEntity<List<DirectorDto>> findAllFilter(@RequestParam(required = false) List<String> nombres,
			@RequestParam(required = false) List<Integer> edades,
			@RequestParam(required = false) List<String> nacionalidades, @RequestParam boolean metodo,
			@RequestParam(defaultValue = "id", value = "Variable para el orden de la lista") String sort,
			@RequestParam(defaultValue = "asc") String orden) throws ServiceException, PresentationException {

		Sort.Direction direction = orden.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(0, 5, Sort.by(new Sort.Order(direction, sort)));

		if (metodo) {
			try {
				return new ResponseEntity<>(
						directorService.findAllCriteriaFilter(pageable, nombres, edades, nacionalidades).stream()
								.map(boToDto::boToDirectorDto).toList(),
						HttpStatus.OK);
			} catch (ServiceException e) {
				throw new PresentationException(e.getLocalizedMessage());
			}
		}
		try {
			return new ResponseEntity<>(
					directorService.getAll(pageable).stream().map(boToDto::boToDirectorDto).toList(), HttpStatus.OK);
		} catch (ServiceException e) {
			throw new PresentationException(e.getLocalizedMessage());
		}
	}
}
