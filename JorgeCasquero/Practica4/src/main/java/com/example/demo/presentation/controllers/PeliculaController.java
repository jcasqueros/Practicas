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
import com.example.demo.presentation.dto.PeliculaDto;
import com.example.demo.servcice.PeliculaService;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pelicula")
public class PeliculaController {

	@Autowired
	PeliculaService peliculaService;

	@Autowired
	BoToDTo boToDto;

	@Autowired
	DtoToBo dtoToBo;

	@GetMapping("/getAll")
	public ResponseEntity<List<PeliculaDto>> getAll(@RequestParam boolean metodo) {
		List<PeliculaDto> peliculas;
		if (metodo) {
			peliculas = peliculaService.getAll().stream().map(pelicula -> boToDto.boToPeliculaDto(pelicula)).toList();
		} else {
			peliculas = peliculaService.getAllCriteria().stream().map(pelicula -> boToDto.boToPeliculaDto(pelicula))
					.toList();
		}
		return ResponseEntity.ok(peliculas);
	}

	@GetMapping("getById/{id}")
	public ResponseEntity<PeliculaDto> getById(@PathVariable long id, @RequestParam boolean metodo)
			throws NotFoundException {
		log.debug("Solicitud de la consulta a la pelicula con id: " + id + ".");
		PeliculaDto pelicula;
		if (metodo) {
			pelicula = boToDto.boToPeliculaDto(peliculaService.getById(id));
		} else {
			pelicula = boToDto.boToPeliculaDto(peliculaService.getByIdCriteria(id));
		}

		return ResponseEntity.ok(pelicula);
	}

	@PostMapping("/save")
	public ResponseEntity<PeliculaDto> save(@RequestBody PeliculaDto pelicula, @RequestParam boolean metodo)
			throws AlreadyExistsExeption, NotFoundException {
		PeliculaDto savedPelicula;
		if (metodo) {
			savedPelicula = boToDto.boToPeliculaDto(peliculaService.create(dtoToBo.dtoToPeliculaBo(pelicula)));
		} else {
			savedPelicula = boToDto.boToPeliculaDto(peliculaService.createCriteria(dtoToBo.dtoToPeliculaBo(pelicula)));
		}
		return ResponseEntity.ok(savedPelicula);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deleteByid(@PathVariable long id, @RequestParam boolean metodo)
			throws NotFoundException {
		if (metodo) {
			peliculaService.deleteById(id);

		} else {
			peliculaService.deleteByIdCriteria(id);
		}

		return ResponseEntity.noContent().build();

	}

}
