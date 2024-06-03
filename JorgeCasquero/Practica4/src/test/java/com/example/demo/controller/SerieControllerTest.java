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
import com.example.demo.presentation.dto.SerieDto;
import com.example.demo.repository.cb.ActorRepositoryCriteria;
import com.example.demo.repository.cb.DirectorRepositoryCriteria;
import com.example.demo.repository.cb.PeliculaRepositoryCriteria;
import com.example.demo.repository.cb.ProductoraRepositoryCriteria;
import com.example.demo.repository.jpa.ActorRepository;
import com.example.demo.repository.jpa.DirectorRepository;
import com.example.demo.repository.jpa.PeliculaRepository;
import com.example.demo.repository.jpa.ProductoraRepository;
import com.example.demo.servcice.SerieService;
import com.example.demo.servcice.bo.SerieBo;
import com.example.demo.servcice.exception.AlreadyExistsExeption;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManagerFactory;

@WebMvcTest
@ComponentScan(basePackages = "com.example.demo")
class SerieControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private BoToDTo boToDTo;

	@MockBean
	private DtoToBo dtoToBo;

	@MockBean
	private SerieService serieService;

	@MockBean
	private ActorRepository actorRepository;

	@MockBean
	private ActorRepositoryCriteria actorRepositoryCriteria;

	@MockBean
	private ProductoraRepository productoraRepository;

	@MockBean
	private ProductoraRepositoryCriteria productoraRepositoryCriteria;

	@MockBean
	private PeliculaRepository peliculaRepository;

	@MockBean
	private PeliculaRepositoryCriteria peliculaRepositoryCriteria;

	@MockBean
	private DirectorRepository serieRepository;

	@MockBean
	private DirectorRepositoryCriteria serieRepositoryCriteria;

	@MockBean
	private EntityManagerFactory entityManagerFactory;

	private SerieBo serieBo;
	private SerieDto serieDto;

	@BeforeEach
	void setup() {
		serieBo = new SerieBo();
		serieBo.setIdSerie(1L);

		serieDto = new SerieDto();
		serieDto.setIdSerie(1L);

	}

	// JuniTest for getAll series-positive
	@Test
	void testGetAllMetodoPostive() throws Exception {
		// given - precondition or setup

		given(boToDTo.boToSerieDto(serieBo)).willReturn(serieDto);
		given(serieService.getAll()).willReturn(List.of(serieBo));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform((get("/serie/getAll?metodo=true")));

		// then - verify the reslt or output asser statement
		response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(1)));
	}

	@Test
	void testGetAllMetodoNegative() throws Exception {
		// given - precondition or setup

		given(boToDTo.boToSerieDto(serieBo)).willReturn(serieDto);
		given(dtoToBo.dtoToSerieBo(serieDto)).willReturn(serieBo);
		given(serieService.getAllCriteria()).willReturn(List.of(serieBo));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform((get("/serie/getAll?metodo=false")));

		// then - verify the reslt or output asser statement
		response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));

	}

	@Test
	void testGetByIdPositive() throws Exception {

		// given - precondition or setup
		Long id = 1L;
		given(boToDTo.boToSerieDto(serieBo)).willReturn(serieDto);
		given(dtoToBo.dtoToSerieBo(serieDto)).willReturn(serieBo);
		given(serieService.getById(id)).willReturn(serieBo);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(get("/serie/getById/{id}?metodo=true", id));

		// then - verify the reslt or output asser statement
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.titulo", is(serieBo.getTitulo())))
				.andExpect(jsonPath("$.anio", is(serieBo.getAnio())));

	}

	@Test
	void testGetByIdNegative() throws Exception {
		Long id = 1L;
		given(boToDTo.boToSerieDto(serieBo)).willReturn(serieDto);
		given(dtoToBo.dtoToSerieBo(serieDto)).willReturn(serieBo);
		given(serieService.getById(id)).willReturn(null);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(get("/serie/getById/{id}?metodo=true", id));

		// then - verify the reslt or output asser statement
		response.andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void testGetByIdCriteriaPositive() throws Exception {
		// given - precondition or setup
		Long id = 1L;
		given(boToDTo.boToSerieDto(serieBo)).willReturn(serieDto);
		given(dtoToBo.dtoToSerieBo(serieDto)).willReturn(serieBo);
		given(serieService.getByIdCriteria(id)).willReturn(serieBo);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(get("/serie/getById/{id}?metodo=false", serieBo.getIdSerie()));

		// then - verify the result or output assert statement
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.titulo", is(serieBo.getTitulo())))
				.andExpect(jsonPath("$.anio", is(serieBo.getAnio())));
	}

	@Test
	void testGetByIdCriteriaNegative() throws Exception {
		// given - precondition or setup
		Long id = 1L;
		given(boToDTo.boToSerieDto(serieBo)).willReturn(serieDto);
		given(dtoToBo.dtoToSerieBo(serieDto)).willReturn(serieBo);
		given(serieService.getByIdCriteria(id)).willReturn(null);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(get("/serie/getById/{id}?metodo=false", id));

		// then - verify the result or output assert statement
		response.andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void testSavePositive() throws JsonProcessingException, Exception {

		// given - precondition or setup
		given(boToDTo.boToSerieDto(serieBo)).willReturn(serieDto);
		given(dtoToBo.dtoToSerieBo(serieDto)).willReturn(serieBo);
		given(serieService.create(serieBo)).willAnswer((invocation) -> invocation.getArgument(0));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(post("/serie/save?metodo=true").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(serieDto)));

		// then - verify the result or output assert statement
		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.titulo", is(serieDto.getTitulo())))
				.andExpect(jsonPath("$.anio", is(serieDto.getAnio())));
	}

	@Test
	void testSaveNegative() throws JsonProcessingException, Exception {

		// given - precondition or setup
		given(boToDTo.boToSerieDto(serieBo)).willReturn(serieDto);
		given(dtoToBo.dtoToSerieBo(serieDto)).willReturn(serieBo);
		given(serieService.create(serieBo)).willThrow(new AlreadyExistsExeption("La serie ya existe."));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(post("/serie/save").param("metodo", "true")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(null)));

		// then - verify the result or output assert statement
		response.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void testDeleteByidPositive() throws Exception {
		// given - precondition or setup
		willDoNothing().given(serieService).deleteById(2L);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(delete("/serie/delete/{id}?metodo=true", 2L));

		// then - verify the result or output assert statement
		response.andDo(print()).andExpect(status().isNoContent());
	}

//	@Test
//	void testDeleteByidNegative() throws Exception {
//		// given - precondition or setup
//		willDoNothing().willThrow(new NotFoundException("No existe el serie")).given(serieService)
//				.deleteById(serieBo.getIdserie());
//
//		// when- - actrion or behaiour that we are going test
//		ResultActions response = mockMvc.perform(
//				delete("/series/delete?metodo=true").param("id", String.valueOf(serieDto.getIdserie())));
//
//		// then - verify the result or output assert statement
//		response.andDo(print()).andExpect(status().isBadRequest());
//	}

//	@Test
//	void testDeleteByidCriteriaPositive() throws Exception {
//		// given - precondition or setup
//		willDoNothing().given(serieService).deleteById(serieDto.getIdserie());
//
//		// when- - actrion or behaiour that we are going test
//		ResultActions response = mockMvc
//				.perform(delete("/series/delete/{id}?metodo=true", serieDto.getIdserie()));
//
//		// then - verify the result or output assert statement
//		response.andExpect(status().isNoContent());
//	}

//	@Test
//	void testDeleteByidCriteriaNegative() throws Exception {
//		// given - precondition or setup
//		willDoNothing().given(serieService).deleteById(serieDto.getIdserie());
//
//		// when- - actrion or behaiour that we are going test
//		ResultActions response = mockMvc
//				.perform(delete("/series/delete/{id}?metodo=true", serieDto.getIdserie()));
//
//		// then - verify the result or output assert statement
//		response.andExpect(status().isNoContent());
//	}
}
