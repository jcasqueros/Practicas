package com.viewnext.Practica4.backend.presentation.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.Practica4.backend.business.bo.PeliculaBo;
import com.viewnext.Practica4.backend.business.services.PeliculaServices;
import com.viewnext.Practica4.backend.presentation.dto.PeliculaDto;
import com.viewnext.Practica4.util.converter.dto.BoToDto;
import com.viewnext.Practica4.util.converter.dto.DtoToBo;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pelicula")
public class PeliculaController {

    @Autowired
    PeliculaServices peliculaServices;
    
    @Autowired
    DtoToBo dtoToBo;

    @Autowired
    BoToDto boToDto;

    @GetMapping("/jpa/all")
    public ResponseEntity<List<PeliculaDto>> getPeliculas() {
        try {
            List<PeliculaBo> peliculaBo = peliculaServices.getAll();
            System.out.println(peliculaBo);
            List<PeliculaDto> peliculaDto = peliculaBo.stream().map(boToDto::peliculaBoToPeliculaDto)
                    .collect(Collectors.toList());
            System.out.println(peliculaDto);
            log.info("Obteniendo lista de peliculas");
            return ResponseEntity.ok(peliculaDto);
        } catch (ServiceException se) {
            log.error("Error al obtener la lista de peliculas");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    @GetMapping("/jpa/{id}")
    public ResponseEntity<PeliculaDto> getPeliculaByCode(@PathVariable long id) {
        try {
            PeliculaBo peliculaBo = peliculaServices.read(id);
            PeliculaDto peliculaDto = boToDto.peliculaBoToPeliculaDto(peliculaBo);
            return ResponseEntity.ok(peliculaDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar buscar una pelicula por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/jpa/create")
    public ResponseEntity<PeliculaDto> createPelicula(@RequestBody PeliculaDto peliculaDto) {
        try {
            PeliculaBo peliculaBo = dtoToBo.peliculaDtoToBo(peliculaDto);
            peliculaServices.create(peliculaBo);
            PeliculaDto savedPeliculaDto = boToDto.peliculaBoToPeliculaDto(peliculaBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPeliculaDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar crear una nueva pelicula");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } 
    }

    @PutMapping("/jpa/{id}")
    public ResponseEntity<PeliculaDto> updatePelicula(@PathVariable long id,
            @RequestBody PeliculaDto peliculaDto) {
        try {
            PeliculaBo peliculaBo = dtoToBo.peliculaDtoToBo(peliculaDto);
            peliculaBo = peliculaServices.read(id);
            peliculaBo.setTitulo(peliculaDto.getTitulo());
            peliculaBo.setId(peliculaDto.getId());
            peliculaBo.setIdDirector(peliculaDto.getIdDirector());
            peliculaBo.setActores(peliculaDto.getActores());
            peliculaBo.setAnho(peliculaDto.getAnho());
            peliculaBo.setIdProductora(peliculaDto.getIdProductora());
            peliculaServices.update(peliculaBo);
            PeliculaDto updatePeliculaDto = boToDto.peliculaBoToPeliculaDto(peliculaBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatePeliculaDto);
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar una pelicula por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/jpa/{id}")
    public ResponseEntity<Void> deletePelicula(@PathVariable long id) {
        try {
            peliculaServices.delete(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar una pelicula por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    
    
    //CRITERIA BUILDER
    
    @GetMapping("/cb/all")
    public ResponseEntity<List<PeliculaDto>> getPeliculaCb() {
        try {
            List<PeliculaBo> peliculas = peliculaServices.getPeliculasCb();
            List<PeliculaDto> peliculaDtos = peliculas.stream().map(boToDto::peliculaBoToPeliculaDto)
                    .collect(Collectors.toList());
            log.info("Obteniendo lista de peliculas");
            return ResponseEntity.ok(peliculaDtos);
        } catch (ServiceException se) {
            log.error("Error al obtener la lista de peliculas");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cb/{id}")
    public ResponseEntity<PeliculaDto> readPeliculaCb(@PathVariable long id) {
        try {
            PeliculaBo peliculaBo = peliculaServices.readCb(id);
            PeliculaDto peliculaDto = boToDto.peliculaBoToPeliculaDto(peliculaBo);
            return ResponseEntity.ok(peliculaDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar buscar una pelicula por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @PostMapping("/cb/create")
    public ResponseEntity<PeliculaDto> createPeliculaCb(@RequestBody PeliculaDto peliculaDto) {
        try {
            PeliculaBo peliculaBo = dtoToBo.peliculaDtoToBo(peliculaDto);
            peliculaServices.createCb(peliculaBo);
            PeliculaDto savedPeliculaDto = boToDto.peliculaBoToPeliculaDto(peliculaBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPeliculaDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar crear una nueva pelicula");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } 
    }

    @Transactional
    @PutMapping("/cb/{id}")
    public ResponseEntity<PeliculaDto> updatePeliculaCb(@PathVariable long id,
            @RequestBody PeliculaDto peliculaDto) {
        try {
            PeliculaBo peliculaBo = dtoToBo.peliculaDtoToBo(peliculaDto);
            peliculaBo = peliculaServices.readCb(id);
            peliculaBo.setTitulo(peliculaDto.getTitulo());
            peliculaBo.setId(peliculaDto.getId());
            peliculaBo.setIdDirector(peliculaDto.getIdDirector());
            peliculaBo.setActores(peliculaDto.getActores());
            peliculaBo.setAnho(peliculaDto.getAnho());
            peliculaBo.setIdProductora(peliculaDto.getIdProductora());
            peliculaServices.updateCb(peliculaBo);
            PeliculaDto updatePeliculaDto = boToDto.peliculaBoToPeliculaDto(peliculaBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatePeliculaDto);
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar una pelicula por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @DeleteMapping("/cb/{id}")
    public ResponseEntity<Void> deletePeliculaCb(@PathVariable long id) {
        try {
            peliculaServices.deleteCb(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar una pelicula por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
