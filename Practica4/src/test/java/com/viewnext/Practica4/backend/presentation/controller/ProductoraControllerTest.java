package com.viewnext.Practica4.backend.presentation.controller;

import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.viewnext.Practica4.backend.business.bo.ProductoraBo;
import com.viewnext.Practica4.backend.business.services.ProductoraServices;
import com.viewnext.Practica4.backend.presentation.controller.ProductoraController;
import com.viewnext.Practica4.backend.presentation.dto.ProductoraDto;
import com.viewnext.Practica4.util.converter.dto.BoToDto;
import com.viewnext.Practica4.util.converter.dto.DtoToBo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductoraControllerTest {

    @InjectMocks
    private ProductoraController productoraController;

    @Mock
    private ProductoraServices productoraServices;

    @Mock
    private DtoToBo dtoToBo;

    @Mock
    private BoToDto boToDto;

    private ProductoraBo productoraBo1;
    private ProductoraBo productoraBo2;
    private ProductoraDto productoraDto1;
    private ProductoraDto productoraDto2;

    @BeforeEach
    public void init() {
        initMocks();
    }

    private void initMocks() {
        productoraBo1 = new ProductoraBo();
        productoraBo1.setId(1L);
        productoraBo1.setNombre("Productora 1");
        productoraBo1.setAnhoFundacion(1990);

        productoraBo2 = new ProductoraBo();
        productoraBo2.setId(2L);
        productoraBo2.setNombre("Productora 2");
        productoraBo2.setAnhoFundacion(2000);

        productoraDto1 = new ProductoraDto();
        productoraDto1.setId(1L);
        productoraDto1.setNombre("Productora 1");
        productoraDto1.setAnhoFundacion(1990);

        productoraDto2 = new ProductoraDto();
        productoraDto2.setId(2L);
        productoraDto2.setNombre("Productora 2");
        productoraDto2.setAnhoFundacion(2000);
    }

    @Test
    public void testGetProductoras() throws ServiceException {
        // Given
        List<ProductoraBo> productoraBoList = new ArrayList<>();
        productoraBoList.add(productoraBo1);
        productoraBoList.add(productoraBo2);
        when(productoraServices.getAll()).thenReturn(productoraBoList);

        // When
        ResponseEntity<List<ProductoraDto>> response = productoraController.getProductoras();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetProductoraByCode() throws ServiceException {
        // Given
        long id = 1L;
        when(productoraServices.read(id)).thenReturn(productoraBo1);

        // When
        ResponseEntity<ProductoraDto> response = productoraController.getProductoraByCode(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateProductora() throws ServiceException {
        // Given
        when(dtoToBo.productoraDtoToBo(productoraDto1)).thenReturn(productoraBo1);
        when(productoraServices.create(productoraBo1)).thenReturn(productoraBo1);

        // When
        ResponseEntity<ProductoraDto> response = productoraController.createProductora(productoraDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateProductora() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.productoraDtoToBo(productoraDto1)).thenReturn(productoraBo1);
        when(productoraServices.read(id)).thenReturn(productoraBo1);
        when(productoraServices.update(productoraBo1)).thenReturn(productoraBo1);

        // When
        ResponseEntity<ProductoraDto> response = productoraController.updateProductora(id, productoraDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    // Criteria Builder tests

    @Test
    public void testGetProductorasCb() throws ServiceException {
        // Given
        List<ProductoraBo> productoraBoList = new ArrayList<>();
        productoraBoList.add(productoraBo1);
        productoraBoList.add(productoraBo2);
        when(productoraServices.getProductorasCb()).thenReturn(productoraBoList);

        // When
        ResponseEntity<List<ProductoraDto>> response = productoraController.getProductorasCb();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testReadProductoraCb() throws ServiceException {
        // Given
        long id = 1L;
        when(productoraServices.readCb(id)).thenReturn(productoraBo1);

        // When
        ResponseEntity<ProductoraDto> response = productoraController.readProductoraCb(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateProductoraCb() throws ServiceException {
        // Given
        when(dtoToBo.productoraDtoToBo(productoraDto1)).thenReturn(productoraBo1);
        when(productoraServices.createCb(productoraBo1)).thenReturn(productoraBo1);

        // When
        ResponseEntity<ProductoraDto> response = productoraController.createProductoraCb(productoraDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateProductoraCb() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.productoraDtoToBo(productoraDto1)).thenReturn(productoraBo1);
        when(productoraServices.readCb(id)).thenReturn(productoraBo1);
        when(productoraServices.updateCb(productoraBo1)).thenReturn(productoraBo1);

        // When
        ResponseEntity<ProductoraDto> response = productoraController.updateProductoraCb(id, productoraDto1);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testDeleteProductora_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        doThrow(new ServiceException("Error deleting productora")).when(productoraServices).delete(id);

        // When
        ResponseEntity<Void> response = productoraController.deleteProductora(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteProductoraCb_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        doThrow(new ServiceException("Error deleting productora")).when(productoraServices).deleteCb(id);

        // When
        ResponseEntity<Void> response = productoraController.deleteProductoraCb(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetProductoras_ThrowsServiceException() throws ServiceException {
        // Given
        when(productoraServices.getAll()).thenThrow(new ServiceException("Error getting productoras"));

        // When
        ResponseEntity<List<ProductoraDto>> response = productoraController.getProductoras();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetProductoraByCode_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(productoraServices.read(id)).thenThrow(new ServiceException("Error getting productora"));

        // When
        ResponseEntity<ProductoraDto> response = productoraController.getProductoraByCode(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateProductora_ThrowsServiceException() throws ServiceException {
        // Given
        when(dtoToBo.productoraDtoToBo(productoraDto1)).thenReturn(productoraBo1);
        when(productoraServices.create(productoraBo1)).thenThrow(new ServiceException("Error creating productora"));

        // When
        ResponseEntity<ProductoraDto> response = productoraController.createProductora(productoraDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateProductora_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.productoraDtoToBo(productoraDto1)).thenReturn(productoraBo1);
        when(productoraServices.read(id)).thenReturn(productoraBo1);
        when(productoraServices.update(productoraBo1)).thenThrow(new ServiceException("Error updating productora"));

        // When
        ResponseEntity<ProductoraDto> response = productoraController.updateProductora(id, productoraDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetProductorasCb_ThrowsServiceException() throws ServiceException {
        // Given
        when(productoraServices.getProductorasCb()).thenThrow(new ServiceException("Error getting productoras"));

        // When
        ResponseEntity<List<ProductoraDto>> response = productoraController.getProductorasCb();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testReadProductoraCb_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(productoraServices.readCb(id)).thenThrow(new ServiceException("Error getting productora"));

        // When
        ResponseEntity<ProductoraDto> response = productoraController.readProductoraCb(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateProductoraCb_ThrowsServiceException() throws ServiceException {
        // Given
        when(dtoToBo.productoraDtoToBo(productoraDto1)).thenReturn(productoraBo1);
        when(productoraServices.createCb(productoraBo1)).thenThrow(new ServiceException("Error creating productora"));

        // When
        ResponseEntity<ProductoraDto> response = productoraController.createProductoraCb(productoraDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateProductoraCb_ThrowsServiceException() throws ServiceException {
        // Given
        long id = 1L;
        when(dtoToBo.productoraDtoToBo(productoraDto1)).thenReturn(productoraBo1);
        when(productoraServices.readCb(id)).thenReturn(productoraBo1);
        when(productoraServices.updateCb(productoraBo1)).thenThrow(new ServiceException("Error updating productora"));

        // When
        ResponseEntity<ProductoraDto> response = productoraController.updateProductoraCb(id, productoraDto1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetProductoras_EmptyList() {
        // Given
        when(productoraServices.getAll()).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<ProductoraDto>> response = productoraController.getProductoras();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void testGetProductorasCb_EmptyList() {
        // Given
        when(productoraServices.getProductorasCb()).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<ProductoraDto>> response = productoraController.getProductorasCb();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
    
    @Test
    public void testDeleteProductora() {
        // Arrange
        // Act
        productoraController.deleteProductora(1L);
        // Assert
    }
     
    @Test
    public void testDeleteProductoraCb() {
        productoraController.deleteProductoraCb(2L);
    }
     
    @Test
    public void testDeleteProductora_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        doThrow(new NullPointerException("Error deleting productora")).when(productoraServices).delete(id);
     
        // When
        ResponseEntity<Void> response = productoraController.deleteProductora(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testDeleteProductoraCb_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        doThrow(new NullPointerException("Error deleting productora")).when(productoraServices).deleteCb(id);
     
        // When
        ResponseEntity<Void> response = productoraController.deleteProductoraCb(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testGetProductoraByCode_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(productoraServices.read(id)).thenThrow(new NullPointerException("Error getting productora"));
     
        // When
        ResponseEntity<ProductoraDto> response = productoraController.getProductoraByCode(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testUpdateProductora_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(dtoToBo.productoraDtoToBo(productoraDto1)).thenReturn(productoraBo1);
        when(productoraServices.read(id)).thenReturn(productoraBo1);
        when(productoraServices.update(productoraBo1)).thenThrow(new NullPointerException("Error updating productora"));
     
        // When
        ResponseEntity<ProductoraDto> response = productoraController.updateProductora(id, productoraDto1);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testReadProductoraCb_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(productoraServices.readCb(id)).thenThrow(new NullPointerException("Error getting productora"));
     
        // When
        ResponseEntity<ProductoraDto> response = productoraController.readProductoraCb(id);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
     
    @Test
    public void testUpdateProductoraCb_ThrowsNullPointerException() throws NullPointerException {
        // Given
        long id = 1L;
        when(dtoToBo.productoraDtoToBo(productoraDto1)).thenReturn(productoraBo1);
        when(productoraServices.readCb(id)).thenReturn(productoraBo1);
        when(productoraServices.updateCb(productoraBo1)).thenThrow(new NullPointerException("Error updating productora"));
     
        // When
        ResponseEntity<ProductoraDto> response = productoraController.updateProductoraCb(id, productoraDto1);
     
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
