package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.ActorRepository;
import com.santander.peliculacrud.model.api.DirectorRepository;
import com.santander.peliculacrud.model.api.SeriesRepository;
import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.bo.SeriesBO;
import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.model.entity.Series;
import com.santander.peliculacrud.service.SeriesServiceInterface;
import com.santander.peliculacrud.util.mapper.SeriesBOMapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Series service.
 */
@Service
public class SeriesService implements SeriesServiceInterface {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private final SeriesBOMapper seriesBOMapper = Mappers.getMapper(SeriesBOMapper.class);

    private static final Logger logger = LoggerFactory.getLogger(SeriesService.class);

    /**
     * Create series boolean.
     *
     * @param seriesBO
     *         the series out
     * @return the boolean
     */

    public SeriesBO createSeries(SeriesBO seriesBO) {

        SeriesBO seriesBOReturn = SeriesBO.builder().build();

        //Se comprueba que los directores y actores se encuentran en el repositorio
        Director director = directorRepository.findById(seriesBO.getDirector().getId()).orElse(null);
        List<Long> idActors = getIdActors(seriesBO.getActors());
        List<Actor> actors = actorRepository.findAllById(idActors);

        if (director != null && !actors.isEmpty()) {
            try {

                Series series = seriesBOMapper.boToEntity(seriesBO);

                Series seriesAux = seriesRepository.save(series);
                seriesBOReturn = seriesBOMapper.entityToBo(seriesAux);

            } catch (DataAccessException e) {
                logger.error("Failed to create actor", e);
                throw new RuntimeException("Failed to create actor: " + e.getMessage(), e);
            } catch (Exception e) {
                logger.error("Failed to create actor: {}", e.getMessage());
                throw new RuntimeException("Failed to create actor: ", e);
            }
        } else {
            logger.error("Failed to create actor invalid director or actor");
            throw new RuntimeException("Failed to create actor invalid director or actor");
        }

        return seriesBOReturn;
    }

    @Override
    public SeriesBO updateSeries(Long id, SeriesBO seriesBO) {
        SeriesBO seriesBOReturn = SeriesBO.builder().build();
        if (seriesRepository.existsById(id)) {

            Director director = directorRepository.findById(seriesBO.getDirector().getId()).orElse(null);
            List<Long> idActors = getIdActors(seriesBO.getActors());
            List<Actor> actors = actorRepository.findAllById(idActors);

            if (director != null && !actors.isEmpty()) {
                try {

                    Series seriesUpdate = seriesBOMapper.boToEntity(seriesBO);
                    seriesUpdate.setId(id);
                    Series series = seriesRepository.save(seriesUpdate);
                    seriesBOReturn = seriesBOMapper.entityToBo(series);

                } catch (DataAccessException e) {
                    logger.error("Failed to update actor", e);
                    throw new RuntimeException("Failed to update actor: " + e.getMessage(), e);
                } catch (Exception e) {
                    logger.error("Failed to update actor: {}", e.getMessage());
                    throw new RuntimeException("Failed to update actor: ", e);
                }
            } else {
                logger.error("Failed to update actor invalid director or actor");
                throw new RuntimeException("Failed to update actor invalid director or actor");
            }
        } else {
            seriesNotfound();
        }

        return seriesBOReturn;
    }

    @Override
    public List<SeriesBO> getAllSeries() {
        List<Series> seriess = seriesRepository.findAll();

        return seriesBOMapper.listEntityListBo(seriess);
    }

    @Override
    public boolean deleteSeries(Long id) {
        boolean deleted = false;
        if (id != null) {

            if (seriesRepository.existsById(id)) {

                try {
                    seriesRepository.deleteById(id);
                    deleted = true;
                } catch (Exception e) {
                    logger.error("Failed to delete series: {}", e.getMessage());
                    throw new RuntimeException("Failed to delete series: {}", e);

                }
            } else {
                seriesNotfound();
            }
        } else {
            throw new RuntimeException("Invalid series id: null");

        }

        return deleted;
    }

    @Override
    public SeriesBO getSeriesById(Long id) {
        Series series = seriesRepository.findById(id).orElse(null);

        return seriesBOMapper.entityToBo(series);
    }

    private List<Long> getIdActors(List<ActorBO> actorBOS) {
        return actorBOS.stream().map(ActorBO::getId).collect(Collectors.toList());
    }

    private void seriesNotfound() {
        logger.error("Series not found");
        throw new RuntimeException("Series not found");

    }

}

