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
import com.example.demo.presentation.controllers.DirectorController;
import com.example.demo.presentation.dto.DirectorDto;
import com.example.demo.servcice.DirectorService;
import com.example.demo.servcice.bo.DirectorBo;

public class DirectorControllerTest {

    @InjectMocks
    private DirectorController directorController;

    @Mock
    private DirectorService directorService;

    @Mock
    private BoToDTo boToDto;

    @Mock
    private DtoToBo dtoToBo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() throws PresentationException {
        // Arrange
        List<DirectorBo> directorBoList = new ArrayList<>();
        DirectorBo directorBo = new DirectorBo();
        directorBoList.add(directorBo);
        Page<DirectorBo> directorBoPage = new PageImpl<>(directorBoList);

        List<DirectorDto> directorDtoList = new ArrayList<>();
        DirectorDto directorDto = new DirectorDto();
        directorDtoList.add(directorDto);

        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());

        when(directorService.getAll(pageable)).thenReturn(directorBoPage);
        when(boToDto.boToDirectorDto(any(DirectorBo.class))).thenReturn(directorDto);

        // Act
        ResponseEntity<List<DirectorDto>> response = directorController.getAll(true, 0, 5, "name", "asc");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(directorService, times(1)).getAll(pageable);
    }

    @Test
    public void testSave() throws AlreadyExistsExeption, NotFoundException {
        // Arrange
        DirectorDto directorDto = new DirectorDto();
        DirectorBo directorBo = new DirectorBo();
        when(directorService.create(any())).thenReturn(directorBo);
        when(boToDto.boToDirectorDto(any())).thenReturn(directorDto);
        when(dtoToBo.dtoToDirectorBo(any())).thenReturn(directorBo);

        // Act
        ResponseEntity<DirectorDto> response = directorController.save(directorDto, true);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(directorService, times(1)).create(any());
    }

    @Test
    public void testDeleteById() throws NotFoundException {
        // Act
        ResponseEntity<Void> response = directorController.deleteByid(1L, true);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(directorService, times(1)).deleteById(1L);
    }

    @Test
    public void testFindAllFilter() throws PresentationException, ServiceException {
        // Arrange
        List<DirectorBo> directorBoList = new ArrayList<>();
        DirectorBo directorBo = new DirectorBo();
        directorBoList.add(directorBo);
        Page<DirectorBo> directorBoPage = new PageImpl<>(directorBoList);

        List<DirectorDto> directorDtoList = new ArrayList<>();
        DirectorDto directorDto = new DirectorDto();
        directorDtoList.add(directorDto);

        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());

        when(directorService.findAllCriteriaFilter(any(Pageable.class), anyList(), anyList(), anyList())).thenReturn(directorBoPage);
        when(boToDto.boToDirectorDto(any(DirectorBo.class))).thenReturn(directorDto);

        // Act
        ResponseEntity<List<DirectorDto>> response = directorController.findAllFilter(null, null, null, true, "name", "asc");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(directorService, times(1)).findAllCriteriaFilter(any(Pageable.class), anyList(), anyList(), anyList());
    }
}
