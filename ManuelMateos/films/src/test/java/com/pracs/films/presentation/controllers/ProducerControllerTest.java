package com.pracs.films.presentation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pracs.films.bussiness.bo.ProducerBO;
import com.pracs.films.bussiness.services.ProducerService;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.repositories.jpa.*;
import com.pracs.films.presentation.converters.BoToDtoConverter;
import com.pracs.films.presentation.converters.DtoToBoConverter;
import com.pracs.films.presentation.dto.ProducerDtoIn;
import com.pracs.films.presentation.dto.ProducerDtoInUpdate;
import com.pracs.films.presentation.dto.ProducerDtoOut;
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
class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoToDtoConverter boToDtoConverter;

    @MockBean
    private DtoToBoConverter dtoToBoConverter;

    @MockBean
    private ProducerService producerService;

    @MockBean
    private ProducerRepository producerRepository;

    @MockBean
    private DirectorRepository directorRepository;

    @MockBean
    private ActorRepository actorRepository;

    @MockBean
    private SerieRepository serieRepository;

    @MockBean
    private FilmRepository filmRepository;

    @MockBean
    private EntityManagerFactory entityManager;

    private ProducerBO producerBO;

    private ProducerDtoIn producerDtoIn;

    private ProducerDtoInUpdate producerDtoInUpdate;

    private ProducerDtoOut producerDtoOut;

    @BeforeEach
    void setup() {

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
    }

    @DisplayName("Junit test for get all producers - positive")
    @Test
    void givenNothing_whenFindAll_thenReturnProducerDTOList() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        Page<ProducerBO> page = new PageImpl<>(List.of(producerBO), PageRequest.of(0, 5, Sort.by("name").ascending()),
                10);
        given(producerService.findAll(any(Pageable.class))).willReturn(page);

        ResultActions response = mockMvc.perform(get("/producers/findAll?method=false&page=5&size=10&sort=name"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get all producers - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(dtoToBoConverter.producerDtoToBo(producerDtoIn)).willReturn(producerBO);
        Page<ProducerBO> page = new PageImpl<>(List.of(producerBO), PageRequest.of(0, 5, Sort.by("name").ascending()),
                10);
        given(producerService.findAll(any(Pageable.class))).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(get("/producers/findAll?method=false&page=5&size=10&sort=name"));

        response.andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get all producers - positive")
    @Test
    void givenNothing_whenFindAllCriteria_thenReturnProducerDTOList() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(dtoToBoConverter.producerDtoToBo(producerDtoIn)).willReturn(producerBO);
        Page<ProducerBO> page = new PageImpl<>(List.of(producerBO), PageRequest.of(0, 5, Sort.by("name").ascending()),
                10);
        given(producerService.findAllCriteria(any(Pageable.class))).willReturn(page);

        ResultActions response = mockMvc.perform(get("/producers/findAll?method=true&page=5&size=10&sort=name"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get all producers - negative")
    @Test
    void givenNothing_whenFindAllCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(dtoToBoConverter.producerDtoToBo(producerDtoIn)).willReturn(producerBO);
        given(producerService.findAllCriteria(any(Pageable.class))).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(get("/producers/findAll?method=true&page=5&size=10&sort=name"));

        response.andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get all producers filtered- positive")
    @Test
    void givenNothing_whenFindAllCriteriaFilter_thenReturnActorDTOList() throws Exception {
        Page<ProducerBO> page = new PageImpl<>(List.of(producerBO), PageRequest.of(0, 5, Sort.by("name").ascending()),
                10);
        given(producerService.findAllCriteriaFilter(any(Pageable.class), anyList(), anyList())).willReturn(page);
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);

        ResultActions response = mockMvc.perform(
                get("/producers/findAllFilter?names=prueba&ages=25&method=true&sort=name&order=asc"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get all producers filtered - negative")
    @Test
    void givenNothing_whenFindAllCriteriaFilter_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(producerService.findAllCriteriaFilter(any(Pageable.class), anyList(), anyList())).willThrow(
                new ServiceException(""));

        ResultActions response = mockMvc.perform(
                get("/producers/findAllFilter?names=prueba&ages=25Spain&method=true&sort=name&order=asc"));

        response.andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get a producer by his id - positive")
    @Test
    void givenProducerId_whenGetById_thenUserDTO() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(dtoToBoConverter.producerDtoToBo(producerDtoIn)).willReturn(producerBO);
        given(producerService.findById(producerBO.getId())).willReturn(producerBO);

        ResultActions response = mockMvc.perform(get("/producers/findById/{id}?method=false", producerBO.getId()));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is((int) producerDtoOut.getId())))
                .andExpect(jsonPath("$.name", is(producerDtoOut.getName())))
                .andExpect(jsonPath("$.debut", is(producerDtoOut.getDebut())));
    }

    @DisplayName("Junit test for get a producer by his id - negative")
    @Test
    void givenProducerId_whenGetById_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(dtoToBoConverter.producerDtoToBo(producerDtoIn)).willReturn(producerBO);
        given(producerService.findById(producerBO.getId())).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(get("/producers/findById/{id}?method=false", producerBO.getId()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get a producer by his id - positive")
    @Test
    void givenProducerId_whenGetByIdCriteria_thenUserDTO() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(dtoToBoConverter.producerDtoToBo(producerDtoIn)).willReturn(producerBO);
        given(producerService.findByIdCriteria(producerBO.getId())).willReturn(producerBO);

        ResultActions response = mockMvc.perform(get("/producers/findById/{id}?method=true", producerBO.getId()));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is((int) producerDtoOut.getId())))
                .andExpect(jsonPath("$.name", is(producerDtoOut.getName())))
                .andExpect(jsonPath("$.debut", is(producerDtoOut.getDebut())));
    }

    @DisplayName("Junit test for get a producer by his id - negative")
    @Test
    void givenProducerId_whenGetByIdCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(dtoToBoConverter.producerDtoToBo(producerDtoIn)).willReturn(producerBO);
        given(producerService.findByIdCriteria(producerBO.getId())).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(get("/producers/findById/{id}?method=true", producerBO.getId()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for save a ProducerDTOO Object - positive")
    @Test
    void givenProducerDTOObject_whenSave_thenReturnSavedProducer() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(dtoToBoConverter.producerDtoToBo(producerDtoIn)).willReturn(producerBO);
        given(producerService.save(any(ProducerBO.class))).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(
                post("/producers/save?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producerDtoIn)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is((int) producerDtoOut.getId())))
                .andExpect(jsonPath("$.name", is(producerDtoOut.getName())))
                .andExpect(jsonPath("$.debut", is(producerDtoOut.getDebut())));
    }

    @DisplayName("Junit test for save a ProducerDTOO Object - negative")
    @Test
    void givenProducerDTOObject_whenSave_PresentationException() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(dtoToBoConverter.producerDtoToBo(producerDtoIn)).willReturn(producerBO);
        given(producerService.save(any(ProducerBO.class))).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(
                post("/producers/save?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producerDtoIn)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for save a ProducerDTOO Object - positive")
    @Test
    void givenProducerDTOObject_whenSaveCriteria_thenReturnSavedProducer() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(dtoToBoConverter.producerDtoToBo(producerDtoIn)).willReturn(producerBO);
        given(producerService.saveCriteria(any(ProducerBO.class))).willAnswer(
                (invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(
                post("/producers/save?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producerDtoIn)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is((int) producerDtoOut.getId())))
                .andExpect(jsonPath("$.name", is(producerDtoOut.getName())))
                .andExpect(jsonPath("$.debut", is(producerDtoOut.getDebut())));
    }

    @DisplayName("Junit test for save a ProducerDTOO Object - negative")
    @Test
    void givenProducerDTOObject_whenSaveCriteria_PresentationException() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(dtoToBoConverter.producerDtoToBo(producerDtoIn)).willReturn(producerBO);
        given(producerService.saveCriteria(any(ProducerBO.class))).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(
                post("/producers/save?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producerDtoIn)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for update a producer - positive")
    @Test
    void givenProducerDTOO_whenUpdate_thenUpdatedProducerDTOO() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(dtoToBoConverter.producerDtoUpdateToBo(producerDtoInUpdate)).willReturn(producerBO);
        given(producerService.update(any(ProducerBO.class))).willAnswer((invocation -> invocation.getArgument(0)));

        ProducerDtoInUpdate updatedProducerDTOO = producerDtoInUpdate;
        updatedProducerDTOO.setName("updated");
        updatedProducerDTOO.setDebut(2000);

        ResultActions response = mockMvc.perform(
                put("/producers/update?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProducerDTOO)));

        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) updatedProducerDTOO.getId())));
    }

    @DisplayName("Junit test for update a producer - negative")
    @Test
    void givenProducerDTOO_whenUpdate_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(dtoToBoConverter.producerDtoToBo(producerDtoIn)).willReturn(producerBO);
        given(producerService.findById(producerBO.getId())).willReturn(producerBO);
        given(producerService.update(any(ProducerBO.class))).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(
                put("/producers/update?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for update a producer - positive")
    @Test
    void givenProducerDTOO_whenUpdateCriteria_thenUpdatedProducerDTOO() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(dtoToBoConverter.producerDtoUpdateToBo(producerDtoInUpdate)).willReturn(producerBO);
        given(producerService.findByIdCriteria(producerBO.getId())).willReturn(producerBO);
        given(producerService.updateCriteria(any(ProducerBO.class))).willAnswer(
                (invocation -> invocation.getArgument(0)));

        ProducerDtoInUpdate updatedProducerDTOO = producerDtoInUpdate;
        updatedProducerDTOO.setName("updated");
        updatedProducerDTOO.setDebut(2000);

        ResultActions response = mockMvc.perform(
                put("/producers/update?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProducerDTOO)));

        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) updatedProducerDTOO.getId())));
    }

    @DisplayName("Junit test for update a producer - negative")
    @Test
    void givenProducerDTOO_whenUpdateCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.producerBoToDtoOut(producerBO)).willReturn(producerDtoOut);
        given(dtoToBoConverter.producerDtoToBo(producerDtoIn)).willReturn(producerBO);
        given(producerService.findByIdCriteria(producerBO.getId())).willReturn(producerBO);
        given(producerService.updateCriteria(any(ProducerBO.class))).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(
                put("/producers/update?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for delete a producer by his id - positive")
    @Test
    void givenProducerId_whenDeleteById_thenDelete() throws Exception {
        willDoNothing().given(producerService).deleteById(producerBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/producers/deleteById?method=false").param("id", String.valueOf(producerDtoInUpdate.getId())));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @DisplayName("Junit test for delete a producer by his id - negative")
    @Test
    void givenProducerId_whenDeleteById_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException("")).given(producerService).deleteById(producerBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/producers/deleteById?method=false").param("id", String.valueOf(producerDtoInUpdate.getId())));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for delete a producer by his id - positive")
    @Test
    void givenProducerId_whenDeleteByIdCriteria_thenDelete() throws Exception {
        willDoNothing().given(producerService).deleteByIdCriteria(producerBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/producers/deleteById?method=true").param("id", String.valueOf(producerDtoInUpdate.getId())));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @DisplayName("Junit test for delete a producer by his id - negative")
    @Test
    void givenProducerId_whenDeleteByIdCriteria_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException("")).given(producerService).deleteByIdCriteria(producerBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/producers/deleteById?method=true").param("id", String.valueOf(producerDtoInUpdate.getId())));

        response.andDo(print()).andExpect(status().isBadRequest());
    }
}
