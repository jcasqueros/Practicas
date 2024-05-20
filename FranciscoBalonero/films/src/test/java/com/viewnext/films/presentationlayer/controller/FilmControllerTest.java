package com.viewnext.films.presentationlayer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewnext.films.businesslayer.bo.ActorBO;
import com.viewnext.films.businesslayer.bo.DirectorBO;
import com.viewnext.films.businesslayer.bo.FilmBO;
import com.viewnext.films.businesslayer.bo.ProducerBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.FilmService;
import com.viewnext.films.persistencelayer.repository.jpa.ActorJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.DirectorJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.ProducerJPARepository;
import com.viewnext.films.persistencelayer.repository.jpa.SerieJPARepository;
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
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ComponentScan(basePackages = "com.viewnext.films")
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FilmService filmService;
    @MockBean
    private ActorJPARepository actorJPARepository;
    @MockBean
    private SerieJPARepository serieJPARepository;
    @MockBean
    private DirectorJPARepository directorJpaRepository;
    @MockBean
    private ProducerJPARepository producerJPARepository;
    @MockBean
    private EntityManagerFactory entityManagerFactory;
    @MockBean
    private Converter converter;

    private FilmBO filmBO;
    private FilmInDTO filmInDTO;
    private FilmOutDTO filmOutDTO;
    private FilmUpdateDTO filmUpdateDTO;

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

        filmBO = new FilmBO();
        filmBO.setId(1L);
        filmBO.setTitle("Friends");
        filmBO.setReleaseYear(2004);
        filmBO.setDirector(directorBO);
        filmBO.setProducer(producerBO);

        List<ActorBO> actorsBO = new ArrayList<>();
        actorsBO.add(actorBO);
        filmBO.setActors(actorsBO);

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

        filmInDTO = new FilmInDTO();
        filmInDTO.setTitle("Friends");
        filmInDTO.setReleaseYear(2004);
        filmInDTO.setDirector(directorOutDTO);
        filmInDTO.setProducer(producerOutDTO);
        List<ActorOutDTO> actorsOutDTO = new ArrayList<>();
        actorsOutDTO.add(actorOutDTO);
        filmInDTO.setActors(actorsOutDTO);

        filmOutDTO = new FilmOutDTO();
        filmOutDTO.setId(1L);
        filmOutDTO.setTitle("Friends");
        filmOutDTO.setReleaseYear(2004);
        filmOutDTO.setDirector(directorOutDTO);
        filmOutDTO.setProducer(producerOutDTO);
        filmOutDTO.setActors(actorsOutDTO);

        filmUpdateDTO = new FilmUpdateDTO();
        filmUpdateDTO.setId(1L);
        filmUpdateDTO.setTitle("Enemies");
        filmUpdateDTO.setReleaseYear(2004);
        filmUpdateDTO.setDirector(directorOutDTO);
        filmUpdateDTO.setProducer(producerOutDTO);
        filmUpdateDTO.setActors(actorsOutDTO);

    }

    @Test
    @DisplayName("Criteria Get film by id : correct case")
    void givenSelectTrue_whenCriteriaGetFilmById_thenReturnFilm() throws Exception {
        given(filmService.criteriaGetById(1L)).willReturn(filmBO);
        given(converter.filmBOToOutDTO(any(FilmBO.class))).willReturn(filmOutDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/Film/getFilm?select=true&id=1"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.title", is("Friends")));
    }

    @Test
    @DisplayName("Criteria Get all films : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaGetAllFilms_thenThrowServiceException() throws Exception {
        given(converter.filmBOToOutDTO(any(FilmBO.class))).willReturn(filmOutDTO);
        given(filmService.criteriaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willThrow(
                new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/v1/Film/getAllFilms?select=true&pageNumber=0&pageSize=10&sortBy=title&sortOrder=true"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Criteria Get all films : correct case")
    void givenSelectTrue_whenCriteriaGetAllFilms_thenReturnAllFilms() throws Exception {
        given(filmService.criteriaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willReturn(List.of(filmBO));
        given(converter.filmBOToOutDTO(any(FilmBO.class))).willReturn(filmOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Film/getAllFilms?select=true&pageNumber=0&pageSize=10&sortBy=title&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("JPA Get all films : correct case")
    void givenSelectFalse_whenJPAGetAllFilms_thenReturnAllFilms() throws Exception {
        given(filmService.jpaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willReturn(List.of(filmBO));
        given(converter.filmBOToOutDTO(filmBO)).willReturn(filmOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Film/getAllFilms?select=false&pageNumber=0&pageSize=10&sortBy=title&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("JPA Get all films : incorrect case -> ServiceException")
    void givenSelectFalse_whenJPAGetAllFilms_thenThrowServiceException() throws Exception {
        given(converter.filmBOToOutDTO(any(FilmBO.class))).willReturn(filmOutDTO);
        given(filmService.jpaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/v1/Film/getAllFilms?select=false&pageNumber=0&pageSize=10&sortBy=title&sortOrder=true"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Criteria Save film : correct case")
    void givenSelectTrue_whenCriteriaSaveFilm_thenReturnSavedFilm() throws Exception {
        given(filmService.criteriaCreate(any(FilmBO.class))).willReturn(filmBO);
        given(converter.filmInDTOToBO(filmInDTO)).willReturn(filmBO);
        given(converter.filmBOToOutDTO(any(FilmBO.class))).willReturn(filmOutDTO);

        ResultActions response = mockMvc.perform(
                post("/api/v1/Film/save?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmInDTO)));

        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.title", is("Friends")));
    }

    @Test
    @DisplayName("Criteria Delete film : correct case")
    void givenSelectTrue_whenCriteriaDeleteFilm_thenDeleteFilm() throws Exception {
        willDoNothing().given(filmService).criteriaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Film/delete?select=true&id=1"));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Criteria Update film : correct case")
    void givenSelectTrue_whenCriteriaUpdateFilm_thenReturnUpdatedFilm() throws Exception {
        filmBO.setTitle(filmUpdateDTO.getTitle());
        filmOutDTO.setTitle(filmUpdateDTO.getTitle());
        given(filmService.criteriaUpdate(any(FilmBO.class))).willReturn(filmBO);
        given(converter.filmUpdateDTOToBO(filmUpdateDTO)).willReturn(filmBO);
        given(converter.filmBOToOutDTO(any(FilmBO.class))).willReturn(filmOutDTO);

        ResultActions response = mockMvc.perform(
                put("/api/v1/Film/update?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmUpdateDTO)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(filmUpdateDTO.getTitle())));
    }

    @Test
    @DisplayName("Criteria Get film by id : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaGetFilmById_thenThrowServiceException() throws Exception {
        given(filmService.criteriaGetById(1L)).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(get("/api/v1/Film/getFilm?select=true&id=1"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Criteria Save film : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaSaveFilm_thenThrowServiceException() throws Exception {
        given(converter.filmInDTOToBO(filmInDTO)).willReturn(filmBO);

        given(filmService.criteriaCreate(any(FilmBO.class))).willThrow(new ServiceException());

        ResultActions response = mockMvc.perform(
                post("/api/v1/Film/save?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmInDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Criteria Delete film : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaDeleteFilm_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException()).given(filmService).criteriaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Film/delete?select=true&id=1"));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Criteria Update film : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaUpdateFilm_thenThrowServiceException() throws Exception {
        given(filmService.criteriaCreate(any(FilmBO.class))).willReturn(filmBO);
        given(converter.filmUpdateDTOToBO(filmUpdateDTO)).willReturn(filmBO);
        given(filmService.criteriaUpdate(any(FilmBO.class))).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                put("/api/v1/Film/update?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmUpdateDTO)));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JPA Get film by id : correct case")
    void givenSelectTrue_whenJPAGetFilmById_thenReturnFilm() throws Exception {
        given(filmService.jpaGetById(1L)).willReturn(filmBO);
        given(converter.filmBOToOutDTO(any(FilmBO.class))).willReturn(filmOutDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/Film/getFilm?select=false&id=1"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.title", is("Friends")));
    }

    @Test
    @DisplayName("JPA Save film : correct case")
    void givenSelectTrue_whenJPASaveFilm_thenReturnSavedFilm() throws Exception {
        given(filmService.jpaCreate(any(FilmBO.class))).willReturn(filmBO);
        given(converter.filmInDTOToBO(filmInDTO)).willReturn(filmBO);
        given(converter.filmBOToOutDTO(any(FilmBO.class))).willReturn(filmOutDTO);

        ResultActions response = mockMvc.perform(
                post("/api/v1/Film/save?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmInDTO)));

        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.title", is("Friends")));
    }

    @Test
    @DisplayName("JPA Delete film : correct case")
    void givenSelectTrue_whenJPADeleteFilm_thenDeleteFilm() throws Exception {
        willDoNothing().given(filmService).jpaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Film/delete?select=false&id=1"));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("JPA Update film : correct case")
    void givenSelectTrue_whenJPAUpdateFilm_thenReturnUpdatedFilm() throws Exception {
        filmBO.setTitle(filmUpdateDTO.getTitle());
        filmOutDTO.setTitle(filmUpdateDTO.getTitle());
        given(filmService.jpaUpdate(any(FilmBO.class))).willReturn(filmBO);
        given(converter.filmUpdateDTOToBO(filmUpdateDTO)).willReturn(filmBO);
        given(converter.filmBOToOutDTO(any(FilmBO.class))).willReturn(filmOutDTO);

        ResultActions response = mockMvc.perform(
                put("/api/v1/Film/update?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmUpdateDTO)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(filmUpdateDTO.getTitle())));
    }

    @Test
    @DisplayName("JPA Get film by id : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPAGetFilmById_thenThrowServiceException() throws Exception {
        given(filmService.jpaGetById(1L)).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(get("/api/v1/Film/getFilm?select=false&id=1"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JPA Save film : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPASaveFilm_thenThrowServiceException() throws Exception {
        given(converter.filmInDTOToBO(filmInDTO)).willReturn(filmBO);

        given(filmService.jpaCreate(any(FilmBO.class))).willThrow(new ServiceException());

        ResultActions response = mockMvc.perform(
                post("/api/v1/Film/save?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmInDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("JPA Delete film : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPADeleteFilm_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException()).given(filmService).jpaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Film/delete?select=false&id=1"));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("JPA Update film : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPAUpdateFilm_thenThrowServiceException() throws Exception {
        given(filmService.criteriaCreate(any(FilmBO.class))).willReturn(filmBO);
        given(converter.filmUpdateDTOToBO(filmUpdateDTO)).willReturn(filmBO);
        given(filmService.jpaUpdate(any(FilmBO.class))).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                put("/api/v1/Film/update?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmUpdateDTO)));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Filter films by title, release year, director, producer, and actor")
    void filterFilms_byTitleReleaseYearDirectorProducerActor_thenReturnFilteredFilms() throws Exception {
        List<FilmBO> filmBOs = List.of(filmBO);
        given(filmService.filterFilms(List.of("Friends"), List.of(2004), List.of("James"), List.of("Paramount"),
                List.of("Jhon"), 0, 10, "id", true)).willReturn(filmBOs);
        given(converter.filmBOToOutDTO(any(FilmBO.class))).willReturn(filmOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Film/filterFilms?titles=Friends&releaseYears=2004&directors=James&producers=Paramount&actors=Jhon&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter films by title only")
    void filterFilms_byTitleOnly_thenReturnFilteredFilms() throws Exception {
        List<FilmBO> filmBOs = List.of(filmBO);
        given(filmService.filterFilms(List.of("Friends"), null, null, null, null, 0, 10, "id", true)).willReturn(
                filmBOs);
        given(converter.filmBOToOutDTO(any(FilmBO.class))).willReturn(filmOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Film/filterFilms?titles=Friends&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter films by release year only")
    void filterFilms_byReleaseYearOnly_thenReturnFilteredFilms() throws Exception {
        List<FilmBO> filmBOs = List.of(filmBO);
        given(filmService.filterFilms(null, List.of(2004), null, null, null, 0, 10, "id", true)).willReturn(filmBOs);
        given(converter.filmBOToOutDTO(any(FilmBO.class))).willReturn(filmOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Film/filterFilms?releaseYears=2004&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter films by director only")
    void filterFilms_byDirectorOnly_thenReturnFilteredFilms() throws Exception {
        List<FilmBO> filmBOs = List.of(filmBO);
        given(filmService.filterFilms(null, null, List.of("James"), null, null, 0, 10, "id", true)).willReturn(filmBOs);
        given(converter.filmBOToOutDTO(any(FilmBO.class))).willReturn(filmOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Film/filterFilms?directors=James&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter films by producer only")
    void filterFilms_byProducerOnly_thenReturnFilteredFilms() throws Exception {
        List<FilmBO> filmBOs = List.of(filmBO);
        given(filmService.filterFilms(null, null, null, List.of("Paramount"), null, 0, 10, "id", true)).willReturn(
                filmBOs);
        given(converter.filmBOToOutDTO(any(FilmBO.class))).willReturn(filmOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Film/filterFilms?producers=Paramount&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter films by actor only")
    void filterFilms_byActorOnly_thenReturnFilteredFilms() throws Exception {
        List<FilmBO> filmBOs = List.of(filmBO);
        given(filmService.filterFilms(null, null, null, null, List.of("Jhon"), 0, 10, "id", true)).willReturn(filmBOs);
        given(converter.filmBOToOutDTO(any(FilmBO.class))).willReturn(filmOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Film/filterFilms?actors=Jhon&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter films with invalid parameters")
    void filterFilms_withInvalidParameters_thenThrowServiceException() throws Exception {
        given(filmService.filterFilms(anyList(), anyList(), anyList(), anyList(), anyList(), anyInt(), anyInt(),
                anyString(), anyBoolean())).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/v1/Film/filterFilms?titles=notfound&releaseYears=1&directors=notfound&producers=notfound&actors=notfound&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

}
