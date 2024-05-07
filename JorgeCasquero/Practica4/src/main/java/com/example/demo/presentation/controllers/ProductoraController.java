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
import com.example.demo.presentation.dto.ProductoraDto;
import com.example.demo.servcice.ProductoraService;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.example.demo.servcice.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/productora")
public class ProductoraController {
	@Autowired
	ProductoraService productoraService;

	@Autowired
	BoToDTo boToDto;

	@Autowired
	DtoToBo dtoToBo;

	@GetMapping("/getAll")
	public List<ProductoraDto> getAll() {
		return productoraService.getAll().stream().map(productora-> boToDto.boToProductoraDto(productora)).toList();
	}

	@GetMapping("getById/{id}")
	public ProductoraDto getById(@PathVariable long id) throws NotFoundException {
		log.debug("Solicitud de la consulta a la productora con id: " + id + ".");
		return boToDto.boToProductoraDto(productoraService.getById(id));
	}

	@PostMapping("/save")
	public ProductoraDto save(@RequestBody ProductoraDto productora) throws AlreadyExistsExeption {
		return boToDto.boToProductoraDto(productoraService.create(dtoToBo.dtoToProductoraBo(productora)));
	}

	
	@DeleteMapping("delete/{id}")
	public void deleteByid(@PathVariable long id) throws NotFoundException {
		productoraService.deleteById(id);

	}

}
