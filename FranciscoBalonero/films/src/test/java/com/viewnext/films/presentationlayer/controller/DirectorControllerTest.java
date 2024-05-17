package com.viewnext.films.presentationlayer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewnext.films.businesslayer.bo.DirectorBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.DirectorService;
import com.viewnext.films.persistencelayer.repository.jpa.*;
import com.viewnext.films.presentationlayer.dto.DirectorInDTO;
import com.viewnext.films.presentationlayer.dto.DirectorOutDTO;
import com.viewnext.films.presentationlayer.dto.DirectorUpdateDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ComponentScan(basePackages = "com.viewnext.films")
class DirectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DirectorService directorService;
    @MockBean
    private ActorJPARepository actorJPARepository;
    @MockBean
    private DirectorJPARepository directorJPARepository;
    @MockBean
    private FilmJPARepository filmJPARepository;
    @MockBean
    private SerieJPARepository serieJPARepository;
    @MockBean
    private ProducerJPARepository producerJPARepository;
    @MockBean
    private EntityManagerFactory entityManagerFactory;
    @MockBean
    private Converter converter;

    private DirectorBO directorBO;
    private DirectorInDTO directorInDTO;
    private DirectorOutDTO directorOutDTO;
    private DirectorUpdateDTO directorUpdateDTO;

    @BeforeEach
    void setup() {

        directorInDTO = new DirectorInDTO("John", 30, "American");
        directorOutDTO = new DirectorOutDTO(1L, "John", 30, "American");
        directorUpdateDTO = new DirectorUpdateDTO(1L, "Jane", 31, "British");
        directorBO = new DirectorBO(1L, "John", 30, "American");
    }

    @Test
    @DisplayName("Criteria Get director by id : correct case")
    void givenSelectTrue_whenCriteriaGetDirectorById_thenReturnDirector() throws Exception {
        given(directorService.criteriaGetById(1L)).willReturn(directorBO);
        given(converter.directorBOToOutDTO(any(DirectorBO.class))).willReturn(directorOutDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/Director/getDirector?select=true&id=1"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name", is("John")));
    }

    @Test
    @DisplayName("Criteria Save director : correct case")
    void givenSelectTrue_whenCriteriaSaveDirector_thenReturnSavedDirector() throws Exception {
        given(directorService.criteriaCreate(any(DirectorBO.class))).willReturn(directorBO);
        given(converter.directorInDTOToBO(directorInDTO)).willReturn(directorBO);
        given(converter.directorBOToOutDTO(any(DirectorBO.class))).willReturn(directorOutDTO);

        ResultActions response = mockMvc.perform(
                post("/api/v1/Director/save?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorInDTO)));

        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("John")));
    }

    @Test
    @DisplayName("Criteria Delete director : correct case")
    void givenSelectTrue_whenCriteriaDeleteDirector_thenDeleteDirector() throws Exception {
        willDoNothing().given(directorService).criteriaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Director/delete?select=true&id=1"));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Criteria Update director : correct case")
    void givenSelectTrue_whenCriteriaUpdateDirector_thenReturnUpdatedDirector() throws Exception {
        directorBO.setId(directorBO.getId());
        directorBO.setName(directorUpdateDTO.getName());
        directorBO.setNationality(directorUpdateDTO.getNationality());
        directorBO.setAge(directorUpdateDTO.getAge());
        directorOutDTO.setId(directorBO.getId());
        directorOutDTO.setName(directorUpdateDTO.getName());
        directorOutDTO.setNationality(directorUpdateDTO.getNationality());
        directorOutDTO.setAge(directorUpdateDTO.getAge());
        given(directorService.criteriaUpdate(any(DirectorBO.class))).willReturn(directorBO);
        given(converter.directorUpdateDTOToBO(directorUpdateDTO)).willReturn(directorBO);
        given(converter.directorBOToOutDTO(any(DirectorBO.class))).willReturn(directorOutDTO);

        ResultActions response = mockMvc.perform(
                put("/api/v1/Director/update?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorUpdateDTO)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(directorUpdateDTO.getName())));
    }

    @Test
    @DisplayName("Criteria Get all directors : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaGetAllDirectors_thenThrowServiceException() throws Exception {
        given(converter.directorBOToOutDTO(any(DirectorBO.class))).willReturn(directorOutDTO);
        given(directorService.criteriaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willThrow(
                new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/v1/Director/getAllDirectors?select=true&pageNumber=0&pageSize=10&sortBy=name&sortOrder=true"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Criteria Get all directors : correct case")
    void givenSelectTrue_whenCriteriaGetAllDirectors_thenReturnAllDirectors() throws Exception {
        given(directorService.criteriaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willReturn(
                List.of(directorBO));
        given(converter.directorBOToOutDTO(any(DirectorBO.class))).willReturn(directorOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Director/getAllDirectors?select=true&pageNumber=0&pageSize=10&sortBy=name&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("JPA Get all directors : correct case")
    void givenSelectFalse_whenJPAGetAllDirectors_thenReturnAllDirectors() throws Exception {
        given(directorService.jpaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willReturn(List.of(directorBO));
        given(converter.directorBOToOutDTO(directorBO)).willReturn(directorOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Director/getAllDirectors?select=false&pageNumber=0&pageSize=10&sortBy=name&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("JPA Get all directors : incorrect case -> ServiceException")
    void givenSelectFalse_whenJPAGetAllDirectors_thenThrowServiceException() throws Exception {
        given(converter.directorBOToOutDTO(any(DirectorBO.class))).willReturn(directorOutDTO);
        given(directorService.jpaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willThrow(
                new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/v1/Director/getAllDirectors?select=false&pageNumber=0&pageSize=10&sortBy=name&sortOrder=true"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Criteria Get director by id : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaGetDirectorById_thenThrowServiceException() throws Exception {
        given(directorService.criteriaGetById(1L)).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(get("/api/v1/Director/getDirector?select=true&id=1"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Criteria Save director : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaSaveDirector_thenThrowServiceException() throws Exception {
        given(converter.directorInDTOToBO(directorInDTO)).willReturn(directorBO);

        given(directorService.criteriaCreate(any(DirectorBO.class))).willThrow(new ServiceException());

        ResultActions response = mockMvc.perform(
                post("/api/v1/Director/save?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorInDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Criteria Delete director : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaDeleteDirector_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException()).given(directorService).criteriaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Director/delete?select=true&id=1"));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Criteria Update director : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaUpdateDirector_thenThrowServiceException() throws Exception {
        given(directorService.criteriaCreate(any(DirectorBO.class))).willReturn(directorBO);
        given(converter.directorUpdateDTOToBO(directorUpdateDTO)).willReturn(directorBO);
        given(directorService.criteriaUpdate(any(DirectorBO.class))).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                put("/api/v1/Director/update?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorUpdateDTO)));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JPA Get director by id : correct case")
    void givenSelectTrue_whenJPAGetDirectorById_thenReturnDirector() throws Exception {
        given(directorService.jpaGetById(1L)).willReturn(directorBO);
        given(converter.directorBOToOutDTO(any(DirectorBO.class))).willReturn(directorOutDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/Director/getDirector?select=false&id=1"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name", is("John")));
    }

    @Test
    @DisplayName("JPA Save director : correct case")
    void givenSelectTrue_whenJPASaveDirector_thenReturnSavedDirector() throws Exception {
        given(directorService.jpaCreate(any(DirectorBO.class))).willReturn(directorBO);
        given(converter.directorInDTOToBO(directorInDTO)).willReturn(directorBO);
        given(converter.directorBOToOutDTO(any(DirectorBO.class))).willReturn(directorOutDTO);

        ResultActions response = mockMvc.perform(
                post("/api/v1/Director/save?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorInDTO)));

        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("John")));
    }

    @Test
    @DisplayName("JPA Delete director : correct case")
    void givenSelectTrue_whenJPADeleteDirector_thenDeleteDirector() throws Exception {
        willDoNothing().given(directorService).jpaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Director/delete?select=false&id=1"));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("JPA Update director : correct case")
    void givenSelectTrue_whenJPAUpdateDirector_thenReturnUpdatedDirector() throws Exception {
        directorBO.setId(directorUpdateDTO.getId());
        directorBO.setName(directorUpdateDTO.getName());
        directorBO.setNationality(directorUpdateDTO.getNationality());
        directorBO.setAge(directorUpdateDTO.getAge());
        directorOutDTO.setId(directorUpdateDTO.getId());
        directorOutDTO.setName(directorUpdateDTO.getName());
        directorOutDTO.setNationality(directorUpdateDTO.getNationality());
        directorOutDTO.setAge(directorUpdateDTO.getAge());
        given(directorService.jpaUpdate(any(DirectorBO.class))).willReturn(directorBO);
        given(converter.directorUpdateDTOToBO(directorUpdateDTO)).willReturn(directorBO);
        given(converter.directorBOToOutDTO(any(DirectorBO.class))).willReturn(directorOutDTO);

        ResultActions response = mockMvc.perform(
                put("/api/v1/Director/update?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorUpdateDTO)));

        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("Jane")));
    }

    @Test
    @DisplayName("JPA Get director by id : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPAGetDirectorById_thenThrowServiceException() throws Exception {
        given(directorService.jpaGetById(1L)).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(get("/api/v1/Director/getDirector?select=false&id=1"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JPA Save director : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPASaveDirector_thenThrowServiceException() throws Exception {
        given(converter.directorInDTOToBO(directorInDTO)).willReturn(directorBO);

        given(directorService.jpaCreate(any(DirectorBO.class))).willThrow(new ServiceException());

        ResultActions response = mockMvc.perform(
                post("/api/v1/Director/save?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorInDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("JPA Delete director : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPADeleteDirector_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException()).given(directorService).jpaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Director/delete?select=false&id=1"));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("JPA Update director : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPAUpdateDirector_thenThrowServiceException() throws Exception {
        given(directorService.criteriaCreate(any(DirectorBO.class))).willReturn(directorBO);
        given(converter.directorUpdateDTOToBO(directorUpdateDTO)).willReturn(directorBO);
        given(directorService.jpaUpdate(any(DirectorBO.class))).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                put("/api/v1/Director/update?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorUpdateDTO)));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Filter directors by name, age, and nationality : correct case")
    void filterDirectors_byNameAgeNationality_thenReturnFilteredDirectors() throws Exception {

        List<DirectorBO> directorBOs = List.of(directorBO);
        given(directorService.filterDirectors(List.of("John"), List.of(30), List.of("American"), 0, 10, "id",
                true)).willReturn(directorBOs);
        given(converter.directorBOToOutDTO(any(DirectorBO.class))).willReturn(directorOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Director/filterDirectors?names=John&ages=30&nationalities=American&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter directors by name only : correct case")
    void filterDirectors_byNameOnly_thenReturnFilteredDirectors() throws Exception {

        List<DirectorBO> directorBOs = List.of(directorBO);
        given(directorService.filterDirectors(List.of("John"), null, null, 0, 10, "id", true)).willReturn(directorBOs);
        given(converter.directorBOToOutDTO(any(DirectorBO.class))).willReturn(directorOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Director/filterDirectors?names=John&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter directors by age only : correct case")
    void filterDirectors_byAgeOnly_thenReturnFilteredDirectors() throws Exception {

        List<DirectorBO> directorBOs = List.of(directorBO);
        given(directorService.filterDirectors(null, List.of(30), null, 0, 10, "id", true)).willReturn(directorBOs);
        given(converter.directorBOToOutDTO(any(DirectorBO.class))).willReturn(directorOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Director/filterDirectors?ages=30&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter directors by nationality only : correct case")
    void filterDirectors_byNationalityOnly_thenReturnFilteredDirectors() throws Exception {

        List<DirectorBO> directorBOs = List.of(directorBO);
        given(directorService.filterDirectors(null, null, List.of("American"), 0, 10, "id", true)).willReturn(
                directorBOs);
        given(converter.directorBOToOutDTO(any(DirectorBO.class))).willReturn(directorOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Director/filterDirectors?nationalities=American&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter directors : incorrect case -> NotFoundException")
    void filterDirectors_withInvalidParameters_thenThrowServiceException() throws Exception {

        given(directorService.filterDirectors(anyList(), anyList(), anyList(), anyInt(), anyInt(), anyString(),
                anyBoolean())).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/v1/Director/filterDirectors?names=notfound&ages=1&nationalities=notfound&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Find directors by name and age using Criteria : correct case")
    void findByNameAndAge_usingCriteria_thenReturnDirectors() throws Exception {
        List<DirectorBO> directorBOs = List.of(directorBO);
        given(directorService.criteriaFindByNameAndAge("John", 30)).willReturn(directorBOs);
        given(converter.directorBOToOutDTO(any(DirectorBO.class))).willReturn(directorOutDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/Director/findByNameAndAge?select=true&name=John&age=30"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Find directors by name and age using JPA : correct case")
    void findByNameAndAge_usingJPA_thenReturnDirectors() throws Exception {
        List<DirectorBO> directorBOs = List.of(directorBO);
        given(directorService.jpaFindByNameAndAge("John", 30)).willReturn(directorBOs);
        given(converter.directorBOToOutDTO(any(DirectorBO.class))).willReturn(directorOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Director/findByNameAndAge?select=false&name=John&age=30"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Find directors by name and age using Criteria : incorrect case  -> NotFoundException")
    void findByNameAndAgeCriteria_withInvalidParameters_thenThrowServiceException() throws Exception {
        given(directorService.criteriaFindByNameAndAge(anyString(), anyInt())).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/v1/Director/findByNameAndAge?select=true&name=notfound&age=1"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Find directors by name and age using JPA : incorrect case  -> NotFoundException")
    void findByNameAndAgeJPA_withInvalidParameters_thenThrowServiceException() throws Exception {
        given(directorService.jpaFindByNameAndAge(anyString(), anyInt())).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/v1/Director/findByNameAndAge?select=false&name=notfound&age=1"));

        response.andDo(print()).andExpect(status().isNotFound());
    }
}
