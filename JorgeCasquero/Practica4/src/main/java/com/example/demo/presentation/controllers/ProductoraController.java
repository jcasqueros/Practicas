package com.example.demo.presentation.controllers;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
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
import com.example.demo.presentation.dto.ProductoraDto;
import com.example.demo.servcice.ProductoraService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/productora")
public class ProductoraController {

	private final ProductoraService productoraService;

	private final BoToDTo boToDto;

	private final DtoToBo dtoToBo;

	@GetMapping("/getAll")
	public ResponseEntity<List<ProductoraDto>> getAll(@RequestParam boolean method,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id", value = "Variable for order the list") String sort)
			throws ServiceException, PresentationException {

		Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

		if (method) {
			try {
				return new ResponseEntity<>(productoraService.getAllCriteria(pageable).stream()
						.map(boToDto::boToProductoraDto).toList(), HttpStatus.OK);
			} catch (ServiceException e) {
				throw new PresentationException(e.getLocalizedMessage());
			}
		}
		try {
			return new ResponseEntity<>(
					productoraService.getAll(pageable).stream().map(boToDto::boToProductoraDto).toList(),
					HttpStatus.OK);
		} catch (ServiceException e) {
			throw new PresentationException(e.getLocalizedMessage());
		}
	}
	@GetMapping("/getAllFilter")
    public ResponseEntity<List<ProductoraDto>> getAllFilter(@RequestParam(required = false) List<String> names,
            @RequestParam(required = false) List<Integer> ages, @RequestParam boolean method,
            @RequestParam(defaultValue = "id", value = "Variable for order the list") String sort,
            @RequestParam(defaultValue = "asc") String order) throws ServiceException, PresentationException {

        Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(0, 5, Sort.by(new Sort.Order(direction, sort)));

        if (method) {
            try {
                return new ResponseEntity<>(productoraService.findAllCriteriaFilter(pageable, names, ages).stream()
                        .map(boToDto::boToProductoraDto).toList(), HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(
                    productoraService.getAll(pageable).stream().map(boToDto::boToProductoraDto).toList(),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
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
