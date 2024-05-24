package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.ActorRepository;
import com.santander.peliculacrud.model.api.DirectorRepository;
import com.santander.peliculacrud.model.api.SeriesRepository;
import com.santander.peliculacrud.model.bo.SeriesBO;
import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.model.entity.Series;
import com.santander.peliculacrud.service.SeriesServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import com.santander.peliculacrud.util.exception.GenericException;
import com.santander.peliculacrud.util.mapper.bo.SeriesBOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The type Series service.
 */
@Service
public class SeriesService implements SeriesServiceInterface {

    private final SeriesRepository seriesRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final SeriesBOMapper seriesBOMapper;
    private final CommonOperation commonOperation;

    private static final Logger logger = LoggerFactory.getLogger(SeriesService.class);

    /**
     * Instantiates a new Series service.
     *
     * @param actorRepository
     *         the actor repository
     * @param directorRepository
     *         the director repository
     * @param commonOperation
     *         the common operation
     * @param seriesBOMapper
     *         the series bo mapper
     * @param seriesRepository
     *         the series repository
     */
    @Autowired
    public SeriesService(ActorRepository actorRepository, DirectorRepository directorRepository,
            CommonOperation commonOperation, SeriesBOMapper seriesBOMapper, SeriesRepository seriesRepository) {
        this.actorRepository = actorRepository;
        this.commonOperation = commonOperation;
        this.directorRepository = directorRepository;
        this.seriesBOMapper = seriesBOMapper;
        this.seriesRepository = seriesRepository;
    }

    /**
     * Create series boolean.
     *
     * @param seriesBO
     *         the series out
     * @return the boolean
     */

    public SeriesBO createSeries(SeriesBO seriesBO) throws GenericException {

        SeriesBO seriesBOReturn;

        //Se comprueba que los directores y actores se encuentran en el repositorio
        Director director = directorRepository.findById(seriesBO.getDirector().getId()).orElse(null);
        List<Long> idActors = commonOperation.getIdObject(Collections.singletonList(seriesBO.getActors()));
        List<Actor> actors = actorRepository.findAllById(idActors);

        if (director != null && !actors.isEmpty()) {
            try {

                Series series = seriesBOMapper.boToEntity(seriesBO);

                Series seriesAux = seriesRepository.save(series);
                seriesBOReturn = seriesBOMapper.entityToBo(seriesAux);

            } catch (Exception e) {
                throw new GenericException("Failed to create actor: ", e);
            }
        } else {
            throw new GenericException("Failed to create actor invalid director or actor");
        }

        return seriesBOReturn;
    }

    @Override
    public SeriesBO updateSeries(Long id, SeriesBO seriesBO) throws GenericException {
        SeriesBO seriesBOReturn = SeriesBO.builder().build();
        if (seriesRepository.existsById(id)) {

            Director director = directorRepository.findById(seriesBO.getDirector().getId()).orElse(null);
            List<Long> idActors = commonOperation.getIdObject(Collections.singletonList(seriesBO.getActors()));
            List<Actor> actors = actorRepository.findAllById(idActors);

            if (director != null && !actors.isEmpty()) {

                Series seriesUpdate = seriesBOMapper.boToEntity(seriesBO);
                seriesUpdate.setId(id);
                Series series = seriesRepository.save(seriesUpdate);
                seriesBOReturn = seriesBOMapper.entityToBo(series);

            } else {
                logger.error("Failed to update actor invalid director or actor");
                throw new GenericException("Failed to update actor invalid director or actor");
            }
        } else {
            seriesNotfound();
        }

        return seriesBOReturn;
    }

    @Override
    public boolean deleteSeries(Long id) throws GenericException {
        boolean deleted = false;
        if (id != null) {

            if (seriesRepository.existsById(id)) {

                seriesRepository.deleteById(id);
                deleted = true;

            } else {
                seriesNotfound();
            }
        } else {
            throw new GenericException("Invalid series id: null");

        }

        return deleted;
    }

    @Override
    public SeriesBO getSeriesById(Long id) {
        Series series = seriesRepository.findById(id).orElse(null);

        return seriesBOMapper.entityToBo(series);
    }

    @Override
    public List<SeriesBO> getAllSeries(int page) {
        Pageable pageable = PageRequest.of(page, 5);

        Page<Series> seriesPage = seriesRepository.findAll(pageable);
        List<SeriesBO> series = seriesBOMapper.listEntityListBo(seriesPage);
        return series.stream().sorted(Comparator.comparing(SeriesBO::getTitle)).toList();
    }

    @Override
    public List<SeriesBO> getSeriesByTitle(String title, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Series> seriesPage = seriesRepository.findAllByTitle(title, pageable);
        List<SeriesBO> series = seriesBOMapper.listEntityListBo(seriesPage);

        return series.stream().sorted(Comparator.comparing(SeriesBO::getTitle)).toList();
    }

    @Override
    public List<SeriesBO> getSeriesByCreated(int created, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Series> seriesPage = seriesRepository.findAllByCreated(created, pageable);
        List<SeriesBO> series = seriesBOMapper.listEntityListBo(seriesPage);

        return series.stream().sorted(Comparator.comparingInt(SeriesBO::getCreated)).toList();
    }

    @Override
    public List<SeriesBO> getSeriesByActors(List<String> actorsName, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        List<Actor> actors = actorRepository.findByNameIn(actorsName);
        Page<Series> seriesPage = seriesRepository.findAllByActorsIn(Collections.singleton(actors), pageable);
        List<SeriesBO> series = seriesBOMapper.listEntityListBo(seriesPage);

        return series.stream().sorted(Comparator.comparingInt(SeriesBO::getCreated)).toList();
    }

    @Override
    public List<SeriesBO> getSeriesByDirectors(List<String> directorsName, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        List<Director> directors = directorRepository.findByNameIn(directorsName);
        Page<Series> seriesPage = seriesRepository.findAllByDirectorIn(directors, pageable);
        List<SeriesBO> series = seriesBOMapper.listEntityListBo(seriesPage);

        return series.stream().sorted(Comparator.comparingInt(SeriesBO::getCreated)).toList();
    }

    private void seriesNotfound() throws GenericException {
        logger.error("Series not found");
        throw new GenericException("Series not found");

    }

}

