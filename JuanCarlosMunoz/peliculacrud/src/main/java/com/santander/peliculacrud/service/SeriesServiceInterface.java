package com.santander.peliculacrud.service;

import com.santander.peliculacrud.model.input.Series;
import com.santander.peliculacrud.model.output.SeriesModelController;
import com.santander.peliculacrud.model.output.SeriesModelService;
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
     * @param seriesModelController
     *         the series in
     * @return the boolean
     */
    boolean updateSeries(Long id, SeriesModelController seriesModelController);

    /**
     * Create series boolean.
     *
     * @param seriesModelController
     *         the series in
     * @return the boolean
     */
    boolean createSeries(SeriesModelController seriesModelController);

    /**
     * Gets all series.
     *
     * @return the all series
     */
    List<SeriesModelService> getAllSeries();

    /**
     * Series out series out.
     *
     * @param id
     *         the id
     * @return the series out
     */
    SeriesModelService seriesOut(Long id);

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
