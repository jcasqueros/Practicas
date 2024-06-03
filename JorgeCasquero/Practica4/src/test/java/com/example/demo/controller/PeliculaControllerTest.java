package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.converters.BoToDTo;
import com.example.demo.converters.DtoToBo;
import com.example.demo.exception.AlreadyExistsExeption;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.PresentationException;
import com.example.demo.presentation.controllers.PeliculaController;
import com.example.demo.presentation.dto.PeliculaDto;
import com.example.demo.servcice.PeliculaService;
import com.example.demo.servcice.bo.PeliculaBo;

public class PeliculaControllerTest {

    @InjectMocks
    private PeliculaController peliculaController;

    @Mock
    private PeliculaService peliculaService;

    @Mock
    private BoToDTo boToDto;

    @Mock
    private DtoToBo dtoToBo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() throws PresentationException, ServiceException {
        // Arrange
        List<PeliculaBo> peliculaBoList = new ArrayList<>();
        PeliculaBo peliculaBo = new PeliculaBo();
        peliculaBoList.add(peliculaBo);
        Page<PeliculaBo> peliculaBoPage = new PageImpl<>(peliculaBoList);

        List<PeliculaDto> peliculaDtoList = new ArrayList<>();
        PeliculaDto peliculaDto = new PeliculaDto();
        peliculaDtoList.add(peliculaDto);

        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").ascending());

        when(peliculaService.getAll(pageable)).thenReturn(peliculaBoPage);
        when(boToDto.boToPeliculaDto(any(PeliculaBo.class))).thenReturn(peliculaDto);

        // Act
        ResponseEntity<List<PeliculaDto>> response = peliculaController.getAll(false, 0, 5, "id");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(peliculaService, times(1)).getAll(pageable);
    }

    @Test
    public void testFindAllFilter() throws PresentationException, ServiceException {
        // Arrange
        List<PeliculaBo> peliculaBoList = new ArrayList<>();
        PeliculaBo peliculaBo = new PeliculaBo();
        peliculaBoList.add(peliculaBo);
        Page<PeliculaBo> peliculaBoPage = new PageImpl<>(peliculaBoList);

        List<PeliculaDto> peliculaDtoList = new ArrayList<>();
        PeliculaDto peliculaDto = new PeliculaDto();
        peliculaDtoList.add(peliculaDto);

        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").ascending());

        when(peliculaService.findAllCriteriaFilter(any(Pageable.class), anyList(), anyList(), anyList(), anyList(), anyList())).thenReturn(peliculaBoPage);
        when(boToDto.boToPeliculaDto(any(PeliculaBo.class))).thenReturn(peliculaDto);

        // Act
        ResponseEntity<List<PeliculaDto>> response = peliculaController.findAllFilter(null, null, null, null, null, true, "id", "asc");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(peliculaService, times(1)).findAllCriteriaFilter(any(Pageable.class), anyList(), anyList(), anyList(), anyList(), anyList());
    }

    @Test
    public void testGetById() throws NotFoundException {
        // Arrange
        PeliculaDto peliculaDto = new PeliculaDto();
        when(peliculaService.getById(1L)).thenReturn(new PeliculaBo());
        when(boToDto.boToPeliculaDto(any())).thenReturn(peliculaDto);

        // Act
        ResponseEntity<PeliculaDto> response = peliculaController.getById(1L, true);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(peliculaService, times(1)).getById(1L);
    }

    @Test
    public void testSave() throws AlreadyExistsExeption, NotFoundException {
        // Arrange
        PeliculaDto peliculaDto = new PeliculaDto();
        PeliculaBo peliculaBo = new PeliculaBo();
        when(peliculaService.create(any())).thenReturn(peliculaBo);
        when(boToDto.boToPeliculaDto(any())).thenReturn(peliculaDto);
        when(dtoToBo.dtoToPeliculaBo(any())).thenReturn(peliculaBo);

        // Act
        ResponseEntity<PeliculaDto> response = peliculaController.save(peliculaDto, true);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(peliculaService, times(1)).create(any());
    }

    @Test
    public void testDeleteById() throws NotFoundException {
        // Act
        ResponseEntity<Object> response = peliculaController.deleteByid(1L, true);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(peliculaService, times(1)).deleteById(1L);
    }
}
