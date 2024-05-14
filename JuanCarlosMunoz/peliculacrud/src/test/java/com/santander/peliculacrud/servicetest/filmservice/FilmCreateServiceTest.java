/*
package com.santander.peliculacrud.servicetest.filmservice;

import com.santander.peliculacrud.model.dto.FilmDTO;
import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.model.entity.Film;
import com.santander.peliculacrud.model.bo.FilmBO;
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
public class FilmCreateServiceTest {

    @Autowired
    private FilmServiceInterface filmService;
    @Autowired
    private ActorServiceInterface actorService;
    @Autowired
    private DirectorServiceInterface directorService;

    @Test
    @DisplayName("Create a new film with valid data")
    public void testFilmCreateValidData() {

        int startSize = -1;
        List<FilmBO> films = List.of();
        try {
            startSize = filmService.getListSize();

            Actor actor = actorService.getLastActor();
            Director director = directorService.getLastDirector();

            List<Long> actorIds = List.of(actor.getId());
            Long directorIds = director.getId();

            FilmDTO film = FilmDTO.builder().title("Film Title").idActor(actorIds).idDirector(directorIds).created(1991)
                    .build();

            filmService.createFilm(film);

            assertEquals(startSize + 1, filmService.getListSize(), "There should be same size");
            Film lastFilm = filmService.getLastFilm();

            assertEquals("Film Title", lastFilm.getTitle(), "Film Title should be the same");
            assertEquals(actorIds.size(), lastFilm.getActors().size(), "There should be same actors");
            assertEquals(1991, lastFilm.getCreated(), "There should be same created");
            assertEquals(director.getName(), lastFilm.getDirector().getName(), "There should be same director");

        } catch (Exception e) {
            fail(e);
        }

    }


    @Test
    @DisplayName("Create a new film with null title")
    public void testFilmCreateNullTitle() {
        try {
            FilmDTO film = FilmDTO.builder()
                    .title(null)
                    .idActor(List.of(actorService.getLastActor().getId()))
                    .idDirector(directorService.getLastDirector().getId())
                    .created(1991)
                    .build();

            assertFalse(filmService.createFilm(film), "There should be false for not create film");


        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Create a new film with empty title")
    public void testFilmCreateEmptyTitle() {
        try {
            FilmDTO film = FilmDTO.builder()
                    .title("")
                    .idActor(List.of(actorService.getLastActor().getId()))
                    .idDirector(directorService.getLastDirector().getId())
                    .created(1991)
                    .build();

            assertFalse(filmService.createFilm(film), "There should be false for not create film");

        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Create a new film with null actors")
    public void testFilmCreateNullActors() {
        try {
            FilmDTO film = FilmDTO.builder()
                    .title("Film Title")
                    .idActor(null)
                    .idDirector(directorService.getLastDirector().getId())
                    .created(1991)
                    .build();
            int startSize = filmService.getListSize();
            assertFalse(filmService.createFilm(film), "There should be false for not create film");
            assertEquals(startSize , filmService.getListSize(), "There should be same size");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Create a new film with null director")
    public void testFilmCreateNullDirector() {
        try {
            FilmDTO film = FilmDTO.builder()
                    .title("Film Title")
                    .idActor(List.of(actorService.getLastActor().getId()))
                    .idDirector(null)
                    .created(1991)
                    .build();

            int startSize = filmService.getListSize();
            assertFalse(filmService.createFilm(film), "There should be false for not create film");
            assertEquals(startSize , filmService.getListSize(), "There should be same size");

        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }

    @Test
    @DisplayName("Create a new film with invalid created year")
    public void testFilmCreateInvalidCreatedYear() {
        try {
            FilmDTO film = FilmDTO.builder()
                    .title("Film Title")
                    .idActor(List.of(actorService.getLastActor().getId()))
                    .idDirector(directorService.getLastDirector().getId())
                    .created(-1)
                    .build();

            int startSize = filmService.getListSize();
            assertFalse(filmService.createFilm(film), "There should be false for not create film");
            assertEquals(startSize , filmService.getListSize(), "There should be same size");

        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid actor data:"));
        }
    }


}
*/
