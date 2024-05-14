//package com.santander.peliculacrud.service.impl;
//
//import com.santander.peliculacrud.model.api.ActorRepository;
//import com.santander.peliculacrud.model.api.DirectorRepository;
//import com.santander.peliculacrud.model.api.SeriesRepository;
//import com.santander.peliculacrud.model.bo.SeriesBO;
//import com.santander.peliculacrud.model.entity.Actor;
//import com.santander.peliculacrud.model.entity.Director;
//import com.santander.peliculacrud.model.entity.Series;
//import com.santander.peliculacrud.model.dto.SeriesDTO;
//
//import com.santander.peliculacrud.service.SeriesServiceInterface;
//import com.santander.peliculacrud.util.CommonOperation;
//import com.santander.peliculacrud.util.TransformObjects;
//import jakarta.validation.Valid;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.validation.BeanPropertyBindingResult;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.Validator;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * The type Series service.
// */
//@Service
//public class SeriesService implements SeriesServiceInterface {
//
//    @Autowired
//    private SeriesRepository seriesRepository;
//
//    @Autowired
//    private ActorRepository actorRepository;
//
//    @Autowired
//    private DirectorRepository directorRepository;
//
//    @Autowired
//    private TransformObjects transformObjects;
//
//    @Autowired
//    private Validator validator;
//
//    private static final Logger logger = LoggerFactory.getLogger(SeriesService.class);
//
//    @Autowired
//    private CommonOperation commonOperation;
//
//    /**
//     * Create series boolean.
//     *
//     * @param seriesDTO
//     *         the series out
//     * @return the boolean
//     */
//    public boolean createSeries(@Valid SeriesDTO seriesDTO) {
//
//        boolean create = false;
//
//        BindingResult result = new BeanPropertyBindingResult(seriesDTO, "SeriesDTO");
//        validator.validate(seriesDTO, result);
//
//        if (result.hasErrors()) {
//            commonOperation.showErrorModel(logger, result);
//            throw new RuntimeException("Invalid actor data: " + result.getAllErrors());
//        } else {
//
//            Director director = directorRepository.findById(seriesDTO.getIdDirector()).orElse(null);
//            List<Actor> actors = actorRepository.findAllById(seriesDTO.getIdActor());
//
//            if (director != null && !actors.isEmpty()) {
//
//                Series series = transformObjects.seriesOutToSeries(seriesDTO);
//
//                try {
//                    seriesRepository.save(series);
//                    create = true;
//
//                } catch (Exception e) {
//                    logger.error("Failed to create actor: {}", e.getMessage());
//                    throw new RuntimeException("Failed to create actor: ", e);
//                }
//            }
//        }
//
//        return create;
//    }
//
//    /**
//     * Update series boolean.
//     *
//     * @param id
//     *         the id
//     * @param seriesDTO
//     *         the series out
//     * @return the boolean
//     */
//    public boolean updateSeries(Long id, @Valid SeriesDTO seriesDTO) {
//
//        boolean update = false;
//        if (seriesRepository.existsById(id)) {
//
//            BindingResult result = new BeanPropertyBindingResult(seriesDTO, "seriesDTO");
//            validator.validate(seriesDTO, result);
//
//            if (result.hasErrors()) {
//                commonOperation.showErrorModel(logger, result);
//                throw new RuntimeException("Invalid actor data: " + result.getAllErrors());
//            } else {
//
//                try {
//                    Series seriesUpdate = transformObjects.seriesOutToSeries(seriesDTO);
//                    if (seriesUpdate.getDirector() != null && !seriesUpdate.getActors().isEmpty()) {
//
//                        seriesUpdate.setId(id);
//                        seriesRepository.save(seriesUpdate);
//                        update = seriesRepository.existsById(id);
//                    }
//                } catch (Exception e) {
//
//                    logger.error("Failed to update series: {}", e.getMessage());
//                    throw new RuntimeException("Failed to update series: ", e);
//
//                }
//            }
//
//        } else {
//            seriesNotfound();
//            throw new RuntimeException("Actor not found");
//        }
//        return update;
//    }
//
//    /**
//     * Gets all series.
//     *
//     * @return the all series
//     */
//    public List<SeriesBO> getAllSeries() {
//
//        List<Series> seriess = seriesRepository.findAll();
//        List<SeriesBO> seriesBOS = new ArrayList<>();
//        for (Series series : seriess) {
//
//            SeriesBO seriesBO = transformObjects.seriesToSeriesOut(series);
//            seriesBOS.add(seriesBO);
//
//        }
//
//        return seriesBOS;
//    }
//
//    /**
//     * Get a series show.
//     *
//     * @param id
//     *         the id
//     * @return the series show
//     */
//    public SeriesBO seriesOut(Long id) {
//
//        Series series = seriesRepository.findById(id).orElse(null);
//        SeriesBO seriesBO = null;
//        if (series != null) {
//            seriesBO = transformObjects.seriesToSeriesOut(series);
//        }
//
//        return seriesBO;
//    }
//
//    /**
//     * Delete series boolean.
//     *
//     * @param id
//     *         the id
//     * @return the boolean
//     */
//    public boolean deleteSeries(Long id) {
//        boolean delete = false;
//
//        if (id == null) {
//            throw new RuntimeException("Invalid series id: null");
//        }
//
//        if (!seriesRepository.existsById(id)) {
//            seriesNotfound();
//        }
//
//        try {
//            seriesRepository.deleteById(id);
//            delete = !seriesRepository.existsById(id);
//        } catch (Exception e) {
//            logger.error("Failed to delete series: {}", e.getMessage());
//            throw new RuntimeException("Failed to delete series: {}", e);
//        }
//
//        return delete;
//    }
//
//    /**
//     * Gets last series.
//     *
//     * @return the last series
//     */
//    public Series getLastSeries() {
//
//        List<Series> seriess = seriesRepository.findLastSeries();
//        long idLastSeries = seriess.get(0).getId();
//        return seriesRepository.findById(idLastSeries).orElse(null);
//    }
//
//    /**
//     * Gets series by id.
//     *
//     * @param id
//     *         the id
//     * @return the series by id
//     */
//    public Series getSeriesById(Long id) {
//        return seriesRepository.findById(id).orElse(null);
//
//    }
//
//    /**
//     * Exists series by id boolean.
//     *
//     * @param id
//     *         the id
//     * @return the boolean
//     */
//    public boolean existsSeriesById(Long id) {
//        return seriesRepository.existsById(id);
//    }
//
//    /**
//     * Seriess list size int.
//     *
//     * @return the int
//     */
//    public int getListSize() {
//        return seriesRepository.findAll().size();
//    }
//
//    private void seriesNotfound() {
//        logger.error("Series not found");
//        throw new RuntimeException("Series not found");
//
//    }
//
//}
