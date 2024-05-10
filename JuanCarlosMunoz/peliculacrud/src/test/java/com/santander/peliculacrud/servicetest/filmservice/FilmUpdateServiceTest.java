package com.santander.peliculacrud.servicetest.filmservice;

import com.santander.peliculacrud.model.input.Film;
import com.santander.peliculacrud.model.output.FilmModelController;
import com.santander.peliculacrud.service.ActorServiceInterface;
import com.santander.peliculacrud.service.DirectorServiceInterface;
import com.santander.peliculacrud.service.FilmServiceInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FilmUpdateServiceTest {

    @Autowired
    private FilmServiceInterface filmService;
    @Autowired
    private ActorServiceInterface actorService;
    @Autowired
    private DirectorServiceInterface directorService;

    @Test
    @DisplayName("Update a film with valid data")
    public void testFilmUpdateValidData() {
        FilmModelController filmModelController = FilmModelController.builder()
                .title("Updated Film Title")
                .idActor(List.of(actorService.getLastActor().getId()))
                .idDirector(directorService.getLastDirector().getId())
                .created(1992)
                .build();

        Long filmId = filmService.getLastFilm().getId();

        try {
            assertTrue(filmService.updateFilm(filmId, filmModelController), "Film should be updated");
            Film updatedFilm = filmService.getFilmById(filmId);

            assertEquals("Updated Film Title", updatedFilm.getTitle(), "Film title should be updated");
            assertEquals(filmModelController.getCreated(), updatedFilm.getCreated(), "Film created year should be updated");
            assertEquals(filmModelController.getIdActor().size(), updatedFilm.getActors().size(), "Film actors should be updated");
            assertEquals(filmModelController.getIdDirector(), updatedFilm.getDirector().getId(), "Film director should be updated");

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    @DisplayName("Update a film with null title")
    public void testFilmUpdateNullTitle() {
        FilmModelController filmModelController = FilmModelController.builder()
                .title(null)
                .idActor(List.of(actorService.getLastActor().getId()))
                .idDirector(directorService.getLastDirector().getId())
                .created(1992)
                .build();

        Long filmId = filmService.getLastFilm().getId();

        try {
            filmService.updateFilm(filmId, filmModelController);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Update a film with empty title")
    public void testFilmUpdateEmptyTitle() {
        FilmModelController filmModelController = FilmModelController.builder()
                .title("")
                .idActor(List.of(actorService.getLastActor().getId()))
                .idDirector(directorService.getLastDirector().getId())
                .created(1992)
                .build();

        Long filmId = filmService.getLastFilm().getId();

        try {
            filmService.updateFilm(filmId, filmModelController);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Update a film with null actors")
    public void testFilmUpdateNullActors() {
        FilmModelController filmModelController = FilmModelController.builder()
                .title("Updated Film Title")
                .idActor(null)
                .idDirector(directorService.getLastDirector().getId())
                .created(1992)
                .build();

        Long filmId = filmService.getLastFilm().getId();

        try {
            filmService.updateFilm(filmId, filmModelController);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Update a film with null director")
    public void testFilmUpdateNullDirector() {
        FilmModelController filmModelController = FilmModelController.builder()
                .title("Updated Film Title")
                .idActor(List.of(actorService.getLastActor().getId()))
                .idDirector(null)
                .created(1992)
                .build();

        Long filmId = filmService.getLastFilm().getId();

        try {
            filmService.updateFilm(filmId, filmModelController);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Update a film with invalid created year")
    public void testFilmUpdateInvalidCreatedYear() {
        FilmModelController filmModelController = FilmModelController.builder()
                .title("Updated Film Title")
                .idActor(List.of(actorService.getLastActor().getId()))
                .idDirector(directorService.getLastDirector().getId())
                .created(-1)
                .build();

        Long filmId = filmService.getLastFilm().getId();

        try {
            filmService.updateFilm(filmId, filmModelController);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

}
