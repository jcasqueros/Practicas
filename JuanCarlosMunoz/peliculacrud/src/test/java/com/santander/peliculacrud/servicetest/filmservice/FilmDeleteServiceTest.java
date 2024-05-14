/*
package com.santander.peliculacrud.servicetest.filmservice;

import com.santander.peliculacrud.service.FilmServiceInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FilmDeleteServiceTest {
    @Autowired
    private FilmServiceInterface filmService;

    @Test
    @DisplayName("Delete a film with valid id")
    public void testFilmDeleteValidId() {
        Long filmId = filmService.getLastFilm().getId();

        try {
            assertTrue(filmService.deleteFilm(filmId), "Film should be deleted");
            assertFalse(filmService.existsFilmById(filmId), "Film should not exist");
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    @DisplayName("Delete a film with null id")
    public void testFilmDeleteNullId() {
        try {
            filmService.deleteFilm(null);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid film id:"));
        }
    }

    @Test
    @DisplayName("Delete a film with invalid id")
    public void testFilmDeleteInvalidId() {
        try {
            filmService.deleteFilm(-1L);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Film not found"));
        }
    }

    @Test
    @DisplayName("Delete a film that does not exist")
    public void testFilmDeleteNonExistingFilm() {
        try {
            filmService.deleteFilm(999L);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Film not found"));
        }
    }



}
*/
