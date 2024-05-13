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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.converters.BoToDTo;
import com.example.demo.converters.DtoToBo;
import com.example.demo.presentation.dto.PeliculaDto;
import com.example.demo.repository.cb.ActorRepositoryCriteria;
import com.example.demo.repository.cb.DirectorRepositoryCriteria;
import com.example.demo.repository.cb.ProductoraRepositoryCriteria;
import com.example.demo.repository.cb.SerieRepositoryCriteria;
import com.example.demo.repository.jpa.ActorRepository;
import com.example.demo.repository.jpa.DirectorRepository;
import com.example.demo.repository.jpa.ProductoraRepository;
import com.example.demo.repository.jpa.SerieRepository;
import com.example.demo.servcice.PeliculaService;
import com.example.demo.servcice.bo.PeliculaBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManagerFactory;

@WebMvcTest
@ComponentScan(basePackages = "com.example.demo")
class PeliculaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private BoToDTo boToDTo;

	@MockBean
	private DtoToBo dtoToBo;

	@MockBean
	private PeliculaService peliculaService;

	@MockBean
	private ActorRepository actorRepository;

	@MockBean
	private ActorRepositoryCriteria actorRepositoryCriteria;

	@MockBean
	private ProductoraRepository productoraRepository;

	@MockBean
	private ProductoraRepositoryCriteria productoraRepositoryCriteria;

	@MockBean
	private SerieRepository serieRepository;

	@MockBean
	private SerieRepositoryCriteria serieRepositoryCriteria;

	@MockBean
	private DirectorRepository peliculaRepository;

	@MockBean
	private DirectorRepositoryCriteria peliculaRepositoryCriteria;

	@MockBean
	private EntityManagerFactory entityManagerFactory;

	private PeliculaBo peliculaBo;
	private PeliculaDto peliculaDto;

	@BeforeEach
	void setup() {
		peliculaBo = new PeliculaBo();
		peliculaBo.setIdPelicula(1L);

		peliculaDto = new PeliculaDto();
		peliculaDto.setIdPelicula(1L);

	}

	// JuniTest for getAll peliculas-positive
	@Test
	void testGetAllMetodoPostive() throws Exception {
		// given - precondition or setup

		given(boToDTo.boToPeliculaDto(peliculaBo)).willReturn(peliculaDto);
		given(peliculaService.getAll()).willReturn(List.of(peliculaBo));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform((get("/pelicula/getAll?metodo=true")));

		// then - verify the reslt or output asser statement
		response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(1)));
	}

	@Test
	void testGetAllMetodoNegative() throws Exception {
		// given - precondition or setup

		given(boToDTo.boToPeliculaDto(peliculaBo)).willReturn(peliculaDto);
		given(dtoToBo.dtoToPeliculaBo(peliculaDto)).willReturn(peliculaBo);
		given(peliculaService.getAllCriteria()).willReturn(List.of(peliculaBo));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform((get("/pelicula/getAll?metodo=false")));

		// then - verify the reslt or output asser statement
		response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));

	}

	@Test
	void testGetByIdPositive() throws Exception {

		// given - precondition or setup
		Long id = 1L;
		given(boToDTo.boToPeliculaDto(peliculaBo)).willReturn(peliculaDto);
		given(dtoToBo.dtoToPeliculaBo(peliculaDto)).willReturn(peliculaBo);
		given(peliculaService.getById(id)).willReturn(peliculaBo);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(get("/pelicula/getById/{id}?metodo=true", peliculaBo.getIdPelicula()));

		// then - verify the reslt or output asser statement
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.titulo", is(peliculaBo.getTitulo())))
				.andExpect(jsonPath("$.anio", is(peliculaBo.getAnio())));

	}

	@Test
	void testGetByIdNegative() throws Exception {
		Long id = 1L;
		given(boToDTo.boToPeliculaDto(peliculaBo)).willReturn(peliculaDto);
		given(dtoToBo.dtoToPeliculaBo(peliculaDto)).willReturn(peliculaBo);
		given(peliculaService.getById(id)).willReturn(null);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(get("/pelicula/getById/{id}?metodo=true", id));

		// then - verify the reslt or output asser statement
		response.andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void testGetByIdCriteriaPositive() throws Exception {
		// given - precondition or setup
		Long id = 1L;
		given(boToDTo.boToPeliculaDto(peliculaBo)).willReturn(peliculaDto);
		given(dtoToBo.dtoToPeliculaBo(peliculaDto)).willReturn(peliculaBo);
		given(peliculaService.getByIdCriteria(id)).willReturn(peliculaBo);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc
				.perform(get("/pelicula/getById/{id}?metodo=false", peliculaBo.getIdPelicula()));

		// then - verify the result or output assert statement
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.titulo", is(peliculaBo.getTitulo())))
				.andExpect(jsonPath("$.anio", is(peliculaBo.getAnio())));
	}

	@Test
	void testGetByIdCriteriaNegative() throws Exception {
		// given - precondition or setup
		Long id = 1L;
		given(boToDTo.boToPeliculaDto(peliculaBo)).willReturn(peliculaDto);
		given(dtoToBo.dtoToPeliculaBo(peliculaDto)).willReturn(peliculaBo);
		given(peliculaService.getByIdCriteria(id)).willReturn(null);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(get("/pelicula/getById/{id}?metodo=false", id));

		// then - verify the result or output assert statement
		response.andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void testSavePositive() throws JsonProcessingException, Exception {

		// given - precondition or setup
		given(boToDTo.boToPeliculaDto(peliculaBo)).willReturn(peliculaDto);
		given(dtoToBo.dtoToPeliculaBo(peliculaDto)).willReturn(peliculaBo);
		given(peliculaService.create(peliculaBo)).willAnswer((invocation) -> invocation.getArgument(0));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(post("/pelicula/save?metodo=true")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(peliculaDto)));

		// then - verify the result or output assert statement
		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.titulo", is(peliculaDto.getTitulo())))
				.andExpect(jsonPath("$.anio", is(peliculaDto.getAnio())));
	}

	@Test
	void testSaveNegative() throws JsonProcessingException, Exception {

		// given - precondition or setup
		given(boToDTo.boToPeliculaDto(peliculaBo)).willReturn(peliculaDto);
		given(dtoToBo.dtoToPeliculaBo(peliculaDto)).willReturn(peliculaBo);
		given(peliculaService.create(peliculaBo)).willThrow(new AlreadyExistsExeption("El pelicula ya existe."));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(post("/pelicula/save").param("metodo", "true")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(null)));

		// then - verify the result or output assert statement
		response.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void testDeleteByidPositive() throws Exception {
		// given - precondition or setup
		willDoNothing().given(peliculaService).deleteById(2L);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(delete("/pelicula/delete/{id}?metodo=true", 2L));

		// then - verify the result or output assert statement
		response.andDo(print()).andExpect(status().isNoContent());
	}

//	@Test
//	void testDeleteByidNegative() throws Exception {
//		// given - precondition or setup
//		willDoNothing().willThrow(new NotFoundException("No existe el pelicula")).given(peliculaService)
//				.deleteById(peliculaBo.getIdPelicula());
//
//		// when- - actrion or behaiour that we are going test
//		ResultActions response = mockMvc.perform(
//				delete("/peliculas/delete?metodo=true").param("id", String.valueOf(peliculaDto.getIdPelicula())));
//
//		// then - verify the result or output assert statement
//		response.andDo(print()).andExpect(status().isBadRequest());
//	}

//	@Test
//	void testDeleteByidCriteriaPositive() throws Exception {
//		// given - precondition or setup
//		willDoNothing().given(peliculaService).deleteById(peliculaDto.getIdPelicula());
//
//		// when- - actrion or behaiour that we are going test
//		ResultActions response = mockMvc
//				.perform(delete("/peliculas/delete/{id}?metodo=true", peliculaDto.getIdPelicula()));
//
//		// then - verify the result or output assert statement
//		response.andExpect(status().isNoContent());
//	}

//	@Test
//	void testDeleteByidCriteriaNegative() throws Exception {
//		// given - precondition or setup
//		willDoNothing().given(peliculaService).deleteById(peliculaDto.getIdPelicula());
//
//		// when- - actrion or behaiour that we are going test
//		ResultActions response = mockMvc
//				.perform(delete("/peliculas/delete/{id}?metodo=true", peliculaDto.getIdPelicula()));
//
//		// then - verify the result or output assert statement
//		response.andExpect(status().isNoContent());
//	}
}
