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

import com.viewnext.Practica4.backend.business.bo.DirectorBo;
import com.viewnext.Practica4.backend.business.services.DirectorServices;
import com.viewnext.Practica4.backend.presentation.dto.DirectorDto;
import com.viewnext.Practica4.util.converter.dto.BoToDto;
import com.viewnext.Practica4.util.converter.dto.DtoToBo;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/director")
public class DirectorController {

    @Autowired
    DirectorServices directorService;
    
    @Autowired
    DtoToBo dtoToBo;

    @Autowired
    BoToDto boToDto;
    
    // Métodos para JPA
    
    @GetMapping("/jpa/all")
    public ResponseEntity<List<DirectorDto>> getDirectores() {
        try {
            List<DirectorBo> directorBo = directorService.getAll();
            List<DirectorDto> directorDto = directorBo.stream().map(boToDto::directorBoToDirectorDto)
                    .collect(Collectors.toList());
            log.info("Obteniendo lista de directores");
            return ResponseEntity.ok(directorDto);
        } catch (ServiceException se) {
            log.error("Error al obtener la lista de directores");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/jpa/{id}")
    public ResponseEntity<DirectorDto> getDirectorByCode(@PathVariable long id) {
        try {
            DirectorBo directorBo = directorService.read(id);
            DirectorDto directorDto = boToDto.directorBoToDirectorDto(directorBo);
            return ResponseEntity.ok(directorDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar buscar un director por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/jpa/create")
    public ResponseEntity<DirectorDto> createDirector(@RequestBody DirectorDto directorDto) {
        try {
            DirectorBo directorBo = dtoToBo.directorDtoToBo(directorDto);
            directorService.create(directorBo);
            DirectorDto savedDirectorDto = boToDto.directorBoToDirectorDto(directorBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDirectorDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar crear un nuevo director");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } 
    }

    @PutMapping("/jpa/{id}")
    public ResponseEntity<DirectorDto> updateDirector(@PathVariable long id,
            @RequestBody DirectorDto directorDto) {
        try {
            DirectorBo directorBo = dtoToBo.directorDtoToBo(directorDto);
            directorBo = directorService.read(id);
            directorBo.setNombre(directorDto.getNombre());
            directorBo.setEdad(directorDto.getEdad());
            directorBo.setNacionalidad(directorDto.getNacionalidad());
            directorBo.setId(directorDto.getId());
            directorService.update(directorBo);
            DirectorDto updateDirectorDto = boToDto.directorBoToDirectorDto(directorBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(updateDirectorDto);
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar un director por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/jpa/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable long id) {
        try {
            directorService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar un director por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Métodos para Criteria Builder
    
    @GetMapping("/cb/all")
    public ResponseEntity<List<DirectorDto>> getDirectoresCb() {
        try {
            List<DirectorBo> directors = directorService.getDirectoresCb();
            List<DirectorDto> directorDtos = directors.stream().map(boToDto::directorBoToDirectorDto)
                    .collect(Collectors.toList());
            log.info("Obteniendo lista de directores");
            return ResponseEntity.ok(directorDtos);
        } catch (ServiceException se) {
            log.error("Error al obtener la lista de directores");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cb/{id}")
    public ResponseEntity<DirectorDto> readDirectorCb(@PathVariable long id) {
        try {
            DirectorBo directorBo = directorService.readCb(id);
            DirectorDto directorDto = boToDto.directorBoToDirectorDto(directorBo);
            return ResponseEntity.ok(directorDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar buscar un director por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @PostMapping("/cb/create")
    public ResponseEntity<DirectorDto> createDirectorCb(@RequestBody DirectorDto directorDto) {
        try {
            DirectorBo directorBo = dtoToBo.directorDtoToBo(directorDto);
            directorService.createCb(directorBo);
            DirectorDto savedDirectorDto = boToDto.directorBoToDirectorDto(directorBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDirectorDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar crear un nuevo director");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } 
    }

    @Transactional
    @PutMapping("/cb/{id}")
    public ResponseEntity<DirectorDto> updateDirectorCb(@PathVariable long id,
            @RequestBody DirectorDto directorDto) {
        try {
            DirectorBo directorBo = dtoToBo.directorDtoToBo(directorDto);
            directorBo = directorService.readCb(id);
            directorBo.setNombre(directorDto.getNombre());
            directorBo.setEdad(directorDto.getEdad());
            directorBo.setNacionalidad(directorDto.getNacionalidad());
            directorBo.setId(directorDto.getId());
            directorService.updateCb(directorBo);
            DirectorDto updateDirectorDto = boToDto.directorBoToDirectorDto(directorBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(updateDirectorDto);
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar un director por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @DeleteMapping("/cb/{id}")
    public ResponseEntity<Void> deleteDirectorCb(@PathVariable long id) {
        try {
            directorService.deleteCb(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar un director por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}