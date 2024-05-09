package com.viewnext.Practica4.backend.presentation.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.viewnext.Practica4.backend.business.bo.ProductoraBo;
import com.viewnext.Practica4.backend.business.model.Productora;
import com.viewnext.Practica4.backend.business.services.ProductoraServices;
import com.viewnext.Practica4.backend.presentation.dto.ProductoraDto;
import com.viewnext.Practica4.backend.presentation.controller.ProductoraController;
import com.viewnext.Practica4.util.converter.dto.BoToDto;
import com.viewnext.Practica4.util.converter.dto.DtoToBo;

@ExtendWith(MockitoExtension.class)
public class ProductoraControllerIntegrationTest {

    @InjectMocks
    private ProductoraController productoraController;

    @Mock
    private ProductoraServices productoraServices;

    @Mock
    private DtoToBo dtoToBo;

    @Mock
    private BoToDto boToDto;

    private ProductoraDto productoraDto;
    private ProductoraBo productoraBo;

    @BeforeEach
    public void init() {
        initMocks();
    }

    private void initMocks() {
        productoraDto = new ProductoraDto();
        productoraDto.setId(1L);
        productoraDto.setNombre("Productora 1");
        productoraDto.setAnhoFundacion(1990);

        productoraBo = new ProductoraBo();
        productoraBo.setId(1L);
        productoraBo.setNombre("Productora 1");
        productoraBo.setAnhoFundacion(1990);
    }

    @Test
    public void testGetProductoras() throws Exception {
        when(productoraServices.getAll()).thenReturn(Arrays.asList(productoraBo));
        when(boToDto.productoraBoToProductoraDto(productoraBo)).thenReturn(productoraDto);

        ResponseEntity<List<ProductoraDto>> response = productoraController.getProductoras();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(productoraDto), response.getBody());
    }

    @Test
    public void testGetProductoraByCode() throws Exception {
        when(productoraServices.read(1L)).thenReturn(productoraBo);
        when(boToDto.productoraBoToProductoraDto(productoraBo)).thenReturn(productoraDto);

        ResponseEntity<ProductoraDto> response = productoraController.getProductoraByCode(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productoraDto, response.getBody());
    }

    @Test
    public void testCreateProductora() throws Exception {
        when(dtoToBo.productoraDtoToBo(productoraDto)).thenReturn(productoraBo);
        when(productoraServices.create(productoraBo)).thenReturn(productoraBo);
        when(boToDto.productoraBoToProductoraDto(productoraBo)).thenReturn(productoraDto);

        ResponseEntity<ProductoraDto> response = productoraController.createProductora(productoraDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productoraDto, response.getBody());
    }

    @Test
    public void testUpdateProductora() throws Exception {
        long id = 1L;
        when(dtoToBo.productoraDtoToBo(productoraDto)).thenReturn(productoraBo);
        when(productoraServices.read(id)).thenReturn(productoraBo);
        when(productoraServices.update(productoraBo)).thenReturn(productoraBo);
        when(boToDto.productoraBoToProductoraDto(productoraBo)).thenReturn(productoraDto);

        ResponseEntity<ProductoraDto> response = productoraController.updateProductora(id, productoraDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productoraDto, response.getBody());
    }

    @Test
    public void testDeleteProductora() throws Exception {
        doNothing().when(productoraServices).delete(1L);

        ResponseEntity<Void> response = productoraController.deleteProductora(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productoraServices).delete(1L);
    }

    @Test
    public void testGetProductorasCb() throws Exception {
        when(productoraServices.getProductorasCb()).thenReturn(Arrays.asList(productoraBo));
        when(boToDto.productoraBoToProductoraDto(productoraBo)).thenReturn(productoraDto);

        ResponseEntity<List<ProductoraDto>> response = productoraController.getProductorasCb();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(productoraDto), response.getBody());
    }

    @Test
    public void testCreateProductoraCb() throws Exception {
        when(dtoToBo.productoraDtoToBo(productoraDto)).thenReturn(productoraBo);
        when(productoraServices.createCb(productoraBo)).thenReturn(productoraBo);
        when(boToDto.productoraBoToProductoraDto(productoraBo)).thenReturn(productoraDto);

        ResponseEntity<ProductoraDto> response = productoraController.createProductoraCb(productoraDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productoraDto, response.getBody());
    }

    @Test
    public void testReadProductoraCb() throws Exception {
        when(productoraServices.readCb(1L)).thenReturn(productoraBo);
        when(boToDto.productoraBoToProductoraDto(productoraBo)).thenReturn(productoraDto);

        ResponseEntity<ProductoraDto> response = productoraController.readProductoraCb(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productoraDto, response.getBody());
    }

    @Test
    public void testUpdateProductoraCb() throws Exception {
        long id = 1L;
        when(dtoToBo.productoraDtoToBo(productoraDto)).thenReturn(productoraBo);
        when(productoraServices.readCb(id)).thenReturn(productoraBo);
        when(productoraServices.updateCb(productoraBo)).thenReturn(productoraBo);
        when(boToDto.productoraBoToProductoraDto(productoraBo)).thenReturn(productoraDto);

        ResponseEntity<ProductoraDto> response = productoraController.updateProductoraCb(id, productoraDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productoraDto, response.getBody());
    }

    @Test
    public void testDeleteProductoraCb() throws Exception {
        doNothing().when(productoraServices).deleteCb(1L);

        ResponseEntity<Void> response = productoraController.deleteProductoraCb(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productoraServices).deleteCb(1L);
    }
}
