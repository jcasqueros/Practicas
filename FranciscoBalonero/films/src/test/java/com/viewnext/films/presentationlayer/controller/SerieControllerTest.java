package com.viewnext.films.presentationlayer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewnext.films.businesslayer.bo.ActorBO;
import com.viewnext.films.businesslayer.bo.DirectorBO;
import com.viewnext.films.businesslayer.bo.ProducerBO;
import com.viewnext.films.businesslayer.bo.SerieBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.SerieService;
import com.viewnext.films.persistencelayer.repository.jpa.ActorJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.DirectorJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.FilmJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.ProducerJPARepository;
import com.viewnext.films.presentationlayer.dto.*;
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
@ComponentScan(basePackages = "com.viewnext.films")
class SerieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SerieService serieService;
    @MockBean
    private ActorJPARepository actorJPARepository;
    @MockBean
    private FilmJPARepository filmJPARepository;
    @MockBean
    private DirectorJPARepository directorJpaRepository;
    @MockBean
    private ProducerJPARepository producerJPARepository;
    @MockBean
    private EntityManagerFactory entityManagerFactory;
    @MockBean
    private Converter converter;

    private SerieBO serieBO;
    private SerieInDTO serieInDTO;
    private SerieOutDTO serieOutDTO;
    private SerieUpdateDTO serieUpdateDTO;

    @BeforeEach
    void setup() {
        ActorBO actorBO = new ActorBO();
        actorBO.setId(1L);
        actorBO.setAge(18);
        actorBO.setName("Jhon");
        actorBO.setNationality("spain");

        DirectorBO directorBO = new DirectorBO();
        directorBO.setId(2L);
        directorBO.setAge(18);
        directorBO.setName("James");
        directorBO.setNationality("france");

        ProducerBO producerBO = new ProducerBO();
        producerBO.setId(1L);
        producerBO.setName("Paramount");
        producerBO.setFoundationYear(2003);

        serieBO = new SerieBO();
        serieBO.setId(1L);
        serieBO.setTitle("Friends");
        serieBO.setReleaseYear(2004);
        serieBO.setDirector(directorBO);
        serieBO.setProducer(producerBO);

        List<ActorBO> actorsBO = new ArrayList<>();
        actorsBO.add(actorBO);
        serieBO.setActors(actorsBO);

        ActorOutDTO actorOutDTO = new ActorOutDTO();
        actorOutDTO.setName("Jhon");
        actorOutDTO.setAge(18);
        actorOutDTO.setNationality("spain");

        DirectorOutDTO directorOutDTO = new DirectorOutDTO();
        directorOutDTO.setName("James");
        directorOutDTO.setAge(18);
        directorOutDTO.setNationality("france");

        ProducerOutDTO producerOutDTO = new ProducerOutDTO();
        producerOutDTO.setName("Paramount");
        producerOutDTO.setFoundationYear(2003);

        serieInDTO = new SerieInDTO();
        serieInDTO.setTitle("Friends");
        serieInDTO.setReleaseYear(2004);
        serieInDTO.setDirector(directorOutDTO);
        serieInDTO.setProducer(producerOutDTO);
        List<ActorOutDTO> actorsOutDTO = new ArrayList<>();
        actorsOutDTO.add(actorOutDTO);
        serieInDTO.setActors(actorsOutDTO);

        serieOutDTO = new SerieOutDTO();
        serieOutDTO.setId(1L);
        serieOutDTO.setTitle("Friends");
        serieOutDTO.setReleaseYear(2004);
        serieOutDTO.setDirector(directorOutDTO);
        serieOutDTO.setProducer(producerOutDTO);
        serieOutDTO.setActors(actorsOutDTO);

        serieUpdateDTO = new SerieUpdateDTO();
        serieUpdateDTO.setId(1L);
        serieUpdateDTO.setTitle("Enemies");
        serieUpdateDTO.setReleaseYear(2004);
        serieUpdateDTO.setDirector(directorOutDTO);
        serieUpdateDTO.setProducer(producerOutDTO);
        serieUpdateDTO.setActors(actorsOutDTO);

    }

    @Test
    @DisplayName("Criteria Get all series : correct case")
    void givenSelectTrue_whenCriteriaGetAllSeries_thenReturnAllSeries() throws Exception {
        given(serieService.criteriaGetAll()).willReturn(List.of(serieBO));
        given(converter.serieBOToOutDTO(any(SerieBO.class))).willReturn(serieOutDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/Serie/getAllSeries?select=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Criteria Get serie by id : correct case")
    void givenSelectTrue_whenCriteriaGetSerieById_thenReturnSerie() throws Exception {
        given(serieService.criteriaGetById(1L)).willReturn(serieBO);
        given(converter.serieBOToOutDTO(any(SerieBO.class))).willReturn(serieOutDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/Serie/getSerie?select=true&id=1"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.title", is("Friends")));
    }

    @Test
    @DisplayName("Criteria Save serie : correct case")
    void givenSelectTrue_whenCriteriaSaveSerie_thenReturnSavedSerie() throws Exception {
        given(serieService.criteriaCreate(any(SerieBO.class))).willReturn(serieBO);
        given(converter.serieInDTOToBO(serieInDTO)).willReturn(serieBO);
        given(converter.serieBOToOutDTO(any(SerieBO.class))).willReturn(serieOutDTO);

        ResultActions response = mockMvc.perform(
                post("/api/v1/Serie/save?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serieInDTO)));

        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.title", is("Friends")));
    }

    @Test
    @DisplayName("Criteria Delete serie : correct case")
    void givenSelectTrue_whenCriteriaDeleteSerie_thenDeleteSerie() throws Exception {
        willDoNothing().given(serieService).criteriaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Serie/delete?select=true&id=1"));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Criteria Update serie : correct case")
    void givenSelectTrue_whenCriteriaUpdateSerie_thenReturnUpdatedSerie() throws Exception {
        serieBO.setTitle(serieUpdateDTO.getTitle());
        serieOutDTO.setTitle(serieUpdateDTO.getTitle());
        given(serieService.criteriaUpdate(any(SerieBO.class))).willReturn(serieBO);
        given(converter.serieUpdateDTOToBO(serieUpdateDTO)).willReturn(serieBO);
        given(converter.serieBOToOutDTO(any(SerieBO.class))).willReturn(serieOutDTO);

        ResultActions response = mockMvc.perform(
                put("/api/v1/Serie/update?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serieUpdateDTO)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(serieUpdateDTO.getTitle())));
    }

    @Test
    @DisplayName("Criteria Get all series : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaGetAllSeries_thenThrowServiceException() throws Exception {
        given(converter.serieBOToOutDTO(any(SerieBO.class))).willReturn(serieOutDTO);
        given(serieService.criteriaGetAll()).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(get("/api/v1/Serie/getAllSeries?select=true"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Criteria Get serie by id : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaGetSerieById_thenThrowServiceException() throws Exception {
        given(serieService.criteriaGetById(1L)).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(get("/api/v1/Serie/getSerie?select=true&id=1"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Criteria Save serie : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaSaveSerie_thenThrowServiceException() throws Exception {
        given(converter.serieInDTOToBO(serieInDTO)).willReturn(serieBO);

        given(serieService.criteriaCreate(any(SerieBO.class))).willThrow(new ServiceException());

        ResultActions response = mockMvc.perform(
                post("/api/v1/Serie/save?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serieInDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Criteria Delete serie : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaDeleteSerie_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException()).given(serieService).criteriaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Serie/delete?select=true&id=1"));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Criteria Update serie : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaUpdateSerie_thenThrowServiceException() throws Exception {
        given(serieService.criteriaCreate(any(SerieBO.class))).willReturn(serieBO);
        given(converter.serieUpdateDTOToBO(serieUpdateDTO)).willReturn(serieBO);
        given(serieService.criteriaUpdate(any(SerieBO.class))).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                put("/api/v1/Serie/update?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serieUpdateDTO)));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JPA Get all series : correct case")
    void givenSelectTrue_whenJPAGetAllSeries_thenReturnAllSeries() throws Exception {

        given(serieService.jpaGetAll()).willReturn(List.of(serieBO));
        given(converter.serieBOToOutDTO(serieBO)).willReturn(serieOutDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/Serie/getAllSeries?select=false"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("JPA Get serie by id : correct case")
    void givenSelectTrue_whenJPAGetSerieById_thenReturnSerie() throws Exception {
        given(serieService.jpaGetById(1L)).willReturn(serieBO);
        given(converter.serieBOToOutDTO(any(SerieBO.class))).willReturn(serieOutDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/Serie/getSerie?select=false&id=1"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.title", is("Friends")));
    }

    @Test
    @DisplayName("JPA Save serie : correct case")
    void givenSelectTrue_whenJPASaveSerie_thenReturnSavedSerie() throws Exception {
        given(serieService.jpaCreate(any(SerieBO.class))).willReturn(serieBO);
        given(converter.serieInDTOToBO(serieInDTO)).willReturn(serieBO);
        given(converter.serieBOToOutDTO(any(SerieBO.class))).willReturn(serieOutDTO);

        ResultActions response = mockMvc.perform(
                post("/api/v1/Serie/save?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serieInDTO)));

        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.title", is("Friends")));
    }

    @Test
    @DisplayName("JPA Delete serie : correct case")
    void givenSelectTrue_whenJPADeleteSerie_thenDeleteSerie() throws Exception {
        willDoNothing().given(serieService).jpaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Serie/delete?select=false&id=1"));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("JPA Update serie : correct case")
    void givenSelectTrue_whenJPAUpdateSerie_thenReturnUpdatedSerie() throws Exception {
        serieBO.setTitle(serieUpdateDTO.getTitle());
        serieOutDTO.setTitle(serieUpdateDTO.getTitle());
        given(serieService.jpaUpdate(any(SerieBO.class))).willReturn(serieBO);
        given(converter.serieUpdateDTOToBO(serieUpdateDTO)).willReturn(serieBO);
        given(converter.serieBOToOutDTO(any(SerieBO.class))).willReturn(serieOutDTO);

        ResultActions response = mockMvc.perform(
                put("/api/v1/Serie/update?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serieUpdateDTO)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(serieUpdateDTO.getTitle())));
    }

    @Test
    @DisplayName("JPA Get all series : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPAGetAllSeries_thenThrowServiceException() throws Exception {
        given(converter.serieBOToOutDTO(any(SerieBO.class))).willReturn(serieOutDTO);
        given(serieService.jpaGetAll()).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(get("/api/v1/Serie/getAllSeries?select=false"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JPA Get serie by id : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPAGetSerieById_thenThrowServiceException() throws Exception {
        given(serieService.jpaGetById(1L)).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(get("/api/v1/Serie/getSerie?select=false&id=1"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JPA Save serie : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPASaveSerie_thenThrowServiceException() throws Exception {
        given(converter.serieInDTOToBO(serieInDTO)).willReturn(serieBO);

        given(serieService.jpaCreate(any(SerieBO.class))).willThrow(new ServiceException());

        ResultActions response = mockMvc.perform(
                post("/api/v1/Serie/save?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serieInDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("JPA Delete serie : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPADeleteSerie_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException()).given(serieService).jpaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Serie/delete?select=false&id=1"));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("JPA Update serie : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPAUpdateSerie_thenThrowServiceException() throws Exception {
        given(serieService.criteriaCreate(any(SerieBO.class))).willReturn(serieBO);
        given(converter.serieUpdateDTOToBO(serieUpdateDTO)).willReturn(serieBO);
        given(serieService.jpaUpdate(any(SerieBO.class))).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                put("/api/v1/Serie/update?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serieUpdateDTO)));

        response.andDo(print()).andExpect(status().isNotFound());
    }
}
