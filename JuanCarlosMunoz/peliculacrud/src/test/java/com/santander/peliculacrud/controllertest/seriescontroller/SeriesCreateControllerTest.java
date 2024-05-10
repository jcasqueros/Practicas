package com.santander.peliculacrud.controllertest.seriescontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.santander.peliculacrud.model.input.Actor;
import com.santander.peliculacrud.model.input.Director;
import com.santander.peliculacrud.model.output.SeriesModelController;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SeriesCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActorServiceInterface actorService;

    @Autowired
    private DirectorServiceInterface directorService;

    /**
     * Test create series with valid data.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create a new series with valid data")
    public void testCreateSeries() throws Exception {

        Actor actor = actorService.getLastActor();
        Director director = directorService.getLastDirector();
        SeriesModelController seriesModelController = SeriesModelController.builder()
                .title("PRV11 Series")
                .created(2022)
                .idActor(List.of(actor.getId()))
                .idDirector(director.getId())
                .build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String seriesInJson = ow.writeValueAsString(seriesModelController);

        ResultActions response = mockMvc.perform(
                post("/series").contentType(MediaType.APPLICATION_JSON).content(seriesInJson));

        response.andDo(print()).andExpect(status().isCreated());

    }

    /**
     * Test create series no title bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create series without title")
    public void testCreateSeries_NoTitle_BadRequest() throws Exception {

        SeriesModelController seriesModelControllerNoTitle = SeriesModelController.builder().created(2022).idActor(List.of(1L, 2L)).idDirector(1L).build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String seriesInJson = ow.writeValueAsString(seriesModelControllerNoTitle);

        ResultActions response = mockMvc.perform(
                post("/series").contentType(MediaType.APPLICATION_JSON).content(seriesInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create series null series bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create series with null series")
    public void testCreateSeries_NullSeries_BadRequest() throws Exception {

        SeriesModelController seriesModelControllerNull = null;

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String seriesInJson = ow.writeValueAsString(seriesModelControllerNull);

        ResultActions response = mockMvc.perform(
                post("/series").contentType(MediaType.APPLICATION_JSON).content(seriesInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create series invalid json bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create series with invalid json")
    public void testCreateSeries_InvalidJson_BadRequest() throws Exception {

        String invalidJson = "{\"title\":\"Invalid Json\"";

        ResultActions response = mockMvc.perform(
                post("/series").contentType(MediaType.APPLICATION_JSON).content(invalidJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create series whitespace title bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create series with space title")
    public void testCreateSeries_WhitespaceTitle_BadRequest() throws Exception {

        SeriesModelController seriesModelControllerWhitespaceTitle = SeriesModelController.builder().title("   ").created(2022).idActor(List.of(1L, 2L)).idDirector(1L).build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String seriesInJson = ow.writeValueAsString(seriesModelControllerWhitespaceTitle);

        ResultActions response = mockMvc.perform(
                post("/series").contentType(MediaType.APPLICATION_JSON).content(seriesInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create series created less than 1900 bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create series with created less than 1900")
    public void testCreateSeries_CreatedLessThan1900_BadRequest() throws Exception {

        SeriesModelController seriesModelControllerCreatedLessThan1900 = SeriesModelController.builder().title("PRV11 Series").created(1800).idActor(List.of(1L, 2L)).idDirector(1L).build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String seriesInJson = ow.writeValueAsString(seriesModelControllerCreatedLessThan1900);

        ResultActions response = mockMvc.perform(
                post("/series").contentType(MediaType.APPLICATION_JSON).content(seriesInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create series empty idActor bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create series with empty idActor")
    public void testCreateSeries_EmptyIdActor_BadRequest() throws Exception {

        SeriesModelController seriesModelControllerEmptyIdActor = SeriesModelController.builder().title("PRV11 Series").created(2022).idActor(List.of()).idDirector(1L).build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String seriesInJson = ow.writeValueAsString(seriesModelControllerEmptyIdActor);

        ResultActions response = mockMvc.perform(
                post("/series").contentType(MediaType.APPLICATION_JSON).content(seriesInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test create series null idDirector bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Create series with null idDirector")
    public void testCreateSeries_NullIdDirector_BadRequest() throws Exception {

        SeriesModelController seriesModelControllerNullIdDirector = SeriesModelController.builder().title("PRV11 Series").created(2022).idActor(List.of(1L, 2L)).idDirector(null).build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String seriesInJson = ow.writeValueAsString(seriesModelControllerNullIdDirector);

        ResultActions response = mockMvc.perform(
                post("/series").contentType(MediaType.APPLICATION_JSON).content(seriesInJson));

        response.andDo(print()).andExpect(status().isBadRequest());

    }
}
