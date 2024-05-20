package com.pracs.films.bussiness.services.impl;

import com.pracs.films.bussiness.bo.FilmBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.services.FilmService;
import com.pracs.films.bussiness.services.WebClientService;
import com.pracs.films.configuration.ConstantMessages;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Actor;
import com.pracs.films.persistence.models.Director;
import com.pracs.films.persistence.models.Film;
import com.pracs.films.persistence.models.Producer;
import com.pracs.films.persistence.repositories.criteria.impl.FilmRepositoryImpl;
import com.pracs.films.persistence.repositories.jpa.ActorRepository;
import com.pracs.films.persistence.repositories.jpa.DirectorRepository;
import com.pracs.films.persistence.repositories.jpa.FilmRepository;
import com.pracs.films.persistence.repositories.jpa.ProducerRepository;
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
 * Implementation of the interface {@link FilmService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final ModelToBoConverter modelToBoConverter;

    private final BoToModelConverter boToModelConverter;

    private final DirectorRepository directorRepository;

    private final ProducerRepository producerRepository;

    private final ActorRepository actorRepository;

    private final FilmRepository filmRepository;

    private final FilmRepositoryImpl filmRepositoryCriteria;

    private final WebClientService webClientService;

    @Override
    public FilmBO save(FilmBO filmBO, String port) throws ServiceException {
        try {
            filmBO.getActors().forEach(a -> webClientService.existsActorJPA(a.getId(), port));
            webClientService.existsDirectorJPA(filmBO.getDirector().getId(), port);
            webClientService.existsProducerJPA(filmBO.getProducer().getId(), port);

            // Conversión de model a bo del resultado de crear un pelicula.
            return modelToBoConverter.filmModelToBo(filmRepository.save(boToModelConverter.filmBoToModel(filmBO)));
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public FilmBO update(FilmBO filmBO) throws ServiceException {
        try {
            // Búsqueda de un pelicula con el id introducido para comprobar que existe
            FilmBO savedfilmBO = modelToBoConverter.filmModelToBo(filmRepository.findById(filmBO.getId())
                    .orElseThrow(() -> new EntityNotFoundException(ConstantMessages.ERRORPRODUCTION)));

            //Actualización con los campos introducidos
            savedfilmBO.setTitle(filmBO.getTitle());
            savedfilmBO.setDebut(filmBO.getDebut());
            savedfilmBO.setDirector(filmBO.getDirector());
            savedfilmBO.setProducer(filmBO.getProducer());
            savedfilmBO.setActors(filmBO.getActors());

            // Conversion de model a bo del resultado de guardar un pelicula
            return modelToBoConverter.filmModelToBo(filmRepository.save(boToModelConverter.filmBoToModel(savedfilmBO)));
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public FilmBO findById(long id) throws ServiceException {
        try {
            //Comprobar si existe ya un pelicula registrado con el mismo id.
            return modelToBoConverter.filmModelToBo(filmRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(ConstantMessages.ERRORPRODUCTION)));
        } catch (NestedRuntimeException e) {
            log.error("Error en la capa de servicio");
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public Page<FilmBO> findAll(Pageable pageable) throws ServiceException {
        try {
            //Búsqueda de los todos las peliculas, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
            Page<Film> filmPage = filmRepository.findAll(pageable);

            if (filmPage.isEmpty()) {
                throw new EmptyException(ConstantMessages.NOFILMS);
            }

            List<FilmBO> filmBOList = filmPage.stream().map(modelToBoConverter::filmModelToBo).toList();

            return new PageImpl<>(filmBOList, filmPage.getPageable(), filmPage.getTotalPages());
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {
            //Comprobar si el pelicula no existe
            if (!filmRepository.existsById(id)) {
                log.error("EntityNotFoundException");
                throw new EntityNotFoundException(ConstantMessages.ERRORPRODUCTION);
            }

            filmRepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public FilmBO saveCriteria(FilmBO filmBO, String port) throws ServiceException {
        try {
            filmBO.getActors().forEach(a -> webClientService.existsActorCriteria(a.getId(), port));
            webClientService.existsDirectorCriteria(filmBO.getDirector().getId(), port);
            webClientService.existsProducerCriteria(filmBO.getProducer().getId(), port);

            // Conversión de model a bo del resultado de crear un pelicula.
            return modelToBoConverter.filmModelToBo(
                    filmRepositoryCriteria.saveFilm(boToModelConverter.filmBoToModel(filmBO)));
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public FilmBO updateCriteria(FilmBO filmBO) throws ServiceException {
        try {
            // Búsqueda de un pelicula con el id introducido para comprobar que existe
            FilmBO savedfilmBO = modelToBoConverter.filmModelToBo(filmRepositoryCriteria.findFilmById(filmBO.getId())
                    .orElseThrow(() -> new EntityNotFoundException(ConstantMessages.ERRORPRODUCTION)));

            //Actualización con los campos introducidos
            savedfilmBO.setTitle(filmBO.getTitle());
            savedfilmBO.setDebut(filmBO.getDebut());
            savedfilmBO.setDirector(filmBO.getDirector());
            savedfilmBO.setProducer(filmBO.getProducer());
            savedfilmBO.setActors(filmBO.getActors());

            // Conversion de model a bo del resultado de guardar un pelicula
            return modelToBoConverter.filmModelToBo(
                    filmRepositoryCriteria.updateFilm(boToModelConverter.filmBoToModel(savedfilmBO)));
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public FilmBO findByIdCriteria(long id) throws ServiceException {
        try {
            // Conversión de model a bo del resultado de buscar un pelicula por id.
            return modelToBoConverter.filmModelToBo(filmRepositoryCriteria.findFilmById(id)
                    .orElseThrow(() -> new EntityNotFoundException(ConstantMessages.ERRORPRODUCTION)));
        } catch (NestedRuntimeException e) {
            log.error("Error en la capa de servicio");
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public Page<FilmBO> findAllCriteria(Pageable pageable) throws ServiceException {
        try {
            //Búsqueda de los todos lo peliculas, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
            Page<Film> filmPage = filmRepositoryCriteria.findAllFilm(pageable);

            if (filmPage.isEmpty()) {
                throw new EmptyException(ConstantMessages.NOFILMS);
            }

            List<FilmBO> filmBOList = filmPage.stream().map(modelToBoConverter::filmModelToBo).toList();

            return new PageImpl<>(filmBOList, filmPage.getPageable(), filmPage.getTotalPages());
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public Page<FilmBO> findAllCriteriaFilter(Pageable pageable, List<String> titles, List<Integer> ages,
            List<String> directors, List<String> producers, List<String> actors) throws ServiceException {
        try {
            //Búsqueda de los todos las peliculas, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
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

            Page<Film> filmPage = filmRepositoryCriteria.findAllFilter(pageable, titles, ages, directorList,
                    producerList, actorList);

            if (filmPage.isEmpty()) {
                throw new EmptyException(ConstantMessages.NOFILMS);
            }

            List<FilmBO> filmsBOList = filmPage.stream().map(modelToBoConverter::filmModelToBo).toList();

            return new PageImpl<>(filmsBOList, filmPage.getPageable(), filmPage.getTotalPages());
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteByIdCriteria(long id) throws ServiceException {
        try {
            //Comprobar si existe el pelicula con el id pasado
            if (filmRepositoryCriteria.findFilmById(id).isEmpty()) {
                log.error("EntityNotFoundException");
                throw new EntityNotFoundException(ConstantMessages.ERRORPRODUCTION);
            }

            filmRepositoryCriteria.deleteFilmById(filmRepositoryCriteria.findFilmById(id).get());
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }
}
