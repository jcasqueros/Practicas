package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.bo.SeriesBO;
import com.santander.peliculacrud.model.dto.SeriesDTO;
import com.santander.peliculacrud.service.SeriesServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import com.santander.peliculacrud.util.mapper.dto.SeriesDTOMapper;
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
 * The type Series controller test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SeriesControllerTest {

    @Mock
    private SeriesServiceInterface seriesService;

    @Mock
    private SeriesDTOMapper seriesDTOMapper;

    @Mock
    private CommonOperation commonOperation;

    @InjectMocks
    private SeriesController seriesController;

    private SeriesDTO seriesDTO;
    private SeriesBO seriesBO;

    /**
     * Sets .
     */
    @BeforeEach
    void setup() {

        ActorBO actorBO1 = ActorBO.builder().nation("ESP").name("Actor1").age(19).build();
        ActorBO actorBO2 = ActorBO.builder().nation("ESP").name("Actor2").age(19).build();
        DirectorBO directorBO = DirectorBO.builder().nation("ESP").name("Actor2").age(19).build();

        seriesDTO = SeriesDTO.builder().title("series").created(1930).idActor(List.of(1L, 2L)).idDirector(1L).build();
        seriesBO = SeriesBO.builder().title("series").created(1930).actors(List.of(actorBO1, actorBO2))
                .director(directorBO).build();

    }

    /**
     * Test create series.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test create series with valid data")
    void testCreateSeries() throws Exception {

        when(seriesDTOMapper.dtoToBo(seriesDTO)).thenReturn(seriesBO);
        when(seriesService.createSeries(seriesBO)).thenReturn(seriesBO);

        // Act
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(seriesDTO, "seriesDTO");
        ResponseEntity<String> response = seriesController.createSeries(seriesDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Series created successfully", response.getBody());
    }

    /**
     * Test update series.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test update series with valid data")
    void testUpdateSeries() throws Exception {
        // Arrange

        when(seriesDTOMapper.dtoToBo(seriesDTO)).thenReturn(seriesBO);
        when(seriesService.updateSeries(1L, seriesBO)).thenReturn(seriesBO);

        // Act
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(seriesDTO, "seriesDTO");

        ResponseEntity<String> response = seriesController.updateSeries(1L, seriesDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Series updated successfully", response.getBody());
    }

    /**
     * Test delete series.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test delete series with valid id")
    void testDeleteSeries() throws Exception {
        // Arrange
        long id = 1L;
        when(seriesService.deleteSeries(id)).thenReturn(true);

        // Act
        ResponseEntity<String> response = seriesController.deleteSeries(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Series delete", response.getBody());
    }

    /**
     * Test get all seriess.
     */
    @Test
    @DisplayName("Test get all series")
    void testGetAllSeries() {
        // Arrange
        List<SeriesBO> seriesBOS = Arrays.asList(SeriesBO.builder().build(), SeriesBO.builder().build());
        List<SeriesDTO> seriesDTOS = Arrays.asList(SeriesDTO.builder().build(), SeriesDTO.builder().build());
        when(seriesService.getAllSeries(0)).thenReturn(seriesBOS);
        when(seriesDTOMapper.bosToDtos(seriesBOS)).thenReturn(seriesDTOS);
        // Act

        ResponseEntity<List<SeriesDTO>> response = seriesController.getAllSeries(0);

        // Assert
        assertNotNull(response);
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get series by id.
     */
    @Test
    @DisplayName("Test get series with valid id")
    void testGetSeriesById() {
        // Arrange
        long id = 1L;

        when(seriesService.getSeriesById(id)).thenReturn(seriesBO);
        when(seriesDTOMapper.boToDTO(seriesBO)).thenReturn(seriesDTO);

        // Act
        SeriesDTO response = seriesController.getSeriesById(id);

        // Assert
        assertNotNull(response);
        assertEquals("series", response.getTitle());
        assertEquals(1930, response.getCreated());
    }

    /**
     * Test get series by id null.
     */
    @Test
    @DisplayName("Test get series with null id")
    void testGetSeriesByIdNull() {
        // Arrange
        long id = 1L;

        when(seriesService.getSeriesById(id)).thenReturn(seriesBO);
        when(seriesDTOMapper.boToDTO(seriesBO)).thenReturn(null);

        // Act
        SeriesDTO response = seriesController.getSeriesById(id);

        // Assert
        assertNull(response);
    }

    /**
     * Test get series by created valid created.
     */
    @Test
    @DisplayName("Test get series by created with valid created")
    void testGetSeriesByCreated_ValidCreated() {
        // Arrange
        int created = 2022;
        int page = 0;
        List<SeriesBO> seriesBOS = Arrays.asList(SeriesBO.builder().title("Series 1").created(2022).build(),
                SeriesBO.builder().title("Series 2").created(2022).build());
        List<SeriesDTO> seriesDTOS = Arrays.asList(SeriesDTO.builder().title("Series 1").created(2022).build(),
                SeriesDTO.builder().title("Series 2").created(2022).build());

        when(seriesService.getSeriesByCreated(created, page)).thenReturn(seriesBOS);
        when(seriesDTOMapper.bosToDtos(seriesBOS)).thenReturn(seriesDTOS);

        // Act
        ResponseEntity<List<SeriesDTO>> response = seriesController.getSeriesByCreated(created, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get series by created invalid created.
     */
    @Test
    @DisplayName("Test get series by created with invalid created")
    void testGetSeriesByCreated_InvalidCreated() {
        // Arrange
        int created = -1;
        int page = 0;

        when(seriesService.getSeriesByCreated(created, page)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<SeriesDTO>> response = seriesController.getSeriesByCreated(created, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test get series by title valid title.
     */
    @Test
    @DisplayName("Test get series by title with valid title")
    void testGetSeriesByTitle_ValidTitle() {
        // Arrange
        String title = "Series 1";
        int page = 0;
        List<SeriesBO> seriesBOS = Collections.singletonList(SeriesBO.builder().title("Series 1").build());
        List<SeriesDTO> seriesDTOS = Collections.singletonList(SeriesDTO.builder().title("Series 1").build());

        when(seriesService.getSeriesByTitle(title, page)).thenReturn(seriesBOS);
        when(seriesDTOMapper.bosToDtos(seriesBOS)).thenReturn(seriesDTOS);

        // Act
        ResponseEntity<List<SeriesDTO>> response = seriesController.getSeriesByTitle(title, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get series by title invalid title.
     */
    @Test
    @DisplayName("Test get series by title with invalid title")
    void testGetSeriesByTitle_InvalidTitle() {
        // Arrange
        String title = "";
        int page = 0;

        when(seriesService.getSeriesByTitle(title, page)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<SeriesDTO>> response = seriesController.getSeriesByTitle(title, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test get seriess by actors valid actors.
     */
    @Test
    @DisplayName("Test get seriess by actors with valid actors")
    void testGetSeriesByActors_ValidActors() {
        // Arrange
        List<ActorBO> actors = Arrays.asList(ActorBO.builder().name("Actor 1").id(1L).age(19).nation("ESP").build(),
                ActorBO.builder().name("Actor 2").id(2L).age(19).nation("ING").build());
        List<String> actorNames = List.of("Actor 1", "Actor 2");
        int page = 0;
        List<SeriesBO> seriesBOS = Collections.singletonList(
                SeriesBO.builder().title("Series 1").actors(actors).build());
        List<SeriesDTO> seriesDTOS = Collections.singletonList(
                SeriesDTO.builder().title("Series 1").idActor(List.of(1L, 2L)).build());

        when(seriesService.getSeriesByActors(actorNames, page)).thenReturn(seriesBOS);
        when(seriesDTOMapper.bosToDtos(seriesBOS)).thenReturn(seriesDTOS);

        // Act
        ResponseEntity<List<SeriesDTO>> response = seriesController.getSeriesByActors(actorNames, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get seriess by actors invalid actors.
     */
    @Test
    @DisplayName("Test get seriess by actors with invalid actors")
    void testGetSeriesByActors_InvalidActors() {
        // Arrange
        List<String> actorsName = Collections.emptyList();
        int page = 0;

        when(seriesService.getSeriesByActors(actorsName, page)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<SeriesDTO>> response = seriesController.getSeriesByActors(actorsName, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test get seriess by directors valid directors.
     */
    @Test
    @DisplayName("Test get seriess by directors with valid directors")
    void testGetSeriesByDirectors_ValidDirectors() {
        // Arrange
        int page = 0;

        List<String> directorsName = List.of("Director 1", "Director 2");

        List<ActorBO> actors = Arrays.asList(ActorBO.builder().name("Actor 1").id(1L).age(19).nation("ESP").build(),
                ActorBO.builder().name("Actor 2").id(2L).age(19).nation("ING").build());
        List<SeriesBO> seriesBOS = Collections.singletonList(SeriesBO.builder().title("Series 1")
                .director(DirectorBO.builder().name("Director 1").age(19).nation("ESP").build()).actors(actors)
                .build());
        List<SeriesDTO> seriesDTOS = Collections.singletonList(
                SeriesDTO.builder().title("Series 1").idActor(List.of(1L, 2L)).idDirector(1L).build());

        when(seriesService.getSeriesByDirectors(directorsName, page)).thenReturn(seriesBOS);
        when(seriesDTOMapper.bosToDtos(seriesBOS)).thenReturn(seriesDTOS);

        // Act
        ResponseEntity<List<SeriesDTO>> response = seriesController.getSeriesByDirectors(directorsName, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());

    }

    /**
     * Test get seriess by directors invalid directors.
     */
    @Test
    @DisplayName("Test get seriess by directors with invalid directors")
    void testGetSeriesByDirectors_InvalidDirectors() {
        // Arrange
        List<String> directorsName = Collections.emptyList();
        int page = 0;

        when(seriesService.getSeriesByDirectors(directorsName, page)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<SeriesDTO>> response = seriesController.getSeriesByDirectors(directorsName, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test create series invalid request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test create series with invalid request")
    void testCreateSeries_InvalidRequest() throws Exception {
        // Arrange
        SeriesDTO seriesDTO = SeriesDTO.builder().title(null).build();
        BindingResult bindingResult = new BeanPropertyBindingResult(seriesDTO, "seriesDTO");
        bindingResult.rejectValue("title", "error.title.null");

        Logger logger = LoggerFactory.getLogger(SeriesController.class);

        // Stubbing commonOperation
        doNothing().when(commonOperation).showErrorModel(logger, bindingResult);

        // Act
        ResponseEntity<String> response = seriesController.createSeries(seriesDTO, bindingResult);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Series not created", response.getBody());
    }

    /**
     * Test update series invalid request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test update series with invalid request")
    void testUpdateSeries_InvalidRequest() throws Exception {
        // Arrange
        SeriesDTO seriesDTO = SeriesDTO.builder().title(null).build();
        BindingResult bindingResult = new BeanPropertyBindingResult(seriesDTO, "seriesDTO");
        bindingResult.rejectValue("title", "error.title.null");

        Logger logger = LoggerFactory.getLogger(SeriesController.class);

        // Stubbing commonOperation
        doNothing().when(commonOperation).showErrorModel(logger, bindingResult);

        // Act
        ResponseEntity<String> response = seriesController.updateSeries(1L, seriesDTO, bindingResult);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Series not update", response.getBody());
    }

}
