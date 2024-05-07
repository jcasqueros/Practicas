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
	public List<DirectorDto> getAll() {
		return directorService.getAll().stream().map(director -> boToDto.boToDirectorDto(director)).toList();
	}

	@GetMapping("getById/{id}")
	public DirectorDto getById(@PathVariable long id) throws NotFoundException {
		log.debug("Solicitud de la consulta al director con id: " + id + ".");
		return boToDto.boToDirectorDto(directorService.getById(id));
	}

	@PostMapping("/save")
	public DirectorDto save(@RequestBody DirectorDto director) throws AlreadyExistsExeption {
		return boToDto.boToDirectorDto(directorService.create(dtoToBo.dtoToDirectorBo(director)));
	}

	@DeleteMapping("delete/{id}")
	public void deleteByid(@PathVariable long id) throws NotFoundException {
		directorService.deleteById(id);

	}
}
