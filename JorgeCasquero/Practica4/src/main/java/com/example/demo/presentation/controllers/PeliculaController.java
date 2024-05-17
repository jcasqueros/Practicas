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
import com.example.demo.presentation.dto.PeliculaDto;
import com.example.demo.servcice.PeliculaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pelicula")
public class PeliculaController {

	private final PeliculaService peliculaService;

	private final BoToDTo boToDto;

	private final DtoToBo dtoToBo;

	@GetMapping("/getAll")
    public ResponseEntity<List<PeliculaDto>> getAll(@RequestParam boolean method,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id", value = "Variable for order the list") String sort)
            throws ServiceException, PresentationException {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if (method) {
            try {
                return new ResponseEntity<>(
                        peliculaService.getAllCriteria(pageable).stream().map(boToDto::boToPeliculaDto).toList(),
                        HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(
                    peliculaService.getAll(pageable).stream().map(boToDto::boToPeliculaDto).toList(),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }
	@GetMapping("/findAllFilter")
    public ResponseEntity<List<PeliculaDto>> findAllFilter(@RequestParam(required = false) List<String> names,
            @RequestParam(required = false) List<Integer> ages, @RequestParam(required = false) List<String> directors,
            @RequestParam(required = false) List<String> producers, @RequestParam(required = false) List<String> actors,
            @RequestParam boolean method,
            @RequestParam(defaultValue = "id", value = "Variable for order the list") String sort,
            @RequestParam(defaultValue = "asc") String order) throws ServiceException, PresentationException {

        Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(0, 5, Sort.by(new Sort.Order(direction, sort)));

        if (method) {
            try {
                return new ResponseEntity<>(
                        peliculaService.findAllCriteriaFilter(pageable, names, ages, directors, producers, actors).stream()
                                .map(boToDto::boToPeliculaDto).toList(), HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(
                    peliculaService.getAll(pageable).stream().map(boToDto::boToPeliculaDto).toList(),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
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
		if (pelicula != null) {
			return ResponseEntity.ok(pelicula);
		} else {
			return ResponseEntity.notFound().build();
		}

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
		return ResponseEntity.status(HttpStatus.CREATED).body(savedPelicula);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deleteByid(@PathVariable long id, @RequestParam boolean metodo)
			throws NotFoundException {
		try {
			if (metodo) {
				peliculaService.deleteById(id);

			} else {
				peliculaService.deleteByIdCriteria(id);
			}

			return ResponseEntity.noContent().build();
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}

	}

}
