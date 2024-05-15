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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The type Film service test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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
        //Variable auxiliar
        FilmBO filmBOReturn = FilmBO.builder().id(1L).title("FilmReturn").created(2020)
                .director(DirectorBO.builder().id(2L).name("FilmReturn").age(18).nation("ESP").build())
                .actors(Collections.singletonList(ActorBO.builder().id(1L).name("Actor").age(18).nation("ESP").build()))
                .build();
        //Configuración Mock
        when(directorRepository.findById(1L)).thenReturn(Optional.of(director));
        when(actorRepository.findAllById(List.of(1L))).thenReturn(actors);
        when(filmBOMapper.boToEntity(filmBO)).thenReturn(film);
        when(filmRepository.save(any(Film.class))).thenReturn(film);
        when(filmBOMapper.entityToBo(any(Film.class))).thenReturn(filmBOReturn);

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
        when(actorRepository.findAllById(List.of(1L))).thenReturn(actors);

        //Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> filmService.createFilm(filmBO));

        //Assert
        assertEquals("Failed to create actor invalid director or actor", exception.getMessage());
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
        RuntimeException exception = assertThrows(RuntimeException.class, () -> filmService.createFilm(filmBO));

        //Assert
        assertEquals("Failed to create actor invalid director or actor", exception.getMessage());
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
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test update film with valid data")
    void testUpdateFilm_ValidData() throws Exception {
        // Arrange
        Long id = 1L;
        FilmBO filmBO = FilmBO.builder().title("Film Updated").created(2020)
                .director(DirectorBO.builder().id(1L).name("Director 1").build())
                .actors(Arrays.asList(ActorBO.builder().id(1L).name("Actor 1").build(),
                        ActorBO.builder().id(2L).name("Actor 2").build())).build();

        Film film = Film.builder().title("Film Updated").created(2020)
                .director(Director.builder().id(1L).name("Director 1").build())
                .actors(Arrays.asList(Actor.builder().id(1L).name("Actor 1").build(),
                        Actor.builder().id(2L).name("Actor 2").build())).build();
        Actor actor1 = Actor.builder().id(1L).name("Actor 1").age(18).nation("ESp").build();
        Actor actor2 = Actor.builder().id(1L).name("Actor 1").age(19).nation("ING").build();

        Director director = Director.builder().id(1L).name("Director 1").age(20).nation("ING").build();

        when(filmRepository.existsById(id)).thenReturn(true);
        when(directorRepository.findById(filmBO.getDirector().getId())).thenReturn(Optional.of(director));
        when(actorRepository.findAllById(List.of(1L, 2L))).thenReturn(Arrays.asList(actor1, actor2));
        when(filmBOMapper.boToEntity(any(FilmBO.class))).thenReturn(film);
        when(filmRepository.save(any(Film.class))).thenReturn(film);
        when(filmBOMapper.entityToBo(any(Film.class))).thenReturn(filmBO);

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
     * Test update film invalid director.
     */
    @Test
    @DisplayName("Test update film with invalid director")
    void testUpdateFilm_InvalidDirector() {
        // Arrange
        Long id = 1L;
        FilmBO filmBO = FilmBO.builder().title("Film Updated").created(2020)
                .director(DirectorBO.builder().id(1L).name("Director 1").build())
                .actors(Arrays.asList(ActorBO.builder().id(1L).name("Actor 1").build(),
                        ActorBO.builder().id(2L).name("Actor 2").build())).build();

        when(filmRepository.existsById(id)).thenReturn(true);
        when(directorRepository.findById(filmBO.getDirector().getId())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> filmService.updateFilm(id, filmBO));
    }

    /**
     * Test update film invalid actors.
     */
    @Test
    @DisplayName("Test update film with invalid actors")
    void testUpdateFilm_InvalidActors() {
        // Arrange
        Long id = 1L;
        FilmBO filmBO = FilmBO.builder().title("Film Updated").created(2020)
                .director(DirectorBO.builder().id(1L).name("Director 1").build())
                .actors(Arrays.asList(ActorBO.builder().id(1L).name("Actor 1").build(),
                        ActorBO.builder().id(2L).name("Actor 2").build())).build();

        when(filmRepository.existsById(id)).thenReturn(true);
        when(directorRepository.findById(filmBO.getDirector().getId())).thenReturn(
                Optional.of(Director.builder().id(1L).name("Director 1").build()));
        when(actorRepository.findAllById(List.of(1L))).thenReturn(Collections.emptyList());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> filmService.updateFilm(id, filmBO));
    }

    /**
     * Test update film non existent id.
     */
    @Test
    @DisplayName("Test update film with non-existent id")
    void testUpdateFilm_NonExistentId() {
        // Arrange
        Long id = 1L;
        FilmBO filmBO = FilmBO.builder().title("Film Updated").created(2020)
                .director(DirectorBO.builder().id(1L).name("Director 1").build())
                .actors(Arrays.asList(ActorBO.builder().id(1L).name("Actor 1").build(),
                        ActorBO.builder().id(2L).name("Actor 2").build())).build();

        when(filmRepository.existsById(id)).thenReturn(false);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> filmService.updateFilm(id, filmBO));
    }

    /**
     * Test get all films.
     */
    @Test
    @DisplayName("Test get all films")
    void testGetAllFilms() {
        // Arrange
        List<Film> films = Arrays.asList(Film.builder().id(1L).title("Film 1").build(),
                Film.builder().id(2L).title("Film 2").build(), Film.builder().id(3L).title("Film 3").build());

        when(filmRepository.findAll()).thenReturn(films);

        // Act
        List<FilmBO> result = filmService.getAllFilm();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Film 1", result.get(0).getTitle());
        assertEquals("Film 2", result.get(1).getTitle());
        assertEquals("Film 3", result.get(2).getTitle());
    }

    /**
     * Test get all films empty list.
     */
    @Test
    @DisplayName("Test get all films empty list")
    void testGetAllFilmsEmptyList() {
        // Arrange
        when(filmRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<FilmBO> result = filmService.getAllFilm();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /**
     * Test get all films with exception.
     */
    @Test
    @DisplayName("Test get all films with exception")
    void testGetAllFilmsWithException() {
        // Arrange
        when(filmRepository.findAll()).thenThrow(new RuntimeException("Error getting all films"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> filmService.getAllFilm());
    }

    /**
     * Test get film by id.
     */
    @Test
    @DisplayName("Test get film by id")
    void testGetFilmById() {
        // Arrange
        Long id = 1L;
        Film film = Film.builder().id(id).title("Film 1").build();

        when(filmRepository.findById(id)).thenReturn(Optional.of(film));

        // Act
        FilmBO result = filmService.getFilmById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Film 1", result.getTitle());
    }

    /**
     * Test get film by id not found.
     */
    @Test
    @DisplayName("Test get film by id not found")
    void testGetFilmByIdNotFound() {
        // Arrange
        Long id = 1L;

        when(filmRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertNull(filmService.getFilmById(id));
    }

    /**
     * Test get film by id with exception.
     */
    @Test
    @DisplayName("Test get film by id with exception")
    void testGetFilmByIdWithException() {
        // Arrange
        Long id = 1L;

        when(filmRepository.findById(id)).thenThrow(new RuntimeException("Error getting film by id"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> filmService.getFilmById(id));
    }

    /**
     * Test delete film successfully.
     */
    @Test
    @DisplayName("Test delete film successfully")
    void testDeleteFilmSuccessfully() {
        // Arrange
        Long id = 1L;

        when(filmRepository.existsById(id)).thenReturn(true);
        doNothing().when(filmRepository).deleteById(id);

        // Act
        boolean result = filmService.deleteFilm(id);

        // Assert
        assertTrue(result);
    }


    /**
     * Test delete film not found.
     */
    @Test
    @DisplayName("Test delete film not found")
    void testDeleteFilmNotFound() {
        // Arrange
        Long id = 1L;

        when(filmRepository.existsById(id)).thenReturn(false);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> filmService.deleteFilm(id));
    }


    /**
     * Test delete film with null id.
     */
    @Test
    @DisplayName("Test delete film with null id")
    void testDeleteFilmWithNullId() {
        // Act and Assert
        assertThrows(RuntimeException.class, () -> filmService.deleteFilm(null));
    }

}
