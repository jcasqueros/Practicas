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
import com.example.demo.presentation.dto.SerieDto;
import com.example.demo.servcice.SerieService;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

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
	public ResponseEntity<List<SerieDto>> getAll(@RequestParam boolean metodo) {
		List<SerieDto> series;
		if (metodo) {
			series = serieService.getAll().stream().map(serie -> boToDto.boToSerieDto(serie)).toList();
		} else {
			series = serieService.getAllCriteria().stream().map(serie -> boToDto.boToSerieDto(serie)).toList();
		}

		return ResponseEntity.ok(series);
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
