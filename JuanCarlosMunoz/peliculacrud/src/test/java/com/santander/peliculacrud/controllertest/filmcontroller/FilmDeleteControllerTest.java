package com.santander.peliculacrud.controllertest.filmcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.santander.peliculacrud.model.input.Actor;
import com.santander.peliculacrud.model.input.Director;
import com.santander.peliculacrud.model.input.Film;
import com.santander.peliculacrud.model.output.FilmModelController;
import com.santander.peliculacrud.service.ActorServiceInterface;
import com.santander.peliculacrud.service.DirectorServiceInterface;
import com.santander.peliculacrud.service.FilmServiceInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmDeleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActorServiceInterface actorService;

    @Autowired
    private DirectorServiceInterface directorService;

    @Autowired
    private FilmServiceInterface filmService;

    /**
     * Test delete film with valid id.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Delete a film with valid id")
    public void testDeleteFilm() throws Exception {

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

        Film film = filmService.getLastFilm();

        response = mockMvc.perform(delete("/films/"+film.getId()));

        response.andDo(print()).andExpect(status().isNoContent());

    }

    /**
     * Test delete film with invalid id not found.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Delete film with invalid id not found")
    public void testDeleteFilm_InvalidId_NotFound() throws Exception {

        try {
            ResultActions response = mockMvc.perform(delete("/films/-1"));
            Assertions.fail("Expected RuntimeException to be thrown");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Film not found"));

        }

    }

    /**
     * Test delete film with null id bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Delete film with null id bad request")
    public void testDeleteFilm_NullId_BadRequest() throws Exception {

        ResultActions response = mockMvc.perform(delete("/films/null"));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test delete film with empty id bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Delete film with empty id bad request")
    public void testDeleteFilm_EmptyId_BadRequest() throws Exception {

        ResultActions response = mockMvc.perform(delete("/films/"));

        response.andDo(print()).andExpect(status().isNotFound());

    }
}
