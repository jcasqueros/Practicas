package com.santander.peliculacrud.servicetest.seriesservice;

import com.santander.peliculacrud.model.input.Actor;
import com.santander.peliculacrud.model.input.Director;
import com.santander.peliculacrud.model.input.Series;
import com.santander.peliculacrud.model.output.SeriesModelController;
import com.santander.peliculacrud.model.output.SeriesModelService;
import com.santander.peliculacrud.service.ActorServiceInterface;
import com.santander.peliculacrud.service.DirectorServiceInterface;
import com.santander.peliculacrud.service.SeriesServiceInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SeriesCreateServiceTest {

    @Autowired
    private SeriesServiceInterface seriesService;
    @Autowired
    private ActorServiceInterface actorService;
    @Autowired
    private DirectorServiceInterface directorService;

    @Test
    @DisplayName("Create a new series with valid data")
    public void testSeriesCreateValidData() {

        int startSize = -1;
        List<SeriesModelService> seriess = List.of();
        try {
            startSize = seriesService.getListSize();

            Actor actor = actorService.getLastActor();
            Director director = directorService.getLastDirector();

            List<Long> actorIds = List.of(actor.getId());
            Long directorIds = director.getId();

            SeriesModelController series = SeriesModelController.builder().title("Series Title").idActor(actorIds).idDirector(directorIds).created(1991)
                    .build();

            seriesService.createSeries(series);

            assertEquals(startSize + 1, seriesService.getListSize(), "There should be same size");
            Series lastSeries = seriesService.getLastSeries();

            assertEquals("Series Title", lastSeries.getTitle(), "Series Title should be the same");
            assertEquals(actorIds.size(), lastSeries.getActors().size(), "There should be same actors");
            assertEquals(1991, lastSeries.getCreated(), "There should be same created");
            assertEquals(director.getName(), lastSeries.getDirector().getName(), "There should be same director");

        } catch (Exception e) {
            fail(e);
        }

    }


    @Test
    @DisplayName("Create a new series with null title")
    public void testSeriesCreateNullTitle() {
        try {
            SeriesModelController series = SeriesModelController.builder()
                    .title(null)
                    .idActor(List.of(actorService.getLastActor().getId()))
                    .idDirector(directorService.getLastDirector().getId())
                    .created(1991)
                    .build();

            assertFalse(seriesService.createSeries(series), "There should be false for not create series");


        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Create a new series with empty title")
    public void testSeriesCreateEmptyTitle() {
        try {
            SeriesModelController series = SeriesModelController.builder()
                    .title("")
                    .idActor(List.of(actorService.getLastActor().getId()))
                    .idDirector(directorService.getLastDirector().getId())
                    .created(1991)
                    .build();

            assertFalse(seriesService.createSeries(series), "There should be false for not create series");

        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Create a new series with null actors")
    public void testSeriesCreateNullActors() {
        try {
            SeriesModelController series = SeriesModelController.builder()
                    .title("Series Title")
                    .idActor(null)
                    .idDirector(directorService.getLastDirector().getId())
                    .created(1991)
                    .build();
            int startSize = seriesService.getListSize();
            assertFalse(seriesService.createSeries(series), "There should be false for not create series");
            assertEquals(startSize , seriesService.getListSize(), "There should be same size");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Create a new series with null director")
    public void testSeriesCreateNullDirector() {
        try {
            SeriesModelController series = SeriesModelController.builder()
                    .title("Series Title")
                    .idActor(List.of(actorService.getLastActor().getId()))
                    .idDirector(null)
                    .created(1991)
                    .build();

            int startSize = seriesService.getListSize();
            assertFalse(seriesService.createSeries(series), "There should be false for not create series");
            assertEquals(startSize , seriesService.getListSize(), "There should be same size");

        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Create a new series with invalid created year")
    public void testSeriesCreateInvalidCreatedYear() {
        try {
            SeriesModelController series = SeriesModelController.builder()
                    .title("Series Title")
                    .idActor(List.of(actorService.getLastActor().getId()))
                    .idDirector(directorService.getLastDirector().getId())
                    .created(-1)
                    .build();

            int startSize = seriesService.getListSize();
            assertFalse(seriesService.createSeries(series), "There should be false for not create series");
            assertEquals(startSize , seriesService.getListSize(), "There should be same size");

        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }


}
