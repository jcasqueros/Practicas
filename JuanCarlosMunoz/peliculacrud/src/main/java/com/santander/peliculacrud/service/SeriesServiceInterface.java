package com.santander.peliculacrud.service;

import com.santander.peliculacrud.model.bo.SeriesBO;
import com.santander.peliculacrud.model.entity.Series;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The interface Series service interface.
 */
@Service
public interface SeriesServiceInterface {

    /**
     * Update series boolean.
     *
     * @param id
     *         the id
     * @param seriesBO
     *         the series in
     * @return the boolean
     */
    boolean updateSeries(Long id, SeriesBO seriesBO);

    /**
     * Create series boolean.
     *
     * @param seriesBO
     *         the series in
     * @return the boolean
     */
    boolean createSeries(SeriesBO seriesBO);

    /**
     * Gets all series.
     *
     * @return the all series
     */
    List<SeriesBO> getAllSeries();

    /**
     * Series out series out.
     *
     * @param id
     *         the id
     * @return the series out
     */
    SeriesBO seriesOut(Long id);

    /**
     * Delete series boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     */
    boolean deleteSeries(Long id);

    /**
     * Gets las series.
     *
     * @return the las series
     */
    Series getLastSeries();

    /**
     * Get list size int.
     *
     * @return the int
     */
    int getListSize();
    /**
     * Gets film by id.
     *
     * @param id
     *         the id
     * @return the film by id
     */
    Series getSeriesById(Long id);

    /**
     * Exists film by id boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     */
    boolean existsSeriesById(Long id);

}
