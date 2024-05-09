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

import com.viewnext.Practica4.backend.business.bo.DirectorBo;
import com.viewnext.Practica4.backend.business.model.Director;
import com.viewnext.Practica4.backend.business.services.DirectorServices;
import com.viewnext.Practica4.backend.presentation.dto.DirectorDto;
import com.viewnext.Practica4.backend.presentation.controller.DirectorController;
import com.viewnext.Practica4.util.converter.dto.BoToDto;
import com.viewnext.Practica4.util.converter.dto.DtoToBo;

@ExtendWith(MockitoExtension.class)
public class DirectorControllerIntegrationTest {

    @InjectMocks
    private DirectorController directorController;

    @Mock
    private DirectorServices directorServices;

    @Mock
    private DtoToBo dtoToBo;

    @Mock
    private BoToDto boToDto;

    private DirectorDto directorDto;
    private DirectorBo directorBo;

    @BeforeEach
    public void init() {
        initMocks();
    }

    private void initMocks() {
        directorDto = new DirectorDto();
        directorDto.setId(1L);
        directorDto.setNombre("Director 1");
        directorDto.setEdad(30);
        directorDto.setNacionalidad("Española");

        directorBo = new DirectorBo();
        directorBo.setId(1L);
        directorBo.setNombre("Director 1");
        directorBo.setEdad(30);
        directorBo.setNacionalidad("Española");
    }

    @Test
    public void testGetDirectors() throws Exception {
        when(directorServices.getAll()).thenReturn(Arrays.asList(directorBo));
        when(boToDto.directorBoToDirectorDto(directorBo)).thenReturn(directorDto);

        ResponseEntity<List<DirectorDto>> response = directorController.getDirectores();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(directorDto), response.getBody());
    }

    @Test
    public void testGetDirectorByCode() throws Exception {
        when(directorServices.read(1L)).thenReturn(directorBo);
        when(boToDto.directorBoToDirectorDto(directorBo)).thenReturn(directorDto);

        ResponseEntity<DirectorDto> response = directorController.getDirectorByCode(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(directorDto, response.getBody());
    }

    @Test
    public void testCreateDirector() throws Exception {
        when(dtoToBo.directorDtoToBo(directorDto)).thenReturn(directorBo);
        when(directorServices.create(directorBo)).thenReturn(directorBo);
        when(boToDto.directorBoToDirectorDto(directorBo)).thenReturn(directorDto);

        ResponseEntity<DirectorDto> response = directorController.createDirector(directorDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(directorDto, response.getBody());
    }

    @Test
    public void testUpdateDirector() throws Exception {
        long id = 1L;
        when(dtoToBo.directorDtoToBo(directorDto)).thenReturn(directorBo);
        when(directorServices.read(id)).thenReturn(directorBo);
        when(directorServices.update(directorBo)).thenReturn(directorBo);
        when(boToDto.directorBoToDirectorDto(directorBo)).thenReturn(directorDto);

        ResponseEntity<DirectorDto> response = directorController.updateDirector(id, directorDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(directorDto, response.getBody());
    }

    @Test
    public void testDeleteDirector() throws Exception {
        doNothing().when(directorServices).delete(1L);

        ResponseEntity<Void> response = directorController.deleteDirector(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(directorServices).delete(1L);
    }

    @Test
    public void testGetDirectorsCb() throws Exception {
        when(directorServices.getDirectoresCb()).thenReturn(Arrays.asList(directorBo));
        when(boToDto.directorBoToDirectorDto(directorBo)).thenReturn(directorDto);

        ResponseEntity<List<DirectorDto>> response = directorController.getDirectoresCb();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(directorDto), response.getBody());
    }

    @Test
    public void testCreateDirectorCb() throws Exception {
        when(dtoToBo.directorDtoToBo(directorDto)).thenReturn(directorBo);
        when(directorServices.createCb(directorBo)).thenReturn(directorBo);
        when(boToDto.directorBoToDirectorDto(directorBo)).thenReturn(directorDto);

        ResponseEntity<DirectorDto> response = directorController.createDirectorCb(directorDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(directorDto, response.getBody());
    }

    @Test
    public void testReadDirectorCb() throws Exception {
        when(directorServices.readCb(1L)).thenReturn(directorBo);
        when(boToDto.directorBoToDirectorDto(directorBo)).thenReturn(directorDto);

        ResponseEntity<DirectorDto> response = directorController.readDirectorCb(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(directorDto, response.getBody());
    }

    @Test
    public void testUpdateDirectorCb() throws Exception {
        long id = 1L;
        when(dtoToBo.directorDtoToBo(directorDto)).thenReturn(directorBo);
        when(directorServices.readCb(id)).thenReturn(directorBo);
        when(directorServices.updateCb(directorBo)).thenReturn(directorBo);
        when(boToDto.directorBoToDirectorDto(directorBo)).thenReturn(directorDto);

        ResponseEntity<DirectorDto> response = directorController.updateDirectorCb(id, directorDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(directorDto, response.getBody());
    }

    @Test
    public void testDeleteDirectorCb() throws Exception {
        doNothing().when(directorServices).deleteCb(1L);

        ResponseEntity<Void> response = directorController.deleteDirectorCb(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(directorServices).deleteCb(1L);
    }
}
