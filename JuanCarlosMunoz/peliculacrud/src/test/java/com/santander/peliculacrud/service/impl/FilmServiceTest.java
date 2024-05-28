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

import com.santander.peliculacrud.util.CommonOperation;
import com.santander.peliculacrud.util.exception.GenericException;
import com.santander.peliculacrud.util.mapper.bo.FilmBOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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

    @Mock
    private CommonOperation commonOperation;

    private Director director;
    private List<Actor> actors;
    private FilmBO filmBO;
    private Film film;
    private DirectorBO directorBO;
    private List<ActorBO> actorsBO;
    private List<Long> listId;
    private Film film1;
    private Film film2;
    private FilmBO filmBO1;
    private FilmBO filmBO2;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        // Configuración Variables
        listId = new ArrayList<>();
        listId.add(1L);
        listId.add(2L);

        director = Director.builder().id(1L).name("Director").age(18).nation("ESP").build();
        directorBO = DirectorBO.builder().id(1L).name("Director").age(18).nation("ESP").build();
        actors = Collections.singletonList(Actor.builder().id(1L).name("Actor").age(18).nation("ESP").build());
        actorsBO = Collections.singletonList(ActorBO.builder().id(1L).name("Actor").age(18).nation("ESP").build());

        filmBO = FilmBO.builder().id(1L).title("Film").created(2020)
                .director(DirectorBO.builder().id(1L).name("Director").age(18).nation("ESP").build())
                .actors(Collections.singletonList(ActorBO.builder().id(1L).name("Actor").age(18).nation("ESP").build()))
                .build();

        film = Film.builder().id(1L).title("Film").created(2020)
                .director(Director.builder().id(1L).name("Director").age(18).nation("ESP").build())
                .actors(Collections.singletonList(Actor.builder().id(1L).name("Actor").age(18).nation("ESP").build()))
                .build();

        film1 = Film.builder().created(2020).director(director).actors(actors).title("Film 1").build();
        film2 = Film.builder().created(2020).director(director).actors(actors).title("Film 2").build();

        filmBO1 = FilmBO.builder().created(2020).director(directorBO).actors(actorsBO).title("Film 1").build();
        filmBO2 = FilmBO.builder().created(2020).director(directorBO).actors(actorsBO).title("Film 2").build();

    }

    /**
     * Test create film valid data.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test create film with valid data")
    void testCreateFilm_ValidData() throws Exception {
        // Arrange

        when(directorRepository.findById(1L)).thenReturn(Optional.ofNullable(this.director));
        when(commonOperation.getIdObject(filmBO.getActors())).thenReturn(listId);

        when(actorRepository.findAllById(listId)).thenReturn(this.actors);

        when(filmBOMapper.boToEntity(any(FilmBO.class))).thenReturn(film);
        when(filmRepository.save(any(Film.class))).thenReturn(film);
        when(filmBOMapper.entityToBo(any(Film.class))).thenReturn(filmBO);

        // Act
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
        GenericException exception = assertThrows(GenericException.class, () -> filmService.createFilm(filmBO));

        //Assert
        assertEquals("Failed to create actor: Invalid director or actor", exception.getMessage());
    }

    /**
     * Test create film invalid actors.
     */
    @Test
    @DisplayName("Test create film with invalid actors")
    void testCreateFilm_InvalidActors() {
        // Ejecucción

        when(actorRepository.findAllById(List.of(2L))).thenReturn(Collections.emptyList());

        //Assert
        GenericException exception = assertThrows(GenericException.class, () -> filmService.createFilm(filmBO));

        //Assert
        assertEquals("Failed to create actor: Invalid director or actor", exception.getMessage());
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

        when(filmRepository.existsById(id)).thenReturn(true);
        when(directorRepository.findById(1L)).thenReturn(Optional.ofNullable(this.director));
        when(commonOperation.getIdObject(filmBO.getActors())).thenReturn(listId);

        when(actorRepository.findAllById(listId)).thenReturn(this.actors);

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
        assertThrows(GenericException.class, () -> filmService.updateFilm(id, filmBO));
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
        assertThrows(GenericException.class, () -> filmService.updateFilm(id, filmBO));
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
        assertThrows(GenericException.class, () -> filmService.updateFilm(id, filmBO));
    }

    /**
     * Test get all films.
     */
    @Test
    @DisplayName("Test get all films")
    void testGetAllFilms() {

        // Configuración de mocks
        Film film1 = Film.builder().title("Film 1").director(director).actors(actors).created(2020).build();
        Film film2 = Film.builder().title("Film 2").director(director).actors(actors).created(2020).build();
        Film film3 = Film.builder().title("Film 3").director(director).actors(actors).created(2020).build();

        Page<Film> filmsPage = new PageImpl<>(Arrays.asList(film1, film2, film3));

        when(filmRepository.findAll(PageRequest.of(0, 5))).thenReturn(filmsPage);

        FilmBO filmBO1 = FilmBO.builder().title("Film 1").director(directorBO).actors(actorsBO).created(2020).build();
        FilmBO filmBO2 = FilmBO.builder().title("Film 2").director(directorBO).actors(actorsBO).created(2020).build();
        FilmBO filmBO3 = FilmBO.builder().title("Film 3").director(directorBO).actors(actorsBO).created(2020).build();

        when(filmBOMapper.listEntityListBo(filmsPage)).thenReturn(Arrays.asList(filmBO1, filmBO2, filmBO3));

        // Ejecución del método bajo prueba
        List<FilmBO> result = filmService.getAllFilm(0);

        // Verificación de resultados
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
        List<FilmBO> result = filmService.getAllFilm(0);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /**
     * Test get film by id.
     */
    @Test
    @DisplayName("Test get film by id")
    void testGetFilmById() {

        filmBO = FilmBO.builder().id(1L).title("Film").created(2020)
                .director(DirectorBO.builder().id(1L).name("Director").age(18).nation("ESP").build())
                .actors(Collections.singletonList(ActorBO.builder().id(1L).name("Actor").age(18).nation("ESP").build()))
                .build();
        when(filmRepository.findById(1L)).thenReturn(Optional.of(film));
        when(filmBOMapper.entityToBo(film)).thenReturn(filmBO);

        // Act
        FilmBO result = filmService.getFilmById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(filmBO.getId(), result.getId());
        assertEquals(filmBO.getCreated(), result.getCreated());
        assertEquals(filmBO.getTitle(), result.getTitle());
        assertEquals(filmBO.getActors(), result.getActors());
        assertEquals(filmBO.getDirector(), result.getDirector());
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
     * Test delete film successfully.
     *
     * @throws GenericException
     *         the generic exception
     */
    @Test
    @DisplayName("Test delete film successfully")
    void testDeleteFilmSuccessfully() throws GenericException {
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
        assertThrows(GenericException.class, () -> filmService.deleteFilm(id));
    }

    /**
     * Test delete film with null id.
     */
    @Test
    @DisplayName("Test delete film with null id")
    void testDeleteFilmWithNullId() {
        // Act and Assert
        assertThrows(GenericException.class, () -> filmService.deleteFilm(null));
    }

    /**
     * Test get film by title.
     */
    @Test
    @DisplayName("Test get film by title")
    void testGetFilmByTitle() {
        // Configuración de mocks
        String title = "Film 1";
        int page = 0;

        Page<Film> filmPage = new PageImpl<>(Arrays.asList(film1, film2));

        when(filmRepository.findAllByTitle(title, PageRequest.of(page, 5))).thenReturn(filmPage);

        when(filmBOMapper.listEntityListBo(filmPage)).thenReturn(Arrays.asList(filmBO1, filmBO2));

        // Ejecución del método bajo prueba
        List<FilmBO> result = filmService.getFilmByTitle(title, page);

        // Verificación de resultados
        assertEquals(2, result.size());
        assertEquals("Film 1", result.get(0).getTitle());
        assertEquals("Film 2", result.get(1).getTitle());
    }

    /**
     * Test get film by created.
     */
    @Test
    @DisplayName("Test get film by created.")
    void testGetFilmByCreated() {
        // Configuración de mocks
        int created = 2020;
        int page = 0;

        Page<Film> filmPage = new PageImpl<>(Arrays.asList(film1, film2));

        when(filmRepository.findAllByCreated(created, PageRequest.of(page, 5))).thenReturn(filmPage);

        when(filmBOMapper.listEntityListBo(filmPage)).thenReturn(Arrays.asList(filmBO1, filmBO2));

        // Ejecución del método bajo prueba
        List<FilmBO> result = filmService.getFilmByCreated(created, page);

        // Verificación de resultados
        assertEquals(2, result.size());
        assertEquals("Film 1", result.get(0).getTitle());
        assertEquals("Film 2", result.get(1).getTitle());
    }

    /**
     * Test get film by actors.
     */
    @Test
    @DisplayName("Test get film by actors")
    void testGetFilmByActors() {
        // Configuración de mocks
        List<String> actorsName = Arrays.asList("Actor 1", "Actor 2");
        int page = 0;

        Actor actor1 = Actor.builder().name("Actor 1").age(54).nation("ESP").build();
        Actor actor2 = Actor.builder().name("Actor 2").age(54).nation("ESP").build();

        List<Actor> actors = Arrays.asList(actor1, actor2);

        when(actorRepository.findByNameIn(actorsName)).thenReturn(actors);

        Page<Film> filmPage = new PageImpl<>(Arrays.asList(film1, film2));

        when(filmRepository.findAllByActorsIn(Collections.singleton(actors), PageRequest.of(page, 5))).thenReturn(
                filmPage);

        when(filmBOMapper.listEntityListBo(filmPage)).thenReturn(Arrays.asList(filmBO1, filmBO2));

        // Ejecución del método bajo prueba
        List<FilmBO> result = filmService.getFilmByActors(actorsName, page);

        // Verificación de resultados
        assertEquals(2, result.size());
        assertEquals("Film 1", result.get(0).getTitle());
        assertEquals("Film 2", result.get(1).getTitle());
    }

    /**
     * Test get film by directors.
     */
    @Test
    @DisplayName("Test get film by directors.")
    void testGetFilmByDirectors() {
        // Configuración de mocks
        List<String> directorsName = Arrays.asList("Director 1", "Director 2");
        int page = 0;

        Director director1 = Director.builder().name("Director 1").age(54).nation("ESP").build();
        Director director2 = Director.builder().name("Director 2").age(54).nation("ESP").build();

        List<Director> directors = Arrays.asList(director1, director2);

        when(directorRepository.findByNameIn(directorsName)).thenReturn(directors);

        Page<Film> filmPage = new PageImpl<>(Arrays.asList(film1, film2));

        when(filmRepository.findAllByDirectorIn(directors, PageRequest.of(page, 5))).thenReturn(filmPage);

        when(filmBOMapper.listEntityListBo(filmPage)).thenReturn(Arrays.asList(filmBO1, filmBO2));

        // Ejecución del método bajo prueba
        List<FilmBO> result = filmService.getFilmByDirectors(directorsName, page);

        // Verificación de resultados
        assertEquals(2, result.size());
        assertEquals("Film 1", result.get(0).getTitle());
        assertEquals("Film 2", result.get(1).getTitle());
    }
}

