package com.santander.peliculacrud.controllertest.seriescontroller;

import com.santander.peliculacrud.model.input.Actor;
import com.santander.peliculacrud.model.input.Director;
import com.santander.peliculacrud.model.input.Series;
import com.santander.peliculacrud.model.output.SeriesModelController;
import com.santander.peliculacrud.service.ActorServiceInterface;
import com.santander.peliculacrud.service.DirectorServiceInterface;
import com.santander.peliculacrud.service.SeriesServiceInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SeriesDeleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActorServiceInterface actorService;

    @Autowired
    private DirectorServiceInterface directorService;

    @Autowired
    private SeriesServiceInterface seriesService;

    /**
     * Test delete series with valid id.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Delete a series with valid id")
    public void testDeleteSeries() throws Exception {

        Actor actor = actorService.getLastActor();
        Director director = directorService.getLastDirector();
        SeriesModelController seriesModelController = SeriesModelController.builder()
                .title("PRV11 Series")
                .created(2022)
                .idActor(List.of(actor.getId()))
                .idDirector(director.getId())
                .build();
        
        seriesService.createSeries(seriesModelController);
        Series series = seriesService.getLastSeries();

        ResultActions response = mockMvc.perform(delete("/series/"+series.getId()));

        response.andDo(print()).andExpect(status().isNoContent());

    }

    /**
     * Test delete series with invalid id not found.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Delete series with invalid id not found")
    public void testDeleteSeries_InvalidId_NotFound() throws Exception {

        try {
            ResultActions response = mockMvc.perform(delete("/series/-1"));
            Assertions.fail("Expected RuntimeException to be thrown");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Series not found"));

        }

    }

    /**
     * Test delete series with null id bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Delete series with null id bad request")
    public void testDeleteSeries_NullId_BadRequest() throws Exception {

        ResultActions response = mockMvc.perform(delete("/series/null"));

        response.andDo(print()).andExpect(status().isBadRequest());

    }

    /**
     * Test delete series with empty id bad request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Delete series with empty id bad request")
    public void testDeleteSeries_EmptyId_BadRequest() throws Exception {

        ResultActions response = mockMvc.perform(delete("/series/"));

        response.andDo(print()).andExpect(status().isNotFound());

    }
}
