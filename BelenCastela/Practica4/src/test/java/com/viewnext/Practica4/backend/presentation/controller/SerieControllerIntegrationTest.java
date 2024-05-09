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

import com.viewnext.Practica4.backend.business.bo.SerieBo;
import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.business.model.Director;
import com.viewnext.Practica4.backend.business.model.Productora;
import com.viewnext.Practica4.backend.business.model.Serie;
import com.viewnext.Practica4.backend.business.services.SerieServices;
import com.viewnext.Practica4.backend.presentation.dto.SerieDto;
import com.viewnext.Practica4.backend.presentation.controller.SerieController;
import com.viewnext.Practica4.util.converter.dto.BoToDto;
import com.viewnext.Practica4.util.converter.dto.DtoToBo;

@ExtendWith(MockitoExtension.class)
public class SerieControllerIntegrationTest {

    @InjectMocks
    private SerieController serieController;

    @Mock
    private SerieServices serieServices;

    @Mock
    private DtoToBo dtoToBo;

    @Mock
    private BoToDto boToDto;

    private SerieDto serieDto;
    private SerieBo serieBo;

    @BeforeEach
    public void init() {
        initMocks();
    }

    private void initMocks() {
        serieDto = new SerieDto();
        serieDto.setId(1L);
        serieDto.setTitulo("Serie 1");
        serieDto.setAnho(2000);
        serieDto.setIdDirector(new Director());
        serieDto.setIdProductora(new Productora());
        List<Actor> actoresDto = new ArrayList<>();
        actoresDto.add(new Actor());
        actoresDto.add(new Actor());
        serieDto.setActores(actoresDto);

        serieBo = new SerieBo();
        serieBo.setId(1L);
        serieBo.setTitulo("Serie 1");
        serieBo.setAnho(2000);
        serieBo.setIdDirector(new Director());
        serieBo.setIdProductora(new Productora());
        List<Actor> actoresBo = new ArrayList<>();
        actoresBo.add(new Actor());
        actoresBo.add(new Actor());
        serieBo.setActores(actoresBo);
    }

    @Test
    public void testGetSeries() throws Exception {
        when(serieServices.getAll()).thenReturn(Arrays.asList(serieBo));
        when(boToDto.serieBoToSerieDto(serieBo)).thenReturn(serieDto);

        ResponseEntity<List<SerieDto>> response = serieController.getSeries();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(serieDto), response.getBody());
    }

    @Test
    public void testGetSerieByCode() throws Exception {
        when(serieServices.read(1L)).thenReturn(serieBo);
        when(boToDto.serieBoToSerieDto(serieBo)).thenReturn(serieDto);

        ResponseEntity<SerieDto> response = serieController.getSerieByCode(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(serieDto, response.getBody());
    }

    @Test
    public void testCreateSerie() throws Exception {
        when(dtoToBo.serieDtoToBo(serieDto)).thenReturn(serieBo);
        when(serieServices.create(serieBo)).thenReturn(serieBo);
        when(boToDto.serieBoToSerieDto(serieBo)).thenReturn(serieDto);

        ResponseEntity<SerieDto> response = serieController.createSerie(serieDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(serieDto, response.getBody());
    }

    @Test
    public void testUpdateSerie() throws Exception {
        long id = 1L;
        when(dtoToBo.serieDtoToBo(serieDto)).thenReturn(serieBo);
        when(serieServices.read(id)).thenReturn(serieBo);
        when(serieServices.update(serieBo)).thenReturn(serieBo);
        when(boToDto.serieBoToSerieDto(serieBo)).thenReturn(serieDto);

        ResponseEntity<SerieDto> response = serieController.updateSerie(id, serieDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(serieDto, response.getBody());
    }

    @Test
    public void testDeleteSerie() throws Exception {
        doNothing().when(serieServices).delete(1L);

        ResponseEntity<Void> response = serieController.deleteSerie(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(serieServices).delete(1L);
    }

    @Test
    public void testGetSeriesCb() throws Exception {
        when(serieServices.getSeriesCb()).thenReturn(Arrays.asList(serieBo));
        when(boToDto.serieBoToSerieDto(serieBo)).thenReturn(serieDto);

        ResponseEntity<List<SerieDto>> response = serieController.getSerieCb();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(serieDto), response.getBody());
    }

    @Test
    public void testCreateSerieCb() throws Exception {
        when(dtoToBo.serieDtoToBo(serieDto)).thenReturn(serieBo);
        when(serieServices.createCb(serieBo)).thenReturn(serieBo);
        when(boToDto.serieBoToSerieDto(serieBo)).thenReturn(serieDto);

        ResponseEntity<SerieDto> response = serieController.createSerieCb(serieDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(serieDto, response.getBody());
    }

    @Test
    public void testReadSerieCb() throws Exception {
        when(serieServices.readCb(1L)).thenReturn(serieBo);
        when(boToDto.serieBoToSerieDto(serieBo)).thenReturn(serieDto);

        ResponseEntity<SerieDto> response = serieController.readSerieCb(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(serieDto, response.getBody());
    }

    @Test
    public void testUpdateSerieCb() throws Exception {
        long id = 1L;
        when(dtoToBo.serieDtoToBo(serieDto)).thenReturn(serieBo);
        when(serieServices.readCb(id)).thenReturn(serieBo);
        when(serieServices.updateCb(serieBo)).thenReturn(serieBo);
        when(boToDto.serieBoToSerieDto(serieBo)).thenReturn(serieDto);

        ResponseEntity<SerieDto> response = serieController.updateSerieCb(id, serieDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(serieDto, response.getBody());
    }

    @Test
    public void testDeleteSerieCb() throws Exception {
        doNothing().when(serieServices).deleteCb(1L);

        ResponseEntity<Void> response = serieController.deleteSerieCb(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(serieServices).deleteCb(1L);
    }
}
