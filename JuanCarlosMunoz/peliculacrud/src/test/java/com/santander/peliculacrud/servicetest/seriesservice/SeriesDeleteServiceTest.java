/*
package com.santander.peliculacrud.servicetest.seriesservice;

import com.santander.peliculacrud.service.SeriesServiceInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SeriesDeleteServiceTest {
    @Autowired
    private SeriesServiceInterface seriesService;

    @Test
    @DisplayName("Delete a series with valid id")
    public void testSeriesDeleteValidId() {
        Long seriesId = seriesService.getLastSeries().getId();

        try {
            assertTrue(seriesService.deleteSeries(seriesId), "Series should be deleted");
            assertFalse(seriesService.existsSeriesById(seriesId), "Series should not exist");
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    @DisplayName("Delete a series with null id")
    public void testSeriesDeleteNullId() {
        try {
            seriesService.deleteSeries(null);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid series id:"));
        }
    }

    @Test
    @DisplayName("Delete a series with invalid id")
    public void testSeriesDeleteInvalidId() {
        try {
            seriesService.deleteSeries(-1L);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Series not found"));
        }
    }

    @Test
    @DisplayName("Delete a series that does not exist")
    public void testSeriesDeleteNonExistingSeries() {
        try {
            seriesService.deleteSeries(999L);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Series not found"));
        }
    }



}
*/
