package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.converters.BoToDTo;
import com.example.demo.converters.DtoToBo;
import com.example.demo.presentation.controllers.SerieController;
import com.example.demo.presentation.dto.SerieDto;
import com.example.demo.servcice.SerieService;
import com.example.demo.servcice.bo.SerieBo;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(SerieController.class)
class SerieControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SerieService serieService;

	@MockBean
	private BoToDTo boToDto;

	@MockBean
	private DtoToBo dtoToBo;

	private SerieDto serieDto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		serieDto = new SerieDto();
		serieDto.setIdSerie(1L);
		serieDto.setTitulo("Test Series");
	}

	@Test
	void testFindAll() throws Exception {
		SerieBo serieBo = new SerieBo(); // Crea un objeto SerieBo según tu implementación
		Page<SerieBo> page = new PageImpl<>(Collections.singletonList(serieBo));

		when(serieService.getAll(any(Pageable.class))).thenReturn(page);
		when(boToDto.boToSerieDto(any())).thenReturn(serieDto);

		mockMvc.perform(
				get("/serie/getAll").param("method", "true").param("page", "0").param("size", "5").param("sort", "id"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1L))
				.andExpect(jsonPath("$[0].name").value("Test Series"));
	}

	@Test
	void testGetById() throws Exception {
		when(serieService.getById(anyLong())).thenReturn(new SerieBo()); // Ajusta según tu implementación
		when(boToDto.boToSerieDto(any())).thenReturn(serieDto);

		mockMvc.perform(get("/serie/getById/1").param("metodo", "true")).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.name").value("Test Series"));
	}

	@Test
	void testSave() throws Exception {
		when(serieService.create(any())).thenReturn(new SerieBo()); // Ajusta según tu implementación
		when(boToDto.boToSerieDto(any())).thenReturn(serieDto);
		when(dtoToBo.dtoToSerieBo(any())).thenReturn(new SerieBo()); // Ajusta según tu implementación

		mockMvc.perform(post("/serie/save").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(serieDto)).param("metodo", "true"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.name").value("Test Series"));
	}
}
