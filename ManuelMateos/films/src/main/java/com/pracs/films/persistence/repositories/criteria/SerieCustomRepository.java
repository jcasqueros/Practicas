package com.pracs.films.persistence.repositories.criteria;

import com.pracs.films.persistence.models.Actor;
import com.pracs.films.persistence.models.Director;
import com.pracs.films.persistence.models.Producer;
import com.pracs.films.persistence.models.Serie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Method for get all series paginated
     *
     * @return List of Serie
     */
    Page<Serie> findAllSerie(Pageable pageable);

    /**
     * Method for delete a serie by his id
     *
     * @param serie
     */

    /**
     * Method for get all series by differents atribbutes
     *
     * @param pageable
     * @param titles
     * @param ages
     * @param directors
     * @param producers
     * @param actors
     * @return
     */
    Page<Serie> findAllFilter(Pageable pageable, List<String> titles, List<Integer> ages, List<Director> directors,
            List<Producer> producers, List<Actor> actors);

    void deleteSerieById(Serie serie);
}
