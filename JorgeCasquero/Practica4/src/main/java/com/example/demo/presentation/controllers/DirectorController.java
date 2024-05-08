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
import com.example.demo.presentation.dto.DirectorDto;
import com.example.demo.servcice.DirectorService;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/director")
public class DirectorController {

	@Autowired
	DirectorService directorService;

	@Autowired
	BoToDTo boToDto;

	@Autowired
	DtoToBo dtoToBo;

	@GetMapping("/getAll")
	public ResponseEntity<List<DirectorDto>> getAll(@RequestParam boolean metodo) {
		List<DirectorDto> directores;
		if (metodo) {
			directores = directorService.getAll().stream().map(director -> boToDto.boToDirectorDto(director)).toList();
		} else {
			directores = directorService.getAllCriteria().stream().map(director -> boToDto.boToDirectorDto(director))
					.toList();
		}
		return ResponseEntity.ok(directores);
	}

	@GetMapping("getById/{id}")
	public ResponseEntity<DirectorDto> getById(@PathVariable long id, @RequestParam boolean metodo)
			throws NotFoundException {
		log.debug("Solicitud de la consulta al director con id: " + id + ".");
		DirectorDto director;
		if (metodo) {
			director = boToDto.boToDirectorDto(directorService.getById(id));
		} else {
			director = boToDto.boToDirectorDto(directorService.getByIdCriteria(id));
		}
		if (director != null) {
			return ResponseEntity.ok(director);
		} else {
			throw new NotFoundException("Actor no encontrado con el id: " + id);
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
		return ResponseEntity.ok(savedActor);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> deleteByid(@PathVariable long id, @RequestParam boolean metodo)
			throws NotFoundException {
		if (metodo) {
			directorService.deleteById(id);
		} else {
			directorService.deleteByIdCriteria(id);
		}
		return ResponseEntity.noContent().build();
	}
}
