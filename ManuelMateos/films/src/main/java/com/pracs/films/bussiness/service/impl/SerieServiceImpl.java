package com.pracs.films.bussiness.service.impl;

import com.pracs.films.bussiness.bo.SerieBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.service.SerieService;
import com.pracs.films.exceptions.DuplicatedIdException;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.repositories.jpa.SerieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the interface {@link SerieService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SerieServiceImpl implements SerieService {

    private final ModelToBoConverter modelToBoConverter;

    private final BoToModelConverter boToModelConverter;

    private final SerieRepository serieRepository;

    private static final String errorProduction = "Production not found";

    private static final String errorService = "Error in service layer";

    @Override
    public SerieBO save(SerieBO serieBO) throws ServiceException {
        try {

            if (serieRepository.existsById(serieBO.getId())) {
                throw new DuplicatedIdException("Existing production");
            }

            return modelToBoConverter.serieModelToBo(serieRepository.save(boToModelConverter.serieBoToModel(serieBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public SerieBO update(SerieBO serieBO) throws ServiceException {
        try {
            SerieBO savedSerieBO = modelToBoConverter.serieModelToBo(serieRepository.findById(serieBO.getId())
                    .orElseThrow(() -> new EntityNotFoundException(errorProduction)));
            savedSerieBO.setTitle(serieBO.getTitle());
            savedSerieBO.setDebut(serieBO.getDebut());
            savedSerieBO.setDirector(serieBO.getDirector());
            savedSerieBO.setProducer(serieBO.getProducer());
            savedSerieBO.setActors(serieBO.getActors());
            return modelToBoConverter.serieModelToBo(
                    serieRepository.save(boToModelConverter.serieBoToModel(savedSerieBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public SerieBO findById(long id) throws ServiceException {
        try {
            return modelToBoConverter.serieModelToBo(
                    serieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorProduction)));
        } catch (NestedRuntimeException e) {
            log.error("Error en la capa de servicio");
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<SerieBO> findAll() throws ServiceException {
        List<SerieBO> seriesBO = new ArrayList<>();

        try {
            seriesBO = serieRepository.findAll().stream().map(modelToBoConverter::serieModelToBo).toList();

            if (seriesBO.isEmpty()) {
                throw new EmptyException("No films");
            }

            return seriesBO;
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {

            if (!serieRepository.existsById(id)) {
                log.error("EntityNotFoundException");
                throw new EntityNotFoundException(errorProduction);
            }

            serieRepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }
}
