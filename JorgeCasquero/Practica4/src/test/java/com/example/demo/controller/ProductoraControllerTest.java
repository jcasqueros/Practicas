package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.converters.BoToDTo;
import com.example.demo.converters.DtoToBo;
import com.example.demo.exception.AlreadyExistsExeption;
import com.example.demo.presentation.dto.ProductoraDto;
import com.example.demo.repository.cb.ActorRepositoryCriteria;
import com.example.demo.repository.cb.DirectorRepositoryCriteria;
import com.example.demo.repository.cb.PeliculaRepositoryCriteria;
import com.example.demo.repository.cb.SerieRepositoryCriteria;
import com.example.demo.repository.jpa.ActorRepository;
import com.example.demo.repository.jpa.DirectorRepository;
import com.example.demo.repository.jpa.PeliculaRepository;
import com.example.demo.repository.jpa.SerieRepository;
import com.example.demo.servcice.ProductoraService;
import com.example.demo.servcice.bo.ProductoraBo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManagerFactory;

@WebMvcTest
@ComponentScan(basePackages = "com.example.demo")
class ProductoraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoToDTo boToDTo;

    @MockBean
    private DtoToBo dtoToBo;

    @MockBean
    private ProductoraService productoraService;

    @MockBean
    private ActorRepository ActorRepository;

    @MockBean
    private ActorRepositoryCriteria ActorRepositoryCriteria;

    @MockBean
    private DirectorRepository productoraRepository;

    @MockBean
    private DirectorRepositoryCriteria productoraRepositoryCriteria;

    @MockBean
    private SerieRepository serieRepository;

    @MockBean
    private SerieRepositoryCriteria serieRepositoryCriteria;

    @MockBean
    private PeliculaRepository peliculaRepository;
    @MockBean
    private PeliculaRepositoryCriteria peliculaRepositoryCriteria;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    private ProductoraBo productoraBo;
    private ProductoraDto productoraDto;

    @BeforeEach
    void setup() {
        productoraBo = new ProductoraBo();
        productoraBo.setIdProductora(0);
        productoraBo.setNombre("Prueba");
        productoraBo.setAnioFundacion(188);

        productoraDto = new ProductoraDto();
        productoraDto.setIdProductora(0);
        productoraDto.setNombre("Prueba");
        productoraDto.setAnioFundacion(188);
    }

    @Test
    void testGetAllMetodoPositive() throws Exception {
        // given - precondition or setup
        List<ProductoraBo> productoraBoList = List.of(productoraBo);
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").ascending());
        Page<ProductoraBo> productoraBoPage = new PageImpl<>(productoraBoList);

        given(boToDTo.boToProductoraDto(productoraBo)).willReturn(productoraDto);
        given(productoraService.getAll(pageable)).willReturn(productoraBoPage);

        // when- - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/productora/getAll?metodo=true")
                .param("page", "0")
                .param("size", "5")
                .param("sort", "id"));

        // then - verify the result or output assert statement
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(1)));
    }

    @Test
    void testGetAllMetodoNegative() throws Exception {
        // given - precondition or setup
        List<ProductoraBo> productoraBoList = List.of(productoraBo);
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").ascending());
        Page<ProductoraBo> productoraBoPage = new PageImpl<>(productoraBoList);

        given(boToDTo.boToProductoraDto(productoraBo)).willReturn(productoraDto);
        given(dtoToBo.dtoToProductoraBo(productoraDto)).willReturn(productoraBo);
        given(productoraService.getAllCriteria(pageable)).willReturn(productoraBoPage);

        // when- - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/productora/getAll?metodo=false")
                .param("page", "0")
                .param("size", "5")
                .param("sort", "id"));

        // then - verify the result or output assert statement
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    void testGetByIdPositive() throws Exception {
        // given - precondition or setup
        Long id = 1L;
        given(boToDTo.boToProductoraDto(productoraBo)).willReturn(productoraDto);
        given(dtoToBo.dtoToProductoraBo(productoraDto)).willReturn(productoraBo);
        given(productoraService.getById(id)).willReturn(productoraBo);

        // when- - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/productora/getById/{id}?metodo=true", id));

        // then - verify the result or output assert statement
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre", is(productoraBo.getNombre())))
                .andExpect(jsonPath("$.anioFundacion", is(productoraBo.getAnioFundacion())));
    }

    @Test
    void testGetByIdNegative() throws Exception {
        Long id = 1L;
        given(boToDTo.boToProductoraDto(productoraBo)).willReturn(productoraDto);
        given(dtoToBo.dtoToProductoraBo(productoraDto)).willReturn(null);
        given(productoraService.getById(id)).willReturn(null);

        // when- - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/productora/getById/{id}?metodo=true", id));

        // then - verify the result or output assert statement
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void testGetByIdCriteriaPositive() throws Exception {
        // given - precondition or setup
        Long id = 1L;
        given(boToDTo.boToProductoraDto(productoraBo)).willReturn(productoraDto);
        given(dtoToBo.dtoToProductoraBo(productoraDto)).willReturn(productoraBo);
        given(productoraService.getByIdCriteria(id)).willReturn(productoraBo);

        // when- - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/productora/getById/{id}?metodo=false", id));

        // then - verify the result or output assert statement
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre", is(productoraBo.getNombre())))
                .andExpect(jsonPath("$.anioFundacion", is(productoraBo.getAnioFundacion())));
    }

    @Test
    void testGetByIdCriteriaNegative() throws Exception {
        // given - precondition or setup
        Long id = 1L;
        given(boToDTo.boToProductoraDto(productoraBo)).willReturn(productoraDto);
        given(dtoToBo.dtoToProductoraBo(productoraDto)).willReturn(productoraBo);
        given(productoraService.getByIdCriteria(id)).willReturn(null);

        // when- - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/productora/getById/{id}?metodo=false", id));

        // then - verify the result or output assert statement
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void testSavePositive() throws JsonProcessingException, Exception {
        // given - precondition or setup
        given(boToDTo.boToProductoraDto(productoraBo)).willReturn(productoraDto);
        given(dtoToBo.dtoToProductoraBo(productoraDto)).willReturn(productoraBo);
        given(productoraService.create(productoraBo)).willAnswer((invocation) -> invocation.getArgument(0));

        // when- - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(post("/productora/save?metodo=true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoraDto)));

        // then - verify the result or output assert statement
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is(productoraDto.getNombre())))
                .andExpect(jsonPath("$.anioFundacion", is(productoraDto.getAnioFundacion())));
    }

    @Test
    void testSaveNegative() throws JsonProcessingException, Exception {
        // given - precondition or setup
        given(boToDTo.boToProductoraDto(productoraBo)).willReturn(productoraDto);
        given(dtoToBo.dtoToProductoraBo(productoraDto)).willReturn(productoraBo);
        given(productoraService.create(productoraBo)).willThrow(new AlreadyExistsExeption("La productora ya existe."));

        // when- - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(post("/productora/save")
                .param("metodo", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)));

        // then - verify the result or output assert statement
        response.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteByidPositive() throws Exception {
        // given - precondition or setup
        willDoNothing().given(productoraService).deleteById(2L);

        // when- - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(delete("/productora/delete/{id}?metodo=true", 2L));

        // then - verify the result or output assert statement
        response.andDo(print())
                .andExpect(status().isNoContent());
    }
}
