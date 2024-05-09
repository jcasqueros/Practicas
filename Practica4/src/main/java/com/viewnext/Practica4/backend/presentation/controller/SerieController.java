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

import com.viewnext.Practica4.backend.business.bo.SerieBo;
import com.viewnext.Practica4.backend.business.services.SerieServices;
import com.viewnext.Practica4.backend.presentation.dto.SerieDto;
import com.viewnext.Practica4.util.converter.dto.BoToDto;
import com.viewnext.Practica4.util.converter.dto.DtoToBo;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/serie")
public class SerieController {

    @Autowired
    SerieServices serieServices;
    
    @Autowired
    DtoToBo dtoToBo;

    @Autowired
    BoToDto boToDto;

    @GetMapping("/jpa/all")
    public ResponseEntity<List<SerieDto>> getSeries() {
        try {
            List<SerieBo> serieBo = serieServices.getAll();
            System.out.println(serieBo);
            List<SerieDto> serieDto = serieBo.stream().map(boToDto::serieBoToSerieDto)
                    .collect(Collectors.toList());
            System.out.println(serieDto);
            log.info("Obteniendo lista de series");
            return ResponseEntity.ok(serieDto);
        } catch (ServiceException se) {
            log.error("Error al obtener la lista de series");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    @GetMapping("/jpa/{id}")
    public ResponseEntity<SerieDto> getSerieByCode(@PathVariable long id) {
        try {
            SerieBo serieBo = serieServices.read(id);
            SerieDto serieDto = boToDto.serieBoToSerieDto(serieBo);
            return ResponseEntity.ok(serieDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar buscar una serie por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/jpa/create")
    public ResponseEntity<SerieDto> createSerie(@RequestBody SerieDto serieDto) {
        try {
            SerieBo serieBo = dtoToBo.serieDtoToBo(serieDto);
            serieServices.create(serieBo);
            SerieDto savedSerieDto = boToDto.serieBoToSerieDto(serieBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSerieDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar crear una nueva serie");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } 
    }

    @PutMapping("/jpa/{id}")
    public ResponseEntity<SerieDto> updateSerie(@PathVariable long id,
            @RequestBody SerieDto serieDto) {
        try {
            SerieBo serieBo = dtoToBo.serieDtoToBo(serieDto);
            serieBo = serieServices.read(id);
            serieBo.setTitulo(serieDto.getTitulo());
            serieBo.setId(serieDto.getId());
            serieBo.setActores(serieDto.getActores());
            serieBo.setAnho(serieDto.getAnho());
            serieBo.setIdDirector(serieDto.getIdDirector());
            serieBo.setIdProductora(serieDto.getIdProductora());
            serieServices.update(serieBo);
            SerieDto updateSerieDto = boToDto.serieBoToSerieDto(serieBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(updateSerieDto);
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar una serie por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/jpa/{id}")
    public ResponseEntity<Void> deleteSerie(@PathVariable long id) {
        try {
            serieServices.delete(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar una serie por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    
    
// Métodos para Criteria Builder
    
    @GetMapping("/cb/all")
    public ResponseEntity<List<SerieDto>> getSerieCb() {
        try {
            List<SerieBo> series = serieServices.getSeriesCb();
            List<SerieDto> serieDtos = series.stream().map(boToDto::serieBoToSerieDto)
                    .collect(Collectors.toList());
            log.info("Obteniendo lista de series");
            return ResponseEntity.ok(serieDtos);
        } catch (ServiceException se) {
            log.error("Error al obtener la lista de series");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cb/{id}")
    public ResponseEntity<SerieDto> readSerieCb(@PathVariable long id) {
        try {
            SerieBo serieBo = serieServices.readCb(id);
            SerieDto serieDto = boToDto.serieBoToSerieDto(serieBo);
            return ResponseEntity.ok(serieDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar buscar una serie por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @PostMapping("/cb/create")
    public ResponseEntity<SerieDto> createSerieCb(@RequestBody SerieDto serieDto) {
        try {
            SerieBo serieBo = dtoToBo.serieDtoToBo(serieDto);
            serieServices.createCb(serieBo);
            SerieDto savedSerieDto = boToDto.serieBoToSerieDto(serieBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSerieDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar crear una nueva serie");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } 
    }

    @Transactional
    @PutMapping("/cb/{id}")
    public ResponseEntity<SerieDto> updateSerieCb(@PathVariable long id,
            @RequestBody SerieDto serieDto) {
        try {
            SerieBo serieBo = dtoToBo.serieDtoToBo(serieDto);
            serieBo = serieServices.readCb(id);
            serieBo.setTitulo(serieDto.getTitulo());
            serieBo.setId(serieDto.getId());
            serieBo.setActores(serieDto.getActores());
            serieBo.setAnho(serieDto.getAnho());
            serieBo.setIdDirector(serieDto.getIdDirector());
            serieBo.setIdProductora(serieDto.getIdProductora());
            serieServices.updateCb(serieBo);
            SerieDto updateSerieDto = boToDto.serieBoToSerieDto(serieBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(updateSerieDto);
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar una serie por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @DeleteMapping("/cb/{id}")
    public ResponseEntity<Void> deleteSerieCb(@PathVariable long id) {
        try {
            serieServices.deleteCb(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar una serie por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
