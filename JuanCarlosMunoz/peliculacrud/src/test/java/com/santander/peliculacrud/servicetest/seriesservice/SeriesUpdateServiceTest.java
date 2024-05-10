package com.santander.peliculacrud.servicetest.seriesservice;

import com.santander.peliculacrud.model.input.Series;
import com.santander.peliculacrud.model.output.SeriesModelController;
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
public class SeriesUpdateServiceTest {

    @Autowired
    private SeriesServiceInterface seriesService;
    @Autowired
    private ActorServiceInterface actorService;
    @Autowired
    private DirectorServiceInterface directorService;

    @Test
    @DisplayName("Update a series with valid data")
    public void testSeriesUpdateValidData() {
        SeriesModelController seriesModelController = SeriesModelController.builder()
                .title("Updated Series Title")
                .idActor(List.of(actorService.getLastActor().getId()))
                .idDirector(directorService.getLastDirector().getId())
                .created(1992)
                .build();

        Long seriesId = seriesService.getLastSeries().getId();

        try {
            assertTrue(seriesService.updateSeries(seriesId, seriesModelController), "Series should be updated");
            Series updatedSeries = seriesService.getSeriesById(seriesId);

            assertEquals("Updated Series Title", updatedSeries.getTitle(), "Series title should be updated");
            assertEquals(seriesModelController.getCreated(), updatedSeries.getCreated(), "Series created year should be updated");
            assertEquals(seriesModelController.getIdActor().size(), updatedSeries.getActors().size(), "Series actors should be updated");
            assertEquals(seriesModelController.getIdDirector(), updatedSeries.getDirector().getId(), "Series director should be updated");

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    @DisplayName("Update a series with null title")
    public void testSeriesUpdateNullTitle() {
        SeriesModelController seriesModelController = SeriesModelController.builder()
                .title(null)
                .idActor(List.of(actorService.getLastActor().getId()))
                .idDirector(directorService.getLastDirector().getId())
                .created(1992)
                .build();

        Long seriesId = seriesService.getLastSeries().getId();

        try {
            seriesService.updateSeries(seriesId, seriesModelController);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Update a series with empty title")
    public void testSeriesUpdateEmptyTitle() {
        SeriesModelController seriesModelController = SeriesModelController.builder()
                .title("")
                .idActor(List.of(actorService.getLastActor().getId()))
                .idDirector(directorService.getLastDirector().getId())
                .created(1992)
                .build();

        Long seriesId = seriesService.getLastSeries().getId();

        try {
            seriesService.updateSeries(seriesId, seriesModelController);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Update a series with null actors")
    public void testSeriesUpdateNullActors() {
        SeriesModelController seriesModelController = SeriesModelController.builder()
                .title("Updated Series Title")
                .idActor(null)
                .idDirector(directorService.getLastDirector().getId())
                .created(1992)
                .build();

        Long seriesId = seriesService.getLastSeries().getId();

        try {
            seriesService.updateSeries(seriesId, seriesModelController);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Update a series with null director")
    public void testSeriesUpdateNullDirector() {
        SeriesModelController seriesModelController = SeriesModelController.builder()
                .title("Updated Series Title")
                .idActor(List.of(actorService.getLastActor().getId()))
                .idDirector(null)
                .created(1992)
                .build();

        Long seriesId = seriesService.getLastSeries().getId();

        try {
            seriesService.updateSeries(seriesId, seriesModelController);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Update a series with invalid created year")
    public void testSeriesUpdateInvalidCreatedYear() {
        SeriesModelController seriesModelController = SeriesModelController.builder()
                .title("Updated Series Title")
                .idActor(List.of(actorService.getLastActor().getId()))
                .idDirector(directorService.getLastDirector().getId())
                .created(-1)
                .build();

        Long seriesId = seriesService.getLastSeries().getId();

        try {
            seriesService.updateSeries(seriesId, seriesModelController);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

}
