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
	public List<SerieDto> getAll() {
		return serieService.getAll().stream().map(serie -> boToDto.boToSerieDto(serie)).toList();
	}

	@GetMapping("getById/{id}")
	public SerieDto getById(@PathVariable long id) throws NotFoundException {
		log.debug("Solicitud de la consulta a la serie con id: " + id + ".");
		return boToDto.boToSerieDto(serieService.getById(id));
	}

	@PostMapping("/save")
	public SerieDto save(@RequestBody SerieDto serie) throws AlreadyExistsExeption {
		return boToDto.boToSerieDto(serieService.create(dtoToBo.dtoToSerieBo(serie)));
	}

	@DeleteMapping("delete/{id}")
	public void deleteByid(@PathVariable long id) throws NotFoundException {
		serieService.deleteById(id);

	}

}
