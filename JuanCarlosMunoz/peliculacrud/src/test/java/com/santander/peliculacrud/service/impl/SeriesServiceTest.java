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

import com.santander.peliculacrud.util.CommonOperation;
import com.santander.peliculacrud.util.exception.GenericException;
import com.santander.peliculacrud.util.mapper.bo.SeriesBOMapper;
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

    @Mock
    private CommonOperation commonOperation;

    private Director director;
    private List<Actor> actors;
    private SeriesBO seriesBO;
    private Series series;
    private DirectorBO directorBO;
    private List<ActorBO> actorsBO;
    private List<Long> listId;
    private Series series1;
    private Series series2;
    private SeriesBO seriesBO1;
    private SeriesBO seriesBO2;

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

        seriesBO = SeriesBO.builder().id(1L).title("Series").created(2020)
                .director(DirectorBO.builder().id(1L).name("Director").age(18).nation("ESP").build())
                .actors(Collections.singletonList(ActorBO.builder().id(1L).name("Actor").age(18).nation("ESP").build()))
                .build();

        series = Series.builder().id(1L).title("Series").created(2020)
                .director(Director.builder().id(1L).name("Director").age(18).nation("ESP").build())
                .actors(Collections.singletonList(Actor.builder().id(1L).name("Actor").age(18).nation("ESP").build()))
                .build();

        series1 = Series.builder().created(2020).director(director).actors(actors).title("Series 1").build();
        series2 = Series.builder().created(2020).director(director).actors(actors).title("Series 2").build();

        seriesBO1 = SeriesBO.builder().created(2020).director(directorBO).actors(actorsBO).title("Series 1").build();
        seriesBO2 = SeriesBO.builder().created(2020).director(directorBO).actors(actorsBO).title("Series 2").build();

    }

    /**
     * Test create series valid data.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test create series with valid data")
    void testCreateSeries_ValidData() throws Exception {
        // Arrange

        when(directorRepository.findById(1L)).thenReturn(Optional.ofNullable(this.director));
        when(commonOperation.getIdObject(seriesBO.getActors())).thenReturn(listId);

        when(actorRepository.findAllById(listId)).thenReturn(this.actors);

        when(seriesBOMapper.boToEntity(any(SeriesBO.class))).thenReturn(series);
        when(seriesRepository.save(any(Series.class))).thenReturn(series);
        when(seriesBOMapper.entityToBo(any(Series.class))).thenReturn(seriesBO);

        // Act
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
        GenericException exception = assertThrows(GenericException.class, () -> seriesService.createSeries(seriesBO));

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

        when(actorRepository.findAllById(List.of(2L))).thenReturn(Collections.emptyList());

        //Assert
        GenericException exception = assertThrows(GenericException.class, () -> seriesService.createSeries(seriesBO));

        //Assert
        assertEquals("Failed to create actor invalid director or actor", exception.getMessage());
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

        when(seriesRepository.existsById(id)).thenReturn(true);
        when(directorRepository.findById(1L)).thenReturn(Optional.ofNullable(this.director));
        when(commonOperation.getIdObject(seriesBO.getActors())).thenReturn(listId);

        when(actorRepository.findAllById(listId)).thenReturn(this.actors);

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
        assertThrows(GenericException.class, () -> seriesService.updateSeries(id, seriesBO));
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
        assertThrows(GenericException.class, () -> seriesService.updateSeries(id, seriesBO));
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
        assertThrows(GenericException.class, () -> seriesService.updateSeries(id, seriesBO));
    }

    /**
     * Test get all series.
     */
    @Test
    @DisplayName("Test get all series")
    void testGetAllSeriess() {

        // Configuración de mocks
        Series series1 = Series.builder().title("Series 1").director(director).actors(actors).created(2020).build();
        Series series2 = Series.builder().title("Series 2").director(director).actors(actors).created(2020).build();
        Series series3 = Series.builder().title("Series 3").director(director).actors(actors).created(2020).build();

        Page<Series> seriesPage = new PageImpl<>(Arrays.asList(series1, series2, series3));

        when(seriesRepository.findAll(PageRequest.of(0, 5))).thenReturn(seriesPage);

        SeriesBO seriesBO1 = SeriesBO.builder().title("Series 1").director(directorBO).actors(actorsBO).created(2020)
                .build();
        SeriesBO seriesBO2 = SeriesBO.builder().title("Series 2").director(directorBO).actors(actorsBO).created(2020)
                .build();
        SeriesBO seriesBO3 = SeriesBO.builder().title("Series 3").director(directorBO).actors(actorsBO).created(2020)
                .build();

        when(seriesBOMapper.listEntityListBo(seriesPage)).thenReturn(Arrays.asList(seriesBO1, seriesBO2, seriesBO3));

        // Ejecución del método bajo prueba
        List<SeriesBO> result = seriesService.getAllSeries(0);

        // Verificación de resultados
        assertEquals(3, result.size());
        assertEquals("Series 1", result.get(0).getTitle());
        assertEquals("Series 2", result.get(1).getTitle());
        assertEquals("Series 3", result.get(2).getTitle());
    }

    /**
     * Test get all series empty list.
     */
    @Test
    @DisplayName("Test get all series empty list")
    void testGetAllSeriessEmptyList() {
        // Arrange
        when(seriesRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<SeriesBO> result = seriesService.getAllSeries(0);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /**
     * Test get series by id.
     */
    @Test
    @DisplayName("Test get series by id")
    void testGetSeriesById() {

        seriesBO = SeriesBO.builder().id(1L).title("Series").created(2020)
                .director(DirectorBO.builder().id(1L).name("Director").age(18).nation("ESP").build())
                .actors(Collections.singletonList(ActorBO.builder().id(1L).name("Actor").age(18).nation("ESP").build()))
                .build();
        when(seriesRepository.findById(1L)).thenReturn(Optional.of(series));
        when(seriesBOMapper.entityToBo(series)).thenReturn(seriesBO);

        // Act
        SeriesBO result = seriesService.getSeriesById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(seriesBO.getId(), result.getId());
        assertEquals(seriesBO.getCreated(), result.getCreated());
        assertEquals(seriesBO.getTitle(), result.getTitle());
        assertEquals(seriesBO.getActors(), result.getActors());
        assertEquals(seriesBO.getDirector(), result.getDirector());
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
     * Test delete series successfully.
     *
     * @throws GenericException
     *         the generic exception
     */
    @Test
    @DisplayName("Test delete series successfully")
    void testDeleteSeriesSuccessfully() throws GenericException {
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
        assertThrows(GenericException.class, () -> seriesService.deleteSeries(id));
    }

    /**
     * Test delete series with null id.
     */
    @Test
    @DisplayName("Test delete series with null id")
    void testDeleteSeriesWithNullId() {
        // Act and Assert
        assertThrows(GenericException.class, () -> seriesService.deleteSeries(null));
    }

    /**
     * Test get series by title.
     */
    @Test
    @DisplayName("Test get series by title")
    void testGetSeriesByTitle() {
        // Configuración de mocks
        String title = "Series 1";
        int page = 0;

        Page<Series> seriesPage = new PageImpl<>(Arrays.asList(series1, series2));

        when(seriesRepository.findAllByTitle(title, PageRequest.of(page, 5))).thenReturn(seriesPage);

        when(seriesBOMapper.listEntityListBo(seriesPage)).thenReturn(Arrays.asList(seriesBO1, seriesBO2));

        // Ejecución del método bajo prueba
        List<SeriesBO> result = seriesService.getSeriesByTitle(title, page);

        // Verificación de resultados
        assertEquals(2, result.size());
        assertEquals("Series 1", result.get(0).getTitle());
        assertEquals("Series 2", result.get(1).getTitle());
    }

    /**
     * Test get series by created.
     */
    @Test
    @DisplayName("Test get series by created.")
    void testGetSeriesByCreated() {
        // Configuración de mocks
        int created = 2020;
        int page = 0;

        Page<Series> seriesPage = new PageImpl<>(Arrays.asList(series1, series2));

        when(seriesRepository.findAllByCreated(created, PageRequest.of(page, 5))).thenReturn(seriesPage);

        when(seriesBOMapper.listEntityListBo(seriesPage)).thenReturn(Arrays.asList(seriesBO1, seriesBO2));

        // Ejecución del método bajo prueba
        List<SeriesBO> result = seriesService.getSeriesByCreated(created, page);

        // Verificación de resultados
        assertEquals(2, result.size());
        assertEquals("Series 1", result.get(0).getTitle());
        assertEquals("Series 2", result.get(1).getTitle());
    }

    /**
     * Test get series by actors.
     */
    @Test
    @DisplayName("Test get series by actors")
    void testGetSeriesByActors() {
        // Configuración de mocks
        List<String> actorsName = Arrays.asList("Actor 1", "Actor 2");
        int page = 0;

        Actor actor1 = Actor.builder().name("Actor 1").age(54).nation("ESP").build();
        Actor actor2 = Actor.builder().name("Actor 2").age(54).nation("ESP").build();

        List<Actor> actors = Arrays.asList(actor1, actor2);

        when(actorRepository.findByNameIn(actorsName)).thenReturn(actors);

        Page<Series> seriesPage = new PageImpl<>(Arrays.asList(series1, series2));

        when(seriesRepository.findAllByActorsIn(Collections.singleton(actors), PageRequest.of(page, 5))).thenReturn(
                seriesPage);

        when(seriesBOMapper.listEntityListBo(seriesPage)).thenReturn(Arrays.asList(seriesBO1, seriesBO2));

        // Ejecución del método bajo prueba
        List<SeriesBO> result = seriesService.getSeriesByActors(actorsName, page);

        // Verificación de resultados
        assertEquals(2, result.size());
        assertEquals("Series 1", result.get(0).getTitle());
        assertEquals("Series 2", result.get(1).getTitle());
    }

    /**
     * Test get series by directors.
     */
    @Test
    @DisplayName("Test get series by directors.")
    void testGetSeriesByDirectors() {
        // Configuración de mocks
        List<String> directorsName = Arrays.asList("Director 1", "Director 2");
        int page = 0;

        Director director1 = Director.builder().name("Director 1").age(54).nation("ESP").build();
        Director director2 = Director.builder().name("Director 2").age(54).nation("ESP").build();

        List<Director> directors = Arrays.asList(director1, director2);

        when(directorRepository.findByNameIn(directorsName)).thenReturn(directors);

        Page<Series> seriesPage = new PageImpl<>(Arrays.asList(series1, series2));

        when(seriesRepository.findAllByDirectorIn(directors, PageRequest.of(page, 5))).thenReturn(seriesPage);

        when(seriesBOMapper.listEntityListBo(seriesPage)).thenReturn(Arrays.asList(seriesBO1, seriesBO2));

        // Ejecución del método bajo prueba
        List<SeriesBO> result = seriesService.getSeriesByDirectors(directorsName, page);

        // Verificación de resultados
        assertEquals(2, result.size());
        assertEquals("Series 1", result.get(0).getTitle());
        assertEquals("Series 2", result.get(1).getTitle());
    }
}

