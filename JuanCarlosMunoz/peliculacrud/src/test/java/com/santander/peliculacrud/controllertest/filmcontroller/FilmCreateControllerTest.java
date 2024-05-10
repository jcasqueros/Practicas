package com.santander.peliculacrud.controllertest.filmcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.santander.peliculacrud.model.input.Actor;
import com.santander.peliculacrud.model.input.Director;
import com.santander.peliculacrud.model.output.FilmModelController;
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

    /**
     * Test create film with valid data.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create a new film with valid data")
    public void testCreateFilm() throws Exception {

        Actor actor = actorService.getLastActor();
        Director director = directorService.getLastDirector();
        FilmModelController filmModelController = FilmModelController.builder()
                .title("PRV11 Film")
                .created(2022)
                .idActor(List.of(actor.getId()))
                .idDirector(director.getId())
                .build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String filmInJson = ow.writeValueAsString(filmModelController);

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(filmInJson));

        response.andDo(print()).andExpect(status().isCreated());

    }

    /**
     * Test create film no title bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create film without title")
    public void testCreateFilm_NoTitle_BadRequest() throws Exception {

        FilmModelController filmModelControllerNoTitle = FilmModelController.builder().created(2022).idActor(List.of(1L, 2L)).idDirector(1L).build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String filmInJson = ow.writeValueAsString(filmModelControllerNoTitle);

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(filmInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create film null film bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create film with null film")
    public void testCreateFilm_NullFilm_BadRequest() throws Exception {

        FilmModelController filmModelControllerNull = null;

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String filmInJson = ow.writeValueAsString(filmModelControllerNull);

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(filmInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create film invalid json bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create film with invalid json")
    public void testCreateFilm_InvalidJson_BadRequest() throws Exception {

        String invalidJson = "{\"title\":\"Invalid Json\"";

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(invalidJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create film whitespace title bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create film with space title")
    public void testCreateFilm_WhitespaceTitle_BadRequest() throws Exception {

        FilmModelController filmModelControllerWhitespaceTitle = FilmModelController.builder().title("   ").created(2022).idActor(List.of(1L, 2L)).idDirector(1L).build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String filmInJson = ow.writeValueAsString(filmModelControllerWhitespaceTitle);

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(filmInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create film created less than 1900 bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create film with created less than 1900")
    public void testCreateFilm_CreatedLessThan1900_BadRequest() throws Exception {

        FilmModelController filmModelControllerCreatedLessThan1900 = FilmModelController.builder().title("PRV11 Film").created(1800).idActor(List.of(1L, 2L)).idDirector(1L).build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String filmInJson = ow.writeValueAsString(filmModelControllerCreatedLessThan1900);

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(filmInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create film empty idActor bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create film with empty idActor")
    public void testCreateFilm_EmptyIdActor_BadRequest() throws Exception {

        FilmModelController filmModelControllerEmptyIdActor = FilmModelController.builder().title("PRV11 Film").created(2022).idActor(List.of()).idDirector(1L).build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String filmInJson = ow.writeValueAsString(filmModelControllerEmptyIdActor);

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(filmInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create film null idDirector bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create film with null idDirector")
    public void testCreateFilm_NullIdDirector_BadRequest() throws Exception {

        FilmModelController filmModelControllerNullIdDirector = FilmModelController.builder().title("PRV11 Film").created(2022).idActor(List.of(1L, 2L)).idDirector(null).build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String filmInJson = ow.writeValueAsString(filmModelControllerNullIdDirector);

        ResultActions response = mockMvc.perform(
                post("/films").contentType(MediaType.APPLICATION_JSON).content(filmInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }
}
