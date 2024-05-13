package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.converters.BoToDTo;
import com.example.demo.converters.DtoToBo;
import com.example.demo.presentation.dto.DirectorDto;
import com.example.demo.repository.cb.ActorRepositoryCriteria;
import com.example.demo.repository.cb.PeliculaRepositoryCriteria;
import com.example.demo.repository.cb.ProductoraRepositoryCriteria;
import com.example.demo.repository.cb.SerieRepositoryCriteria;
import com.example.demo.repository.jpa.ActorRepository;
import com.example.demo.repository.jpa.PeliculaRepository;
import com.example.demo.repository.jpa.ProductoraRepository;
import com.example.demo.repository.jpa.SerieRepository;
import com.example.demo.servcice.DirectorService;
import com.example.demo.servcice.bo.DirectorBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManagerFactory;

@WebMvcTest
@ComponentScan(basePackages = "com.example.demo")
class DirectorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private BoToDTo boToDTo;

	@MockBean
	private DtoToBo dtoToBo;

	@MockBean
	private DirectorService directorService;

	@MockBean
	private ActorRepository ActorRepository;

	@MockBean
	private ActorRepositoryCriteria ActorRepositoryCriteria;

	@MockBean
	private ProductoraRepository productoraRepository;

	@MockBean
	private ProductoraRepositoryCriteria productoraRepositoryCriteria;

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

	private DirectorBo directorBo;
	private DirectorDto directorDto;

	@BeforeEach
	void setup() {
		directorBo = new DirectorBo(1, "prueba", 25, "Spain");
		directorDto = new DirectorDto(1, "prueba", 25, "Spain");

	}

	// JuniTest for getAll directors-positive
	@Test
	void testGetAllMetodoPostive() throws Exception {
		// given - precondition or setup

		given(boToDTo.boToDirectorDto(directorBo)).willReturn(directorDto);
		given(directorService.getAll()).willReturn(List.of(directorBo));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform((get("/director/getAll?metodo=true")));

		// then - verify the reslt or output asser statement
		response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(1)));
	}

	@Test
	void testGetAllMetodoNegative() throws Exception {
		// given - precondition or setup

		given(boToDTo.boToDirectorDto(directorBo)).willReturn(directorDto);
		given(dtoToBo.dtoToDirectorBo(directorDto)).willReturn(directorBo);
		given(directorService.getAllCriteria()).willReturn(List.of(directorBo));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform((get("/director/getAll?metodo=false")));

		// then - verify the reslt or output asser statement
		response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));

	}

	@Test
	void testGetByIdPositive() throws Exception {

		// given - precondition or setup
		Long id = 1L;
		given(boToDTo.boToDirectorDto(directorBo)).willReturn(directorDto);
		given(dtoToBo.dtoToDirectorBo(directorDto)).willReturn(directorBo);
		given(directorService.getById(id)).willReturn(directorBo);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(get("/director/getById/{id}?metodo=true", directorBo.getIdDirector()));

		// then - verify the reslt or output asser statement
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.nombre", is(directorBo.getNombre())))
				.andExpect(jsonPath("$.edad", is(directorBo.getEdad())))
				.andExpect(jsonPath("$.nacionalidad", is(directorBo.getNacionalidad())));

	}

	@Test
	void testGetByIdNegative() throws Exception {
		Long id = 1L;
		given(boToDTo.boToDirectorDto(directorBo)).willReturn(directorDto);
		given(dtoToBo.dtoToDirectorBo(directorDto)).willReturn(directorBo);
		given(directorService.getById(id)).willReturn(null);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(get("/director/getById/{id}?metodo=true", id));

		// then - verify the reslt or output asser statement
		response.andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void testGetByIdCriteriaPositive() throws Exception {
		// given - precondition or setup
		Long id = 1L;
		given(boToDTo.boToDirectorDto(directorBo)).willReturn(directorDto);
		given(dtoToBo.dtoToDirectorBo(directorDto)).willReturn(directorBo);
		given(directorService.getByIdCriteria(id)).willReturn(directorBo);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc
				.perform(get("/director/getById/{id}?metodo=false", directorBo.getIdDirector()));

		// then - verify the result or output assert statement
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.nombre", is(directorBo.getNombre())))
				.andExpect(jsonPath("$.edad", is(directorBo.getEdad())))
				.andExpect(jsonPath("$.nacionalidad", is(directorBo.getNacionalidad())));
	}

	@Test
	void testGetByIdCriteriaNegative() throws Exception {
		// given - precondition or setup
		Long id = 1L;
		given(boToDTo.boToDirectorDto(directorBo)).willReturn(directorDto);
		given(dtoToBo.dtoToDirectorBo(directorDto)).willReturn(directorBo);
		given(directorService.getByIdCriteria(id)).willReturn(null);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(get("/director/getById/{id}?metodo=false", id));

		// then - verify the result or output assert statement
		response.andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void testSavePositive() throws JsonProcessingException, Exception {

		// given - precondition or setup
		given(boToDTo.boToDirectorDto(directorBo)).willReturn(directorDto);
		given(dtoToBo.dtoToDirectorBo(directorDto)).willReturn(directorBo);
		given(directorService.create(directorBo)).willAnswer((invocation) -> invocation.getArgument(0));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(post("/director/save?metodo=true")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(directorDto)));

		// then - verify the result or output assert statement
		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.nombre", is(directorDto.getNombre())))
				.andExpect(jsonPath("$.edad", is(directorDto.getEdad())))
				.andExpect(jsonPath("$.nacionalidad", is(directorDto.getNacionalidad())));
	}

	@Test
	void testSaveNegative() throws JsonProcessingException, Exception {

		// given - precondition or setup
		given(boToDTo.boToDirectorDto(directorBo)).willReturn(directorDto);
		given(dtoToBo.dtoToDirectorBo(directorDto)).willReturn(directorBo);
		given(directorService.create(directorBo)).willThrow(new AlreadyExistsExeption("El director ya existe."));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(post("/director/save").param("metodo", "true")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(null)));

		// then - verify the result or output assert statement
		response.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void testDeleteByidPositive() throws Exception {
		// given - precondition or setup
		willDoNothing().given(directorService).deleteById(2L);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(delete("/director/delete/{id}?metodo=true", 2L));

		// then - verify the result or output assert statement
		response.andDo(print()).andExpect(status().isNoContent());
	}

//	@Test
//	void testDeleteByidNegative() throws Exception {
//		// given - precondition or setup
//		willDoNothing().willThrow(new NotFoundException("No existe el director")).given(directorService)
//				.deleteById(directorBo.getIdDirector());
//
////		given(directorService).deleteById(directorDto.getIdDirector()).willReturn(true);
//
//		// when- - actrion or behaiour that we are going test
//		ResultActions response = mockMvc.perform(
//				delete("/directors/delete?metodo=true").param("id", String.valueOf(directorDto.getIdDirector())));
//
//		// then - verify the result or output assert statement
//		response.andDo(print()).andExpect(status().isBadRequest());
//	}
////
//	@Test
//	void testDeleteByidCriteriaPositive() throws Exception {
//		// given - precondition or setup
//		willDoNothing().given(directorService).deleteById(directorDto.getIddirector());
//
//		// when- - actrion or behaiour that we are going test
//		ResultActions response = mockMvc.perform(delete("/directors/delete/{id}?method=true", directorDto.getIddirector()));
//
//		// then - verify the result or output assert statement
//		response.andExpect(status().isNoContent());
//	}
//
//	@Test
//	void testDeleteByidCriteriaNegative() throws Exception {
//		// given - precondition or setup
//		willDoNothing().given(directorService).deleteById(directorDto.getIddirector());
//
//		// when- - actrion or behaiour that we are going test
//		ResultActions response = mockMvc.perform(delete("/directors/delete/{id}?method=true", directorDto.getIddirector()));
//
//		// then - verify the result or output assert statement
//		response.andExpect(status().isNoContent());
//	}

}
