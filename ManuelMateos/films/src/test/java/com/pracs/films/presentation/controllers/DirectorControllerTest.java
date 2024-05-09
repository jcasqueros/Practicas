package com.pracs.films.presentation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pracs.films.bussiness.bo.DirectorBO;
import com.pracs.films.bussiness.services.DirectorService;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.repositories.jpa.ActorRepository;
import com.pracs.films.persistence.repositories.jpa.FilmRepository;
import com.pracs.films.persistence.repositories.jpa.ProducerRepository;
import com.pracs.films.persistence.repositories.jpa.SerieRepository;
import com.pracs.films.presentation.converters.BoToDtoConverter;
import com.pracs.films.presentation.converters.DtoToBoConverter;
import com.pracs.films.presentation.dto.DirectorDtoIn;
import com.pracs.films.presentation.dto.DirectorDtoOut;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
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
class DirectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoToDtoConverter boToDtoConverter;

    @MockBean
    private DtoToBoConverter dtoToBoConverter;

    @MockBean
    private DirectorService directorService;

    @MockBean
    private ActorRepository actorRepository;

    @MockBean
    private ProducerRepository producerRepository;

    @MockBean
    private SerieRepository serieRepository;

    @MockBean
    private FilmRepository filmRepository;

    @MockBean
    private EntityManagerFactory entityManager;

    private DirectorBO directorBO;

    private DirectorDtoIn directorDtoIn;

    private DirectorDtoOut directorDtoOut;

    @BeforeEach
    void setup() {

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
    }

    @DisplayName("Junit test for get all directors - positive")
    @Test
    void givenNothing_whenFindAll_thenReturnDirectorDTOList() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(directorService.findAll()).willReturn(List.of(directorBO));

        ResultActions response = mockMvc.perform(get("/directors/findAll?method=false"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get all directors - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(dtoToBoConverter.directorDtoToBo(directorDtoIn)).willReturn(directorBO);
        given(directorService.findAll()).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(get("/directors/findAll?method=false"));

        response.andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get all directors - positive")
    @Test
    void givenNothing_whenFindAllCriteria_thenReturnDirectorDTOList() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(dtoToBoConverter.directorDtoToBo(directorDtoIn)).willReturn(directorBO);
        given(directorService.findAllCriteria()).willReturn(List.of(directorBO));

        ResultActions response = mockMvc.perform(get("/directors/findAll?method=true"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Junit test for get all directors - negative")
    @Test
    void givenNothing_whenFindAllCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(dtoToBoConverter.directorDtoToBo(directorDtoIn)).willReturn(directorBO);
        given(directorService.findAllCriteria()).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(get("/directors/findAll?method=true"));

        response.andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get a director by his id - positive")
    @Test
    void givenDirectorId_whenGetById_thenUserDTO() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(dtoToBoConverter.directorDtoToBo(directorDtoIn)).willReturn(directorBO);
        given(directorService.findById(directorBO.getId())).willReturn(directorBO);

        ResultActions response = mockMvc.perform(get("/directors/findById/{id}?method=false", directorBO.getId()));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is((int) directorDtoOut.getId())))
                .andExpect(jsonPath("$.name", is(directorDtoOut.getName())))
                .andExpect(jsonPath("$.nationality", is(directorDtoOut.getNationality())))
                .andExpect(jsonPath("$.age", is(directorDtoOut.getAge())));
    }

    @DisplayName("Junit test for get a director by his id - negative")
    @Test
    void givenDirectorId_whenGetById_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(dtoToBoConverter.directorDtoToBo(directorDtoIn)).willReturn(directorBO);
        given(directorService.findById(directorBO.getId())).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(get("/directors/findById/{id}?method=false", directorBO.getId()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get a director by his id - positive")
    @Test
    void givenDirectorId_whenGetByIdCriteria_thenUserDTO() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(dtoToBoConverter.directorDtoToBo(directorDtoIn)).willReturn(directorBO);
        given(directorService.findByIdCriteria(directorBO.getId())).willReturn(directorBO);

        ResultActions response = mockMvc.perform(get("/directors/findById/{id}?method=true", directorBO.getId()));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is((int) directorDtoOut.getId())))
                .andExpect(jsonPath("$.name", is(directorDtoOut.getName())))
                .andExpect(jsonPath("$.nationality", is(directorDtoOut.getNationality())))
                .andExpect(jsonPath("$.age", is(directorDtoOut.getAge())));
    }

    @DisplayName("Junit test for get a director by his id - negative")
    @Test
    void givenDirectorId_whenGetByIdCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(dtoToBoConverter.directorDtoToBo(directorDtoIn)).willReturn(directorBO);
        given(directorService.findByIdCriteria(directorBO.getId())).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(get("/directors/findById/{id}?method=true", directorBO.getId()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for save a DirectorDTO Object - positive")
    @Test
    void givenDirectorDTOObject_whenSave_thenReturnSavedDirector() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(dtoToBoConverter.directorDtoToBo(directorDtoIn)).willReturn(directorBO);
        given(directorService.save(any(DirectorBO.class))).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(
                post("/directors/save?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorDtoIn)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is((int) directorDtoOut.getId())))
                .andExpect(jsonPath("$.name", is(directorDtoOut.getName())))
                .andExpect(jsonPath("$.age", is(directorDtoOut.getAge())))
                .andExpect(jsonPath("$.nationality", is(directorDtoOut.getNationality())));
    }

    @DisplayName("Junit test for save a directorDTO Object - negative")
    @Test
    void givenDirectorDTOObject_whenSave_PresentationException() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(dtoToBoConverter.directorDtoToBo(directorDtoIn)).willReturn(directorBO);
        given(directorService.save(any(DirectorBO.class))).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(
                post("/directors/save?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorDtoIn)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for save a DirectorDTO Object - positive")
    @Test
    void givenDirectorDTOObject_whenSaveCriteria_thenReturnSavedDirector() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(dtoToBoConverter.directorDtoToBo(directorDtoIn)).willReturn(directorBO);
        given(directorService.saveCriteria(any(DirectorBO.class))).willAnswer(
                (invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(
                post("/directors/save?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorDtoIn)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is((int) directorDtoOut.getId())))
                .andExpect(jsonPath("$.name", is(directorDtoOut.getName())))
                .andExpect(jsonPath("$.age", is(directorDtoOut.getAge())))
                .andExpect(jsonPath("$.nationality", is(directorDtoOut.getNationality())));
    }

    @DisplayName("Junit test for save a directorDTO Object - negative")
    @Test
    void givenDirectorDTOObject_whenSaveCriteria_PresentationException() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(dtoToBoConverter.directorDtoToBo(directorDtoIn)).willReturn(directorBO);
        given(directorService.saveCriteria(any(DirectorBO.class))).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(
                post("/directors/save?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorDtoIn)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for update a director - positive")
    @Test
    void givenDirectorDTO_whenUpdate_thenupdatedDirectorDTO() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(dtoToBoConverter.directorDtoToBo(directorDtoIn)).willReturn(directorBO);
        given(directorService.update(any(DirectorBO.class))).willAnswer((invocation -> invocation.getArgument(0)));

        DirectorDtoIn updatedDirectorDTO = new DirectorDtoIn();
        updatedDirectorDTO.setId(directorDtoIn.getId());
        updatedDirectorDTO.setName("updated");
        updatedDirectorDTO.setAge(90);
        updatedDirectorDTO.setNationality("updated");

        ResultActions response = mockMvc.perform(
                put("/directors/update?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDirectorDTO)));

        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) updatedDirectorDTO.getId())))
                .andExpect(jsonPath("$.name", is("updated"))).andExpect(jsonPath("$.age", is(90)))
                .andExpect(jsonPath("$.nationality", is("updated")));
    }

    @DisplayName("Junit test for update a director - negative")
    @Test
    void givenDirectorDTO_whenUpdate_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(dtoToBoConverter.directorDtoToBo(directorDtoIn)).willReturn(directorBO);
        given(directorService.findById(directorBO.getId())).willReturn(directorBO);
        given(directorService.update(any(DirectorBO.class))).willThrow(new ServiceException(""));

        DirectorDtoIn updatedDirectorDTO = directorDtoIn;
        updatedDirectorDTO.setName("updated");
        updatedDirectorDTO.setAge(100);
        updatedDirectorDTO.setNationality("updated");

        ResultActions response = mockMvc.perform(
                put("/directors/update?method=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDirectorDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for update a director - positive")
    @Test
    void givenDirectorDTO_whenUpdateCriteria_thenupdatedDirectorDTO() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(dtoToBoConverter.directorDtoToBo(directorDtoIn)).willReturn(directorBO);
        given(directorService.findByIdCriteria(directorBO.getId())).willReturn(directorBO);
        given(directorService.updateCriteria(any(DirectorBO.class))).willAnswer(
                (invocation -> invocation.getArgument(0)));

        DirectorDtoIn updatedDirectorDTO = directorDtoIn;
        updatedDirectorDTO.setName("updated");
        updatedDirectorDTO.setAge(100);
        updatedDirectorDTO.setNationality("updated");

        ResultActions response = mockMvc.perform(
                put("/directors/update?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDirectorDTO)));

        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) updatedDirectorDTO.getId())))
                .andExpect(jsonPath("$.name", is("updated"))).andExpect(jsonPath("$.age", is(90)))
                .andExpect(jsonPath("$.nationality", is("updated")));
    }

    @DisplayName("Junit test for update a director - negative")
    @Test
    void givenDirectorDTO_whenUpdateCriteria_thenThrowServiceException() throws Exception {
        given(boToDtoConverter.directorBoToDtoOut(directorBO)).willReturn(directorDtoOut);
        given(dtoToBoConverter.directorDtoToBo(directorDtoIn)).willReturn(directorBO);
        given(directorService.findByIdCriteria(directorBO.getId())).willReturn(directorBO);
        given(directorService.updateCriteria(any(DirectorBO.class))).willThrow(new ServiceException(""));

        DirectorDtoIn updatedDirectorDTO = directorDtoIn;
        updatedDirectorDTO.setName("updated");
        updatedDirectorDTO.setAge(100);
        updatedDirectorDTO.setNationality("updated");

        ResultActions response = mockMvc.perform(
                put("/directors/update?method=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDirectorDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for delete a director by his id - positive")
    @Test
    void givenDirectorId_whenDeleteById_thenDelete() throws Exception {
        willDoNothing().given(directorService).deleteById(directorBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/directors/deleteById?method=false").param("id", String.valueOf(directorDtoIn.getId())));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @DisplayName("Junit test for delete a director by his id - negative")
    @Test
    void givenDirectorId_whenDeleteById_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException("")).given(directorService).deleteById(directorBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/directors/deleteById?method=false").param("id", String.valueOf(directorDtoIn.getId())));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for delete a director by his id - positive")
    @Test
    void givenDirectorId_whenDeleteByIdCriteria_thenDelete() throws Exception {
        willDoNothing().given(directorService).deleteByIdCriteria(directorBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/directors/deleteById?method=true").param("id", String.valueOf(directorDtoIn.getId())));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @DisplayName("Junit test for delete a director by his id - negative")
    @Test
    void givenDirectorId_whenDeleteByIdCriteria_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException("")).given(directorService).deleteByIdCriteria(directorBO.getId());

        ResultActions response = mockMvc.perform(
                delete("/directors/deleteById?method=true").param("id", String.valueOf(directorDtoIn.getId())));

        response.andDo(print()).andExpect(status().isBadRequest());
    }
}