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

import com.viewnext.Practica4.backend.business.bo.ProductoraBo;
import com.viewnext.Practica4.backend.business.services.ProductoraServices;
import com.viewnext.Practica4.backend.presentation.dto.ProductoraDto;
import com.viewnext.Practica4.util.converter.dto.BoToDto;
import com.viewnext.Practica4.util.converter.dto.DtoToBo;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/productora")
public class ProductoraController {

    @Autowired
    ProductoraServices productoraService;
    
    @Autowired
    DtoToBo dtoToBo;

    @Autowired
    BoToDto boToDto;
    
    // Métodos para JPA
    
    @GetMapping("/jpa/all")
    public ResponseEntity<List<ProductoraDto>> getProductoras() {
        try {
            List<ProductoraBo> productoraBo = productoraService.getAll();
            List<ProductoraDto> productoraDto = productoraBo.stream().map(boToDto::productoraBoToProductoraDto)
                    .collect(Collectors.toList());
            log.info("Obteniendo lista de productoras");
            return ResponseEntity.ok(productoraDto);
        } catch (ServiceException se) {
            log.error("Error al obtener la lista de productoras");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/jpa/{id}")
    public ResponseEntity<ProductoraDto> getProductoraByCode(@PathVariable long id) {
        try {
            ProductoraBo productoraBo = productoraService.read(id);
            ProductoraDto productoraDto = boToDto.productoraBoToProductoraDto(productoraBo);
            return ResponseEntity.ok(productoraDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar buscar una productora por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/jpa/create")
    public ResponseEntity<ProductoraDto> createProductora(@RequestBody ProductoraDto productoraDto) {
        try {
            ProductoraBo productoraBo = dtoToBo.productoraDtoToBo(productoraDto);
            productoraService.create(productoraBo);
            ProductoraDto savedProductoraDto = boToDto.productoraBoToProductoraDto(productoraBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProductoraDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar crear una nueva productora");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } 
    }

    @PutMapping("/jpa/{id}")
    public ResponseEntity<ProductoraDto> updateProductora(@PathVariable long id,
            @RequestBody ProductoraDto productoraDto) {
        try {
            ProductoraBo productoraBo = dtoToBo.productoraDtoToBo(productoraDto);
            productoraBo = productoraService.read(id);
            productoraBo.setNombre(productoraDto.getNombre());
            productoraBo.setAnhoFundacion(productoraDto.getAnhoFundacion());
            productoraBo.setId(productoraDto.getId());
            productoraService.update(productoraBo);
            ProductoraDto updateProductoraDto = boToDto.productoraBoToProductoraDto(productoraBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(updateProductoraDto);
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar una productora por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/jpa/{id}")
    public ResponseEntity<Void> deleteProductora(@PathVariable long id) {
        try {
            productoraService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar una productora por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Métodos para Criteria Builder
    
    @GetMapping("/cb/all")
    public ResponseEntity<List<ProductoraDto>> getProductorasCb() {
        try {
            List<ProductoraBo> productoras = productoraService.getProductorasCb();
            List<ProductoraDto> productoraDtos = productoras.stream().map(boToDto::productoraBoToProductoraDto)
                    .collect(Collectors.toList());
            log.info("Obteniendo lista de productoras");
            return ResponseEntity.ok(productoraDtos);
        } catch (ServiceException se) {
            log.error("Error al obtener la lista de productoras");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cb/{id}")
    public ResponseEntity<ProductoraDto> readProductoraCb(@PathVariable long id) {
        try {
            ProductoraBo productoraBo = productoraService.readCb(id);
            ProductoraDto productoraDto = boToDto.productoraBoToProductoraDto(productoraBo);
            return ResponseEntity.ok(productoraDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar buscar una productora por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @PostMapping("/cb/create")
    public ResponseEntity<ProductoraDto> createProductoraCb(@RequestBody ProductoraDto productoraDto) {
        try {
            ProductoraBo productoraBo = dtoToBo.productoraDtoToBo(productoraDto);
            productoraService.createCb(productoraBo);
            ProductoraDto savedProductoraDto = boToDto.productoraBoToProductoraDto(productoraBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProductoraDto);
        } catch (ServiceException se) {
            log.error("Error de servicio al intentar crear una nueva productora");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } 
    }

    @Transactional
    @PutMapping("/cb/{id}")
    public ResponseEntity<ProductoraDto> updateProductoraCb(@PathVariable long id,
            @RequestBody ProductoraDto productoraDto) {
        try {
            ProductoraBo productoraBo = dtoToBo.productoraDtoToBo(productoraDto);
            productoraBo = productoraService.readCb(id);
            productoraBo.setNombre(productoraDto.getNombre());
            productoraBo.setAnhoFundacion(productoraDto.getAnhoFundacion());
            productoraBo.setId(productoraDto.getId());
            productoraService.updateCb(productoraBo);
            ProductoraDto updateProductoraDto = boToDto.productoraBoToProductoraDto(productoraBo);
            return ResponseEntity.status(HttpStatus.CREATED).body(updateProductoraDto);
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar una productora por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @DeleteMapping("/cb/{id}")
    public ResponseEntity<Void> deleteProductoraCb(@PathVariable long id) {
        try {
            productoraService.deleteCb(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            log.error("Error de servicio al intentar buscar una productora por codigo: "+id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NullPointerException npe) {
            log.error("Error de lectura - codigo "+id+" no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}