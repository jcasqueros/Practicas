package com.pracs.films.bussiness.services.impl;

import com.pracs.films.bussiness.bo.SerieBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.services.SerieService;
import com.pracs.films.bussiness.services.WebClientService;
import com.pracs.films.configuration.ConstantMessages;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Actor;
import com.pracs.films.persistence.models.Director;
import com.pracs.films.persistence.models.Producer;
import com.pracs.films.persistence.models.Serie;
import com.pracs.films.persistence.repositories.criteria.impl.SerieRepositoryImpl;
import com.pracs.films.persistence.repositories.jpa.ActorRepository;
import com.pracs.films.persistence.repositories.jpa.DirectorRepository;
import com.pracs.films.persistence.repositories.jpa.ProducerRepository;
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

    private final ActorRepository actorRepository;

    private final DirectorRepository directorRepository;

    private final ProducerRepository producerRepository;

    private final SerieRepository serieRepository;

    private final SerieRepositoryImpl serieRepositoryCriteria;

    private final WebClientService webClientService;

    @Override
    public SerieBO save(SerieBO serieBO) throws ServiceException {
        try {
            serieBO.getActors().stream().forEach(a -> webClientService.existsActorJPA(a.getId()));
            webClientService.existsDirectorJPA(serieBO.getDirector().getId());
            webClientService.existsProducerJPA(serieBO.getProducer().getId());

            // Conversión de model a bo del resultado de crear un serie.
            return modelToBoConverter.serieModelToBo(serieRepository.save(boToModelConverter.serieBoToModel(serieBO)));
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public SerieBO update(SerieBO serieBO) throws ServiceException {
        try {
            // Búsqueda de un serie con el id introducido para comprobar que existe
            SerieBO savedSerieBO = modelToBoConverter.serieModelToBo(serieRepository.findById(serieBO.getId())
                    .orElseThrow(() -> new EntityNotFoundException(ConstantMessages.ERRORPRODUCTION)));

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
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public SerieBO findById(long id) throws ServiceException {
        try {
            //Comprobar si existe ya un serie registrado con el mismo id.
            return modelToBoConverter.serieModelToBo(serieRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(ConstantMessages.ERRORPRODUCTION)));
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

            if (seriePage.isEmpty()) {
                throw new EmptyException("No series");
            }

            List<SerieBO> serieBOList = seriePage.stream().map(modelToBoConverter::serieModelToBo).toList();

            return new PageImpl<>(serieBOList, seriePage.getPageable(), seriePage.getTotalPages());
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {
            //Comprobar si el serie no existe
            if (!serieRepository.existsById(id)) {
                log.error("EntityNotFoundException");
                throw new EntityNotFoundException(ConstantMessages.ERRORPRODUCTION);
            }

            serieRepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public SerieBO saveCriteria(SerieBO serieBO) throws ServiceException {
        try {
            serieBO.getActors().stream().forEach(a -> webClientService.existsActorCriteria(a.getId()));
            webClientService.existsDirectorCriteria(serieBO.getDirector().getId());
            webClientService.existsProducerCriteria(serieBO.getProducer().getId());

            // Conversión de model a bo del resultado de crear un serie.
            return modelToBoConverter.serieModelToBo(
                    serieRepositoryCriteria.saveSerie(boToModelConverter.serieBoToModel(serieBO)));
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public SerieBO updateCriteria(SerieBO serieBO) throws ServiceException {
        try {
            // Búsqueda de un serie con el id introducido para comprobar que existe
            SerieBO savedSerieBO = modelToBoConverter.serieModelToBo(
                    serieRepositoryCriteria.findSerieById(serieBO.getId())
                            .orElseThrow(() -> new EntityNotFoundException(ConstantMessages.ERRORPRODUCTION)));
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
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public SerieBO findByIdCriteria(long id) throws ServiceException {
        try {
            // Conversión de model a bo del resultado de buscar un serie por id.
            return modelToBoConverter.serieModelToBo(serieRepositoryCriteria.findSerieById(id)
                    .orElseThrow(() -> new EntityNotFoundException(ConstantMessages.ERRORPRODUCTION)));
        } catch (NestedRuntimeException e) {
            log.error("Error en la capa de servicio");
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public Page<SerieBO> findAllCriteria(Pageable pageable) throws ServiceException {
        try {
            //Búsqueda de los todos lo series, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
            Page<Serie> seriePage = serieRepositoryCriteria.findAllSerie(pageable);

            if (seriePage.isEmpty()) {
                throw new EmptyException("No series");
            }

            List<SerieBO> serieBOList = seriePage.stream().map(modelToBoConverter::serieModelToBo).toList();

            return new PageImpl<>(serieBOList, seriePage.getPageable(), seriePage.getTotalPages());
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public Page<SerieBO> findAllCriteriaFilter(Pageable pageable, List<String> titles, List<Integer> ages,
            List<String> directors, List<String> producers, List<String> actors) throws ServiceException {
        try {
            //Búsqueda de los todos las series, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
            List<Director> directorList = new ArrayList<>();
            List<Producer> producerList = new ArrayList<>();
            List<Actor> actorList = new ArrayList<>();

            if (directors != null && !directors.isEmpty()) {
                directors.forEach(d -> directorList.addAll(directorRepository.findByName(d)));
            }

            if (producers != null && !producers.isEmpty()) {
                producers.forEach(p -> producerList.addAll(producerRepository.findByName(p)));
            }

            if (actors != null && !actors.isEmpty()) {
                actors.forEach(a -> actorList.addAll(actorRepository.findByName(a)));
            }

            Page<Serie> seriePage = serieRepositoryCriteria.findAllFilter(pageable, titles, ages, directorList,
                    producerList, actorList);

            if (seriePage.isEmpty()) {
                throw new EmptyException("No films");
            }

            List<SerieBO> serieBOList = seriePage.stream().map(modelToBoConverter::serieModelToBo).toList();

            return new PageImpl<>(serieBOList, seriePage.getPageable(), seriePage.getTotalPages());
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteByIdCriteria(long id) throws ServiceException {
        try {
            //Comprobar si existe el serie con el id pasado
            if (serieRepositoryCriteria.findSerieById(id).isEmpty()) {
                log.error("EntityNotFoundException");
                throw new EntityNotFoundException(ConstantMessages.ERRORPRODUCTION);
            }

            serieRepositoryCriteria.deleteSerieById((serieRepositoryCriteria.findSerieById(id)).get());
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }
}
