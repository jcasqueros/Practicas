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
import com.example.demo.exception.AlreadyExistsExeption;
import com.example.demo.presentation.dto.ActorDto;
import com.example.demo.repository.cb.DirectorRepositoryCriteria;
import com.example.demo.repository.cb.PeliculaRepositoryCriteria;
import com.example.demo.repository.cb.ProductoraRepositoryCriteria;
import com.example.demo.repository.cb.SerieRepositoryCriteria;
import com.example.demo.repository.jpa.DirectorRepository;
import com.example.demo.repository.jpa.PeliculaRepository;
import com.example.demo.repository.jpa.ProductoraRepository;
import com.example.demo.repository.jpa.SerieRepository;
import com.example.demo.servcice.ActorService;
import com.example.demo.servcice.bo.ActorBo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManagerFactory;

@WebMvcTest
@ComponentScan(basePackages = "com.example.demo")
class ActorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private BoToDTo boToDTo;

	@MockBean
	private DtoToBo dtoToBo;

	@MockBean
	private ActorService actorService;

	@MockBean
	private DirectorRepository directorRepository;

	@MockBean
	private DirectorRepositoryCriteria directorRepositoryCriteria;

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

	private ActorBo actorBo;
	private ActorDto actorDto;

	@BeforeEach
	void setup() {
		actorBo = new ActorBo(1, "prueba", 25, "Spain");
		actorDto = new ActorDto(1, "prueba", 25, "Spain");

	}

	// JuniTest for getAll Actors-positive
	@Test
	void testGetAllMetodoPostive() throws Exception {
		// given - precondition or setup

		given(boToDTo.boToActorDto(actorBo)).willReturn(actorDto);
		given(actorService.getAll()).willReturn(List.of(actorBo));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform((get("/actor/getAll?metodo=true")));

		// then - verify the reslt or output asser statement
		response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(1)));
	}

	@Test
	void testGetAllMetodoNegative() throws Exception {
		// given - precondition or setup

		given(boToDTo.boToActorDto(actorBo)).willReturn(actorDto);
		given(dtoToBo.dtoToActorBo(actorDto)).willReturn(actorBo);
		given(actorService.getAllCriteria()).willReturn(List.of(actorBo));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform((get("/actor/getAll?metodo=false")));

		// then - verify the reslt or output asser statement
		response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));

	}

	@Test
	void testGetByIdPositive() throws Exception {

		// given - precondition or setup
		Long id = 1L;
		given(boToDTo.boToActorDto(actorBo)).willReturn(actorDto);
		given(dtoToBo.dtoToActorBo(actorDto)).willReturn(actorBo);
		given(actorService.getById(id)).willReturn(actorBo);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(get("/actor/getById/{id}?metodo=true", actorBo.getIdActor()));

		// then - verify the reslt or output asser statement
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.nombre", is(actorBo.getNombre())))
				.andExpect(jsonPath("$.edad", is(actorBo.getEdad())))
				.andExpect(jsonPath("$.nacionalidad", is(actorBo.getNacionalidad())));

	}

	@Test
	void testGetByIdNegative() throws Exception {
		Long id = 1L;
		given(boToDTo.boToActorDto(actorBo)).willReturn(actorDto);
		given(dtoToBo.dtoToActorBo(actorDto)).willReturn(actorBo);
		given(actorService.getById(id)).willReturn(null);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(get("/actor/getById/{id}?metodo=true", id));

		// then - verify the reslt or output asser statement
		response.andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void testGetByIdCriteriaPositive() throws Exception {
		// given - precondition or setup
		Long id = 1L;
		given(boToDTo.boToActorDto(actorBo)).willReturn(actorDto);
		given(dtoToBo.dtoToActorBo(actorDto)).willReturn(actorBo);
		given(actorService.getByIdCriteria(id)).willReturn(actorBo);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(get("/actor/getById/{id}?metodo=false", actorBo.getIdActor()));

		// then - verify the result or output assert statement
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.nombre", is(actorBo.getNombre())))
				.andExpect(jsonPath("$.edad", is(actorBo.getEdad())))
				.andExpect(jsonPath("$.nacionalidad", is(actorBo.getNacionalidad())));
	}

	@Test
	void testGetByIdCriteriaNegative() throws Exception {
		// given - precondition or setup
		Long id = 1L;
		given(boToDTo.boToActorDto(actorBo)).willReturn(actorDto);
		given(dtoToBo.dtoToActorBo(actorDto)).willReturn(actorBo);
		given(actorService.getByIdCriteria(id)).willReturn(null);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(get("/actor/getById/{id}?metodo=false", id));

		// then - verify the result or output assert statement
		response.andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void testSavePositive() throws JsonProcessingException, Exception {

		// given - precondition or setup
		given(boToDTo.boToActorDto(actorBo)).willReturn(actorDto);
		given(dtoToBo.dtoToActorBo(actorDto)).willReturn(actorBo);
		given(actorService.create(actorBo)).willAnswer((invocation) -> invocation.getArgument(0));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(post("/actor/save?metodo=true").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(actorDto)));

		// then - verify the result or output assert statement
		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.nombre", is(actorDto.getNombre())))
				.andExpect(jsonPath("$.edad", is(actorDto.getEdad())))
				.andExpect(jsonPath("$.nacionalidad", is(actorDto.getNacionalidad())));
	}

	@Test
	void testSaveNegative() throws JsonProcessingException, Exception {

		// given - precondition or setup
		given(boToDTo.boToActorDto(actorBo)).willReturn(actorDto);
		given(dtoToBo.dtoToActorBo(actorDto)).willReturn(actorBo);
		given(actorService.create(actorBo)).willThrow(new AlreadyExistsExeption("El actor ya existe."));

		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(post("/actor/save").param("metodo", "true")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(null)));

		// then - verify the result or output assert statement
		response.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void testDeleteByidPositive() throws Exception {
		// given - precondition or setup
		willDoNothing().given(actorService).deleteById(2L);
		// when- - actrion or behaiour that we are going test
		ResultActions response = mockMvc.perform(delete("/actors/delete/{id}?metodo=true", 2L));

		// then - verify the result or output assert statement
		response.andDo(print()).andExpect(status().isNoContent());
	}

//	@Test
//	void testDeleteByidNegative() throws Exception {
//		// given - precondition or setup
//		willDoNothing().willThrow(new NotFoundException("No existe el actor")).given(actorService)
//				.deleteById(actorBo.getIdActor());
//
//		given(actorService).deleteById(actorDto.getIdActor()).willReturn(true);
//
//		// when- - actrion or behaiour that we are going test
//		ResultActions response = mockMvc
//				.perform(delete("/actors/delete?metodo=true").param("id", String.valueOf(actorDto.getIdActor())));
//
//		// then - verify the result or output assert statement
//		response.andDo(print()).andExpect(status().isBadRequest());
//	}
////
//	@Test
//	void testDeleteByidCriteriaPositive() throws Exception {
//		// given - precondition or setup
//		willDoNothing().given(actorService).deleteById(actorDto.getIdActor());
//
//		// when- - actrion or behaiour that we are going test
//		ResultActions response = mockMvc.perform(delete("/actors/delete/{id}?method=true", actorDto.getIdActor()));
//
//		// then - verify the result or output assert statement
//		response.andExpect(status().isNoContent());
//	}
//
//	@Test
//	void testDeleteByidCriteriaNegative() throws Exception {
//		// given - precondition or setup
//		willDoNothing().given(actorService).deleteById(actorDto.getIdActor());
//
//		// when- - actrion or behaiour that we are going test
//		ResultActions response = mockMvc.perform(delete("/actors/delete/{id}?method=true", actorDto.getIdActor()));
//
//		// then - verify the result or output assert statement
//		response.andExpect(status().isNoContent());
//	}

}
