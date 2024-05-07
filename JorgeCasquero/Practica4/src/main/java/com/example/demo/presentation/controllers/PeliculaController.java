package com.example.demo.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public List<PeliculaDto> getAll() {
		return peliculaService.getAll().stream().map(pelicula -> boToDto.boToPeliculaDto(pelicula)).toList();
	}

	@GetMapping("getById/{id}")
	public PeliculaDto getById(@PathVariable long id) throws NotFoundException {
		log.debug("Solicitud de la consulta a la pelicula con id: " + id + ".");
		return boToDto.boToPeliculaDto(peliculaService.getById(id));
	}

	@PostMapping("/save")
	public PeliculaDto save(@RequestBody PeliculaDto pelicula) throws AlreadyExistsExeption {
		return boToDto.boToPeliculaDto(peliculaService.create(dtoToBo.dtoToPeliculaBo(pelicula)));
	}

	
	@DeleteMapping("delete/{id}")
	public void deleteByid(@PathVariable long id) throws NotFoundException {
		peliculaService.deleteById(id);

	}

}
