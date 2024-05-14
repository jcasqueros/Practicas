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

import com.viewnext.Practica4.backend.business.bo.ActorBo;
import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.business.services.ActorServices;
import com.viewnext.Practica4.backend.presentation.dto.ActorDto;
import com.viewnext.Practica4.backend.presentation.controller.ActorController;
import com.viewnext.Practica4.util.converter.dto.BoToDto;
import com.viewnext.Practica4.util.converter.dto.DtoToBo;

@ExtendWith(MockitoExtension.class)
public class ActorControllerIntegrationTest {

    @InjectMocks
    private ActorController actorController;

    @Mock
    private ActorServices actorServices;

    @Mock
    private DtoToBo dtoToBo;

    @Mock
    private BoToDto boToDto;

    private ActorDto actorDto;
    private ActorBo actorBo;

    @BeforeEach
    public void init() {
        initMocks();
    }

    private void initMocks() {
        actorDto = new ActorDto();
        actorDto.setId(1L);
        actorDto.setNombre("Actor 1");
        actorDto.setEdad(30);
        actorDto.setNacionalidad("Española");

        actorBo = new ActorBo();
        actorBo.setId(1L);
        actorBo.setNombre("Actor 1");
        actorBo.setEdad(30);
        actorBo.setNacionalidad("Española");
    }

    @Test
    public void testGetActors() throws Exception {
        when(actorServices.getAll()).thenReturn(Arrays.asList(actorBo));
        when(boToDto.actorBoToActorDto(actorBo)).thenReturn(actorDto);

        ResponseEntity<List<ActorDto>> response = actorController.getActors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(actorDto), response.getBody());
    }

    @Test
    public void testGetActorByCode() throws Exception {
        when(actorServices.read(1L)).thenReturn(actorBo);
        when(boToDto.actorBoToActorDto(actorBo)).thenReturn(actorDto);

        ResponseEntity<ActorDto> response = actorController.getActorByCode(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(actorDto, response.getBody());
    }

    @Test
    public void testCreateActor() throws Exception {
        when(dtoToBo.actorDtoToBo(actorDto)).thenReturn(actorBo);
        when(actorServices.create(actorBo)).thenReturn(actorBo);
        when(boToDto.actorBoToActorDto(actorBo)).thenReturn(actorDto);

        ResponseEntity<ActorDto> response = actorController.createActor(actorDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(actorDto, response.getBody());
    }

    @Test
    public void testUpdateActor() throws Exception {
    	long id = 1L;
        when(dtoToBo.actorDtoToBo(actorDto)).thenReturn(actorBo);
        when(actorServices.read(id)).thenReturn(actorBo);
        when(actorServices.update(actorBo)).thenReturn(actorBo);
        when(boToDto.actorBoToActorDto(actorBo)).thenReturn(actorDto);

        ResponseEntity<ActorDto> response = actorController.updateActor(id, actorDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(actorDto, response.getBody());
    }

    @Test
    public void testDeleteActor() throws Exception {
        doNothing().when(actorServices).delete(1L);

        ResponseEntity<Void> response = actorController.deleteActor(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(actorServices).delete(1L);
    }

    @Test
    public void testGetActoresCb() throws Exception {
        when(actorServices.getActoresCb()).thenReturn(Arrays.asList(actorBo));
        when(boToDto.actorBoToActorDto(actorBo)).thenReturn(actorDto);

        ResponseEntity<List<ActorDto>> response = actorController.getActoresCb();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(actorDto), response.getBody());
    }

    @Test
    public void testCreateActorCb() throws Exception {
        when(dtoToBo.actorDtoToBo(actorDto)).thenReturn(actorBo);
        when(actorServices.createCb(actorBo)).thenReturn(actorBo);
        when(boToDto.actorBoToActorDto(actorBo)).thenReturn(actorDto);

        ResponseEntity<ActorDto> response = actorController.createActorCb(actorDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(actorDto, response.getBody());
    }

    @Test
    public void testReadActorCb() throws Exception {
        when(actorServices.readCb(1L)).thenReturn(actorBo);
        when(boToDto.actorBoToActorDto(actorBo)).thenReturn(actorDto);

        ResponseEntity<ActorDto> response = actorController.readActorCb(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(actorDto, response.getBody());
    }

    @Test
    public void testUpdateActorCb() throws Exception {
    	long id = 1L;
        when(dtoToBo.actorDtoToBo(actorDto)).thenReturn(actorBo);
        when(actorServices.readCb(id)).thenReturn(actorBo);
        when(actorServices.updateCb(actorBo)).thenReturn(actorBo);
        when(boToDto.actorBoToActorDto(actorBo)).thenReturn(actorDto);

        ResponseEntity<ActorDto> response = actorController.updateActorCb(id, actorDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(actorDto, response.getBody());
    }

    @Test
    public void testDeleteActorCb() throws Exception {
        doNothing().when(actorServices).deleteCb(1L);

        ResponseEntity<Void> response = actorController.deleteActorCb(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(actorServices).deleteCb(1L);
    }
}
