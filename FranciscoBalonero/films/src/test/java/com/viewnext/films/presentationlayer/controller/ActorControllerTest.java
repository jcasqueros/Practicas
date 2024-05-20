package com.viewnext.films.presentationlayer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewnext.films.businesslayer.bo.ActorBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.ActorService;
import com.viewnext.films.persistencelayer.repository.jpa.*;
import com.viewnext.films.presentationlayer.dto.ActorInDTO;
import com.viewnext.films.presentationlayer.dto.ActorOutDTO;
import com.viewnext.films.presentationlayer.dto.ActorUpdateDTO;
import com.viewnext.films.util.Converter;
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
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ComponentScan(basePackages = "com.viewnext.films")
class ActorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ActorService actorService;
    @MockBean
    private DirectorJPARepository directorJPARepository;
    @MockBean
    private FilmJPARepository filmJPARepository;
    @MockBean
    private SerieJPARepository serieJPARepository;
    @MockBean
    private ActorJPARepository actorJPARepository;
    @MockBean
    private ProducerJPARepository producerJPARepository;
    @MockBean
    private EntityManagerFactory entityManagerFactory;
    @MockBean
    private Converter converter;

    private ActorBO actorBO;
    private ActorInDTO actorInDTO;
    private ActorOutDTO actorOutDTO;
    private ActorUpdateDTO actorUpdateDTO;

    @BeforeEach
    void setup() {

        actorInDTO = new ActorInDTO("John", 30, "American");
        actorOutDTO = new ActorOutDTO(1L, "John", 30, "American");
        actorUpdateDTO = new ActorUpdateDTO(1L, "Jane", 31, "British");
        actorBO = new ActorBO(1L, "John", 30, "American");
    }

    @Test
    @DisplayName("Criteria Get actor by id : correct case")
    void givenSelectTrue_whenCriteriaGetActorById_thenReturnActor() throws Exception {
        given(actorService.criteriaGetById(1L)).willReturn(actorBO);
        given(converter.actorBOToOutDTO(any(ActorBO.class))).willReturn(actorOutDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/Actor/getActor?select=true&id=1"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name", is("John")));
    }

    @Test
    @DisplayName("Criteria Save actor : correct case")
    void givenSelectTrue_whenCriteriaSaveActor_thenReturnSavedActor() throws Exception {
        given(actorService.criteriaCreate(any(ActorBO.class))).willReturn(actorBO);
        given(converter.actorInDTOToBO(actorInDTO)).willReturn(actorBO);
        given(converter.actorBOToOutDTO(any(ActorBO.class))).willReturn(actorOutDTO);

        ResultActions response = mockMvc.perform(
                post("/api/v1/Actor/save?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorInDTO)));

        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("John")));
    }

    @Test
    @DisplayName("Criteria Delete actor : correct case")
    void givenSelectTrue_whenCriteriaDeleteActor_thenDeleteActor() throws Exception {
        willDoNothing().given(actorService).criteriaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Actor/delete?select=true&id=1"));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Criteria Update actor : correct case")
    void givenSelectTrue_whenCriteriaUpdateActor_thenReturnUpdatedActor() throws Exception {
        actorBO.setId(actorBO.getId());
        actorBO.setName(actorUpdateDTO.getName());
        actorBO.setNationality(actorUpdateDTO.getNationality());
        actorBO.setAge(actorUpdateDTO.getAge());
        actorOutDTO.setId(actorBO.getId());
        actorOutDTO.setName(actorUpdateDTO.getName());
        actorOutDTO.setNationality(actorUpdateDTO.getNationality());
        actorOutDTO.setAge(actorUpdateDTO.getAge());
        given(actorService.criteriaUpdate(any(ActorBO.class))).willReturn(actorBO);
        given(converter.actorUpdateDTOToBO(actorUpdateDTO)).willReturn(actorBO);
        given(converter.actorBOToOutDTO(any(ActorBO.class))).willReturn(actorOutDTO);

        ResultActions response = mockMvc.perform(
                put("/api/v1/Actor/update?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorUpdateDTO)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(actorUpdateDTO.getName())));
    }

    @Test
    @DisplayName("Criteria Get all actors : correct case")
    void givenSelectTrue_whenCriteriaGetAllActors_thenReturnAllActors() throws Exception {
        given(actorService.criteriaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willReturn(List.of(actorBO));
        given(converter.actorBOToOutDTO(any(ActorBO.class))).willReturn(actorOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Actor/getAllActors?select=true&pageNumber=0&pageSize=10&sortBy=name&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Criteria Get all actors : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaGetAllActors_thenThrowServiceException() throws Exception {
        given(converter.actorBOToOutDTO(any(ActorBO.class))).willReturn(actorOutDTO);
        given(actorService.criteriaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willThrow(
                new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/v1/Actor/getAllActors?select=true&pageNumber=0&pageSize=10&sortBy=name&sortOrder=true"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JPA Get all actors : correct case")
    void givenSelectFalse_whenJPAGetAllActors_thenReturnAllActors() throws Exception {
        given(actorService.jpaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willReturn(List.of(actorBO));
        given(converter.actorBOToOutDTO(actorBO)).willReturn(actorOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Actor/getAllActors?select=false&pageNumber=0&pageSize=10&sortBy=name&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("JPA Get all actors : incorrect case -> ServiceException")
    void givenSelectFalse_whenJPAGetAllActors_thenThrowServiceException() throws Exception {
        given(converter.actorBOToOutDTO(any(ActorBO.class))).willReturn(actorOutDTO);
        given(actorService.jpaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/v1/Actor/getAllActors?select=false&pageNumber=0&pageSize=10&sortBy=name&sortOrder=true"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Criteria Get actor by id : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaGetActorById_thenThrowServiceException() throws Exception {
        given(actorService.criteriaGetById(1L)).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(get("/api/v1/Actor/getActor?select=true&id=1"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Criteria Save actor : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaSaveActor_thenThrowServiceException() throws Exception {
        given(converter.actorInDTOToBO(actorInDTO)).willReturn(actorBO);

        given(actorService.criteriaCreate(any(ActorBO.class))).willThrow(new ServiceException());

        ResultActions response = mockMvc.perform(
                post("/api/v1/Actor/save?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorInDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Criteria Delete actor : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaDeleteActor_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException()).given(actorService).criteriaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Actor/delete?select=true&id=1"));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Criteria Update actor : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaUpdateActor_thenThrowServiceException() throws Exception {
        given(actorService.criteriaCreate(any(ActorBO.class))).willReturn(actorBO);
        given(converter.actorUpdateDTOToBO(actorUpdateDTO)).willReturn(actorBO);
        given(actorService.criteriaUpdate(any(ActorBO.class))).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                put("/api/v1/Actor/update?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorUpdateDTO)));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JPA Get actor by id : correct case")
    void givenSelectTrue_whenJPAGetActorById_thenReturnActor() throws Exception {
        given(actorService.jpaGetById(1L)).willReturn(actorBO);
        given(converter.actorBOToOutDTO(any(ActorBO.class))).willReturn(actorOutDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/Actor/getActor?select=false&id=1"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name", is("John")));
    }

    @Test
    @DisplayName("JPA Save actor : correct case")
    void givenSelectTrue_whenJPASaveActor_thenReturnSavedActor() throws Exception {
        given(actorService.jpaCreate(any(ActorBO.class))).willReturn(actorBO);
        given(converter.actorInDTOToBO(actorInDTO)).willReturn(actorBO);
        given(converter.actorBOToOutDTO(any(ActorBO.class))).willReturn(actorOutDTO);

        ResultActions response = mockMvc.perform(
                post("/api/v1/Actor/save?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorInDTO)));

        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("John")));
    }

    @Test
    @DisplayName("JPA Delete actor : correct case")
    void givenSelectTrue_whenJPADeleteActor_thenDeleteActor() throws Exception {
        willDoNothing().given(actorService).jpaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Actor/delete?select=false&id=1"));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("JPA Update actor : correct case")
    void givenSelectTrue_whenJPAUpdateActor_thenReturnUpdatedActor() throws Exception {
        actorBO.setId(actorUpdateDTO.getId());
        actorBO.setName(actorUpdateDTO.getName());
        actorBO.setNationality(actorUpdateDTO.getNationality());
        actorBO.setAge(actorUpdateDTO.getAge());
        actorOutDTO.setId(actorUpdateDTO.getId());
        actorOutDTO.setName(actorUpdateDTO.getName());
        actorOutDTO.setNationality(actorUpdateDTO.getNationality());
        actorOutDTO.setAge(actorUpdateDTO.getAge());
        given(actorService.jpaUpdate(any(ActorBO.class))).willReturn(actorBO);
        given(converter.actorUpdateDTOToBO(actorUpdateDTO)).willReturn(actorBO);
        given(converter.actorBOToOutDTO(any(ActorBO.class))).willReturn(actorOutDTO);

        ResultActions response = mockMvc.perform(
                put("/api/v1/Actor/update?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorUpdateDTO)));

        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("Jane")));
    }

    @Test
    @DisplayName("JPA Get actor by id : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPAGetActorById_thenThrowServiceException() throws Exception {
        given(actorService.jpaGetById(1L)).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(get("/api/v1/Actor/getActor?select=false&id=1"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JPA Save actor : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPASaveActor_thenThrowServiceException() throws Exception {
        given(converter.actorInDTOToBO(actorInDTO)).willReturn(actorBO);

        given(actorService.jpaCreate(any(ActorBO.class))).willThrow(new ServiceException());

        ResultActions response = mockMvc.perform(
                post("/api/v1/Actor/save?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorInDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("JPA Delete actor : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPADeleteActor_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException()).given(actorService).jpaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Actor/delete?select=false&id=1"));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("JPA Update actor : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPAUpdateActor_thenThrowServiceException() throws Exception {
        given(actorService.criteriaCreate(any(ActorBO.class))).willReturn(actorBO);
        given(converter.actorUpdateDTOToBO(actorUpdateDTO)).willReturn(actorBO);
        given(actorService.jpaUpdate(any(ActorBO.class))).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                put("/api/v1/Actor/update?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorUpdateDTO)));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Filter actors by name, age, and nationality : correct case")
    void filterActors_byNameAgeNationality_thenReturnFilteredActors() throws Exception {

        List<ActorBO> actorBOs = List.of(actorBO);
        given(actorService.filterActors(List.of("John"), List.of(30), List.of("American"), 0, 10, "id",
                true)).willReturn(actorBOs);
        given(converter.actorBOToOutDTO(any(ActorBO.class))).willReturn(actorOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Actor/filterActors?names=John&ages=30&nationalities=American&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter actors by name only : correct case")
    void filterActors_byNameOnly_thenReturnFilteredActors() throws Exception {

        List<ActorBO> actorBOs = List.of(actorBO);
        given(actorService.filterActors(List.of("John"), null, null, 0, 10, "id", true)).willReturn(actorBOs);
        given(converter.actorBOToOutDTO(any(ActorBO.class))).willReturn(actorOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Actor/filterActors?names=John&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter actors by age only : correct case")
    void filterActors_byAgeOnly_thenReturnFilteredActors() throws Exception {

        List<ActorBO> actorBOs = List.of(actorBO);
        given(actorService.filterActors(null, List.of(30), null, 0, 10, "id", true)).willReturn(actorBOs);
        given(converter.actorBOToOutDTO(any(ActorBO.class))).willReturn(actorOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Actor/filterActors?ages=30&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter actors by nationality only : correct case")
    void filterActors_byNationalityOnly_thenReturnFilteredActors() throws Exception {

        List<ActorBO> actorBOs = List.of(actorBO);
        given(actorService.filterActors(null, null, List.of("American"), 0, 10, "id", true)).willReturn(actorBOs);
        given(converter.actorBOToOutDTO(any(ActorBO.class))).willReturn(actorOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Actor/filterActors?nationalities=American&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter actors : incorrect case -> NotFoundException")
    void filterActors_withInvalidParameters_thenThrowServiceException() throws Exception {

        given(actorService.filterActors(anyList(), anyList(), anyList(), anyInt(), anyInt(), anyString(),
                anyBoolean())).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/v1/Actor/filterActors?names=notfound&ages=1&nationalities=notfound&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Find actors by name and age using Criteria : correct case")
    void findByNameAndAge_usingCriteria_thenReturnActors() throws Exception {
        List<ActorBO> actorBOs = List.of(actorBO);
        given(actorService.criteriaFindByNameAndAge("John", 30)).willReturn(actorBOs);
        given(converter.actorBOToOutDTO(any(ActorBO.class))).willReturn(actorOutDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/Actor/findByNameAndAge?select=true&name=John&age=30"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Find actors by name and age using JPA : correct case")
    void findByNameAndAge_usingJPA_thenReturnActors() throws Exception {
        List<ActorBO> actorBOs = List.of(actorBO);
        given(actorService.jpaFindByNameAndAge("John", 30)).willReturn(actorBOs);
        given(converter.actorBOToOutDTO(any(ActorBO.class))).willReturn(actorOutDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/Actor/findByNameAndAge?select=false&name=John&age=30"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Find actors by name and age using Criteria : incorrect case  -> NotFoundException")
    void findByNameAndAgeCriteria_withInvalidParameters_thenThrowServiceException() throws Exception {
        given(actorService.criteriaFindByNameAndAge(anyString(), anyInt())).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(get("/api/v1/Actor/findByNameAndAge?select=true&name=notfound&age=1"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Find actors by name and age using JPA : incorrect case  -> NotFoundException")
    void findByNameAndAgeJPA_withInvalidParameters_thenThrowServiceException() throws Exception {
        given(actorService.jpaFindByNameAndAge(anyString(), anyInt())).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/v1/Actor/findByNameAndAge?select=false&name=notfound&age=1"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

}
