package com.pracs.films.presentation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pracs.films.bussiness.bo.ActorBO;
import com.pracs.films.bussiness.bo.DirectorBO;
import com.pracs.films.bussiness.bo.FilmBO;
import com.pracs.films.bussiness.bo.ProducerBO;
import com.pracs.films.bussiness.services.FilmService;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.repositories.jpa.ActorRepository;
import com.pracs.films.persistence.repositories.jpa.DirectorRepository;
import com.pracs.films.persistence.repositories.jpa.ProducerRepository;
import com.pracs.films.persistence.repositories.jpa.SerieRepository;
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
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoToDtoConverter boToDtoConverter;

    @MockBean
    private DtoToBoConverter dtoToBoConverter;

    @MockBean
    private FilmService filmService;

    @MockBean
    private DirectorRepository directorRepository;

    @MockBean
    private ProducerRepository producerRepository;

    @MockBean
    private ActorRepository actorRepository;

    @MockBean
    private SerieRepository serieRepository;

    @MockBean
    private EntityManagerFactory entityManager;

    private ActorBO actorBO;

    private ActorDtoIn actorDtoIn;

    private ActorDtoOut actorDtoOut;

    private DirectorBO directorBO;

    private DirectorDtoIn directorDtoIn;

    private DirectorDtoOut directorDtoOut;

    private ProducerBO producerBO;

    private ProducerDtoIn producerDtoIn;

    private ProducerDtoOut producerDtoOut;

    private FilmBO filmBO;

    private FilmDtoIn filmDtoIn;

    private FilmDtoOut filmDtoOut;

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
        actorDtoIn.setId(1L);
        actorDtoIn.setName("prueba");
        actorDtoIn.setAge(25);
        actorDtoIn.setNationality("Spain");
        List<ActorDtoIn> actorsDtoIn = new ArrayList<>();
        actorsDtoIn.add(actorDtoIn);

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
        directorDtoIn.setId(1L);
        directorDtoIn.setName("prueba");
        directorDtoIn.setAge(25);
        directorDtoIn.setNationality("Spain");

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
        producerDtoIn.setId(1L);
        producerDtoIn.setName("prueba");
        producerDtoIn.setDebut(2020);

        producerDtoOut = new ProducerDtoOut();
        producerDtoOut.setId(1L);
        producerDtoOut.setName("prueba");
        producerDtoOut.setDebut(2020);

        filmBO = new FilmBO();
        filmBO.setId(1L);
        filmBO.setTitle("prueba");
        filmBO.setDebut(2020);
        filmBO.setProducer(producerBO);
        filmBO.setDirector(directorBO);
        filmBO.setActors(actorsBO);

        filmDtoIn = new FilmDtoIn();
        filmDtoIn.setId(1L);
        filmDtoIn.setTitle("prueba");
        filmDtoIn.setDebut(2020);
        filmDtoIn.setProducer(producerDtoIn);
        filmDtoIn.setDirector(directorDtoIn);
        filmDtoIn.setActors(actorsDtoIn);

        filmDtoOut = new FilmDtoOut();
        filmDtoOut.setId(1L);
        filmDtoOut.setTitle("prueba");
        filmDtoOut.setDebut(2020);
        filmDtoOut.setProducer(producerDtoOut);
        filmDtoOut.setDirector(directorDtoOut);
        filmDtoOut.setActors(actorsDtoOut);
    }

    @DisplayName("Junit test for get all series - positive")
    @Test
    void givenNothing_whenFindAll_thenReturnFilmDTOList() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        Page<FilmBO> page = new PageImpl<>(List.of(filmBO), PageRequest.of(0, 5, Sort.by("title").ascending()), 10);
        given(filmService.findAll(any(Pageable.class))).willReturn(page);

        ResultActions response = mockMvc.perform(get("/films/findAll?method=false&page=5&size=10&sort=title"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get all series - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        given(dtoToBoConverter.filmDtoToBo(filmDtoIn)).willReturn(filmBO);
        given(filmService.findAll(any(Pageable.class))).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(get("/films/findAll?method=false&page=5&size=10&sort=title"));

        response.andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get all series - positive")
    @Test
    void givenNothing_whenFindAllCriteria_thenReturnFilmDTOList() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        given(dtoToBoConverter.filmDtoToBo(filmDtoIn)).willReturn(filmBO);
        Page<FilmBO> page = new PageImpl<>(List.of(filmBO), PageRequest.of(0, 5, Sort.by("title").ascending()), 10);
        given(filmService.findAllCriteria(any(Pageable.class))).willReturn(page);

        ResultActions response = mockMvc.perform(get("/films/findAll?method=true&page=5&size=10&sort=title"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get all series - negative")
    @Test
    void givenNothing_whenFindAllCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        given(dtoToBoConverter.filmDtoToBo(filmDtoIn)).willReturn(filmBO);
        given(filmService.findAllCriteria(any(Pageable.class))).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(get("/films/findAll?method=true&page=5&size=10&sort=title"));

        response.andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get a serie by his id - positive")
    @Test
    void givenActorId_whenGetById_thenUserDTO() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        given(dtoToBoConverter.filmDtoToBo(filmDtoIn)).willReturn(filmBO);
        given(filmService.findById(filmBO.getId())).willReturn(filmBO);

        ResultActions response = mockMvc.perform(get("/films/findById/{id}?method=false", filmBO.getId()));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is((int) filmDtoOut.getId())))
                .andExpect(jsonPath("$.title", is(filmDtoOut.getTitle())))
                .andExpect(jsonPath("$.debut", is(filmDtoOut.getDebut())));
    }

    @DisplayName("Junit test for get a serie by his id - negative")
    @Test
    void givenActorId_whenGetById_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        given(dtoToBoConverter.filmDtoToBo(filmDtoIn)).willReturn(filmBO);
        given(filmService.findById(filmBO.getId())).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(get("/films/findById/{id}?method=false", filmBO.getId()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get a serie by his id - positive")
    @Test
    void givenActorId_whenGetByIdCriteria_thenUserDTO() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        given(dtoToBoConverter.filmDtoToBo(filmDtoIn)).willReturn(filmBO);
        given(filmService.findByIdCriteria(filmBO.getId())).willReturn(filmBO);

        ResultActions response = mockMvc.perform(get("/films/findById/{id}?method=true", filmBO.getId()));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is((int) filmDtoOut.getId())))
                .andExpect(jsonPath("$.title", is(filmDtoOut.getTitle())))
                .andExpect(jsonPath("$.debut", is(filmDtoOut.getDebut())));
    }

    @DisplayName("Junit test for get a serie by his id - negative")
    @Test
    void givenActorId_whenGetByIdCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        given(dtoToBoConverter.filmDtoToBo(filmDtoIn)).willReturn(filmBO);
        given(filmService.findByIdCriteria(filmBO.getId())).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(get("/films/findById/{id}?method=true", filmBO.getId()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for save a FilmDTO Object - positive")
    @Test
    void givenFilmDTOObject_whenSave_thenReturnSavedActor() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        given(dtoToBoConverter.filmDtoToBo(filmDtoIn)).willReturn(filmBO);
        given(filmService.save(any(FilmBO.class))).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(
                post("/films/save?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmDtoIn)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is((int) filmDtoOut.getId())))
                .andExpect(jsonPath("$.title", is(filmDtoOut.getTitle())))
                .andExpect(jsonPath("$.debut", is(filmDtoOut.getDebut())));
    }

    @DisplayName("Junit test for save an FilmDTO Object - negative")
    @Test
    void givenFilmDTOObject_whenSave_PresentationException() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        given(dtoToBoConverter.filmDtoToBo(filmDtoIn)).willReturn(filmBO);
        given(filmService.save(any(FilmBO.class))).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(
                post("/films/save?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmDtoIn)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for save a FilmDTO Object - positive")
    @Test
    void givenFilmDTOObject_whenSaveCriteria_thenReturnSavedActor() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        given(dtoToBoConverter.filmDtoToBo(filmDtoIn)).willReturn(filmBO);
        given(filmService.saveCriteria(any(FilmBO.class))).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/films/save?method=true").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmDtoIn)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is((int) filmDtoOut.getId())))
                .andExpect(jsonPath("$.title", is(filmDtoOut.getTitle())))
                .andExpect(jsonPath("$.debut", is(filmDtoOut.getDebut())));
    }

    @DisplayName("Junit test for save an FilmDTO Object - negative")
    @Test
    void givenFilmDTOObject_whenSaveCriteria_PresentationException() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        given(dtoToBoConverter.filmDtoToBo(filmDtoIn)).willReturn(filmBO);
        given(filmService.saveCriteria(any(FilmBO.class))).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(post("/films/save?method=true").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmDtoIn)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for update a serie - positive")
    @Test
    void givenFilmDTO_whenUpdate_thenUpdatedFilmDTO() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        given(dtoToBoConverter.filmDtoToBo(filmDtoIn)).willReturn(filmBO);
        given(filmService.update(any(FilmBO.class))).willAnswer((invocation -> invocation.getArgument(0)));

        FilmDtoIn updatedFilmDTO = new FilmDtoIn();
        updatedFilmDTO.setId(filmDtoIn.getId());
        updatedFilmDTO.setTitle("updated");
        updatedFilmDTO.setDebut(2000);

        ResultActions response = mockMvc.perform(
                put("/films/update?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedFilmDTO)));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is((int) updatedFilmDTO.getId())))
                .andExpect(jsonPath("$.name", is("updated"))).andExpect(jsonPath("$.age", is(2000)))
                .andExpect(jsonPath("$.nationality", is("updated")));
    }

    @DisplayName("Junit test for update a serie - negative")
    @Test
    void givenFilmDTO_whenUpdate_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        given(dtoToBoConverter.filmDtoToBo(filmDtoIn)).willReturn(filmBO);
        given(filmService.findById(filmBO.getId())).willReturn(filmBO);
        given(filmService.update(any(FilmBO.class))).willThrow(new ServiceException(""));

        FilmDtoIn updatedFilmDTO = filmDtoIn;
        updatedFilmDTO.setTitle("updated");
        updatedFilmDTO.setDebut(2000);

        ResultActions response = mockMvc.perform(
                put("/films/update?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedFilmDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for update a serie - positive")
    @Test
    void givenFilmDTO_whenUpdateCriteria_thenUpdatedFilmDTO() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        given(dtoToBoConverter.filmDtoToBo(filmDtoIn)).willReturn(filmBO);
        given(filmService.findByIdCriteria(filmBO.getId())).willReturn(filmBO);
        given(filmService.updateCriteria(any(FilmBO.class))).willAnswer((invocation -> invocation.getArgument(0)));

        FilmDtoIn updatedFilmDTO = filmDtoIn;
        updatedFilmDTO.setTitle("updated");
        updatedFilmDTO.setDebut(2000);

        ResultActions response = mockMvc.perform(
                put("/films/update?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedFilmDTO)));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is((int) updatedFilmDTO.getId())))
                .andExpect(jsonPath("$.name", is("updated"))).andExpect(jsonPath("$.age", is(2000)))
                .andExpect(jsonPath("$.nationality", is("updated")));
    }

    @DisplayName("Junit test for update a serie - negative")
    @Test
    void givenFilmDTO_whenUpdateCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.filmBoToDtoOut(filmBO)).willReturn(filmDtoOut);
        given(dtoToBoConverter.filmDtoToBo(filmDtoIn)).willReturn(filmBO);
        given(filmService.findByIdCriteria(filmBO.getId())).willReturn(filmBO);
        given(filmService.updateCriteria(any(FilmBO.class))).willThrow(new ServiceException(""));

        FilmDtoIn updatedFilmDTO = filmDtoIn;
        updatedFilmDTO.setTitle("updated");
        updatedFilmDTO.setDebut(2000);

        ResultActions response = mockMvc.perform(
                put("/films/update?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedFilmDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for delete a serie by his id - positive")
    @Test
    void givenActorId_whenDeleteById_thenDelete() throws Exception {
        willDoNothing().given(filmService).deleteById(filmBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/films/deleteById?method=false").param("id", String.valueOf(filmDtoIn.getId())));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @DisplayName("Junit test for delete a serie by his id - negative")
    @Test
    void givenActorId_whenDeleteById_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException("")).given(filmService).deleteById(filmBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/films/deleteById?method=false").param("id", String.valueOf(filmDtoIn.getId())));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for delete a serie by his id - positive")
    @Test
    void givenActorId_whenDeleteByIdCriteria_thenDelete() throws Exception {
        willDoNothing().given(filmService).deleteByIdCriteria(filmBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/films/deleteById?method=true").param("id", String.valueOf(filmDtoIn.getId())));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @DisplayName("Junit test for delete a serie by his id - negative")
    @Test
    void givenActorId_whenDeleteByIdCriteria_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException("")).given(filmService).deleteByIdCriteria(filmBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/films/deleteById?method=true").param("id", String.valueOf(filmDtoIn.getId())));

        response.andDo(print()).andExpect(status().isBadRequest());
    }
}
