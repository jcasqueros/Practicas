package com.viewnext.Practica4.backend.presentation.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.viewnext.Practica4.backend.business.bo.ActorBo;
import com.viewnext.Practica4.backend.business.bo.DirectorBo;
import com.viewnext.Practica4.backend.business.bo.PeliculaBo;
import com.viewnext.Practica4.backend.business.bo.ProductoraBo;
import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.business.model.Director;
import com.viewnext.Practica4.backend.business.model.Pelicula;
import com.viewnext.Practica4.backend.business.model.Productora;
import com.viewnext.Practica4.backend.business.services.PeliculaServices;
import com.viewnext.Practica4.backend.presentation.dto.ActorDto;
import com.viewnext.Practica4.backend.presentation.dto.DirectorDto;
import com.viewnext.Practica4.backend.presentation.dto.PeliculaDto;
import com.viewnext.Practica4.backend.presentation.dto.ProductoraDto;
import com.viewnext.Practica4.backend.presentation.controller.PeliculaController;
import com.viewnext.Practica4.util.converter.dto.BoToDto;
import com.viewnext.Practica4.util.converter.dto.DtoToBo;

@ExtendWith(MockitoExtension.class)
public class PeliculaControllerIntegrationTest {

    @InjectMocks
    private PeliculaController peliculaController;

    @Mock
    private PeliculaServices peliculaServices;

    @Mock
    private DtoToBo dtoToBo;

    @Mock
    private BoToDto boToDto;

    private PeliculaDto peliculaDto;
    private PeliculaBo peliculaBo;

    @BeforeEach
    public void init() {
        initMocks();
    }

    private void initMocks() {
        peliculaDto = new PeliculaDto();
        peliculaDto.setId(1L);
        peliculaDto.setTitulo("Pelicula 1");
        peliculaDto.setAnho(2000);
        peliculaDto.setIdDirector(new Director());
        peliculaDto.setIdProductora(new Productora());
        List<Actor> actoresDto = new ArrayList<>();
        actoresDto.add(new Actor());
        actoresDto.add(new Actor());
        peliculaDto.setActores(actoresDto);

        peliculaBo = new PeliculaBo();
        peliculaBo.setId(1L);
        peliculaBo.setTitulo("Pelicula 1");
        peliculaBo.setAnho(2000);
        peliculaBo.setIdDirector(new Director());
        peliculaBo.setIdProductora(new Productora());
        List<Actor> actoresBo = new ArrayList<>();
        actoresBo.add(new Actor());
        actoresBo.add(new Actor());
        peliculaBo.setActores(actoresBo);
    }

    @Test
    public void testGetPeliculas() throws Exception {
        when(peliculaServices.getAll()).thenReturn(Arrays.asList(peliculaBo));
        when(boToDto.peliculaBoToPeliculaDto(peliculaBo)).thenReturn(peliculaDto);

        ResponseEntity<List<PeliculaDto>> response = peliculaController.getPeliculas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(peliculaDto), response.getBody());
    }

    @Test
    public void testGetPeliculaByCode() throws Exception {
        when(peliculaServices.read(1L)).thenReturn(peliculaBo);
        when(boToDto.peliculaBoToPeliculaDto(peliculaBo)).thenReturn(peliculaDto);

        ResponseEntity<PeliculaDto> response = peliculaController.getPeliculaByCode(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(peliculaDto, response.getBody());
    }

    @Test
    public void testCreatePelicula() throws Exception {
        when(dtoToBo.peliculaDtoToBo(peliculaDto)).thenReturn(peliculaBo);
        when(peliculaServices.create(peliculaBo)).thenReturn(peliculaBo);
        when(boToDto.peliculaBoToPeliculaDto(peliculaBo)).thenReturn(peliculaDto);

        ResponseEntity<PeliculaDto> response = peliculaController.createPelicula(peliculaDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(peliculaDto, response.getBody());
    }

    @Test
    public void testUpdatePelicula() throws Exception {
        long id = 1L;
        when(dtoToBo.peliculaDtoToBo(peliculaDto)).thenReturn(peliculaBo);
        when(peliculaServices.read(id)).thenReturn(peliculaBo);
        when(peliculaServices.update(peliculaBo)).thenReturn(peliculaBo);
        when(boToDto.peliculaBoToPeliculaDto(peliculaBo)).thenReturn(peliculaDto);

        ResponseEntity<PeliculaDto> response = peliculaController.updatePelicula(id, peliculaDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(peliculaDto, response.getBody());
    }

    @Test
    public void testDeletePelicula() throws Exception {
        doNothing().when(peliculaServices).delete(1L);

        ResponseEntity<Void> response = peliculaController.deletePelicula(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(peliculaServices).delete(1L);
    }
    
    @Test
    public void testGetPeliculasCb() throws Exception {
        when(peliculaServices.getPeliculasCb()).thenReturn(Arrays.asList(peliculaBo));
        when(boToDto.peliculaBoToPeliculaDto(peliculaBo)).thenReturn(peliculaDto);

        ResponseEntity<List<PeliculaDto>> response = peliculaController.getPeliculaCb();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(peliculaDto), response.getBody());
    }

    @Test
    public void testCreatePeliculaCb() throws Exception {
        when(dtoToBo.peliculaDtoToBo(peliculaDto)).thenReturn(peliculaBo);
        when(peliculaServices.createCb(peliculaBo)).thenReturn(peliculaBo);
        when(boToDto.peliculaBoToPeliculaDto(peliculaBo)).thenReturn(peliculaDto);

        ResponseEntity<PeliculaDto> response = peliculaController.createPeliculaCb(peliculaDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(peliculaDto, response.getBody());
    }

    @Test
    public void testReadPeliculaCb() throws Exception {
        when(peliculaServices.readCb(1L)).thenReturn(peliculaBo);
        when(boToDto.peliculaBoToPeliculaDto(peliculaBo)).thenReturn(peliculaDto);

        ResponseEntity<PeliculaDto> response = peliculaController.readPeliculaCb(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(peliculaDto, response.getBody());
    }

    @Test
    public void testUpdatePeliculaCb() throws Exception {
        long id = 1L;
        when(dtoToBo.peliculaDtoToBo(peliculaDto)).thenReturn(peliculaBo);
        when(peliculaServices.readCb(id)).thenReturn(peliculaBo);
        when(peliculaServices.updateCb(peliculaBo)).thenReturn(peliculaBo);
        when(boToDto.peliculaBoToPeliculaDto(peliculaBo)).thenReturn(peliculaDto);

        ResponseEntity<PeliculaDto> response = peliculaController.updatePeliculaCb(id, peliculaDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(peliculaDto, response.getBody());
    }

    @Test
    public void testDeletePeliculaCb() throws Exception {
        doNothing().when(peliculaServices).deleteCb(1L);

        ResponseEntity<Void> response = peliculaController.deletePeliculaCb(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(peliculaServices).deleteCb(1L);
    }

}