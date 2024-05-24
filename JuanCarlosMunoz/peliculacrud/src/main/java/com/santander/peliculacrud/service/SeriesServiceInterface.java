package com.santander.peliculacrud.service;

import com.santander.peliculacrud.model.bo.SeriesBO;
import com.santander.peliculacrud.util.exception.GenericException;
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
     * @throws GenericException
     *         the generic exception
     */
    SeriesBO createSeries(SeriesBO seriesBO) throws GenericException;

    /**
     * Update series boolean.
     *
     * @param id
     *         the id
     * @param seriesBO
     *         the series in
     * @return the boolean
     * @throws GenericException
     *         the generic exception
     */
    SeriesBO updateSeries(Long id, SeriesBO seriesBO) throws GenericException;

    /**
     * Delete series boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     * @throws GenericException
     *         the generic exception
     */
    boolean deleteSeries(Long id) throws GenericException;

    /**
     * Gets series by id.
     *
     * @param id
     *         the id
     * @return the series by id
     */
    SeriesBO getSeriesById(Long id);

    /**
     * Gets all series.
     *
     * @param page
     *         the page
     * @return the all series
     */
    List<SeriesBO> getAllSeries(int page);

    /**
     * Gets series by title.
     *
     * @param title
     *         the title
     * @param page
     *         the page
     * @return the series by title
     */
    List<SeriesBO> getSeriesByTitle(String title, int page);

    /**
     * Gets series by created.
     *
     * @param created
     *         the created
     * @param page
     *         the page
     * @return the series by created
     */
    List<SeriesBO> getSeriesByCreated(int created, int page);

    /**
     * Gets series by actors.
     *
     * @param actorsName
     *         the actors name
     * @param page
     *         the page
     * @return the series by actors
     */
    List<SeriesBO> getSeriesByActors(List<String> actorsName, int page);

    /**
     * Gets series by directors.
     *
     * @param directorsName
     *         the directors name
     * @param page
     *         the page
     * @return the series by directors
     */
    List<SeriesBO> getSeriesByDirectors(List<String> directorsName, int page);

}
