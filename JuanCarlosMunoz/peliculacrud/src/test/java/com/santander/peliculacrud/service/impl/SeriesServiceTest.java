package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.ActorRepository;
import com.santander.peliculacrud.model.api.DirectorRepository;
import com.santander.peliculacrud.model.api.SeriesRepository;
import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.bo.SeriesBO;
import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.model.entity.Series;
import com.santander.peliculacrud.model.entity.Director;

import com.santander.peliculacrud.util.mapper.SeriesBOMapper;
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
 * The type Series service test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SeriesServiceTest {

    @InjectMocks
    private SeriesService seriesService;

    @Mock
    private SeriesBOMapper seriesBOMapper;

    @Mock
    private SeriesRepository seriesRepository;

    @Mock
    private DirectorRepository directorRepository;
    @Mock
    private ActorRepository actorRepository;

    private Director director;
    private List<Actor> actors;
    private SeriesBO seriesBO;
    private Series series;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        // Configuración Variables

        director = Director.builder().id(1L).name("Director").age(18).nation("ESP").build();
        actors = Collections.singletonList(Actor.builder().id(1L).name("Actor").age(18).nation("ESP").build());

        seriesBO = SeriesBO.builder().id(1L).title("Series").created(2020)
                .director(DirectorBO.builder().id(1L).name("Director").age(18).nation("ESP").build())
                .actors(Collections.singletonList(ActorBO.builder().id(1L).name("Actor").age(18).nation("ESP").build()))
                .build();

        series = Series.builder().id(1L).title("Series").created(2020)
                .director(Director.builder().id(1L).name("Director").age(18).nation("ESP").build())
                .actors(Collections.singletonList(Actor.builder().id(1L).name("Actor").age(18).nation("ESP").build()))
                .build();
    }

    /**
     * Test create series valid data.
     */
    @Test
    @DisplayName("Test create series with valid data")
    void testCreateSeries_ValidData() {
        //Variable auxiliar
        SeriesBO seriesBOReturn = SeriesBO.builder().id(1L).title("SeriesReturn").created(2020)
                .director(DirectorBO.builder().id(2L).name("SeriesReturn").age(18).nation("ESP").build())
                .actors(Collections.singletonList(ActorBO.builder().id(1L).name("Actor").age(18).nation("ESP").build()))
                .build();
        //Configuración Mock
        when(directorRepository.findById(1L)).thenReturn(Optional.of(director));
        when(actorRepository.findAllById(List.of(1L))).thenReturn(actors);
        when(seriesBOMapper.boToEntity(seriesBO)).thenReturn(series);
        when(seriesRepository.save(any(Series.class))).thenReturn(series);
        when(seriesBOMapper.entityToBo(any(Series.class))).thenReturn(seriesBOReturn);

        //Ejecución
        SeriesBO result = seriesService.createSeries(seriesBO);

        // Assert
        assertNotNull(result);
        assertEquals("Series", result.getTitle());
        assertEquals(2020, result.getCreated());
        assertNotNull(result.getDirector());
        assertNotNull(result.getActors());
    }

    /**
     * Test create series invalid director.
     */
    @Test
    @DisplayName("Test create series with invalid director")
    void testCreateSeries_InvalidDirector() {
        // Ejecución
        when(directorRepository.findById(2L)).thenReturn(Optional.empty());
        when(actorRepository.findAllById(List.of(1L))).thenReturn(actors);

        //Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> seriesService.createSeries(seriesBO));

        //Assert
        assertEquals("Failed to create actor invalid director or actor", exception.getMessage());
    }

    /**
     * Test create series invalid actors.
     */
    @Test
    @DisplayName("Test create series with invalid actors")
    void testCreateSeries_InvalidActors() {
        // Ejecucción

        when(actorRepository.findAllById(Arrays.asList(2L))).thenReturn(Collections.emptyList());

        //Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> seriesService.createSeries(seriesBO));

        //Assert
        assertEquals("Failed to create actor invalid director or actor", exception.getMessage());
    }

    /**
     * Test create series data access exception.
     */
    @Test
    @DisplayName("Test create series with data access exception")
    void testCreateSeries_DataAccessException() {

        //Configuración Mock

        when(directorRepository.findById(1L)).thenReturn(
                Optional.of(Director.builder().id(1L).name("Director").build()));
        when(actorRepository.findAllById(Arrays.asList(1L))).thenReturn(
                Arrays.asList(Actor.builder().id(1L).name("Actor").build()));

        when(seriesRepository.save(any(Series.class))).thenThrow(new RuntimeException("Failed to create actor: "));
        //Ejecución
        RuntimeException exception = assertThrows(RuntimeException.class, () -> seriesService.createSeries(seriesBO));

        //Assert
        assertEquals("Failed to create actor: ", exception.getMessage());

    }

    /**
     * Test update series valid data.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test update series with valid data")
    void testUpdateSeries_ValidData() throws Exception {
        // Arrange
        Long id = 1L;
        SeriesBO seriesBO = SeriesBO.builder().title("Series Updated").created(2020)
                .director(DirectorBO.builder().id(1L).name("Director 1").build())
                .actors(Arrays.asList(ActorBO.builder().id(1L).name("Actor 1").build(),
                        ActorBO.builder().id(2L).name("Actor 2").build())).build();

        Series series = Series.builder().title("Series Updated").created(2020)
                .director(Director.builder().id(1L).name("Director 1").build())
                .actors(Arrays.asList(Actor.builder().id(1L).name("Actor 1").build(),
                        Actor.builder().id(2L).name("Actor 2").build())).build();
        Actor actor1 = Actor.builder().id(1L).name("Actor 1").age(18).nation("ESp").build();
        Actor actor2 = Actor.builder().id(1L).name("Actor 1").age(19).nation("ING").build();

        Director director = Director.builder().id(1L).name("Director 1").age(20).nation("ING").build();

        when(seriesRepository.existsById(id)).thenReturn(true);
        when(directorRepository.findById(seriesBO.getDirector().getId())).thenReturn(Optional.of(director));
        when(actorRepository.findAllById(List.of(1L, 2L))).thenReturn(Arrays.asList(actor1, actor2));
        when(seriesBOMapper.boToEntity(any(SeriesBO.class))).thenReturn(series);
        when(seriesRepository.save(any(Series.class))).thenReturn(series);
        when(seriesBOMapper.entityToBo(any(Series.class))).thenReturn(seriesBO);

        // Act
        SeriesBO result = seriesService.updateSeries(id, seriesBO);

        // Assert
        assertNotNull(result);
        assertEquals("Series Updated", result.getTitle());
        assertEquals(2020, result.getCreated());
        assertNotNull(result.getDirector());
        assertNotNull(result.getActors());
    }

    /**
     * Test update series invalid director.
     */
    @Test
    @DisplayName("Test update series with invalid director")
    void testUpdateSeries_InvalidDirector() {
        // Arrange
        Long id = 1L;
        SeriesBO seriesBO = SeriesBO.builder().title("Series Updated").created(2020)
                .director(DirectorBO.builder().id(1L).name("Director 1").build())
                .actors(Arrays.asList(ActorBO.builder().id(1L).name("Actor 1").build(),
                        ActorBO.builder().id(2L).name("Actor 2").build())).build();

        when(seriesRepository.existsById(id)).thenReturn(true);
        when(directorRepository.findById(seriesBO.getDirector().getId())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> seriesService.updateSeries(id, seriesBO));
    }

    /**
     * Test update series invalid actors.
     */
    @Test
    @DisplayName("Test update series with invalid actors")
    void testUpdateSeries_InvalidActors() {
        // Arrange
        Long id = 1L;
        SeriesBO seriesBO = SeriesBO.builder().title("Series Updated").created(2020)
                .director(DirectorBO.builder().id(1L).name("Director 1").build())
                .actors(Arrays.asList(ActorBO.builder().id(1L).name("Actor 1").build(),
                        ActorBO.builder().id(2L).name("Actor 2").build())).build();

        when(seriesRepository.existsById(id)).thenReturn(true);
        when(directorRepository.findById(seriesBO.getDirector().getId())).thenReturn(
                Optional.of(Director.builder().id(1L).name("Director 1").build()));
        when(actorRepository.findAllById(List.of(1L))).thenReturn(Collections.emptyList());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> seriesService.updateSeries(id, seriesBO));
    }

    /**
     * Test update series non existent id.
     */
    @Test
    @DisplayName("Test update series with non-existent id")
    void testUpdateSeries_NonExistentId() {
        // Arrange
        Long id = 1L;
        SeriesBO seriesBO = SeriesBO.builder().title("Series Updated").created(2020)
                .director(DirectorBO.builder().id(1L).name("Director 1").build())
                .actors(Arrays.asList(ActorBO.builder().id(1L).name("Actor 1").build(),
                        ActorBO.builder().id(2L).name("Actor 2").build())).build();

        when(seriesRepository.existsById(id)).thenReturn(false);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> seriesService.updateSeries(id, seriesBO));
    }

    /**
     * Test get all seriess.
     */
    @Test
    @DisplayName("Test get all seriess")
    void testGetAllSeriess() {
        // Arrange
        List<Series> seriess = Arrays.asList(Series.builder().id(1L).title("Series 1").build(),
                Series.builder().id(2L).title("Series 2").build(), Series.builder().id(3L).title("Series 3").build());

        when(seriesRepository.findAll()).thenReturn(seriess);

        // Act
        List<SeriesBO> result = seriesService.getAllSeries();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Series 1", result.get(0).getTitle());
        assertEquals("Series 2", result.get(1).getTitle());
        assertEquals("Series 3", result.get(2).getTitle());
    }

    /**
     * Test get all seriess empty list.
     */
    @Test
    @DisplayName("Test get all seriess empty list")
    void testGetAllSeriessEmptyList() {
        // Arrange
        when(seriesRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<SeriesBO> result = seriesService.getAllSeries();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /**
     * Test get all seriess with exception.
     */
    @Test
    @DisplayName("Test get all seriess with exception")
    void testGetAllSeriessWithException() {
        // Arrange
        when(seriesRepository.findAll()).thenThrow(new RuntimeException("Error getting all seriess"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> seriesService.getAllSeries());
    }

    /**
     * Test get series by id.
     */
    @Test
    @DisplayName("Test get series by id")
    void testGetSeriesById() {
        // Arrange
        Long id = 1L;
        Series series = Series.builder().id(id).title("Series 1").build();

        when(seriesRepository.findById(id)).thenReturn(Optional.of(series));

        // Act
        SeriesBO result = seriesService.getSeriesById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Series 1", result.getTitle());
    }

    /**
     * Test get series by id not found.
     */
    @Test
    @DisplayName("Test get series by id not found")
    void testGetSeriesByIdNotFound() {
        // Arrange
        Long id = 1L;

        when(seriesRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertNull(seriesService.getSeriesById(id));
    }

    /**
     * Test get series by id with exception.
     */
    @Test
    @DisplayName("Test get series by id with exception")
    void testGetSeriesByIdWithException() {
        // Arrange
        Long id = 1L;

        when(seriesRepository.findById(id)).thenThrow(new RuntimeException("Error getting series by id"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> seriesService.getSeriesById(id));
    }

    /**
     * Test delete series successfully.
     */
    @Test
    @DisplayName("Test delete series successfully")
    void testDeleteSeriesSuccessfully() {
        // Arrange
        Long id = 1L;

        when(seriesRepository.existsById(id)).thenReturn(true);
        doNothing().when(seriesRepository).deleteById(id);

        // Act
        boolean result = seriesService.deleteSeries(id);

        // Assert
        assertTrue(result);
    }


    /**
     * Test delete series not found.
     */
    @Test
    @DisplayName("Test delete series not found")
    void testDeleteSeriesNotFound() {
        // Arrange
        Long id = 1L;

        when(seriesRepository.existsById(id)).thenReturn(false);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> seriesService.deleteSeries(id));
    }


    /**
     * Test delete series with null id.
     */
    @Test
    @DisplayName("Test delete series with null id")
    void testDeleteSeriesWithNullId() {
        // Act and Assert
        assertThrows(RuntimeException.class, () -> seriesService.deleteSeries(null));
    }

}