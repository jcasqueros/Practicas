package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.bo.SeriesBO;
import com.santander.peliculacrud.model.dto.SeriesDTO;
import com.santander.peliculacrud.service.SeriesServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import com.santander.peliculacrud.util.mapper.SeriesDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    @InjectMocks
    private SeriesController seriesController;

    private SeriesDTO seriesDTO;
    private  SeriesBO seriesBO;


    @BeforeEach
    void setup(){

        ActorBO actorBO1 = ActorBO.builder().nation("ESP").name("Actor1").age(19).build();
        ActorBO actorBO2 = ActorBO.builder().nation("ESP").name("Actor2").age(19).build();
        DirectorBO directorBO = DirectorBO.builder().nation("ESP").name("Actor2").age(19).build();


        seriesDTO = SeriesDTO.builder().title("series").created(1930).idActor(List.of(1L, 2L)).idDirector(1L).build();
        seriesBO = SeriesBO.builder().title("series").created(1930).actors(List.of(actorBO1, actorBO2)).director(directorBO).build();

    }

    @Test
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

    @Test
    void testCreateSeries_InvalidRequest() throws Exception {
        // Arrange
        SeriesDTO seriesDTO = SeriesDTO.builder().title(null).created(-1).idActor(null).idDirector(null).build();

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(seriesDTO, "seriesDTO");


        // Act
        ResponseEntity<String> response = seriesController.createSeries(seriesDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Series not created", response.getBody());
    }

    @Test
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

    @Test
    void testUpdateSeries_InvalidRequest() throws Exception {
        // Arrange
        long id = 1L;
        SeriesDTO seriesDTO = SeriesDTO.builder().title(null).created(-1).idActor(null).idDirector(null).build();

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(seriesDTO, "seriesDTO");

        // Act
        ResponseEntity<String> response = seriesController.updateSeries(id, seriesDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Series not update", response.getBody());
    }

    @Test
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

    @Test
    void testGetAllSeriess() throws Exception {
        // Arrange
        List<SeriesBO> seriesBOS = Arrays.asList(SeriesBO.builder().build(), SeriesBO.builder().build());
        List<SeriesDTO> seriesDTOS = Arrays.asList(SeriesDTO.builder().build(), SeriesDTO.builder().build());
        when(seriesService.getAllSeries()).thenReturn(seriesBOS);
        when(seriesDTOMapper.bosToDtos(seriesBOS)).thenReturn(seriesDTOS);
        // Act
        List<SeriesDTO> response = seriesController.getAllSeriess();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    void testGetSeriesById() throws Exception {
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
    @Test
    void testGetSeriesByIdNull() throws Exception {
        // Arrange
        long id = 1L;



        when(seriesService.getSeriesById(id)).thenReturn(seriesBO);
        when(seriesDTOMapper.boToDTO(seriesBO)).thenReturn(null);

        // Act
        SeriesDTO response = seriesController.getSeriesById(id);

        // Assert
        assertNull(response);
    }

}
