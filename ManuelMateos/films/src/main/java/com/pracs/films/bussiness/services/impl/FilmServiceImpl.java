package com.pracs.films.bussiness.services.impl;

import com.pracs.films.bussiness.bo.FilmBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.services.FilmService;
import com.pracs.films.exceptions.DuplicatedIdException;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.repositories.jpa.FilmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the interface {@link FilmService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final ModelToBoConverter modelToBoConverter;

    private final BoToModelConverter boToModelConverter;

    private final FilmRepository filmRepository;

    private static final String errorProduction = "Production not found";

    private static final String errorService = "Error in service layer";

    @Override
    public FilmBO save(FilmBO filmBO) throws ServiceException {
        try {

            if (filmRepository.existsById(filmBO.getId())) {
                throw new DuplicatedIdException("Existing production");
            }

            return modelToBoConverter.filmModelToBo(filmRepository.save(boToModelConverter.filmBoToModel(filmBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public FilmBO update(FilmBO filmBO) throws ServiceException {
        try {
            FilmBO savedfilmBO = modelToBoConverter.filmModelToBo(filmRepository.findById(filmBO.getId())
                    .orElseThrow(() -> new EntityNotFoundException(errorProduction)));
            savedfilmBO.setTitle(filmBO.getTitle());
            savedfilmBO.setDebut(filmBO.getDebut());
            savedfilmBO.setDirector(filmBO.getDirector());
            savedfilmBO.setProducer(filmBO.getProducer());
            savedfilmBO.setActors(filmBO.getActors());
            return modelToBoConverter.filmModelToBo(filmRepository.save(boToModelConverter.filmBoToModel(savedfilmBO)));
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public FilmBO findById(long id) throws ServiceException {
        try {
            return modelToBoConverter.filmModelToBo(
                    filmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorProduction)));
        } catch (NestedRuntimeException e) {
            log.error("Error en la capa de servicio");
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<FilmBO> findAll() throws ServiceException {
        List<FilmBO> filmsBO = new ArrayList<>();

        try {
            filmsBO = filmRepository.findAll().stream().map(modelToBoConverter::filmModelToBo).toList();

            if (filmsBO.isEmpty()) {
                throw new EmptyException("No films");
            }

            return filmsBO;
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {

            if (!filmRepository.existsById(id)) {
                log.error("EntityNotFoundException");
                throw new EntityNotFoundException(errorProduction);
            }

            filmRepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }
}
