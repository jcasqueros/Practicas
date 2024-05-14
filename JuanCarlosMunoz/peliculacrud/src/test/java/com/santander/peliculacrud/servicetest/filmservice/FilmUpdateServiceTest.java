/*
package com.santander.peliculacrud.servicetest.filmservice;

import com.santander.peliculacrud.model.dto.FilmDTO;
import com.santander.peliculacrud.model.entity.Film;
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
        FilmDTO filmDTO = FilmDTO.builder()
                .title("Updated Film Title")
                .idActor(List.of(actorService.getLastActor().getId()))
                .idDirector(directorService.getLastDirector().getId())
                .created(1992)
                .build();

        Long filmId = filmService.getLastFilm().getId();

        try {
            assertTrue(filmService.updateFilm(filmId, filmDTO), "Film should be updated");
            Film updatedFilm = filmService.getFilmById(filmId);

            assertEquals("Updated Film Title", updatedFilm.getTitle(), "Film title should be updated");
            assertEquals(filmDTO.getCreated(), updatedFilm.getCreated(), "Film created year should be updated");
            assertEquals(filmDTO.getIdActor().size(), updatedFilm.getActors().size(), "Film actors should be updated");
            assertEquals(filmDTO.getIdDirector(), updatedFilm.getDirector().getId(), "Film director should be updated");

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    @DisplayName("Update a film with null title")
    public void testFilmUpdateNullTitle() {
        FilmDTO filmDTO = FilmDTO.builder()
                .title(null)
                .idActor(List.of(actorService.getLastActor().getId()))
                .idDirector(directorService.getLastDirector().getId())
                .created(1992)
                .build();

        Long filmId = filmService.getLastFilm().getId();

        try {
            filmService.updateFilm(filmId, filmDTO);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Update a film with empty title")
    public void testFilmUpdateEmptyTitle() {
        FilmDTO filmDTO = FilmDTO.builder()
                .title("")
                .idActor(List.of(actorService.getLastActor().getId()))
                .idDirector(directorService.getLastDirector().getId())
                .created(1992)
                .build();

        Long filmId = filmService.getLastFilm().getId();

        try {
            filmService.updateFilm(filmId, filmDTO);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Update a film with null actors")
    public void testFilmUpdateNullActors() {
        FilmDTO filmDTO = FilmDTO.builder()
                .title("Updated Film Title")
                .idActor(null)
                .idDirector(directorService.getLastDirector().getId())
                .created(1992)
                .build();

        Long filmId = filmService.getLastFilm().getId();

        try {
            filmService.updateFilm(filmId, filmDTO);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Update a film with null director")
    public void testFilmUpdateNullDirector() {
        FilmDTO filmDTO = FilmDTO.builder()
                .title("Updated Film Title")
                .idActor(List.of(actorService.getLastActor().getId()))
                .idDirector(null)
                .created(1992)
                .build();

        Long filmId = filmService.getLastFilm().getId();

        try {
            filmService.updateFilm(filmId, filmDTO);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Update a film with invalid created year")
    public void testFilmUpdateInvalidCreatedYear() {
        FilmDTO filmDTO = FilmDTO.builder()
                .title("Updated Film Title")
                .idActor(List.of(actorService.getLastActor().getId()))
                .idDirector(directorService.getLastDirector().getId())
                .created(-1)
                .build();

        Long filmId = filmService.getLastFilm().getId();

        try {
            filmService.updateFilm(filmId, filmDTO);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

}
*/
