package com.pracs.films.presentation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pracs.films.bussiness.bo.ActorBO;
import com.pracs.films.bussiness.bo.DirectorBO;
import com.pracs.films.bussiness.bo.ProducerBO;
import com.pracs.films.bussiness.bo.SerieBO;
import com.pracs.films.bussiness.services.SerieService;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.repositories.jpa.ActorRepository;
import com.pracs.films.persistence.repositories.jpa.DirectorRepository;
import com.pracs.films.persistence.repositories.jpa.FilmRepository;
import com.pracs.films.persistence.repositories.jpa.ProducerRepository;
import com.pracs.films.presentation.converters.BoToDtoConverter;
import com.pracs.films.presentation.converters.DtoToBoConverter;
import com.pracs.films.presentation.dto.*;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ComponentScan(basePackages = "com.pracs.films")
class SerieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoToDtoConverter boToDtoConverter;

    @MockBean
    private DtoToBoConverter dtoToBoConverter;

    @MockBean
    private SerieService serieService;

    @MockBean
    private DirectorRepository directorRepository;

    @MockBean
    private ProducerRepository producerRepository;

    @MockBean
    private ActorRepository actorRepository;

    @MockBean
    private FilmRepository filmRepository;

    @MockBean
    private EntityManagerFactory entityManager;

    private ActorBO actorBO;

    private ActorDtoIn actorDtoIn;

    private ActorDtoInUpdate actorDtoInUpdate;

    private ActorDtoOut actorDtoOut;

    private DirectorBO directorBO;

    private DirectorDtoIn directorDtoIn;

    private DirectorDtoInUpdate directorDtoInUpdate;

    private DirectorDtoOut directorDtoOut;

    private ProducerBO producerBO;

    private ProducerDtoIn producerDtoIn;

    private ProducerDtoInUpdate producerDtoInUpdate;

    private ProducerDtoOut producerDtoOut;

    private SerieBO serieBO;

    private SerieDtoIn serieDtoIn;

    private SerieDtoInUpdate serieDtoInUpdate;

    private SerieDtoOut serieDtoOut;

    @BeforeEach
    void setup() {

        actorBO = new ActorBO();
        actorBO.setId(1L);
        actorBO.setName("prueba");
        actorBO.setAge(25);
        actorBO.setNationality("Spain");
        List<ActorBO> actorsBO = new ArrayList<>();
        actorsBO.add(actorBO);

        actorDtoIn = new ActorDtoIn();
        actorDtoIn.setName("prueba");
        actorDtoIn.setAge(25);
        actorDtoIn.setNationality("Spain");

        actorDtoInUpdate = new ActorDtoInUpdate();
        actorDtoInUpdate.setId(1L);
        actorDtoInUpdate.setName("prueba");
        actorDtoInUpdate.setAge(25);
        actorDtoInUpdate.setNationality("Spain");

        actorDtoOut = new ActorDtoOut();
        actorDtoOut.setId(1L);
        actorDtoOut.setName("prueba");
        actorDtoOut.setAge(25);
        actorDtoOut.setNationality("Spain");
        List<ActorDtoOut> actorsDtoOut = new ArrayList<>();
        actorsDtoOut.add(actorDtoOut);

        directorBO = new DirectorBO();
        directorBO.setId(1L);
        directorBO.setName("prueba");
        directorBO.setAge(25);
        directorBO.setNationality("Spain");

        directorDtoIn = new DirectorDtoIn();
        directorDtoIn.setName("prueba");
        directorDtoIn.setAge(25);
        directorDtoIn.setNationality("Spain");

        directorDtoInUpdate = new DirectorDtoInUpdate();
        directorDtoInUpdate.setId(1L);
        directorDtoInUpdate.setName("prueba");
        directorDtoInUpdate.setAge(25);
        directorDtoInUpdate.setNationality("Spain");

        directorDtoOut = new DirectorDtoOut();
        directorDtoOut.setId(1L);
        directorDtoOut.setName("prueba");
        directorDtoOut.setAge(25);
        directorDtoOut.setNationality("Spain");

        producerBO = new ProducerBO();
        producerBO.setId(1L);
        producerBO.setName("prueba");
        producerBO.setDebut(2020);

        producerDtoIn = new ProducerDtoIn();
        producerDtoIn.setName("prueba");
        producerDtoIn.setDebut(2020);

        producerDtoInUpdate = new ProducerDtoInUpdate();
        producerDtoInUpdate.setId(1L);
        producerDtoInUpdate.setName("prueba");
        producerDtoInUpdate.setDebut(2020);

        producerDtoOut = new ProducerDtoOut();
        producerDtoOut.setId(1L);
        producerDtoOut.setName("prueba");
        producerDtoOut.setDebut(2020);

        serieBO = new SerieBO();
        serieBO.setId(1L);
        serieBO.setTitle("prueba");
        serieBO.setDebut(2020);
        serieBO.setProducer(producerBO);
        serieBO.setDirector(directorBO);
        serieBO.setActors(actorsBO);

        serieDtoIn = new SerieDtoIn();
        serieDtoIn.setTitle("prueba");
        serieDtoIn.setDebut(2020);
        serieDtoIn.setProducer(producerDtoOut);
        serieDtoIn.setDirector(directorDtoOut);
        serieDtoIn.setActors(actorsDtoOut);

        serieDtoInUpdate = new SerieDtoInUpdate();
        serieDtoInUpdate.setId(1L);
        serieDtoInUpdate.setTitle("prueba");
        serieDtoInUpdate.setDebut(2020);
        serieDtoInUpdate.setProducer(producerDtoOut);
        serieDtoInUpdate.setDirector(directorDtoOut);
        serieDtoInUpdate.setActors(actorsDtoOut);

        serieDtoOut = new SerieDtoOut();
        serieDtoOut.setId(1L);
        serieDtoOut.setTitle("prueba");
        serieDtoOut.setDebut(2020);
        serieDtoOut.setProducer(producerDtoOut);
        serieDtoOut.setDirector(directorDtoOut);
        serieDtoOut.setActors(actorsDtoOut);
    }

    @DisplayName("Junit test for get all series - positive")
    @Test
    void givenNothing_whenFindAll_thenReturnActorDTOList() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        Page<SerieBO> page = new PageImpl<>(List.of(serieBO), PageRequest.of(0, 5, Sort.by("title").ascending()), 10);
        given(serieService.findAll(any(Pageable.class))).willReturn(page);

        ResultActions response = mockMvc.perform(get("/series/findAll?method=false&page=5&size=10&sort=title"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get all series - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(dtoToBoConverter.serieDtoToBo(serieDtoIn)).willReturn(serieBO);
        given(serieService.findAll(any(Pageable.class))).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(get("/series/findAll?method=false&page=5&size=10&sort=title"));

        response.andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get all series - positive")
    @Test
    void givenNothing_whenFindAllCriteria_thenReturnActorDTOList() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(dtoToBoConverter.serieDtoToBo(serieDtoIn)).willReturn(serieBO);
        Page<SerieBO> page = new PageImpl<>(List.of(serieBO), PageRequest.of(0, 5, Sort.by("title").ascending()), 10);
        given(serieService.findAllCriteria(any(Pageable.class))).willReturn(page);

        ResultActions response = mockMvc.perform(get("/series/findAll?method=true&page=5&size=10&sort=title"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get all series - negative")
    @Test
    void givenNothing_whenFindAllCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(dtoToBoConverter.serieDtoToBo(serieDtoIn)).willReturn(serieBO);
        given(serieService.findAllCriteria(any(Pageable.class))).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(get("/series/findAll?method=true&page=5&size=10&sort=title"));

        response.andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get all series filtered- positive")
    @Test
    void givenNothing_whenFindAllCriteriaFilter_thenReturnSerieDTOList() throws Exception {
        Page<SerieBO> page = new PageImpl<>(List.of(serieBO), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(serieService.findAllCriteriaFilter(any(Pageable.class), anyList(), anyList(), anyList(), anyList(),
                anyList())).willReturn(page);
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);

        ResultActions response = mockMvc.perform(
                get("/series/findAllFilter?names=prueba&ages=2020&directors=prueba&producers=prueba&actors=prueba&method=true&sort=title&order=asc"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get all series filtered - negative")
    @Test
    void givenNothing_whenFindAllCriteriaFilter_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(serieService.findAllCriteriaFilter(any(Pageable.class), anyList(), anyList(), anyList(), anyList(),
                anyList())).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(
                get("/series/findAllFilter?names=prueba&ages=2020&directors=prueba&producers=prueba&actors=prueba&method=true&sort=title&order=asc"));

        response.andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get an actor by his id - positive")
    @Test
    void givenActorId_whenGetById_thenUserDTO() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(dtoToBoConverter.serieDtoToBo(serieDtoIn)).willReturn(serieBO);
        given(serieService.findById(serieBO.getId())).willReturn(serieBO);

        ResultActions response = mockMvc.perform(get("/series/findById/{id}?method=false", serieBO.getId()));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is((int) serieDtoOut.getId())))
                .andExpect(jsonPath("$.title", is(serieDtoOut.getTitle())))
                .andExpect(jsonPath("$.debut", is(serieDtoOut.getDebut())));
    }

    @DisplayName("Junit test for get an actor by his id - negative")
    @Test
    void givenActorId_whenGetById_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(dtoToBoConverter.serieDtoToBo(serieDtoIn)).willReturn(serieBO);
        given(serieService.findById(serieBO.getId())).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(get("/series/findById/{id}?method=false", serieBO.getId()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get an actor by his id - positive")
    @Test
    void givenActorId_whenGetByIdCriteria_thenUserDTO() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(dtoToBoConverter.serieDtoToBo(serieDtoIn)).willReturn(serieBO);
        given(serieService.findByIdCriteria(serieBO.getId())).willReturn(serieBO);

        ResultActions response = mockMvc.perform(get("/series/findById/{id}?method=true", serieBO.getId()));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is((int) serieDtoOut.getId())))
                .andExpect(jsonPath("$.title", is(serieDtoOut.getTitle())))
                .andExpect(jsonPath("$.debut", is(serieDtoOut.getDebut())));
    }

    @DisplayName("Junit test for get an actor by his id - negative")
    @Test
    void givenActorId_whenGetByIdCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(dtoToBoConverter.serieDtoToBo(serieDtoIn)).willReturn(serieBO);
        given(serieService.findByIdCriteria(serieBO.getId())).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(get("/series/findById/{id}?method=true", serieBO.getId()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for save a ActorDTO Object - positive")
    @Test
    void givenActorDTOObject_whenSave_thenReturnSavedActor() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(dtoToBoConverter.serieDtoToBo(serieDtoIn)).willReturn(serieBO);
        given(serieService.save(any(SerieBO.class), anyString())).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(
                post("/series/save?method=false&port=8080").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serieDtoIn)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is((int) serieDtoOut.getId())))
                .andExpect(jsonPath("$.title", is(serieDtoOut.getTitle())))
                .andExpect(jsonPath("$.debut", is(serieDtoOut.getDebut())));
    }

    @DisplayName("Junit test for save an actorDTO Object - negative")
    @Test
    void givenActorDTOObject_whenSave_PresentationException() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(dtoToBoConverter.serieDtoToBo(serieDtoIn)).willReturn(serieBO);
        given(serieService.save(any(SerieBO.class), anyString())).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(
                post("/series/save?method=false&port=8080").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serieDtoIn)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for save a ActorDTO Object - positive")
    @Test
    void givenActorDTOObject_whenSaveCriteria_thenReturnSavedActor() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(dtoToBoConverter.serieDtoToBo(serieDtoIn)).willReturn(serieBO);
        given(serieService.saveCriteria(any(SerieBO.class), anyString())).willAnswer(
                (invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(
                post("/series/save?method=true&port=8080").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serieDtoIn)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is((int) serieDtoOut.getId())))
                .andExpect(jsonPath("$.title", is(serieDtoOut.getTitle())))
                .andExpect(jsonPath("$.debut", is(serieDtoOut.getDebut())));
    }

    @DisplayName("Junit test for save an actorDTO Object - negative")
    @Test
    void givenActorDTOObject_whenSaveCriteria_PresentationException() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(dtoToBoConverter.serieDtoToBo(serieDtoIn)).willReturn(serieBO);
        given(serieService.saveCriteria(any(SerieBO.class), anyString())).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(
                post("/series/save?method=true&port=8080").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serieDtoIn)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for update an actor - positive")
    @Test
    void givenActorDTO_whenUpdate_thenUpdatedActorDTO() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(dtoToBoConverter.serieDtoUpdateToBo(serieDtoInUpdate)).willReturn(serieBO);
        given(serieService.update(any(SerieBO.class))).willAnswer((invocation -> invocation.getArgument(0)));

        SerieDtoInUpdate updatedActorDTO = serieDtoInUpdate;
        updatedActorDTO.setTitle("updated");
        updatedActorDTO.setDebut(2000);

        ResultActions response = mockMvc.perform(
                put("/series/update?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedActorDTO)));

        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) updatedActorDTO.getId())));
    }

    @DisplayName("Junit test for update an actor - negative")
    @Test
    void givenActorDTO_whenUpdate_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(dtoToBoConverter.serieDtoToBo(serieDtoIn)).willReturn(serieBO);
        given(serieService.findById(serieBO.getId())).willReturn(serieBO);
        given(serieService.update(any(SerieBO.class))).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(
                put("/series/update?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for update an actor - positive")
    @Test
    void givenActorDTO_whenUpdateCriteria_thenUpdatedActorDTO() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(dtoToBoConverter.serieDtoUpdateToBo(serieDtoInUpdate)).willReturn(serieBO);
        given(serieService.findByIdCriteria(serieBO.getId())).willReturn(serieBO);
        given(serieService.updateCriteria(any(SerieBO.class))).willAnswer((invocation -> invocation.getArgument(0)));

        SerieDtoInUpdate updatedActorDTO = serieDtoInUpdate;
        updatedActorDTO.setTitle("updated");
        updatedActorDTO.setDebut(2000);

        ResultActions response = mockMvc.perform(
                put("/series/update?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedActorDTO)));

        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) updatedActorDTO.getId())));
    }

    @DisplayName("Junit test for update an actor - negative")
    @Test
    void givenActorDTO_whenUpdateCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.serieBoToDtoOut(serieBO)).willReturn(serieDtoOut);
        given(dtoToBoConverter.serieDtoToBo(serieDtoIn)).willReturn(serieBO);
        given(serieService.findByIdCriteria(serieBO.getId())).willReturn(serieBO);
        given(serieService.updateCriteria(any(SerieBO.class))).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(
                put("/series/update?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for delete an actor by his id - positive")
    @Test
    void givenActorId_whenDeleteById_thenDelete() throws Exception {
        willDoNothing().given(serieService).deleteById(serieBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/series/deleteById?method=false").param("id", String.valueOf(serieDtoInUpdate.getId())));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @DisplayName("Junit test for delete an actor by his id - negative")
    @Test
    void givenActorId_whenDeleteById_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException("")).given(serieService).deleteById(serieBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/series/deleteById?method=false").param("id", String.valueOf(serieDtoInUpdate.getId())));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for delete an actor by his id - positive")
    @Test
    void givenActorId_whenDeleteByIdCriteria_thenDelete() throws Exception {
        willDoNothing().given(serieService).deleteByIdCriteria(serieBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/series/deleteById?method=true").param("id", String.valueOf(serieDtoInUpdate.getId())));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @DisplayName("Junit test for delete an actor by his id - negative")
    @Test
    void givenActorId_whenDeleteByIdCriteria_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException("")).given(serieService).deleteByIdCriteria(serieBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/series/deleteById?method=true").param("id", String.valueOf(serieDtoInUpdate.getId())));

        response.andDo(print()).andExpect(status().isBadRequest());
    }
}
