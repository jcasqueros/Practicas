package com.pracs.films.persistence.repositories.criteria;

import com.pracs.films.persistence.models.Serie;

import java.util.List;
import java.util.Optional;

/**
 * Criteria Repository of the class {@link Serie}
 *
 * @author Manuel Mateos de Torres
 */
public interface SerieCustomRepository {

    /**
     * Method for create a serie
     *
     * @param serie
     * @return Optional Serie
     */
    Serie saveSerie(Serie serie);

    /**
     * Method for update a serie
     *
     * @param serie
     * @return Optional Serie
     */
    Serie updateSerie(Serie serie);

    /**
     * Method for find a serie by his id
     *
     * @param id
     * @return Optional Serie
     */
    Optional<Serie> findSerieById(long id);

    /**
     * Method for get all series
     *
     * @return List of Serie
     */
    List<Serie> findAllSerie();

    /**
     * Method for delete a serie by his id
     *
     * @param id
     */
    void deleteSerieById(long id);
}
