package com.example.demo.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<List<ProductoraDto>> getAll(@RequestParam boolean metodo) {
		List<ProductoraDto> productoras;

		if (metodo) {
			productoras = productoraService.getAll().stream().map(productora -> boToDto.boToProductoraDto(productora))
					.toList();
		} else {
			productoras = productoraService.getAllCriteria().stream()
					.map(productora -> boToDto.boToProductoraDto(productora)).toList();
		}
		return ResponseEntity.ok(productoras);

	}

	@GetMapping("getById/{id}")
	public ResponseEntity<ProductoraDto> getById(@PathVariable long id, @RequestParam boolean metodo)
			throws NotFoundException {
		log.debug("Solicitud de la consulta a la productora con id: " + id + ".");
		ProductoraDto productoraDto;

		if (metodo) {
			productoraDto = boToDto.boToProductoraDto(productoraService.getById(id));
		} else {
			productoraDto = boToDto.boToProductoraDto(productoraService.getByIdCriteria(id));
		}
		if (productoraDto != null) {
			return ResponseEntity.ok(productoraDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/save")
	public ResponseEntity<ProductoraDto> save(@RequestBody ProductoraDto productora, @RequestParam boolean metodo)
			throws AlreadyExistsExeption, NotFoundException {
		ProductoraDto savedProductora;
		if (metodo) {
			savedProductora = boToDto
					.boToProductoraDto(productoraService.create(dtoToBo.dtoToProductoraBo(productora)));
		} else {
			savedProductora = boToDto
					.boToProductoraDto(productoraService.createCriteria(dtoToBo.dtoToProductoraBo(productora)));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(savedProductora);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deleteByid(@PathVariable long id, @RequestParam boolean metodo)
			throws NotFoundException {
		if (metodo) {
			productoraService.deleteById(id);

		} else {
			productoraService.deleteByIdCriteria(id);
		}

		return ResponseEntity.noContent().build();

	}

}
