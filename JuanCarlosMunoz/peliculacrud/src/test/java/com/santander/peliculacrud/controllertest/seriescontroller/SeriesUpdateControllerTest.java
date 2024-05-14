/*
package com.santander.peliculacrud.controllertest.seriescontroller;

import com.santander.peliculacrud.model.dto.SeriesDTO;

import com.santander.peliculacrud.service.impl.SeriesService;
import com.santander.peliculacrud.web.SeriesController;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SeriesControllerTest {

    @Mock
    private SeriesService seriesService;

    @Mock
    private Logger logger;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private SeriesController seriesController;

    @Test
    void updateSeriesOut_validInput_OK() throws Exception {
        // Given
        Long id = 1L;
        SeriesDTO updatedSeriesDTO = SeriesDTO.builder().title("Updated Series").build();
        when(seriesService.updateSeries(id, updatedSeriesDTO)).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(false);

        // When
        ResponseEntity<String> response = seriesController.updateSeriesOut(id, updatedSeriesDTO, bindingResult);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Series updated successfully", response.getBody());
    }

    @Test
    void updateSeriesOut_invalidInput_BadRequest() throws Exception {
        // Given
        Long id = 1L;
        SeriesDTO updatedSeriesDTO = SeriesDTO.builder().title("").build();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getErrorCount()).thenReturn(1);
        when(bindingResult.getAllErrors()).thenReturn(List.of(new ObjectError("title", "Title is required")));

        // When
        ResponseEntity<String> response = seriesController.updateSeriesOut(id, updatedSeriesDTO, bindingResult);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Series not updated", response.getBody());
    }

    @Test
    void updateSeriesOut_serviceThrowsException_InternalServerError() throws Exception {
        // Given
        Long id = 1L;
        SeriesDTO updatedSeriesDTO = SeriesDTO.builder().title("Updated Series").build();
        when(seriesService.updateSeries(id, updatedSeriesDTO)).thenThrow(new RuntimeException("Error updating series"));
        when(bindingResult.hasErrors()).thenReturn(false);

        // When
        ResponseEntity<String> response = seriesController.updateSeriesOut(id, updatedSeriesDTO, bindingResult);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error updating series", response.getBody());
    }
}
*/
