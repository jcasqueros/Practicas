package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import com.example.demo.presentation.controllers.ActorController;
import com.example.demo.presentation.dto.ActorDto;
import com.example.demo.servcice.ActorService;
import com.example.demo.servcice.bo.ActorBo;

public class ActorControllerTest {

    @InjectMocks
    private ActorController actorController;

    @Mock
    private ActorService actorService;

    @Mock
    private BoToDTo boToDto;

    @Mock
    private DtoToBo dtoToBo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        // Arrange
        List<ActorBo> actorBoList = new ArrayList<>();
        ActorBo actorBo = new ActorBo();
        actorBoList.add(actorBo);
        Page<ActorBo> actorBoPage = new PageImpl<>(actorBoList);

        List<ActorDto> actorDtoList = new ArrayList<>();
        ActorDto actorDto = new ActorDto();
        actorDtoList.add(actorDto);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        when(actorService.getAll(pageable)).thenReturn(actorBoPage);
        when(boToDto.boToActorDto(any(ActorBo.class))).thenReturn(actorDto);

        // Act
        ResponseEntity<List<ActorDto>> response = actorController.getAll(true, 0, 10, "name");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(actorService, times(1)).getAll(pageable);
    }

    @Test
    public void testGetById() throws NotFoundException {
        // Arrange
        ActorDto actorDto = new ActorDto();
        when(actorService.getById(1L)).thenReturn(new ActorBo());
        when(boToDto.boToActorDto(any())).thenReturn(actorDto);

        // Act
        ResponseEntity<ActorDto> response = actorController.getById(1L, true);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(actorService, times(1)).getById(1L);
    }

    @Test
    public void testSave() throws AlreadyExistsExeption, NotFoundException {
        // Arrange
        ActorDto actorDto = new ActorDto();
        ActorBo actorBo = new ActorBo();
        when(actorService.create(any())).thenReturn(actorBo);
        when(boToDto.boToActorDto(any())).thenReturn(actorDto);
        when(dtoToBo.dtoToActorBo(any())).thenReturn(actorBo);

        // Act
        ResponseEntity<ActorDto> response = actorController.save(actorDto, true);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(actorService, times(1)).create(any());
    }

    @Test
    public void testDeleteById() throws NotFoundException {
        // Act
        ResponseEntity<Void> response = actorController.deleteByid(1L, true);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(actorService, times(1)).deleteById(1L);
    }

    @Test
    public void testGetNombreEdad() throws PresentationException {
        // Arrange
        List<ActorBo> actorBoList = new ArrayList<>();
        ActorBo actorBo = new ActorBo();
        actorBoList.add(actorBo);

        List<ActorDto> actorDtoList = new ArrayList<>();
        ActorDto actorDto = new ActorDto();
        actorDtoList.add(actorDto);

        when(actorService.findByNameAndAge(anyString(), anyInt())).thenReturn(actorBoList);
        when(boToDto.boToActorDto(any())).thenReturn(actorDto);

        // Act
        ResponseEntity<List<ActorDto>> response = actorController.getNombreEdad(true, "John", 30);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(actorService, times(1)).findByNameAndAge("John", 30);
    }
}
