package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.bo.FilmBO;
import com.santander.peliculacrud.model.dto.FilmDTO;
import com.santander.peliculacrud.service.FilmServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import com.santander.peliculacrud.util.mapper.dto.FilmDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * The type Film controller test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FilmControllerTest {

    @Mock
    private FilmServiceInterface filmService;

    @Mock
    private FilmDTOMapper filmDTOMapper;

    @Mock
    private CommonOperation commonOperation;

    @InjectMocks
    private FilmController filmController;

    private FilmDTO filmDTO;
    private FilmBO filmBO;

    /**
     * Sets .
     */
    @BeforeEach
    void setup() {

        ActorBO actorBO1 = ActorBO.builder().nation("ESP").name("Actor1").age(19).build();
        ActorBO actorBO2 = ActorBO.builder().nation("ESP").name("Actor2").age(19).build();
        DirectorBO directorBO = DirectorBO.builder().nation("ESP").name("Actor2").age(19).build();

        filmDTO = FilmDTO.builder().title("film").created(1930).idActor(List.of(1L, 2L)).idDirector(1L).build();
        filmBO = FilmBO.builder().title("film").created(1930).actors(List.of(actorBO1, actorBO2)).director(directorBO)
                .build();

    }

    /**
     * Test create film.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test create film with valid data")
    void testCreateFilm() throws Exception {

        when(filmDTOMapper.dtoToBo(filmDTO)).thenReturn(filmBO);
        when(filmService.createFilm(filmBO)).thenReturn(filmBO);

        // Act
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(filmDTO, "filmDTO");
        ResponseEntity<String> response = filmController.createFilm(filmDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Film created successfully", response.getBody());
    }

    /**
     * Test update film.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test update film with valid data")
    void testUpdateFilm() throws Exception {
        // Arrange

        when(filmDTOMapper.dtoToBo(filmDTO)).thenReturn(filmBO);
        when(filmService.updateFilm(1L, filmBO)).thenReturn(filmBO);

        // Act
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(filmDTO, "filmDTO");

        ResponseEntity<String> response = filmController.updateFilm(1L, filmDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Film updated successfully", response.getBody());
    }

    /**
     * Test delete film.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test delete film with valid id")
    void testDeleteFilm() throws Exception {
        // Arrange
        long id = 1L;
        when(filmService.deleteFilm(id)).thenReturn(true);

        // Act
        ResponseEntity<String> response = filmController.deleteFilm(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Film delete", response.getBody());
    }

    /**
     * Test get all films.
     */
    @Test
    @DisplayName("Test get all film")
    void testGetAllFilms() {
        // Arrange
        List<FilmBO> filmBOS = Arrays.asList(FilmBO.builder().build(), FilmBO.builder().build());
        List<FilmDTO> filmDTOS = Arrays.asList(FilmDTO.builder().build(), FilmDTO.builder().build());
        when(filmService.getAllFilm(0)).thenReturn(filmBOS);
        when(filmDTOMapper.bosToDtos(filmBOS)).thenReturn(filmDTOS);
        // Act

        ResponseEntity<List<FilmDTO>> response = filmController.getAllFilm(0);

        // Assert
        assertNotNull(response);
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get film by id.
     */
    @Test
    @DisplayName("Test get film with valid id")
    void testGetFilmById() {
        // Arrange
        long id = 1L;

        when(filmService.getFilmById(id)).thenReturn(filmBO);
        when(filmDTOMapper.boToDTO(filmBO)).thenReturn(filmDTO);

        // Act
        FilmDTO response = filmController.getFilmById(id);

        // Assert
        assertNotNull(response);
        assertEquals("film", response.getTitle());
        assertEquals(1930, response.getCreated());
    }

    /**
     * Test get film by id null.
     */
    @Test
    @DisplayName("Test get film with null id")
    void testGetFilmByIdNull() {
        // Arrange
        long id = 1L;

        when(filmService.getFilmById(id)).thenReturn(filmBO);
        when(filmDTOMapper.boToDTO(filmBO)).thenReturn(null);

        // Act
        FilmDTO response = filmController.getFilmById(id);

        // Assert
        assertNull(response);
    }

    /**
     * Test get film by created valid created.
     */
    @Test
    @DisplayName("Test get film by created with valid created")
    void testGetFilmByCreated_ValidCreated() {
        // Arrange
        int created = 2022;
        int page = 0;
        List<FilmBO> filmBOS = Arrays.asList(FilmBO.builder().title("Film 1").created(2022).build(),
                FilmBO.builder().title("Film 2").created(2022).build());
        List<FilmDTO> filmDTOS = Arrays.asList(FilmDTO.builder().title("Film 1").created(2022).build(),
                FilmDTO.builder().title("Film 2").created(2022).build());

        when(filmService.getFilmByCreated(created, page)).thenReturn(filmBOS);
        when(filmDTOMapper.bosToDtos(filmBOS)).thenReturn(filmDTOS);

        // Act
        ResponseEntity<List<FilmDTO>> response = filmController.getFilmByCreated(created, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get film by created invalid created.
     */
    @Test
    @DisplayName("Test get film by created with invalid created")
    void testGetFilmByCreated_InvalidCreated() {
        // Arrange
        int created = -1;
        int page = 0;

        when(filmService.getFilmByCreated(created, page)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<FilmDTO>> response = filmController.getFilmByCreated(created, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test get film by title valid title.
     */
    @Test
    @DisplayName("Test get film by title with valid title")
    void testGetFilmByTitle_ValidTitle() {
        // Arrange
        String title = "Film 1";
        int page = 0;
        List<FilmBO> filmBOS = Collections.singletonList(FilmBO.builder().title("Film 1").build());
        List<FilmDTO> filmDTOS = Collections.singletonList(FilmDTO.builder().title("Film 1").build());

        when(filmService.getFilmByTitle(title, page)).thenReturn(filmBOS);
        when(filmDTOMapper.bosToDtos(filmBOS)).thenReturn(filmDTOS);

        // Act
        ResponseEntity<List<FilmDTO>> response = filmController.getFilmByTitle(title, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get film by title invalid title.
     */
    @Test
    @DisplayName("Test get film by title with invalid title")
    void testGetFilmByTitle_InvalidTitle() {
        // Arrange
        String title = "";
        int page = 0;

        when(filmService.getFilmByTitle(title, page)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<FilmDTO>> response = filmController.getFilmByTitle(title, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test get films by actors valid actors.
     */
    @Test
    @DisplayName("Test get films by actors with valid actors")
    void testGetFilmsByActors_ValidActors() {
        // Arrange
        List<ActorBO> actors = Arrays.asList(ActorBO.builder().name("Actor 1").id(1L).age(19).nation("ESP").build(),
                ActorBO.builder().name("Actor 2").id(2L).age(19).nation("ING").build());
        List<String> actorNames = List.of("Actor 1", "Actor 2");
        int page = 0;
        List<FilmBO> filmBOS = Collections.singletonList(FilmBO.builder().title("Film 1").actors(actors).build());
        List<FilmDTO> filmDTOS = Collections.singletonList(
                FilmDTO.builder().title("Film 1").idActor(List.of(1L, 2L)).build());

        when(filmService.getFilmByActors(actorNames, page)).thenReturn(filmBOS);
        when(filmDTOMapper.bosToDtos(filmBOS)).thenReturn(filmDTOS);

        // Act
        ResponseEntity<List<FilmDTO>> response = filmController.getFilmsByActors(actorNames, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get films by actors invalid actors.
     */
    @Test
    @DisplayName("Test get films by actors with invalid actors")
    void testGetFilmsByActors_InvalidActors() {
        // Arrange
        List<String> actorsName = Collections.emptyList();
        int page = 0;

        when(filmService.getFilmByActors(actorsName, page)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<FilmDTO>> response = filmController.getFilmsByActors(actorsName, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test get films by directors valid directors.
     */
    @Test
    @DisplayName("Test get films by directors with valid directors")
    void testGetFilmsByDirectors_ValidDirectors() {
        // Arrange
        int page = 0;

        List<String> directorsName = List.of("Director 1", "Director 2");

        List<ActorBO> actors = Arrays.asList(ActorBO.builder().name("Actor 1").id(1L).age(19).nation("ESP").build(),
                ActorBO.builder().name("Actor 2").id(2L).age(19).nation("ING").build());
        List<FilmBO> filmBOS = Collections.singletonList(FilmBO.builder().title("Film 1")
                .director(DirectorBO.builder().name("Director 1").age(19).nation("ESP").build()).actors(actors)
                .build());
        List<FilmDTO> filmDTOS = Collections.singletonList(
                FilmDTO.builder().title("Film 1").idActor(List.of(1L, 2L)).idDirector(1L).build());

        when(filmService.getFilmByDirectors(directorsName, page)).thenReturn(filmBOS);
        when(filmDTOMapper.bosToDtos(filmBOS)).thenReturn(filmDTOS);

        // Act
        ResponseEntity<List<FilmDTO>> response = filmController.getFilmsByDirectors(directorsName, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());

    }

    /**
     * Test get films by directors invalid directors.
     */
    @Test
    @DisplayName("Test get films by directors with invalid directors")
    void testGetFilmsByDirectors_InvalidDirectors() {
        // Arrange
        List<String> directorsName = Collections.emptyList();
        int page = 0;

        when(filmService.getFilmByDirectors(directorsName, page)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<FilmDTO>> response = filmController.getFilmsByDirectors(directorsName, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test create film invalid request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test create film with invalid request")
    void testCreateFilm_InvalidRequest() throws Exception {
        // Arrange
        FilmDTO filmDTO = FilmDTO.builder().title(null).build();
        BindingResult bindingResult = new BeanPropertyBindingResult(filmDTO, "filmDTO");
        bindingResult.rejectValue("title", "error.title.null");

        Logger logger = LoggerFactory.getLogger(FilmController.class);

        // Stubbing commonOperation
        doNothing().when(commonOperation).showErrorModel(logger, bindingResult);

        // Act
        ResponseEntity<String> response = filmController.createFilm(filmDTO, bindingResult);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Film not created", response.getBody());
    }

    /**
     * Test update film invalid request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test update film with invalid request")
    void testUpdateFilm_InvalidRequest() throws Exception {
        // Arrange
        FilmDTO filmDTO = FilmDTO.builder().title(null).build();
        BindingResult bindingResult = new BeanPropertyBindingResult(filmDTO, "filmDTO");
        bindingResult.rejectValue("title", "error.title.null");

        Logger logger = LoggerFactory.getLogger(FilmController.class);

        // Stubbing commonOperation
        doNothing().when(commonOperation).showErrorModel(logger, bindingResult);

        // Act
        ResponseEntity<String> response = filmController.updateFilm(1L, filmDTO, bindingResult);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Film not update", response.getBody());
    }

}
