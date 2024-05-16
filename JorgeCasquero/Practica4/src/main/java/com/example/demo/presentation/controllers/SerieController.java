package com.example.demo.presentation.controllers;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.presentation.dto.SerieDto;
import com.example.demo.servcice.SerieService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/serie")
public class SerieController {

	@Autowired
	SerieService serieService;

	@Autowired
	BoToDTo boToDto;

	@Autowired
	DtoToBo dtoToBo;

	@GetMapping("/getAll")
	public ResponseEntity<List<SerieDto>> findAll(@RequestParam boolean method,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id", value = "Variable for order the list") String sort)
			throws ServiceException, PresentationException {

		Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

		if (method) {
			try {
				return new ResponseEntity<>(
						serieService.getAll(pageable).stream().map(boToDto::boToSerieDto).toList(),
						HttpStatus.OK);
			} catch (ServiceException e) {
				throw new PresentationException(e.getLocalizedMessage());
			}
		}
		try {
			return new ResponseEntity<>(
					serieService.getAllCriteria(pageable).stream().map(boToDto::boToSerieDto).toList(),
					HttpStatus.OK);
		} catch (ServiceException e) {
			throw new PresentationException(e.getLocalizedMessage());
		}
	}

	@GetMapping("getById/{id}")
	public ResponseEntity<SerieDto> getById(@PathVariable long id, @RequestParam boolean metodo)
			throws NotFoundException {
		log.debug("Solicitud de la consulta a la serie con id: " + id + ".");
		SerieDto serieDto;
		if (metodo) {
			serieDto = boToDto.boToSerieDto(serieService.getById(id));
		} else {
			serieDto = boToDto.boToSerieDto(serieService.getByIdCriteria(id));
		}

		return ResponseEntity.ok(serieDto);
	}

	@PostMapping("/save")
	public ResponseEntity<SerieDto> save(@RequestBody SerieDto serie, @RequestParam boolean metodo)
			throws AlreadyExistsExeption, NotFoundException {
		SerieDto serieDto;
		if (metodo) {
			serieDto = boToDto.boToSerieDto(serieService.create(dtoToBo.dtoToSerieBo(serie)));
		} else {
			serieDto = boToDto.boToSerieDto(serieService.createCriteria(dtoToBo.dtoToSerieBo(serie)));
		}
		return ResponseEntity.ok(serieDto);

	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deleteByid(@PathVariable long id, @RequestParam boolean metodo)
			throws NotFoundException {
		if (metodo) {
			serieService.deleteById(id);
		} else {
			serieService.deleteByIdCriteria(id);
		}
		return ResponseEntity.noContent().build();
	}
}
