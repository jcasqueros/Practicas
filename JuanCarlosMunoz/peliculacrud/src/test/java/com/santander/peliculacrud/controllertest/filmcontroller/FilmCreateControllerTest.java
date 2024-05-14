/*
package com.santander.peliculacrud.controllertest.filmcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.santander.peliculacrud.model.dto.FilmDTO;
import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.service.ActorServiceInterface;
import com.santander.peliculacrud.service.DirectorServiceInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActorServiceInterface actorService;

    @Autowired
    private DirectorServiceInterface directorService;

    */
/**
     * Test create film with valid data.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create a new film with valid data")
    public void testCreateFilm() throws Exception {

        Actor actor = actorService.getLastActor();
        Director director = directorService.getLastDirector();
        FilmDTO filmDTO = FilmDTO.builder()
                .title("PRV11 Film")
                .created(2022)
                .idActor(List.of(actor.getId()))
                .idDirector(director.getId())
                .build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String filmInJson = ow.writeValueAsString(filmDTO);

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(filmInJson));

        response.andDo(print()).andExpect(status().isCreated());

    }

    */
/**
     * Test create film no title bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create film without title")
    public void testCreateFilm_NoTitle_BadRequest() throws Exception {

        FilmDTO filmDTONoTitle = FilmDTO.builder().created(2022).idActor(List.of(1L, 2L)).idDirector(1L).build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String filmInJson = ow.writeValueAsString(filmDTONoTitle);

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(filmInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    */
/**
     * Test create film null film bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create film with null film")
    public void testCreateFilm_NullFilm_BadRequest() throws Exception {

        FilmDTO filmDTONull = null;

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String filmInJson = ow.writeValueAsString(filmDTONull);

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(filmInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    */
/**
     * Test create film invalid json bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create film with invalid json")
    public void testCreateFilm_InvalidJson_BadRequest() throws Exception {

        String invalidJson = "{\"title\":\"Invalid Json\"";

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(invalidJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    */
/**
     * Test create film whitespace title bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create film with space title")
    public void testCreateFilm_WhitespaceTitle_BadRequest() throws Exception {

        FilmDTO filmDTOWhitespaceTitle = FilmDTO.builder().title("   ").created(2022).idActor(List.of(1L, 2L)).idDirector(1L).build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String filmInJson = ow.writeValueAsString(filmDTOWhitespaceTitle);

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(filmInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    */
/**
     * Test create film created less than 1900 bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create film with created less than 1900")
    public void testCreateFilm_CreatedLessThan1900_BadRequest() throws Exception {

        FilmDTO filmDTOCreatedLessThan1900 = FilmDTO.builder().title("PRV11 Film").created(1800).idActor(List.of(1L, 2L)).idDirector(1L).build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String filmInJson = ow.writeValueAsString(filmDTOCreatedLessThan1900);

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(filmInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    */
/**
     * Test create film empty idActor bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create film with empty idActor")
    public void testCreateFilm_EmptyIdActor_BadRequest() throws Exception {

        FilmDTO filmDTOEmptyIdActor = FilmDTO.builder().title("PRV11 Film").created(2022).idActor(List.of()).idDirector(1L).build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String filmInJson = ow.writeValueAsString(filmDTOEmptyIdActor);

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(filmInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    */
/**
     * Test create film null idDirector bad request.
     *
     * @throws Exception
     *         the exception
     *//*

    @Test
    @DisplayName("Create film with null idDirector")
    public void testCreateFilm_NullIdDirector_BadRequest() throws Exception {

        FilmDTO filmDTONullIdDirector = FilmDTO.builder().title("PRV11 Film").created(2022).idActor(List.of(1L, 2L)).idDirector(null).build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String filmInJson = ow.writeValueAsString(filmDTONullIdDirector);

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(filmInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }
}
*/
