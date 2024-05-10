package com.pracs.films.bussiness.services.impl;

import com.pracs.films.bussiness.bo.SerieBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.services.SerieService;
import com.pracs.films.exceptions.DuplicatedIdException;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Serie;
import com.pracs.films.persistence.repositories.criteria.impl.SerieRepositoryImpl;
import com.pracs.films.persistence.repositories.jpa.SerieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    private final SerieRepositoryImpl serieRepositoryCriteria;

    private static final String errorProduction = "Production not found";

    private static final String errorService = "Error in service layer";

    @Override
    public SerieBO save(SerieBO serieBO) throws ServiceException {
        try {
            //Comprobar si existe ya un serie registrado con el mismo id.
            if (serieRepository.existsById(serieBO.getId())) {
                throw new DuplicatedIdException("Existing production");
            }

            // Conversión de model a bo del resultado de crear un serie.
            return modelToBoConverter.serieModelToBo(serieRepository.save(boToModelConverter.serieBoToModel(serieBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public SerieBO update(SerieBO serieBO) throws ServiceException {
        try {
            // Búsqueda de un serie con el id introducido para comprobar que existe
            SerieBO savedSerieBO = modelToBoConverter.serieModelToBo(serieRepository.findById(serieBO.getId())
                    .orElseThrow(() -> new EntityNotFoundException(errorProduction)));

            //Actualización con los campos introducidos
            savedSerieBO.setTitle(serieBO.getTitle());
            savedSerieBO.setDebut(serieBO.getDebut());
            savedSerieBO.setDirector(serieBO.getDirector());
            savedSerieBO.setProducer(serieBO.getProducer());
            savedSerieBO.setActors(serieBO.getActors());

            // Conversion de model a bo del resultado de guardar un serie
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
            //Comprobar si existe ya un serie registrado con el mismo id.
            return modelToBoConverter.serieModelToBo(
                    serieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorProduction)));
        } catch (NestedRuntimeException e) {
            log.error("Error en la capa de servicio");
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public Page<SerieBO> findAll(Pageable pageable) throws ServiceException {
        try {
            //Búsqueda de los todos lo series, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
            Page<Serie> seriePage = serieRepository.findAll(pageable);
            List<SerieBO> serieBOList = seriePage.stream().map(modelToBoConverter::serieModelToBo).toList();

            Page<SerieBO> directorBOPage = new PageImpl<>(serieBOList, seriePage.getPageable(),
                    seriePage.getTotalPages());

            if (serieBOList.isEmpty()) {
                throw new EmptyException("No series");
            }

            return directorBOPage;
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {
            //Comprobar si el serie no existe
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

    @Override
    public SerieBO saveCriteria(SerieBO serieBO) throws ServiceException {
        try {
            //Comprobar si existe ya un serie registrado con el mismo id.
            if (!serieRepositoryCriteria.findSerieById(serieBO.getId()).isEmpty()) {
                throw new DuplicatedIdException("Existing production");
            }

            // Conversión de model a bo del resultado de crear un serie.
            return modelToBoConverter.serieModelToBo(
                    serieRepositoryCriteria.saveSerie(boToModelConverter.serieBoToModel(serieBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public SerieBO updateCriteria(SerieBO serieBO) throws ServiceException {
        try {
            // Búsqueda de un serie con el id introducido para comprobar que existe
            SerieBO savedSerieBO = modelToBoConverter.serieModelToBo(
                    serieRepositoryCriteria.findSerieById(serieBO.getId())
                            .orElseThrow(() -> new EntityNotFoundException(errorProduction)));
            //Actualización con los campos introducidos
            savedSerieBO.setTitle(serieBO.getTitle());
            savedSerieBO.setDebut(serieBO.getDebut());
            savedSerieBO.setDirector(serieBO.getDirector());
            savedSerieBO.setProducer(serieBO.getProducer());
            savedSerieBO.setActors(serieBO.getActors());

            // Conversion de model a bo del resultado de guardar un serie
            return modelToBoConverter.serieModelToBo(
                    serieRepositoryCriteria.updateSerie(boToModelConverter.serieBoToModel(savedSerieBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public SerieBO findByIdCriteria(long id) throws ServiceException {
        try {
            // Conversión de model a bo del resultado de buscar un serie por id.
            return modelToBoConverter.serieModelToBo(serieRepositoryCriteria.findSerieById(id)
                    .orElseThrow(() -> new EntityNotFoundException(errorProduction)));
        } catch (NestedRuntimeException e) {
            log.error("Error en la capa de servicio");
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<SerieBO> findAllCriteria() throws ServiceException {
        List<SerieBO> seriesBO = new ArrayList<>();

        try {
            //Búsqueda de los todos lo series, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
            seriesBO = serieRepositoryCriteria.findAllSerie().stream().map(modelToBoConverter::serieModelToBo).toList();

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
    public void deleteByIdCriteria(long id) throws ServiceException {
        try {
            //Comprobar si existe el serie con el id pasado
            if (serieRepositoryCriteria.findSerieById(id).isEmpty()) {
                log.error("EntityNotFoundException");
                throw new EntityNotFoundException(errorProduction);
            }

            serieRepositoryCriteria.deleteSerieById((serieRepositoryCriteria.findSerieById(id)).get());
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }
}
