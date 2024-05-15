package com.santander.peliculacrud.service;

import com.santander.peliculacrud.model.bo.SeriesBO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The interface Series service interface.
 */
@Service
public interface SeriesServiceInterface {

    /**
     * Create series boolean.
     *
     * @param seriesBO
     *         the series in
     * @return the boolean
     */
    SeriesBO createSeries(SeriesBO seriesBO);

    /**
     * Update series boolean.
     *
     * @param id
     *         the id
     * @param seriesBO
     *         the series in
     * @return the boolean
     */
    SeriesBO updateSeries(Long id, SeriesBO seriesBO);

    /**
     * Gets all series.
     *
     * @return the all series
     */
    List<SeriesBO> getAllSeries();

    /**
     * Delete series boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     */
    boolean deleteSeries(Long id);

    /**
     * Gets series by id.
     *
     * @param id
     *         the id
     * @return the series by id
     */
    SeriesBO getSeriesById(Long id);

}
