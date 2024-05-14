package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.ActorRepository;
import com.santander.peliculacrud.model.api.DirectorRepository;
import com.santander.peliculacrud.model.api.FilmRepository;
import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.bo.FilmBO;
import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.model.entity.Film;
import com.santander.peliculacrud.model.entity.Director;

import com.santander.peliculacrud.util.mapper.FilmBOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * The type Film service test.
 */
@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @InjectMocks
    private FilmService filmService;

    @Mock
    private FilmBOMapper filmBOMapper;

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private DirectorRepository directorRepository;
    @Mock
    private ActorRepository actorRepository;

    private Director director;
    private List<Actor> actors;
    private FilmBO filmBO;
    private Film film;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        // Configuración Variables

        director = Director.builder().id(1L).name("Director").age(18).nation("ESP").build();
        actors = Collections.singletonList(Actor.builder().id(1L).name("Actor").age(18).nation("ESP").build());

        filmBO = FilmBO.builder().id(1L).title("Film").created(2020)
                .director(DirectorBO.builder().id(1L).name("Director").age(18).nation("ESP").build())
                .actors(Collections.singletonList(ActorBO.builder().id(1L).name("Actor").age(18).nation("ESP").build()))
                .build();

        film = Film.builder().id(1L).title("Film").created(2020)
                .director(Director.builder().id(1L).name("Director").age(18).nation("ESP").build())
                .actors(Collections.singletonList(Actor.builder().id(1L).name("Actor").age(18).nation("ESP").build()))
                .build();
    }

    /**
     * Test create film valid data.
     */
    @Test
    @DisplayName("Test create film with valid data")
    void testCreateFilm_ValidData() {

        //Configuración Mock
        when(directorRepository.findById(1L)).thenReturn(Optional.of(director));
        when(actorRepository.findAllById(List.of(1L))).thenReturn(actors);
        when(filmBOMapper.boToEntity(filmBO)).thenReturn(film);
        when(filmRepository.save(any(Film.class))).thenReturn(film);
        when(filmBOMapper.entityToBo(film)).thenReturn(this.filmBO);

        //Ejecución
        FilmBO result = filmService.createFilm(filmBO);

        // Assert
        assertNotNull(result);
        assertEquals("Film", result.getTitle());
        assertEquals(2020, result.getCreated());
        assertNotNull(result.getDirector());
        assertNotNull(result.getActors());
    }

    /**
     * Test create film invalid director.
     */
    @Test
    @DisplayName("Test create film with invalid director")
    void testCreateFilm_InvalidDirector() {
        // Ejecución
        when(directorRepository.findById(2L)).thenReturn(Optional.empty());

        // Assert
        assertThrows(RuntimeException.class, () -> filmService.createFilm(filmBO));
    }

    /**
     * Test create film invalid actors.
     */
    @Test
    @DisplayName("Test create film with invalid actors")
    void testCreateFilm_InvalidActors() {
        // Ejecucción

        when(actorRepository.findAllById(Arrays.asList(2L))).thenReturn(Collections.emptyList());

        //Assert
        assertThrows(RuntimeException.class, () -> filmService.createFilm(filmBO));
    }

    /**
     * Test create film data access exception.
     */
    @Test
    @DisplayName("Test create film with data access exception")
    void testCreateFilm_DataAccessException() {

        //Configuración Mock

        when(directorRepository.findById(1L)).thenReturn(
                Optional.of(Director.builder().id(1L).name("Director").build()));
        when(actorRepository.findAllById(Arrays.asList(1L))).thenReturn(
                Arrays.asList(Actor.builder().id(1L).name("Actor").build()));

        when(filmRepository.save(any(Film.class))).thenThrow(new RuntimeException("Failed to create actor: "));
        //Ejecución
        RuntimeException exception = assertThrows(RuntimeException.class, () -> filmService.createFilm(filmBO));

        //Assert
        assertEquals("Failed to create actor: ", exception.getMessage());

    }

    /**
     * Test update film valid data.
     */
    @Test
    @DisplayName("Test update film with valid data")
    void testUpdateFilm_ValidData() {
        // Arrange
        Long id = 1L;

        when(filmRepository.existsById(id)).thenReturn(true);
        filmBO.setTitle("Film Updated");
        film.setTitle("Film Updated");
        when(filmBOMapper.boToEntity(filmBO)).thenReturn(film);
        when(filmBOMapper.entityToBo(film)).thenReturn(filmBO);

        // Act
        FilmBO result = filmService.updateFilm(id, filmBO);

        // Assert
        assertNotNull(result);
        assertEquals("Film Updated", result.getTitle());
        assertEquals(2020, result.getCreated());
        assertNotNull(result.getDirector());
        assertNotNull(result.getActors());
    }

    /**
     * Test update film non existent film.
     */
    @Test
    @DisplayName("Test update film with non-existent film")
    void testUpdateFilm_NonExistentFilm() {
        // Arrange
        Long id = 2L;

        when(filmRepository.existsById(id)).thenReturn(false);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> filmService.updateFilm(id, filmBO));
    }

    /**
     * Test update film invalid director.
     */
    @Test
    @DisplayName("Test update film with invalid director")
    void testUpdateFilm_InvalidDirector() {
        // Arrange
        Long id = 1L;

        when(filmRepository.existsById(id)).thenReturn(true);

        Film filmUpdate = filmBOMapper.boToEntity(filmBO);
        filmUpdate.setId(id);

        when(filmRepository.save(any(Film.class))).thenThrow(DataAccessException.class);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> filmService.updateFilm(id, filmBO));
    }

    /**
     * Test update film invalid actors.
     */
    @Test
    @DisplayName("Test update film with invalid actors")
    @Order(8)
    void testUpdateFilm_InvalidActors() {
        // Arrange
        Long id = 1L;

        when(filmRepository.existsById(id)).thenReturn(true);

        Film filmUpdate = filmBOMapper.boToEntity(filmBO);
        filmUpdate.setId(id);

        when(filmRepository.save(any(Film.class))).thenThrow(DataAccessException.class);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> filmService.updateFilm(id, filmBO));
    }

}
