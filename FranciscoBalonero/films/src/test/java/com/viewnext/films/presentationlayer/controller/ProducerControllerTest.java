package com.viewnext.films.presentationlayer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewnext.films.businesslayer.bo.ProducerBO;
import com.viewnext.films.businesslayer.exception.NotFoundException;
import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.ProducerService;
import com.viewnext.films.persistencelayer.repository.jpa.*;
import com.viewnext.films.presentationlayer.dto.ProducerInDTO;
import com.viewnext.films.presentationlayer.dto.ProducerOutDTO;
import com.viewnext.films.presentationlayer.dto.ProducerUpdateDTO;
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
class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProducerService producerService;
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

    private ProducerBO producerBO;
    private ProducerInDTO producerInDTO;
    private ProducerOutDTO producerOutDTO;
    private ProducerUpdateDTO producerUpdateDTO;

    @BeforeEach
    void setup() {
        producerBO = new ProducerBO();
        producerBO.setId(1L);
        producerBO.setName("Producer");
        producerBO.setFoundationYear(2000);
        producerInDTO = new ProducerInDTO();
        producerInDTO.setName("Producer");
        producerInDTO.setFoundationYear(200);
        producerOutDTO = new ProducerOutDTO();
        producerOutDTO.setId(1L);
        producerOutDTO.setName("Producer");
        producerOutDTO.setFoundationYear(2000);
        producerUpdateDTO = new ProducerUpdateDTO();
        producerUpdateDTO.setId(1L);
        producerUpdateDTO.setName("Updated");
        producerUpdateDTO.setFoundationYear(2000);
    }

    @Test
    @DisplayName("Criteria Get producer by id : correct case")
    void givenSelectTrue_whenCriteriaGetProducerById_thenReturnProducer() throws Exception {
        given(producerService.criteriaGetById(1L)).willReturn(producerBO);
        given(converter.producerBOToOutDTO(any(ProducerBO.class))).willReturn(producerOutDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/Producer/getProducer?select=true&id=1"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name", is(producerInDTO.getName())));
    }

    @Test
    @DisplayName("Criteria Save producer : correct case")
    void givenSelectTrue_whenCriteriaSaveProducer_thenReturnSavedProducer() throws Exception {
        given(producerService.criteriaCreate(any(ProducerBO.class))).willReturn(producerBO);
        given(converter.producerInDTOToBO(producerInDTO)).willReturn(producerBO);
        given(converter.producerBOToOutDTO(any(ProducerBO.class))).willReturn(producerOutDTO);

        ResultActions response = mockMvc.perform(
                post("/api/v1/Producer/save?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producerInDTO)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(producerInDTO.getName())));
    }

    @Test
    @DisplayName("Criteria Delete producer : correct case")
    void givenSelectTrue_whenCriteriaDeleteProducer_thenDeleteProducer() throws Exception {
        willDoNothing().given(producerService).criteriaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Producer/delete?select=true&id=1"));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Criteria Update producer : correct case")
    void givenSelectTrue_whenCriteriaUpdateProducer_thenReturnUpdatedProducer() throws Exception {
        producerBO.setName(producerUpdateDTO.getName());
        producerOutDTO.setName(producerUpdateDTO.getName());
        given(producerService.criteriaUpdate(any(ProducerBO.class))).willReturn(producerBO);
        given(converter.producerUpdateDTOToBO(producerUpdateDTO)).willReturn(producerBO);
        given(converter.producerBOToOutDTO(any(ProducerBO.class))).willReturn(producerOutDTO);

        ResultActions response = mockMvc.perform(
                put("/api/v1/Producer/update?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producerUpdateDTO)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(producerUpdateDTO.getName())));
    }

    @Test
    @DisplayName("Criteria Get all producers : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaGetAllProducers_thenThrowServiceException() throws Exception {
        given(converter.producerBOToOutDTO(any(ProducerBO.class))).willReturn(producerOutDTO);
        given(producerService.criteriaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willThrow(
                new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/v1/Producer/getAllProducers?select=true&pageNumber=0&pageSize=10&sortBy=name&sortOrder=true"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Criteria Get all producers : correct case")
    void givenSelectTrue_whenCriteriaGetAllProducers_thenReturnAllProducers() throws Exception {
        given(producerService.criteriaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willReturn(
                List.of(producerBO));
        given(converter.producerBOToOutDTO(any(ProducerBO.class))).willReturn(producerOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Producer/getAllProducers?select=true&pageNumber=0&pageSize=10&sortBy=name&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("JPA Get all producers : correct case")
    void givenSelectFalse_whenJPAGetAllProducers_thenReturnAllProducers() throws Exception {
        given(producerService.jpaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willReturn(List.of(producerBO));
        given(converter.producerBOToOutDTO(producerBO)).willReturn(producerOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Producer/getAllProducers?select=false&pageNumber=0&pageSize=10&sortBy=name&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("JPA Get all producers : incorrect case -> ServiceException")
    void givenSelectFalse_whenJPAGetAllProducers_thenThrowServiceException() throws Exception {
        given(converter.producerBOToOutDTO(any(ProducerBO.class))).willReturn(producerOutDTO);
        given(producerService.jpaGetAll(anyInt(), anyInt(), anyString(), anyBoolean())).willThrow(
                new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/v1/Producer/getAllProducers?select=false&pageNumber=0&pageSize=10&sortBy=name&sortOrder=true"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Criteria Get producer by id : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaGetProducerById_thenThrowServiceException() throws Exception {
        given(producerService.criteriaGetById(1L)).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(get("/api/v1/Producer/getProducer?select=true&id=1"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Criteria Save producer : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaSaveProducer_thenThrowServiceException() throws Exception {
        given(converter.producerInDTOToBO(producerInDTO)).willReturn(producerBO);

        given(producerService.criteriaCreate(any(ProducerBO.class))).willThrow(new ServiceException());

        ResultActions response = mockMvc.perform(
                post("/api/v1/Producer/save?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producerInDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Criteria Delete producer : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaDeleteProducer_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException()).given(producerService).criteriaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Producer/delete?select=true&id=1"));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Criteria Update producer : incorrect case -> ServiceException")
    void givenSelectTrue_whenCriteriaUpdateProducer_thenThrowServiceException() throws Exception {
        given(producerService.criteriaCreate(any(ProducerBO.class))).willReturn(producerBO);
        given(converter.producerUpdateDTOToBO(producerUpdateDTO)).willReturn(producerBO);
        given(producerService.criteriaUpdate(any(ProducerBO.class))).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                put("/api/v1/Producer/update?select=true").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producerUpdateDTO)));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JPA Get producer by id : correct case")
    void givenSelectTrue_whenJPAGetProducerById_thenReturnProducer() throws Exception {
        given(producerService.jpaGetById(1L)).willReturn(producerBO);
        given(converter.producerBOToOutDTO(any(ProducerBO.class))).willReturn(producerOutDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/Producer/getProducer?select=false&id=1"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name", is(producerInDTO.getName())));
    }

    @Test
    @DisplayName("JPA Save producer : correct case")
    void givenSelectTrue_whenJPASaveProducer_thenReturnSavedProducer() throws Exception {
        given(producerService.jpaCreate(any(ProducerBO.class))).willReturn(producerBO);
        given(converter.producerInDTOToBO(producerInDTO)).willReturn(producerBO);
        given(converter.producerBOToOutDTO(any(ProducerBO.class))).willReturn(producerOutDTO);

        ResultActions response = mockMvc.perform(
                post("/api/v1/Producer/save?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producerInDTO)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(producerInDTO.getName())));
    }

    @Test
    @DisplayName("JPA Delete producer : correct case")
    void givenSelectTrue_whenJPADeleteProducer_thenDeleteProducer() throws Exception {
        willDoNothing().given(producerService).jpaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Producer/delete?select=false&id=1"));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("JPA Update producer : correct case")
    void givenSelectTrue_whenJPAUpdateProducer_thenReturnUpdatedProducer() throws Exception {
        producerBO.setName(producerUpdateDTO.getName());
        producerOutDTO.setName(producerUpdateDTO.getName());
        given(producerService.jpaUpdate(any(ProducerBO.class))).willReturn(producerBO);
        given(converter.producerUpdateDTOToBO(producerUpdateDTO)).willReturn(producerBO);
        given(converter.producerBOToOutDTO(any(ProducerBO.class))).willReturn(producerOutDTO);

        ResultActions response = mockMvc.perform(
                put("/api/v1/Producer/update?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producerUpdateDTO)));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(producerUpdateDTO.getName())));
    }

    @Test
    @DisplayName("JPA Get producer by id : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPAGetProducerById_thenThrowServiceException() throws Exception {
        given(producerService.jpaGetById(1L)).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(get("/api/v1/Producer/getProducer?select=false&id=1"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JPA Save producer : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPASaveProducer_thenThrowServiceException() throws Exception {
        given(converter.producerInDTOToBO(producerInDTO)).willReturn(producerBO);

        given(producerService.jpaCreate(any(ProducerBO.class))).willThrow(new ServiceException());

        ResultActions response = mockMvc.perform(
                post("/api/v1/Producer/save?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producerInDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("JPA Delete producer : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPADeleteProducer_thenThrowServiceException() throws Exception {
        willThrow(new ServiceException()).given(producerService).jpaDeleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/Producer/delete?select=false&id=1"));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("JPA Update producer : incorrect case -> ServiceException")
    void givenSelectTrue_whenJPAUpdateProducer_thenThrowServiceException() throws Exception {
        given(producerService.criteriaCreate(any(ProducerBO.class))).willReturn(producerBO);
        given(converter.producerUpdateDTOToBO(producerUpdateDTO)).willReturn(producerBO);
        given(producerService.jpaUpdate(any(ProducerBO.class))).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                put("/api/v1/Producer/update?select=false").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producerUpdateDTO)));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Filter producers by name, foundation year, and sort order")
    void filterProducers_byNameFoundationYearAndSortOrder_thenReturnFilteredProducers() throws Exception {

        List<ProducerBO> producerBOs = List.of(producerBO);
        given(producerService.filterProducers(List.of("Producer"), List.of(2000), 0, 10, "id", true)).willReturn(
                producerBOs);
        given(converter.producerBOToOutDTO(any(ProducerBO.class))).willReturn(producerOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Producer/filterProducers?names=Producer&foundationYears=2000&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter producers by name only")
    void filterProducers_byNameOnly_thenReturnFilteredProducers() throws Exception {

        List<ProducerBO> producerBOs = List.of(producerBO);
        given(producerService.filterProducers(List.of("Producer"), null, 0, 10, "id", true)).willReturn(producerBOs);
        given(converter.producerBOToOutDTO(any(ProducerBO.class))).willReturn(producerOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Producer/filterProducers?names=Producer&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter producers by foundation year only")
    void filterProducers_byFoundationYearOnly_thenReturnFilteredProducers() throws Exception {

        List<ProducerBO> producerBOs = List.of(producerBO);
        given(producerService.filterProducers(null, List.of(2000), 0, 10, "id", true)).willReturn(producerBOs);
        given(converter.producerBOToOutDTO(any(ProducerBO.class))).willReturn(producerOutDTO);

        ResultActions response = mockMvc.perform(
                get("/api/v1/Producer/filterProducers?foundationYears=2000&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Filter producers with invalid parameters")
    void filterProducers_withInvalidParameters_thenThrowServiceException() throws Exception {

        given(producerService.filterProducers(anyList(), anyList(), anyInt(), anyInt(), anyString(),
                anyBoolean())).willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/v1/Producer/filterProducers?names=notfound&foundationYears=1&pageNumber=0&pageSize=10&sortBy=id&sortOrder=true"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

}
