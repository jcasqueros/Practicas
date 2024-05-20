package com.pracs.films.presentation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pracs.films.bussiness.bo.ActorBO;
import com.pracs.films.bussiness.services.ActorService;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.repositories.jpa.*;
import com.pracs.films.presentation.converters.BoToDtoConverter;
import com.pracs.films.presentation.converters.DtoToBoConverter;
import com.pracs.films.presentation.dto.ActorDtoIn;
import com.pracs.films.presentation.dto.ActorDtoInUpdate;
import com.pracs.films.presentation.dto.ActorDtoOut;
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
class ActorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoToDtoConverter boToDtoConverter;

    @MockBean
    private DtoToBoConverter dtoToBoConverter;

    @MockBean
    private ActorService actorService;

    @MockBean
    private ActorRepository actorRepository;

    @MockBean
    private DirectorRepository directorRepository;

    @MockBean
    private ProducerRepository producerRepository;

    @MockBean
    private SerieRepository serieRepository;

    @MockBean
    private FilmRepository filmRepository;

    @MockBean
    private EntityManagerFactory entityManager;

    private ActorBO actorBO;

    private ActorDtoIn actorDtoIn;

    private ActorDtoInUpdate actorDtoInUpdate;

    private ActorDtoOut actorDtoOut;

    @BeforeEach
    void setup() {

        actorBO = new ActorBO();
        actorBO.setId(1L);
        actorBO.setName("prueba");
        actorBO.setAge(25);
        actorBO.setNationality("Spain");

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
    }

    @DisplayName("Junit test for get all actors - positive")
    @Test
    void givenNothing_whenFindAll_thenReturnActorDTOList() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        Page<ActorBO> page = new PageImpl<>(List.of(actorBO), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(actorService.findAll(any(Pageable.class))).willReturn(page);

        ResultActions response = mockMvc.perform(get("/actors/findAll?method=false&page=5&size=10&sort=name"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get all actors - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.findAll(any(Pageable.class))).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(get("/actors/findAll?method=false&page=5&size=10&sort=name"));

        response.andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get all actors - positive")
    @Test
    void givenNothing_whenFindAllCriteria_thenReturnActorDTOList() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        Page<ActorBO> page = new PageImpl<>(List.of(actorBO), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(actorService.findAllCriteria(any(Pageable.class))).willReturn(page);

        ResultActions response = mockMvc.perform(get("/actors/findAll?method=true&page=5&size=10&sort=name"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get all actors - negative")
    @Test
    void givenNothing_whenFindAllCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.findAllCriteria(any(Pageable.class))).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(get("/actors/findAll?method=true&page=5&size=10&sort=name"));

        response.andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get all actors filtered- positive")
    @Test
    void givenNothing_whenFindAllCriteriaFilter_thenReturnActorDTOList() throws Exception {
        Page<ActorBO> page = new PageImpl<>(List.of(actorBO), PageRequest.of(0, 5, Sort.by("name").ascending()), 10);
        given(actorService.findAllCriteriaFilter(any(Pageable.class), anyList(), anyList(), anyList())).willReturn(
                page);
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);

        ResultActions response = mockMvc.perform(
                get("/actors/findAllFilter?names=prueba&ages=25&nationalities=Spain&method=true&sort=name&order=asc"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get all actors filtered - negative")
    @Test
    void givenNothing_whenFindAllCriteriaFilter_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(actorService.findAllCriteriaFilter(any(Pageable.class), anyList(), anyList(), anyList())).willThrow(
                new ServiceException(""));

        ResultActions response = mockMvc.perform(
                get("/actors/findAllFilter?names=prueba&ages=25&nationalities=Spain&method=true&sort=name&order=asc"));

        response.andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get an actor by his id - positive")
    @Test
    void givenActorId_whenGetById_thenUserDTO() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.findById(actorBO.getId())).willReturn(actorBO);

        ResultActions response = mockMvc.perform(get("/actors/findById/{id}?method=false", actorBO.getId()));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is((int) actorDtoOut.getId())))
                .andExpect(jsonPath("$.name", is(actorDtoOut.getName())))
                .andExpect(jsonPath("$.nationality", is(actorDtoOut.getNationality())))
                .andExpect(jsonPath("$.age", is(actorDtoOut.getAge())));
    }

    @DisplayName("Junit test for get an actor by his id - negative")
    @Test
    void givenActorId_whenGetById_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.findById(actorBO.getId())).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(get("/actors/findById/{id}?method=false", actorBO.getId()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get an actor by his id - positive")
    @Test
    void givenActorId_whenGetByIdCriteria_thenUserDTO() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.findByIdCriteria(actorBO.getId())).willReturn(actorBO);

        ResultActions response = mockMvc.perform(get("/actors/findById/{id}?method=true", actorBO.getId()));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is((int) actorDtoOut.getId())))
                .andExpect(jsonPath("$.name", is(actorDtoOut.getName())))
                .andExpect(jsonPath("$.nationality", is(actorDtoOut.getNationality())))
                .andExpect(jsonPath("$.age", is(actorDtoOut.getAge())));
    }

    @DisplayName("Junit test for get an actor by his id - negative")
    @Test
    void givenActorId_whenGetByIdCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.findByIdCriteria(actorBO.getId())).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(get("/actors/findById/{id}?method=true", actorBO.getId()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get an actor by his name - positive")
    @Test
    void givenActorName_whenGetByName_thenActorDTO() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.findByName(actorBO.getName())).willReturn(List.of(actorBO));

        ResultActions response = mockMvc.perform(get("/actors/findByName/{name}?method=false", actorBO.getName()));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get an actor by his id - negative")
    @Test
    void givenActorName_whenGetByName_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.findByName(actorBO.getName())).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(get("/actors/findById/{name}?method=false", actorBO.getName()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get actor by his name and age - positive")
    @Test
    void givenActorNameAndAge_whenFindByNameAndAge_thenUserDTO() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.findByNameAndAge(actorBO.getName(), actorBO.getAge())).willReturn(List.of(actorBO));

        ResultActions response = mockMvc.perform(
                get("/actors/findByNameAndAge?method=false&name=" + actorDtoIn.getName() + "&age=" + actorDtoIn.getAge()));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get an actor by his name and age - negative")
    @Test
    void givenActorNameAndAge_whenFindByNameAndAge_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.findByNameAndAge(actorBO.getName(), actorBO.getAge())).willThrow(
                new ServiceException("error"));

        ResultActions response = mockMvc.perform(
                get("/actors/findByNameAndAge?method=false&name=" + actorDtoIn.getName() + "&age=" + actorDtoIn.getAge()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get an actor by his name and age - positive")
    @Test
    void givenActorNameAndAge_whenFindByNameAndAgeCriteria_thenUserDTO() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.findByNameAndAgeCriteria(actorBO.getName(), actorBO.getAge())).willReturn(List.of(actorBO));

        ResultActions response = mockMvc.perform(
                get("/actors/findByNameAndAge?method=true&name=" + actorDtoIn.getName() + "&age=" + actorDtoIn.getAge()));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get an actor by his name and age - negative")
    @Test
    void givenActorNameAndAge_whenFindByNameAndAgeCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.findByNameAndAgeCriteria(actorBO.getName(), actorBO.getAge())).willThrow(
                new ServiceException("error"));

        ResultActions response = mockMvc.perform(
                get("/actors/findByNameAndAge?method=true&name=" + actorDtoIn.getName() + "&age=" + actorDtoIn.getAge()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for save a ActorDTO Object - positive")
    @Test
    void givenActorDTOObject_whenSave_thenReturnSavedActor() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.save(any(ActorBO.class))).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(
                post("/actors/save?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorDtoIn)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is((int) actorDtoOut.getId())))
                .andExpect(jsonPath("$.name", is(actorDtoOut.getName())))
                .andExpect(jsonPath("$.age", is(actorDtoOut.getAge())))
                .andExpect(jsonPath("$.nationality", is(actorDtoOut.getNationality())));
    }

    @DisplayName("Junit test for save an actorDTO Object - negative")
    @Test
    void givenActorDTOObject_whenSave_PresentationException() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.save(any(ActorBO.class))).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(
                post("/actors/save?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorDtoIn)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for save a ActorDTO Object - positive")
    @Test
    void givenActorDTOObject_whenSaveCriteria_thenReturnSavedActor() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.saveCriteria(any(ActorBO.class))).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(
                post("/actors/save?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorDtoIn)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is((int) actorDtoOut.getId())))
                .andExpect(jsonPath("$.name", is(actorDtoOut.getName())))
                .andExpect(jsonPath("$.age", is(actorDtoOut.getAge())))
                .andExpect(jsonPath("$.nationality", is(actorDtoOut.getNationality())));
    }

    @DisplayName("Junit test for save an actorDTO Object - negative")
    @Test
    void givenActorDTOObject_whenSaveCriteria_PresentationException() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.saveCriteria(any(ActorBO.class))).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(
                post("/actors/save?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorDtoIn)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for update an actor - positive")
    @Test
    void givenActorDTO_whenUpdate_thenUpdatedActorDTO() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoUpdateToBo(actorDtoInUpdate)).willReturn(actorBO);
        given(actorService.update(any(ActorBO.class))).willAnswer((invocation -> invocation.getArgument(0)));

        ActorDtoInUpdate updatedActorDTO = actorDtoInUpdate;
        updatedActorDTO.setName("updated");
        updatedActorDTO.setAge(100);
        updatedActorDTO.setNationality("updated");

        ResultActions response = mockMvc.perform(
                put("/actors/update?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedActorDTO)));

        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) updatedActorDTO.getId())));
    }

    @DisplayName("Junit test for update an actor - negative")
    @Test
    void givenActorDTO_whenUpdate_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.findById(actorBO.getId())).willReturn(actorBO);
        given(actorService.update(any(ActorBO.class))).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(
                put("/actors/update?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for update an actor - positive")
    @Test
    void givenActorDTO_whenUpdateCriteria_thenUpdatedActorDTO() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoUpdateToBo(actorDtoInUpdate)).willReturn(actorBO);
        given(actorService.findByIdCriteria(actorBO.getId())).willReturn(actorBO);
        given(actorService.updateCriteria(any(ActorBO.class))).willAnswer((invocation -> invocation.getArgument(0)));

        ActorDtoInUpdate updatedActorDTO = actorDtoInUpdate;
        updatedActorDTO.setName("updated");
        updatedActorDTO.setAge(100);
        updatedActorDTO.setNationality("updated");

        ResultActions response = mockMvc.perform(
                put("/actors/update?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedActorDTO)));

        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) updatedActorDTO.getId())));
    }

    @DisplayName("Junit test for update an actor - negative")
    @Test
    void givenActorDTO_whenUpdateCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.actorBoToDtoOut(actorBO)).willReturn(actorDtoOut);
        given(dtoToBoConverter.actorDtoToBo(actorDtoIn)).willReturn(actorBO);
        given(actorService.findByIdCriteria(actorBO.getId())).willReturn(actorBO);
        given(actorService.updateCriteria(any(ActorBO.class))).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(
                put("/actors/update?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for delete an actor by his id - positive")
    @Test
    void givenActorId_whenDeleteById_thenDelete() throws Exception {
        willDoNothing().given(actorService).deleteById(actorBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/actors/deleteById?method=false").param("id", String.valueOf(actorDtoInUpdate.getId())));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @DisplayName("Junit test for delete an actor by his id - negative")
    @Test
    void givenActorId_whenDeleteById_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException("")).given(actorService).deleteById(actorBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/actors/deleteById?method=false").param("id", String.valueOf(actorDtoInUpdate.getId())));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for delete an actor by his id - positive")
    @Test
    void givenActorId_whenDeleteByIdCriteria_thenDelete() throws Exception {
        willDoNothing().given(actorService).deleteByIdCriteria(actorBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/actors/deleteById?method=true").param("id", String.valueOf(actorDtoInUpdate.getId())));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @DisplayName("Junit test for delete an actor by his id - negative")
    @Test
    void givenActorId_whenDeleteByIdCriteria_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException("")).given(actorService).deleteByIdCriteria(actorBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/actors/deleteById?method=true").param("id", String.valueOf(actorDtoInUpdate.getId())));

        response.andDo(print()).andExpect(status().isBadRequest());
    }
}
